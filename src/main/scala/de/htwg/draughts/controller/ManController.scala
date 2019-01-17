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

    ownField.getPiece match {
      case Some(piece) =>
        val nextLeftField: Option[Field] = getNextField(board, ownField, piece, Left(), 1)
        val nextRightField: Option[Field] = getNextField(board, ownField, piece, Right(), 1)

        val overnextLeftField = getNextField(board, ownField, piece, Left(), 2)
        if (checkNextField(nextLeftField, overnextLeftField, piece)) {
          fieldList = overnextLeftField.get :: fieldList
        }
        val overnextRightField = getNextField(board, ownField, piece, Right(), 2)
        if (checkNextField(nextRightField, overnextRightField, piece)) {
          fieldList = overnextRightField.get :: fieldList
        }
      case None =>
    }
    fieldList
  }

  private def checkNextField(field: Option[Field], overnextField: Option[Field], piece: Piece): Boolean = {
    field match {
      case Some(f) => f.getPiece match {
        case Some(nextRightPiece) => nextRightPiece.getColour != piece.getColour && overnextField.isDefined && !overnextField.get.hasPiece
        case _ => false
      }
      case _ => false
    }

  }

  private def getNextField(board: Board, ownField: Field, piece: Piece, direction: Direction, step: Int): Option[Field] = {
    direction match {
      case Left() => board.getField(ownField.getColumn - step)(ownField.getRow + step * getCaptureMoveDependingOnColour(piece.getColour))
      case Right() => board.getField(ownField.getColumn + step)(ownField.getRow + step * getCaptureMoveDependingOnColour(piece.getColour))
      case _ => Option.empty
    }

  }

  private def getCaptureMoveDependingOnColour(colour: Colour.Value): Int = {
    colour match {
      case Colour.BLACK => 1
      case Colour.WHITE => -1
    }
  }

  private trait Direction

  private case class Right() extends Direction

  private case class Left() extends Direction

}
