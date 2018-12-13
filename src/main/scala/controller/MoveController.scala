package controller

import model._

//TODO: check validation
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
      case (0, 0) => movePiece(piece, oldField, newField)
      case (0, 1) => capturePiece(piece, oldField, newField, captureField)
      case (_, _) => return false
    }
  }

    def movePiece(piece: Piece, oldField: Field, newField: Field) = {
        if (piece.isInstanceOf[Man]) {
            moveMan(piece, oldField, newField)
        } else if (piece.isInstanceOf[King]) {
            moveKing(piece, oldField, newField)
        } else {
            false
        }
    }

  def moveMan(man: Piece, oldField: Field, newField: Field): Boolean = {
    val rowMove = newField.getRow - oldField.getRow
    val columnMove = newField.getColumn - oldField.getColumn
    (man.getColour, rowMove, columnMove) match {
      case (Colour.BLACK, 1, 1) => moveManHelp(man, oldField, newField); true
      case (Colour.BLACK, 1, -1) => moveManHelp(man, oldField, newField); true
      case (Colour.WHITE, -1, 1) => moveManHelp(man, oldField, newField); true
      case (Colour.WHITE, -1, -1) => moveManHelp(man, oldField, newField); true
      case (_, _, _) => false
    }
  }

  private def moveManHelp(man: Piece, oldField: Field, newField: Field): Unit = {
    oldField.clearPiece()
    newField.piece_(Some(man))
  }

    def moveKing(king: Piece, oldField: Field, newField: Field): Boolean = {
        oldField.clearPiece()
        newField.piece_(Some(king))
        true
    }

    def capturePiece(piece: Piece, oldField: Field, newField: Field, captureField: Option[Field]) = {
        if (piece.isInstanceOf[Man]) {
            captureMan(piece, oldField, newField, captureField)
        } else if (piece.isInstanceOf[King]) {
            captureKing(piece, oldField, newField, captureField)
        } else {
            false
        }
    }

    def captureMan(man: Piece, oldField: Field, newField: Field, captureField: Option[Field]): Boolean = {
        val rowMove = newField.getRow - oldField.getRow
        val columnMove = newField.getColumn - oldField.getColumn
        (man.getColour, rowMove, columnMove) match {
            case (Colour.BLACK, 2, 2) => captureManHelp(man, oldField, newField, captureField); true
            case (Colour.BLACK, 2, -2) => captureManHelp(man, oldField, newField, captureField); true
            case (Colour.WHITE, -2, 2) => captureManHelp(man, oldField, newField, captureField); true
            case (Colour.WHITE, -2, -2) => captureManHelp(man, oldField, newField, captureField); true
            case (_, _, _) => false
        }
    }

    private def captureManHelp(man: Piece, oldField: Field, newField: Field, captureField: Option[Field]) = {
        oldField.clearPiece()
        newField.piece_(Some(man))
        captureField.get.clearPiece()
    }

    def captureKing(king: Piece, oldField: Field, newField: Field, captureField: Option[Field]): Boolean = {
        val rowMove = newField.getRow - captureField.get.getRow
        val columnMove = newField.getColumn - captureField.get.getColumn
        (rowMove, columnMove) match {
            case (1, 1) => captureKingHelp(king, oldField, newField, captureField); true
            case (1, -1) => captureKingHelp(king, oldField, newField, captureField); true
            case (-1, 1) => captureKingHelp(king, oldField, newField, captureField); true
            case (-1, -1) => captureKingHelp(king, oldField, newField, captureField); true
            case (_, _) => false
        }
    }

    private def captureKingHelp(king: Piece, oldField: Field, newField: Field, captureField: Option[Field]) = {
        oldField.clearPiece()
        newField.piece_(Some(king))
        captureField.get.clearPiece()
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
