package de.htwg.draughts.controller

import com.google.inject.assistedinject.Assisted
import de.htwg.draughts.model._
import javax.inject.Inject

import scala.collection.mutable

//TODO: check validation
class MoveController @Inject()(val board: Board, @Assisted("blackPlayer") val blackPlayer: Player, @Assisted("whitePlayer") val whitePlayer: Player) extends GameController {

  var colourTurn: Colour.Value = Colour.BLACK
  var multipleMove: mutable.Map[Field, List[Field]] = mutable.Map()

  def toggleHighlightField(col: Int, row: Int): Boolean = {
    board.getField(col)(row).get.highlighted = !board.getField(col)(row).get.highlighted
    board.getField(col)(row).get.highlighted
  }

  def checkIfPieceIsValid(field: Field, player: Player): Boolean = {
    field.hasPiece && field.getPiece.get.getColour == player.color
  }

  def move(oldColumn: Int, oldRow: Int, newColumn: Int, newRow: Int): Boolean = {
    if (checkIfGameIsOver()) return false

    val forcedFieldMap = if (multipleMove.isEmpty) checkForcedCapture() else multipleMove

    val oldField: Field = board.getField(oldColumn)(oldRow).get
    val newField: Field = board.getField(newColumn)(newRow).get
      var forcedMove = false

    if (forcedFieldMap.nonEmpty) {
        forcedFieldMap get oldField match {
            case Some(lf) => if (!lf.contains(newField)) return false
            case None => return false
        }
        forcedMove = true
    }

    if (newField.hasPiece) {
      return false
    }

    val piece = oldField.getPiece.get

    if (piece.getColour != colourTurn) return false

    val rowMove = newField.getRow - oldField.getRow
    val columnMove = newField.getColumn - oldField.getColumn

    if (getUnsignedInt(rowMove) != getUnsignedInt(columnMove)) {
      return false
    }

    var currentColumn = oldColumn
    var currentRow = oldRow
    var ownPieces = 0
    var opponentPieces = 0
    var captureField = None: Option[Field]

    do {
      (columnMove / getUnsignedInt(columnMove), rowMove / getUnsignedInt(rowMove)) match {
        case (1, 1) => currentColumn += 1; currentRow += 1
        case (-1, 1) => currentColumn -= 1; currentRow += 1
        case (1, -1) => currentColumn += 1; currentRow -= 1
        case (-1, -1) => currentColumn -= 1; currentRow -= 1
      }

      val field = board.getField(currentColumn)(currentRow).get

      if (field.hasPiece) {
        field.getPiece.get.getColour == piece.getColour match {
          case true => ownPieces += 1
          case false => opponentPieces += 1; captureField = Some(field)
        }
      }
    } while (currentColumn != newColumn && currentRow != newRow)

    val pieceController: PieceController = getPieceController(piece)

    val player = if (colourTurn == Colour.BLACK) whitePlayer else blackPlayer

    val result = (ownPieces, opponentPieces) match {
      case (0, 0) => pieceController.move(oldField, newField)
      case (0, 1) => pieceController.capture(oldField, newField, captureField, player)
      case (_, _) => false
    }

      val anotherList = pieceController.checkIfNextFieldHasOpponentPiece(board, newField)
      if (!forcedMove || anotherList.isEmpty) {
          if (colourTurn == Colour.BLACK) colourTurn = Colour.WHITE else colourTurn = Colour.BLACK
          multipleMove = multipleMove.empty
      } else {
          multipleMove(newField) = anotherList
      }

      result
  }

  def checkIfGameIsOver(): Boolean = {
    if (blackPlayer.pieces == 0 || whitePlayer.pieces == 0) true else false
  }

  private def getUnsignedInt(x: Int) = {
    if (x < 0) {
      x * (-1)
    } else {
      x
    }
  }

  def checkForcedCapture(): mutable.Map[Field, List[Field]] = {
    var fieldMap: mutable.Map[Field, List[Field]] = mutable.Map()
      for (field <- board.iterator) {
          if (field.hasPiece) {
              val piece = field.getPiece.get
              if (piece.getColour == colourTurn) {
                val pieceController: PieceController = getPieceController(piece)
                  val forcedFields = pieceController.checkIfNextFieldHasOpponentPiece(board, field)
                  if (forcedFields.nonEmpty) {
                      fieldMap(field) = forcedFields
                  }
              }
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

  private def forceCapture(): Unit = {
    //Check if Enemy Piece is around (diagonally)
    //Check if Piece could be captured


    //ForcedCaptureMove
    // Capture automatically?
    // OR Restrict All other moves!
  }

}
