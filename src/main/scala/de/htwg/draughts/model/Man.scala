package de.htwg.draughts.model

case class Man(colour: Colour.Value) extends Piece(colour) {
    override def toString: String = {
        this.colour match {
            case Colour.BLACK => "b"
            case Colour.WHITE => "w"
        }
    }
}
