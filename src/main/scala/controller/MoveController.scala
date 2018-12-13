package controller

import model._

//TODO: oliver move methods from piececontroller, check validation
class MoveController(board: Board) {
  def checkIfPieceIsValid(field: Field, player: Player) = {
    field.hasPiece && field.getPiece.get.getColour == player.color
  }

  def move(oldColumn: Int, oldRow: Int, newColumn: Int, newRow: Int): Boolean = {
    val oldField: Field = board.getField(oldColumn)(oldRow)
    val newField: Field = board.getField(newColumn)(newRow)

    if (newField.hasPiece) {
      return false
    }

    val piece = oldField.getPiece.get

    var rowMove = newField.getRow - oldField.getRow
    var columnMove = newField.getColumn - oldField.getColumn

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

    (ownPieces, opponentPieces) match {
      case (0, 0) => piece.move(oldField, newField)
      case (0, 1) => piece.capture(oldField, newField, captureField)
      case (_, _) => return false
    }
  }

  private def getUnsignedInt(x: Int) = {
    if (x < 0) {
      x * (-1)
    } else {
      x
    }
  }

  private def isFieldKingsRow(moveColumn: Int, player: Player) = {
    //ToDo: Add in Move and Capture
    if (moveColumn == 1 && player.color == Colour.WHITE) {
      if (false) //!Player.hasKing
        true;
    }

    if (moveColumn == 8 && player.color == Colour.BLACK) {
      if (false) //!Player.hasKing
        true;
    }
    false;
  }

  private def forceCapture() = {
    //Check if Enemy Piece is around (diagonally)
    //Check if Piece could be captured


    //ForcedCaptureMove
    // Capture automatically?
    // OR Restrict All other moves!
  }

}
