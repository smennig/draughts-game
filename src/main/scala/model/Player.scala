package model

class Player(val name: String, val color: Colour.Value, var turn: Boolean, var hasKing: Boolean = false) {

  def turn_(): Unit = {
      turn = !turn
  }

  override def toString: String = {
    "Name : " + name + " Farbe: " + color
  }
}