package model

class Player(val name: String, val color: Colour.Value, var hasKing: Boolean = false) {

  override def toString: String = {
    "Name : " + name + " Farbe: " + color
  }
}