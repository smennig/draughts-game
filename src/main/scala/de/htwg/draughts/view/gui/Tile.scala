package de.htwg.draughts.view.gui

import de.htwg.draughts.model.{Field, Piece}
import scalafx.scene.layout.Region

case class Tile(field: Field) {
  def piece: Option[Piece] = field.piece

  def region: Region = FieldRegion(color = field.getColour)

  def col: Int = field.getColumn

  def row: Int = field.getRow

}
