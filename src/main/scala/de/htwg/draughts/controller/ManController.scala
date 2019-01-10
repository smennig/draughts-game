package de.htwg.draughts.controller

import de.htwg.draughts.model._

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

  private def isFieldKingsRow(moveRow: Int, colour: Colour.Value): Boolean = {
    if (moveRow == 0 && colour == Colour.WHITE || moveRow == 7 && colour == Colour.BLACK) {
      true
    } else {
      false
    }
  }

  def capture(oldField: Field, newField: Field, captureField: Option[Field], player: Player): Boolean = {
    val rowMove = newField.getRow - oldField.getRow
    val columnMove = newField.getColumn - oldField.getColumn
    (man.getColour, rowMove, columnMove) match {
      case (Colour.BLACK, 2, 2) => captureHelp(oldField, newField, captureField, player); true
      case (Colour.BLACK, 2, -2) => captureHelp(oldField, newField, captureField, player); true
      case (Colour.WHITE, -2, 2) => captureHelp(oldField, newField, captureField, player); true
      case (Colour.WHITE, -2, -2) => captureHelp(oldField, newField, captureField, player); true
      case (_, _, _) => false
    }
  }

  private def captureHelp(oldField: Field, newField: Field, captureField: Option[Field], player: Player): Unit = {
    oldField.clearPiece()
    if (isFieldKingsRow(newField.getRow, man.getColour)) {
      val king: Piece = new King(man.getColour)
      newField.piece_(Some(king))
    } else {
      newField.piece_(Some(man))
    }
    captureField.get.clearPiece()
    player.removePiece()
  }

  override def checkIfNextFieldHasOpponentPiece(board: Board, ownField: Field): List[Field] = {
    var fieldList: List[Field] = List()

    if (ownField.getColumn > 1) {
      val nextLeftField = board.getField(ownField.getColumn - 1)(ownField.getRow + getCaptureMoveDependingOnColour(ownField.getPiece.get.getColour))
      val overnextLeftField = board.getField(ownField.getColumn - 2)(ownField.getRow + 2 * getCaptureMoveDependingOnColour(ownField.getPiece.get.getColour))
      if (nextLeftField.hasPiece && nextLeftField.getPiece.get.getColour != ownField.getPiece.get.getColour && !overnextLeftField.hasPiece) {
        fieldList = overnextLeftField :: fieldList
      }
    }
    if (ownField.getColumn < 6) {
      val nextRightField = board.getField(ownField.getColumn + 1)(ownField.getRow + getCaptureMoveDependingOnColour(ownField.getPiece.get.getColour))
      val overnextRightField = board.getField(ownField.getColumn + 2)(ownField.getRow + 2 * getCaptureMoveDependingOnColour(ownField.getPiece.get.getColour))
      if (nextRightField.hasPiece && nextRightField.getPiece.get.getColour != ownField.getPiece.get.getColour && !overnextRightField.hasPiece) {
        fieldList = overnextRightField :: fieldList
      }
    }

    fieldList
  }

  private def getCaptureMoveDependingOnColour(colour: Colour.Value): Int = {
    colour match {
      case Colour.BLACK => 1
      case Colour.WHITE => -1
    }
  }
}
