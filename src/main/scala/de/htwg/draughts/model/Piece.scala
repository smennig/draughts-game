package de.htwg.draughts.model

abstract class Piece(colour: Colour.Value) {
  def getColour: Colour.Value = colour

  override def toString: String
}
