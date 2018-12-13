package model

class Man(colour: Colour.Value) extends Piece(colour) {
    override def move(oldField: Field, newField: Field): Boolean = {
        val rowMove = newField.getRow - oldField.getRow
        val columnMove = newField.getColumn - oldField.getColumn
        (colour, rowMove, columnMove) match {
            case (Colour.BLACK, 1, 1) => moveHelp(oldField, newField); true
            case (Colour.BLACK, 1, -1) => moveHelp(oldField, newField); true
            case (Colour.WHITE, -1, 1) => moveHelp(oldField, newField); true
            case (Colour.WHITE, -1, -1) => moveHelp(oldField, newField); true
            case (_, _, _) => false
        }
    }

    private def moveHelp(oldField: Field, newField: Field) = {
        oldField.clearPiece()
        newField.piece_(Some(this))
    }

    override def capture(oldField: Field, newField: Field, captureField: Option[Field]): Boolean = {
        val rowMove = newField.getRow - oldField.getRow
        val columnMove = newField.getColumn - oldField.getColumn
        (colour, rowMove, columnMove) match {
            case (Colour.BLACK, 2, 2) => captureHelp(oldField, newField, captureField); true
            case (Colour.BLACK, 2, -2) => captureHelp(oldField, newField, captureField); true
            case (Colour.WHITE, -2, 2) => captureHelp(oldField, newField, captureField); true
            case (Colour.WHITE, -2, -2) => captureHelp(oldField, newField, captureField); true
            case (_, _, _) => false
        }
    }

    private def captureHelp(oldField: Field, newField: Field, captureField: Option[Field]) = {
        oldField.clearPiece()
        newField.piece_(Some(this))
        captureField.get.clearPiece()
    }

    override def toString: String = {
        this.colour match {
            case Colour.BLACK => "b"
            case Colour.WHITE => "w"
        }
    }
}
