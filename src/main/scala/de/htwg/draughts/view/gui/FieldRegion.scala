package de.htwg.draughts.view.gui

import de.htwg.draughts.model.Colour
import de.htwg.draughts.view.gui.styles.Styles
import scalafx.scene.layout.Region

case class FieldRegion(size: Double = 100, color: Colour.Value) extends Region {

  def blackOrWhite: String = {
    color match {
      case Colour.BLACK
      => "-fx-background-color: " + Styles.blackFieldColor + ";"
      case Colour.WHITE
      => "-fx-background-color: " + Styles.whiteFieldColor + ";"
      case _ => ""
    }
  }

  style = blackOrWhite
  prefHeight = size
  prefWidth = size

}
