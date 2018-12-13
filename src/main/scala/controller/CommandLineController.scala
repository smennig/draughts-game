package controller

import model.{Player, PlayerColor}

import scala.io.StdIn.readLine

class CommandLineController {

    def readPlayerAttributes() {
      println("Willkommen bei Scala-Dame")
      println("Spieler 1: Bitte geben Sie Ihren Namen ein: ")
      val nameInputOne =  readLine()

      println("Hallo " + nameInputOne + ": Bitte wählen sie Ihre Farbe")
      println("Geben sie dazu 'Schwarz' oder 'Weiß' ein")
      val colorOne = chooseFirstColor()
      val playerOne = new Player(nameInputOne, colorOne)

      println("Spieler 2: Bitte geben Sie Ihren Namen ein: ")
      val nameInputTwo =  readLine()
      val colorTwo = chooseSecondColor(colorOne)
      val playerTwo = new Player(nameInputTwo, colorTwo)

      println("Es spielt: " + playerOne.name + " mit " + playerOne.color  + " gegen " + playerTwo.name + " mit " + playerTwo.color)


    }

  def chooseFirstColor(): PlayerColor.Value = {
    val colorInput = readLine()
    colorInput match {
      case "Weiß" => println("Sie spielen Weiß"); PlayerColor.Weiß
      case "Schwarz" => println("Sie spielen Schwarz"); PlayerColor.Schwarz
      case _ => println(colorInput + " ist eine ungültige Eingabe: Wählen sie 'Schwarz' oder 'Weiß'"); chooseFirstColor()
    }
  }

  def chooseSecondColor(input: PlayerColor.Value): PlayerColor.Value = {
    input match {
      case PlayerColor.Schwarz => println("Sie spielen Weiß"); PlayerColor.Weiß
      case PlayerColor.Weiß => println("Sie spielen Schwarz"); PlayerColor.Schwarz
    }
  }
}