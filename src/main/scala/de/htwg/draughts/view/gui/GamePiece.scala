package de.htwg.draughts.view.gui

import de.htwg.draughts.model.Colour
import scalafx.scene.Node
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

case class GamePiece(color: Colour.Value, size: Double = 30, isKing: Boolean = false) {

    val crownBlackPath = "img/crown_black.png"
    val crownWhitePath = "img/crown_white.png"

    def getView: Node = {
        if (isKing) {
            new StackPane {
                children = List(blackOrWhite, blackOrWhiteKing)
            }
        } else {
            new StackPane() {
                children = blackOrWhite
            }
        }

    }

    def blackOrWhite: Circle = {
        color match {
            case Colour.BLACK
            => new Circle {
                fill = Color.Black
                radius = size
            }
            case Colour.WHITE
            => new Circle {
                fill = Color.White
                radius = size
            }
        }
    }

    def blackOrWhiteKing: ImageView = {
        color match {
            case Colour.BLACK
            => new ImageView(
                image = new Image(crownWhitePath)
            )
            case Colour.WHITE
            => new ImageView(
                image = new Image(crownBlackPath)
            )
        }
    }

}
