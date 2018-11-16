package view

import scalafx.scene.layout.Region

case class FieldRegion(position: (Int, Int)) extends Region {

  val white = "rgb(230,230,230)"
  val black = "rgba(117, 117, 117, 0.7)"

  def blackOrWhite: String = {
    val isBlack = if (position._1 % 2 == 0) (position._2 % 2) == 1 else (position._2 % 2) == 0
    if (isBlack) {
      "-fx-background-color: " + black + ";"
    } else {
      "-fx-background-color: " + white + ";"
    }
  }

  style = blackOrWhite

  prefHeight = 80
  prefWidth = 80

}