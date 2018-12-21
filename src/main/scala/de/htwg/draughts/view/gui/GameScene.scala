package de.htwg.draughts.view.gui

import de.htwg.draughts.controller.{GameController, MoveController}
import de.htwg.draughts.model._
import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout._
import scalafx.scene.paint.Color
import scalafx.scene.text.{Font, FontWeight, Text}
import de.htwg.draughts.view.gui.styles.Styles

class GameScene(val controller: GameController, val playerOne: Player = new Player(color = Colour.BLACK, name = "Player1", turn = true), val playerTwo: Player = new Player(color = Colour.WHITE, name = "Player2", turn = false)) extends Scene {

  var lastClickedField: Option[FieldPane] = Option.empty

  def boardPane: BorderPane = new BorderPane() {
    center = new GridPane {
      style = Styles.boardBorder
      fields.foreach(tile => {
        add(addClickHandlers(new FieldPane(tile) {
        }), tile.col, tile.row)
      })
    }

    top = new VBox {
      style = Styles.lightGrayBackground
      children = Seq(
        new HBox {
          children = Seq(
            new Text {
              margin = Insets(0, 10, 0, 0)
              text = playerOne.name
              fill = getNameColor(playerOne)
              font = Font.font(null, FontWeight.Bold, getSize(playerOne.color, controller.colourTurn))
            },
            //TODO:simon make current Turn indicator Dynamic
            new Text {
              text = if (controller.colourTurn == playerOne.color) "ist dran" else ""
              fill = getNameColor(playerOne)
              font = Font.font(null, FontWeight.Bold, getSize(playerOne.color, controller.colourTurn))
            })
        },
        new HBox {
          children = Seq(
            new Text {
              margin = Insets(0, 10, 0, 0)
              text = playerTwo.name
              fill = getNameColor(playerTwo)
              font = Font.font(null, FontWeight.Bold, getSize(playerTwo.color, controller.colourTurn))
            },
            new Text {
              //TODO:simon make current Turn indicator Dynamic
              text = if (controller.colourTurn == playerTwo.color) "ist dran" else ""
              fill = getNameColor(playerTwo)
              font = Font.font(null, FontWeight.Bold, getSize(playerTwo.color, controller.colourTurn))
            })
        })

      padding = Insets(20)
    }
    antialiasing
  }

  def fields: Seq[Tile] = {
    val positions = for {
      row <- controller.board.fields.indices
      col <- controller.board.fields.indices
    } yield (row, col)
    positions.map { position =>
      Tile(controller.board.fields(position._1)(position._2))
    }
  }

  def getSize(playerColour: Colour.Value, turnColour: Colour.Value): Int = {
    if (playerColour == turnColour) 32 else 16
  }

  def addClickHandlers(fieldPane: FieldPane): FieldPane = {
    fieldPane.onMouseClicked = (e: MouseEvent) => {
      println("last clickedField is: " + lastClickedField)

      lastClickedField match {
        case Some(oldField) =>
          oldField.piece match {
            case Some(piece) =>
              controller.move(oldField.col, oldField.row, fieldPane.col, fieldPane.row)
              println((oldField.col, oldField.row, fieldPane.col, fieldPane.row))
              controller.toggleHighlightField(oldField.col, oldField.row)
              lastClickedField = Option.empty
            case None => lastClickedField = Option.empty
          }
        case None =>
          fieldPane.piece match {
            case Some(piece) => lastClickedField = Some(fieldPane)
              controller.toggleHighlightField(fieldPane.col, fieldPane.row)
            case None => lastClickedField = Option.empty
          }
      }
      repaint()
      println("click on row: " + fieldPane.row + "field :" + fieldPane.col + "piece: " + fieldPane.piece)
    }
    fieldPane
  }


  def getNameColor(player: Player): Color = {
    player.color match {
      case Colour.WHITE => Color.White
      case Colour.BLACK => Color.Black
    }
  }

  private def repaint(): Unit = {
    root = boardPane
  }


  repaint()


}
