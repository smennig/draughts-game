package de.htwg.draughts.model

class Player(val name: String, val color: Colour.Value, var turn: Boolean, var hasKing: Boolean = false, var pieces: Int = 12) {

    def turn_(): Unit = {
        turn = !turn
    }

    def removePiece(): Unit = {
        pieces -= 1
    }

    override def toString: String = {
        "Name : " + name + " Farbe: " + color
    }
}