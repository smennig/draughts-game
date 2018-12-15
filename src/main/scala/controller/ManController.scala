package controller

import model.{Colour, Field, Man, Piece}

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
        newField.piece_(Some(man))
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
        newField.piece_(Some(man))
        captureField.get.clearPiece()
    }
}
