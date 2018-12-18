package controller

import model._

class ManController(man: Man) extends PieceController {
  def move(oldField: Field, newField: Field): Boolean = {
    val rowMove = newField.getRow - oldField.getRow
    val columnMove = newField.getColumn - oldField.getColumn
    (man.getColour, rowMove, columnMove) match {
      case (Colour.BLACK, 1, 1) => moveHelp(oldField, newField); true
      case (Colour.BLACK, 1, -1) => moveHelp(oldField, newField); true
      case (Colour.WHITE, -1, 1) => moveHelp(oldField, newField); true
      case (Colour.WHITE, -1, -1) => moveHelp(oldField, newField); true
      case (_, _, _) => false
    }
  }

  private def moveHelp(oldField: Field, newField: Field): Unit = {
    oldField.clearPiece()
    if (isFieldKingsRow(newField.getRow, man.getColour)) {
      val king: Piece = new King(man.getColour)
      newField.piece_(Some(king))
    } else {
      newField.piece_(Some(man))
    }
  }

  def capture(oldField: Field, newField: Field, captureField: Option[Field]): Boolean = {
    val rowMove = newField.getRow - oldField.getRow
    val columnMove = newField.getColumn - oldField.getColumn
    (man.getColour, rowMove, columnMove) match {
      case (Colour.BLACK, 2, 2) => captureHelp(oldField, newField, captureField); true
      case (Colour.BLACK, 2, -2) => captureHelp(oldField, newField, captureField); true
      case (Colour.WHITE, -2, 2) => captureHelp(oldField, newField, captureField); true
      case (Colour.WHITE, -2, -2) => captureHelp(oldField, newField, captureField); true
      case (_, _, _) => false
    }
  }

  private def captureHelp(oldField: Field, newField: Field, captureField: Option[Field]): Unit = {
    oldField.clearPiece()
    if (isFieldKingsRow(newField.getRow, man.getColour)) {
      val king: Piece = new King(man.getColour)
      newField.piece_(Some(king))
    } else {
      newField.piece_(Some(man))
    }
    captureField.get.clearPiece()
  }

  private def isFieldKingsRow(moveRow: Int, colour: Colour.Value): Boolean = {
    if (moveRow == 0 && colour == Colour.WHITE || moveRow == 7 && colour == Colour.BLACK) {
      true
    } else {
      false
    }
  }
}
