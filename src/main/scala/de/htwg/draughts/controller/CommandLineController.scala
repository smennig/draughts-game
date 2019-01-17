package de.htwg.draughts.controller


import com.google.inject.Inject
import de.htwg.draughts.model.{Colour, Player}

import scala.io.StdIn.readLine

class CommandLineController @Inject()() {

  var moveController: GameController = _;
  var BOARD_SIZE = 8


  def readPlayerAttributes(): (Player, Player) = {
    println("Willkommen bei Scala-Dame")
    println("Spieler 1: Bitte geben Sie Ihren Namen ein: ")
    val nameInputOne = readLine()

    println("Hallo " + nameInputOne + ": Bitte wählen sie Ihre Farbe")
    println("Geben sie dazu 'b' oder 'w' ein")

    var colorOne: Option[Colour.Value] = None
    while (colorOne isEmpty) {
      val inputColor = readLine()
      colorOne = chooseFirstColor(inputColor)
    }

    val playerOne = new Player(nameInputOne, colorOne.get, true)

    println("Spieler 2: Bitte geben Sie Ihren Namen ein: ")
    val nameInputTwo = readLine()
    val colorTwo = chooseSecondColor(colorOne.get)
    val playerTwo = new Player(nameInputTwo, colorTwo, false)

    println("Es spielt: " + playerOne.name + " mit " + translateEnumColour(playerOne.color)  + " gegen " + playerTwo.name + " mit " + translateEnumColour(playerTwo.color))
    if (playerOne.color == Colour.BLACK) {
      val boardTuple: (Player, Player) = (playerOne, playerTwo)
      boardTuple
    } else {
      val boardTuple: (Player, Player) = (playerTwo, playerOne)
      boardTuple
    }
  }

  def chooseFirstColor(colorInput: String): Option[Colour.Value] = {
    colorInput match {
      case "w" => println("Sie spielen Weiß"); Option(Colour.WHITE);
      case "b" => println("Sie spielen Schwarz"); Option(Colour.BLACK);
      case _ => println(colorInput + " ist eine ungültige Eingabe: Wählen sie 'b' oder 'w'"); None;
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
      case Colour.BLACK => "b"
      case Colour.WHITE => "w"
    }
  }

  def checkCoordinate(coordinate: String): Option[Int] = {
    val NumberPattern = "^([1-8])$".r
    val QuitPattern = "^([q])$".r
    coordinate match {
      case NumberPattern(`coordinate`) => Option(coordinate.toInt);
      case QuitPattern(`coordinate`) => println("Spiel auf Wunsch eines Spielers abgebrochen");
                                        System.exit(0); None;
      case _ => println(coordinate + " ist eine ungültige Eingabe: Wählen sie eine ganze Zahl zwischen 1 und 8"); None;
    }
  }

  def performTurn(currentPlayer: Player, nextPlayer: Player): Unit = {
    println(moveController.board)
    readGameMoves(currentPlayer)

    if(moveController.checkIfGameIsOver()) {
      printWin(currentPlayer)
      return
    }
    if(moveController.colourTurn == nextPlayer.color){
      performTurn(nextPlayer, currentPlayer)
    } else {
      performTurn(currentPlayer, nextPlayer)
    }


  }

  def readGameMoves(currentTurnPlayer: Player): Unit = {
    println(currentTurnPlayer.name +  " ist am Zug:")

    println("Bitte geben sie die Koordinaten der Figur an, die bewegt werden soll:")
    println("Möchten sie das Spiel beenden, geben sie bitte 'q' ein.")
    println("Geben sie dazu die X - Koordinate der Figur als ganze Zahl (1-8) ein.")
    var xCoordPiece: Option[Int] = None
    while(xCoordPiece isEmpty) {
      val inputCoord = readLine()
      xCoordPiece = checkCoordinate(inputCoord)
    }

    println("Geben sie nun die Y - Koordinate der Figur als ganze Zahl(1-8) ein.")
    var yCoordPiece: Option[Int] = None
    while(yCoordPiece isEmpty) {
      val inputCoord = readLine()
      yCoordPiece = checkCoordinate(inputCoord)
    }

    println("Bitte geben sie nun die Zielkoordinaten des Zuges an:")
    println("Geben sie dazu die X - Koordinate des Ziels als ganze Zahl (1-8) ein.")

    var xCoordTarget: Option[Int] = None
    while(xCoordTarget isEmpty) {
      val inputCoord = readLine()
      xCoordTarget = checkCoordinate(inputCoord)
    }

    println("Geben sie nun die Y - Koordinate des Ziels als ganze Zahl(1-8) ein.")
    var yCoordTarget: Option[Int] = None
    while(yCoordTarget isEmpty) {
      val inputCoord = readLine()
      yCoordTarget = checkCoordinate(inputCoord)
    }

    println("Versuche Figur " + xCoordPiece.get + "|" + yCoordPiece.get + " nach " + xCoordTarget.get + "|" + yCoordTarget.get + " zu bewegen")
    val (validMove, winPlayer) = moveController.move(xCoordPiece.get - 1, yCoordPiece.get - 1, xCoordTarget.get - 1, yCoordTarget.get - 1)


    winPlayer match {
      case Some(player) => printWin(player)
      case None =>
    }

    if (moveController.checkIfGameIsOver()) {
      printWin(currentTurnPlayer)
      return
    }

    if(!validMove){
      println("Zug ungültig! Bitte wählen sie einen anderen Zug)")
      readGameMoves(currentTurnPlayer)
    }

  }

  def printWin(winningPlayer: Player): Unit = {
    println("Glückwunsch, " + winningPlayer.name + " hat das Spiel gewonnen!")
    println("Das Spiel wird beendet...")
    System.exit(0)
  }

  def addController(moveController: GameController): Unit = {
    this.moveController = moveController
  }
}