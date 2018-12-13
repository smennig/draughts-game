package model

//TODO : oliver maybe extrac  trait
abstract class Piece(colour: Colour.Value) {
  def getColour: Colour.Value = colour

  override def toString: String

  def move(oldField: Field, newField: Field): Boolean

  def capture(oldField: Field, newField: Field, captureField: Option[Field]): Boolean
}
