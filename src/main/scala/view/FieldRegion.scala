package view

import model.Colour
import scalafx.scene.layout.Region


case class FieldRegion(position: (Int, Int), size: Double = 80, color: Colour.Value) extends Region {

  val white = "rgb(230,230,230)"
  val black = "rgba(117, 117, 117, 0.7)"

  def blackOrWhite: String = {
    color match {
      case Colour.BLACK
      => "-fx-background-color: " + black + ";"
      case Colour.WHITE
      => "-fx-background-color: " + white + ";"
      case _ => ""
    }

  }

  style = blackOrWhite

  prefHeight = size
  prefWidth = size

}