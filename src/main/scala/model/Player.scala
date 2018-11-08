package model

class Player(var name: String, var color: Colour.Value) {

  override def toString: String = {
    "Name : " + name +" Farbe: " + color
  }
}