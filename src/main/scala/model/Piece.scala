package model

class Piece(colour: Colour.Value, var position: Field) {
    position.piece_(Some(this))
    def getColour: Colour.Value = colour

    def move(newPosition: Field) = {
        if (newPosition.hasPiece) {
            throw new IllegalArgumentException("The new position is already occupied by a piece")
        }

        position = newPosition
    }
}
