package de.htwg.draughts.model

case class King(colour: Colour.Value) extends Piece(colour) {
    override def toString: String = {
        this.colour match {
            case Colour.BLACK => "B"
            case Colour.WHITE => "W"
        }
    }
}
