package de.htwg.draughts.controller

import de.htwg.draughts.model._

//TODO: check validation
class MoveController(var board: Board) extends GameController {
  def toggleHighlightField(col: Int, row: Int): Boolean = {
    board.getField(col)(row).highlighted = !board.getField(col)(row).highlighted
    board.getField(col)(row).highlighted
  }

  def checkIfPieceIsValid(field: Field, player: Player): Boolean = {
    field.hasPiece && field.getPiece.get.getColour == player.color
  }

  def move(oldColumn: Int, oldRow: Int, newColumn: Int, newRow: Int): Boolean = {
    val oldField: Field = board.getField(oldColumn)(oldRow)
    val newField: Field = board.getField(newColumn)(newRow)

    if (newField.hasPiece) {
      return false
    }

    val piece = oldField.getPiece.get

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

    (ownPieces, opponentPieces) match {
      case (0, 0) => pieceController.move(oldField, newField)
      case (0, 1) => pieceController.capture(oldField, newField, captureField)
      case (_, _) => false
    }
  }


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
