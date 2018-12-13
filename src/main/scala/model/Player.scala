package model

class Player(var name: String, var color: Colour.Value) {

  //var hasKing: Boolean

  override def toString: String = {
    "Name : " + name +" Farbe: " + color
  }
}