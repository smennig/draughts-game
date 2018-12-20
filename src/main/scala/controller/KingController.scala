package controller

import model.{Field, King, Player}

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
}
