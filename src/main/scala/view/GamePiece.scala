package view

import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

case class GamePiece(color: Color) extends Circle {
  fill = color
  radius = 30

}
