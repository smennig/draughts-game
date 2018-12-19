package de.htwg.draughts.controller

import de.htwg.draughts.model.{Colour, Player}

import scala.io.StdIn.readLine

class CommandLineController {

  def readPlayerAttributes() {
    println("Willkommen bei Scala-Dame")
    println("Spieler 1: Bitte geben Sie Ihren Namen ein: ")
    val nameInputOne = readLine()

    println("Hallo " + nameInputOne + ": Bitte wählen sie Ihre Farbe")
    println("Geben sie dazu 'Schwarz' oder 'Weiß' ein")
    var colorOne: Option[Colour.Value] = None
    while (colorOne isEmpty) {
      val inputColor = readLine()
      colorOne = chooseFirstColor(inputColor)
    }

    val playerOne = new Player(nameInputOne, colorOne.get)

    println("Spieler 2: Bitte geben Sie Ihren Namen ein: ")
    val nameInputTwo = readLine()
    val colorTwo = chooseSecondColor(colorOne.get)
    val playerTwo = new Player(nameInputTwo, colorTwo)

    println("Es spielt: " + playerOne.name + " mit " + translateEnumColour(playerOne.color) + " gegen " + playerTwo.name + " mit " + playerTwo.color)


  }

  def chooseFirstColor(colorInput: String): Option[Colour.Value] = {
    colorInput match {
      case "Weiß" => println("Sie spielen Weiß"); Option(Colour.WHITE);
      case "Schwarz" => println("Sie spielen Schwarz"); Option(Colour.BLACK);
      case _ => println(colorInput + " ist eine ungültige Eingabe: Wählen sie 'Schwarz' oder 'Weiß'"); None;
    }
  }

  def chooseSecondColor(input: Colour.Value): Colour.Value = {
    input match {
      case Colour.BLACK => println("Sie spielen Weiß"); Colour.WHITE
      case Colour.WHITE => println("Sie spielen Schwarz"); Colour.BLACK
    }
  }

  def translateEnumColour(input: Colour.Value): String = {
    input match {
      case Colour.BLACK => "Schwarz"
      case Colour.WHITE => "Weiß"
    }
  }
}