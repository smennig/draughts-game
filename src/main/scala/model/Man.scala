package model

class Man(colour: Colour.Value) extends Piece(colour) {
    override def move(oldField: Field, newField: Field): Boolean = {
        (newField.getRow - oldField.getRow, newField.getColumn - oldField.getColumn) match {
            case (1, 1) => true
            case (1, -1) => true
            case (_, _) => false
        }
    }
}
