package de.htwg.draughts.view.gui

import de.htwg.draughts.model.{Colour, Player}
import de.htwg.draughts.view.gui.styles.Styles
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{Button, TextField}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.Text


class BeginGameGUI(changeScene: (Player, Player) => Unit) {

  val firstPlayerNameTextField: TextField = new TextField {
    margin = Insets(0, 10, 0, 60)
    text = "Spieler 1"
    style = "-fx-font-size: 20pt"
  }

  val secondPlayerNameTextField: TextField = new TextField {
    margin = Insets(0, 10, 0, 85)
    text = "Spieler 2"
    style = "-fx-font-size: 20pt"
  }

  val firstPlayerColorButton: Button = new Button {
    margin = Insets(0, 0, 0, 65)
    prefWidth = 125
    prefHeight = 125
    style = "-fx-background-color: white;"
  }

  val secondPlayerColorButton: Button = new Button {
    prefWidth = 125
    prefHeight = 125
    margin = Insets(0, 0, 0, 300)
    style = "-fx-background-color: black;"
  }

  val beginScene: Scene = new Scene {
    fill = Black
    content = new VBox {
      style = Styles.lightGrayBackground
      padding = Insets(20)
      children = Seq(
        new HBox {
          padding = Insets(20)
          children = Seq(
            new Text {
              text = "Willkommen bei Scala-Dame"
              style = "-fx-font-size: 48pt"
              fill = new LinearGradient(
                endX = 0,
                stops = Stops(White, White)
              )
            }
          )
        },
        new HBox{
          padding = Insets(0, 0, 0, 150)
          children = Seq(
            new Text {
              text = "Spieler 1:"
              style = "-fx-font-size: 30pt"
              fill = new LinearGradient(
                endX = 0,
                stops = Stops(White, White)
              )
            },
            new Text {
              margin = Insets(0, 0, 0, 250)
              text = "Spieler 2:"
              style = "-fx-font-size: 30pt"
              fill = new LinearGradient(
                endX = 0,
                stops = Stops(White, White)
              )
            }
          )
        },
        new HBox{
          padding = Insets(20, 0, 0, 20)
          children = Seq(
            new Text {
              text = "Name:"
              style = "-fx-font-size: 30pt"
              fill = new LinearGradient(
                endX = 0,
                stops = Stops(White, White)
              )
            },
            firstPlayerNameTextField,
            secondPlayerNameTextField
          )
        },
        new HBox {
          padding = Insets(20, 0, 0, 20)
          children = Seq(
            new Text {
              text = "Farbe:"
              style = "-fx-font-size: 30pt"
              fill = new LinearGradient(
                endX = 0,
                stops = Stops(White, White)
              )
            },
            firstPlayerColorButton,
            secondPlayerColorButton
          )
        },

        new HBox {
          padding = Insets(50, 20, 20, 625)
          children = Seq(
            new Button {
              prefWidth = 300
              prefHeight = 50
              text = "Spiel starten"
              onAction = (event: ActionEvent) =>  {
                if(!firstPlayerNameTextField.text.value.isEmpty && !secondPlayerNameTextField.text.value.isEmpty) {

                  val firstPlayerColor = getColorValueFromButton(firstPlayerColorButton)
                  val secondPlayerColor = getColorValueFromButton(secondPlayerColorButton)

                  val firstPlayer = new Player(firstPlayerNameTextField.text.value, firstPlayerColor, getInitialTurn(firstPlayerColor))
                  val secondPlayer = new Player(secondPlayerNameTextField.text.value, secondPlayerColor, getInitialTurn(secondPlayerColor))

                  changeScene(firstPlayer, secondPlayer)
                }
              }
            }
          )
        }
      )
    }
  }

  def getStartGameScene: Scene = {
    firstPlayerColorButton.onAction =
      (event: ActionEvent) =>  {
        performColorSwitch()
      }
    secondPlayerColorButton.onAction =
      (event: ActionEvent) =>  {
        performColorSwitch()
    }
    beginScene
  }

  def performColorSwitch(): Unit = {
    if(secondPlayerColorButton.getStyle == "-fx-background-color: white;")
    {
      secondPlayerColorButton.setStyle ("-fx-background-color: black;")
      firstPlayerColorButton.setStyle("-fx-background-color: white;")
    } else {
      secondPlayerColorButton.setStyle("-fx-background-color: white;")
      firstPlayerColorButton.setStyle("-fx-background-color: black;")
    }
  }

  def getColorValueFromButton(button: Button): Colour.Value = {
    if(button.getStyle == "-fx-background-color: black;"){
       Colour.BLACK
    } else {
       Colour.WHITE
    }

  }


  def getInitialTurn(color : Colour.Value): Boolean = {
    if(color == Colour.WHITE) {
      false
    } else {
      true
    }
  }
}