package de.htwg.draughts.controller

import de.htwg.draughts.model.{Field, King}

class KingController(king: King) extends PieceController {
  def move(oldField: Field, newField: Field): Boolean = {
    oldField.clearPiece()
    newField.piece_(Some(king))
    true
  }

  def capture(oldField: Field, newField: Field, captureField: Option[Field]): Boolean = {
    val rowMove = newField.getRow - captureField.get.getRow
    val columnMove = newField.getColumn - captureField.get.getColumn
    (rowMove, columnMove) match {
      case (1, 1) => captureHelp(oldField, newField, captureField); true
      case (1, -1) => captureHelp(oldField, newField, captureField); true
      case (-1, 1) => captureHelp(oldField, newField, captureField); true
      case (-1, -1) => captureHelp(oldField, newField, captureField); true
      case (_, _) => false
    }
  }

  private def captureHelp(oldField: Field, newField: Field, captureField: Option[Field]): Unit = {
    oldField.clearPiece()
    newField.piece_(Some(king))
    captureField.get.clearPiece()
  }
}
