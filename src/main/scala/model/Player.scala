package model

class Player(var name: String, var color: PlayerColor.Value) {

  override def toString: String = {
    "Name : " + name +" Farbe: " + color
  }
}

object PlayerColor extends Enumeration {
  val Schwarz, Weiß = Value
}