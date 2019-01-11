package de.htwg.draughts.controller

import de.htwg.draughts.model.{Board, Field, King, Player}

class KingController(king: King) extends PieceController {
  def move(oldField: Field, newField: Field): Boolean = {
    oldField.clearPiece()
    newField.piece_(Some(king))
    true
  }

  def capture(oldField: Field, newField: Field, captureField: Option[Field], player: Player): Boolean = {
    val rowMove = newField.getRow - captureField.get.getRow
    val columnMove = newField.getColumn - captureField.get.getColumn
    (rowMove, columnMove) match {
      case (1, 1) => captureHelp(oldField, newField, captureField, player); true
      case (1, -1) => captureHelp(oldField, newField, captureField, player); true
      case (-1, 1) => captureHelp(oldField, newField, captureField, player); true
      case (-1, -1) => captureHelp(oldField, newField, captureField, player); true
      case (_, _) => false
    }
  }

  private def captureHelp(oldField: Field, newField: Field, captureField: Option[Field], player: Player): Unit = {
    oldField.clearPiece()
    newField.piece_(Some(king))
    captureField.get.clearPiece()
    player.removePiece()
  }

  override def checkIfNextFieldHasOpponentPiece(board: Board, ownField: Field): List[Field] = {
    var fieldList: List[Field] = List()

    var startColumn = ownField.getColumn
    var startRow = ownField.getRow

    val topRightField = checkFieldsRec(board, startColumn + 1, startRow + 1, 1, 1)
    val topLeftField = checkFieldsRec(board, startColumn - 1, startRow + 1, -1, 1)
    val bottomRightField = checkFieldsRec(board, startColumn + 1, startRow - 1, 1, -1)
    val bottomLeftField = checkFieldsRec(board, startColumn - 1, startRow - 1, -1, -1)

    if (topRightField.isDefined && topRightField.get.getPiece.get.getColour != ownField.getPiece.get.getColour) {
      val nextAfterTopRightField = board.getField(topRightField.get.getColumn + 1)(topRightField.get.getRow + 1)
      if (nextAfterTopRightField.isDefined && !nextAfterTopRightField.get.hasPiece) {
        fieldList = nextAfterTopRightField.get :: fieldList
      }
    }

    if (topLeftField.isDefined && topLeftField.get.getPiece.get.getColour != ownField.getPiece.get.getColour) {
      val nextAfterTopLeftField = board.getField(topLeftField.get.getColumn - 1)(topLeftField.get.getRow + 1)
      if (nextAfterTopLeftField.isDefined && !nextAfterTopLeftField.get.hasPiece) {
        fieldList = nextAfterTopLeftField.get :: fieldList
      }
    }

    if (bottomRightField.isDefined && bottomRightField.get.getPiece.get.getColour != ownField.getPiece.get.getColour) {
      val nextAfterBottomRightField = board.getField(bottomRightField.get.getColumn + 1)(bottomRightField.get.getRow - 1)
      if (nextAfterBottomRightField.isDefined && !nextAfterBottomRightField.get.hasPiece) {
        fieldList = nextAfterBottomRightField.get :: fieldList
      }
    }

    if (bottomLeftField.isDefined && bottomLeftField.get.getPiece.get.getColour != ownField.getPiece.get.getColour) {
      val nextAfterBottomLeftField = board.getField(bottomLeftField.get.getColumn - 1)(bottomLeftField.get.getRow - 1)
      if (nextAfterBottomLeftField.isDefined && !nextAfterBottomLeftField.get.hasPiece) {
        fieldList = nextAfterBottomLeftField.get :: fieldList
      }
    }

    fieldList
  }

  private def checkFieldsRec(board: Board, column: Int, row: Int, columnMove: Int, rowMove: Int): Option[Field] = {
    val field = board.getField(column)(row)
    field match {
      case Some(f) => if (f.hasPiece) Some(f) else checkFieldsRec(board, column + columnMove, row + rowMove, columnMove, rowMove)
      case None => None
    }
  }
}
