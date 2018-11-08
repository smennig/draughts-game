package model

abstract class Piece(colour: Colour.Value) {
    def getColour: Colour.Value = colour

    override def toString: String = {

        (this.colour) match {
            case (Colour.BLACK) => "b"
            case (Colour.WHITE) => "w"
        }
    }

    def move(oldField: Field, newField: Field): Boolean
}
