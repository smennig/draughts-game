package de.htwg.draughts.controller

import de.htwg.draughts.model._

//TODO: check validation
class MoveController(var board: Board, blackPlayer: Player, whitePlayer: Player, var colourTurn: Colour.Value = Colour.BLACK) extends GameController {
  def toggleHighlightField(col: Int, row: Int): Boolean = {
    board.getField(col)(row).highlighted = !board.getField(col)(row).highlighted
    board.getField(col)(row).highlighted
  }

  def checkIfPieceIsValid(field: Field, player: Player): Boolean = {
    field.hasPiece && field.getPiece.get.getColour == player.color
  }

  def move(oldColumn: Int, oldRow: Int, newColumn: Int, newRow: Int): Boolean = {
    if (checkIfGameIsOver()) return false

    val oldField: Field = board.getField(oldColumn)(oldRow)
    val newField: Field = board.getField(newColumn)(newRow)

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

      val field = board.getField(currentColumn)(currentRow)

      if (field.hasPiece) {
        field.getPiece.get.getColour == piece.getColour match {
          case true => ownPieces += 1
          case false => opponentPieces += 1; captureField = Some(field)
        }
      }
    } while (currentColumn != newColumn && currentRow != newRow)

    val pieceController: PieceController = piece match {
      case m: Man => new ManController(m)
      case k: King => new KingController(k)
    }

    val player = if (colourTurn == Colour.BLACK) whitePlayer else blackPlayer
    if (colourTurn == Colour.BLACK) colourTurn = Colour.WHITE else colourTurn = Colour.BLACK

    (ownPieces, opponentPieces) match {
      case (0, 0) => pieceController.move(oldField, newField)
      case (0, 1) => pieceController.capture(oldField, newField, captureField, player)
      case (_, _) => false
    }
  }

  def checkIfGameIsOver(): Boolean = {
    if (blackPlayer.pieces == 0 || whitePlayer.pieces == 0) true else false
  }

  //  def checkWinner() = {
  //    if (blackPlayer.pieces == 0) {
  //      "White has won"
  //    }
  //    if (whitePlayer.pieces == 0) {
  //      "Black has won"
  //    }
  //    "Game not over"
  //  }

  private def getUnsignedInt(x: Int) = {
    if (x < 0) {
      x * (-1)
    } else {
      x
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
