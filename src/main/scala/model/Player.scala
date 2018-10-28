package model

class Player {


object PlayerColor extends Enumeration {
  val Weiss, Schwarz = Value
}

class Player(var name: String, var color: PlayerColor.Value) {

}

//Name
//Farbe
