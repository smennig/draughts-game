package model

class Man(colour: Colour.Value) extends Piece(colour) {
    override def move(oldField: Field, newField: Field): Boolean = {
        val rowMove = newField.getRow - oldField.getRow
        val columnMove = newField.getColumn - oldField.getColumn
        (rowMove, columnMove) match {
            case (1, 1) => true
            case (1, -1) => true
            case (_, _) => false
        }
    }

    override def toString: String = {
        this.colour match {
            case Colour.BLACK => "b"
            case Colour.WHITE => "w"
        }
    }
}
