package de.htwg.draughts.controller

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.google.inject.assistedinject.Assisted
import de.htwg.draughts.controller.StopGameChecker.CheckPlayer
import de.htwg.draughts.model._
import javax.inject.Inject

import scala.collection.mutable
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class MoveController @Inject()(val board: Board, @Assisted("blackPlayer") val blackPlayer: Player, @Assisted("whitePlayer") val whitePlayer: Player) extends GameController {

    val system: ActorSystem = ActorSystem("MySystem")
    val gameStopActor: ActorRef = system.actorOf(Props[StopGameChecker], name = "gameStopActor")
    var colourTurn: Colour.Value = Colour.BLACK
    var multipleMove: mutable.Map[Field, List[Field]] = mutable.Map()

    override def toggleHighlightField(col: Int, row: Int): Boolean = {
        board.getField(col)(row).get.highlighted = !board.getField(col)(row).get.highlighted
        board.getField(col)(row).get.highlighted
    }

    override def checkIfPieceIsValid(field: Field, player: Player): Boolean = {
        field.getPiece match {
            case Some(p) => p.getColour == player.color
            case None => false
        }
    }

    //noinspection SimplifyBooleanMatch
    /**
      * Moves the piece from one field to another
      *
      * @param oldColumn column of the pieces' current position
      * @param oldRow    row of the pieces' current position
      * @param newColumn column of the pieces' next position
      * @param newRow    row of the pieces' next position
      * @return true if the move was successful, false if not
      */
    override def move(oldColumn: Int, oldRow: Int, newColumn: Int, newRow: Int): (Boolean, Option[Player]) = {
        val forcedFieldMap = if (multipleMove.isEmpty) checkForcedCapture() else multipleMove

        val oldField: Option[Field] = board.getField(oldColumn)(oldRow)
        val newField: Option[Field] = board.getField(newColumn)(newRow)

        (oldField, newField) match {
            case (Some(of), Some(nf)) => moveA(of, nf, forcedFieldMap)
            case (_, _) => (false, None)
        }
    }

    def moveA(oldField: Field, newField: Field, forcedFieldMap: mutable.Map[Field, List[Field]]): (Boolean, Option[Player]) = {
        var forcedMove = false

        if (forcedFieldMap.nonEmpty) {
            forcedFieldMap get oldField match {
                case Some(lf) => if (!lf.contains(newField)) return (false, None)
                case None => return (false, None)
            }
            forcedMove = true
        }

        newField.getPiece match {
            case Some(_) => (false, None)
            case None => moveB(oldField, newField, forcedMove)
        }
    }

    def moveB(oldField: Field, newField: Field, forcedMove: Boolean): (Boolean, Option[Player]) = {
        oldField.getPiece match {
            case Some(p) => moveC(oldField, newField, p, forcedMove)
            case None => (false, None)
        }
    }

    def moveC(oldField: Field, newField: Field, piece: Piece, forcedMove: Boolean): (Boolean, Option[Player]) = {
        if (piece.getColour != colourTurn) return (false, None)

        val rowMove = newField.getRow - oldField.getRow
        val columnMove = newField.getColumn - oldField.getColumn

        if (getUnsignedInt(rowMove) != getUnsignedInt(columnMove)) return (false, None)

        var currentColumn = oldField.getColumn
        var currentRow = oldField.getRow
        var ownPieces = 0
        var opponentPieces = 0
        var captureField = None: Option[Field]

        do {
            (columnMove / getUnsignedInt(columnMove), rowMove / getUnsignedInt(rowMove)) match {
                case (1, 1) => currentColumn += 1; currentRow += 1
                case (-1, 1) => currentColumn -= 1; currentRow += 1
                case (1, -1) => currentColumn += 1; currentRow -= 1
                case (-1, -1) => currentColumn -= 1; currentRow -= 1
                case (_, _) => ;
            }

            val field = board.getField(currentColumn)(currentRow).get

            field.getPiece match {
                case Some(fieldPiece) => fieldPiece.getColour == piece.getColour match {
                    case true => ownPieces += 1
                    case false => opponentPieces += 1; captureField = Some(field)
                }
                case _ =>
            }
        } while (currentColumn != newField.getColumn && currentRow != newField.getRow)

        val pieceController: PieceController = getPieceController(piece)

        val result = (ownPieces, opponentPieces) match {
            case (0, 0) => pieceController.move(oldField, newField)
            case (0, 1) => pieceController.capture(oldField, newField, captureField)
            case (_, _) => false
        }

        val anotherList = pieceController.checkIfNextFieldHasOpponentPiece(board, newField)
        if (!forcedMove || anotherList.isEmpty) {
            if (colourTurn == Colour.BLACK) colourTurn = Colour.WHITE else colourTurn = Colour.BLACK
            multipleMove = multipleMove.empty
        } else {
            multipleMove(newField) = anotherList
        }

        implicit val timeout: Timeout = Timeout(5 seconds)
        val future = gameStopActor ? CheckPlayer(board, colourTurn)
        val response = Await.result(future, timeout.duration).asInstanceOf[Boolean]

        val winner = if (response) {
            Some(if (colourTurn == blackPlayer.color) whitePlayer else blackPlayer)
        }
        else None

        (result, winner)
    }

    private def getUnsignedInt(x: Int) = if (x < 0) x * (-1) else x

    /**
      * Checks if a field must do a capture move
      *
      * @return List of the possible fields where the piece has to move
      */
    def checkForcedCapture(): mutable.Map[Field, List[Field]] = {
        val fieldMap: mutable.Map[Field, List[Field]] = mutable.Map()
        for (field <- board.iterator) {
            field.getPiece match {
                case Some(piece) =>
                    if (piece.getColour == colourTurn) {
                        val pieceController: PieceController = getPieceController(piece)
                        val forcedFields = pieceController.checkIfNextFieldHasOpponentPiece(board, field)
                        if (forcedFields.nonEmpty) {
                            fieldMap(field) = forcedFields
                        }
                    }
                case None => ;
            }
        }

        fieldMap
    }

    def getPieceController(piece: Piece): PieceController = {
        piece match {
            case m: Man => new ManController(m)
            case k: King => new KingController(k)
        }
    }

    override def checkIfGameIsOver(): Boolean = if (blackPlayer.pieces == 0 || whitePlayer.pieces == 0) true else false
}
