package view

import model.Colour
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

case class GamePiece(color: Colour.Value) extends Circle {
  fill = blackOrWhite
  radius = 30

  def blackOrWhite: Color = {
    color match {
      case Colour.BLACK
      => Color.Black
      case Colour.WHITE
      => Color.White
    }
  }
}
