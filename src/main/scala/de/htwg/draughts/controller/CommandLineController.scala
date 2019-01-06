package de.htwg.draughts.controller

import de.htwg.draughts.DraughtsTui.{board, clc, playerTuple}
import de.htwg.draughts.model._
import de.htwg.draughts.model.{Colour, Player}

import scala.io.StdIn.readLine

class CommandLineController {

  var moveController : MoveController = null;
  var BOARD_SIZE = 8


  def readPlayerAttributes(): (Player, Player) = {
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

    val playerOne = new Player(nameInputOne, colorOne.get, true)

    println("Spieler 2: Bitte geben Sie Ihren Namen ein: ")
    val nameInputTwo = readLine()
    val colorTwo = chooseSecondColor(colorOne.get)
    val playerTwo = new Player(nameInputTwo, colorTwo, false)

    println("Es spielt: " + playerOne.name + " mit " + translateEnumColour(playerOne.color)  + " gegen " + playerTwo.name + " mit " + translateEnumColour(playerTwo.color))
    val boardTuple : (Player, Player) = (playerOne, playerTwo);
    boardTuple
  }

  def chooseFirstColor(colorInput: String): Option[Colour.Value] = {
    colorInput match {
      case "Weiß" => println("Sie spielen Weiß"); Option(Colour.WHITE);
      case "Schwarz" => println("Sie spielen Schwarz"); Option(Colour.BLACK);
      case _ => println(colorInput + " ist eine ungültige Eingabe: Wählen sie 'Schwarz' oder 'Weiß'"); None;
    }
  }

  def initializeGame(): Unit = {
    val playerTuple : (Player, Player) = readPlayerAttributes();
    val board = new BoardCreator().setupFields();
    moveController = new MoveController(board, playerTuple._1, playerTuple._2)
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

  def checkCoordinate(coordinate: String): Option[Int] ={
    val Pattern = "^([1-8])$".r
    coordinate match {
      case Pattern(coordinate) => Option(coordinate.toInt);
      case _ => println(coordinate + " ist eine ungültige Eingabe: Wählen sie eine ganze Zahl zwischen 1 und 8"); None;
    }
  }

  def performTurn(currentPlayer: Player, nextPlayer: Player, currentBoard: Board): Unit = {
    println(currentBoard.toString())
    readGameMoves(currentPlayer)

    if(moveController.checkIfGameIsOver()) {
      printWin(currentPlayer)
      return
    }
    performTurn(nextPlayer, currentPlayer, currentBoard)

  }

  def readGameMoves(currentTurnPlayer: Player): Unit = {
    println(currentTurnPlayer.name +  " ist am Zug:")

    println("Bitte geben sie die Koordinaten der Figur an, die bewegt werden soll:")
    println("Geben sie dazu die X - Koordinate der Figur als ganze Zahl (1-8) ein");
    var xCoordPiece: Option[Int] = None
    while(xCoordPiece isEmpty) {
      val inputCoord = readLine()
      xCoordPiece = checkCoordinate(inputCoord)
    }
    println("Geben sie nun die Y - Koordinate der Figur als ganze Zahl(1-8) ein");
    var yCoordPiece: Option[Int] = None
    while(yCoordPiece isEmpty) {
      val inputCoord = readLine()
      yCoordPiece = checkCoordinate(inputCoord)
    }

    println("Bitte geben sie nun die Zielkoordinaten des Zuges an:")
    println("Geben sie dazu die X - Koordinate des Ziels als ganze Zahl (1-8) ein");
    var xCoordTarget: Option[Int] = None
    while(xCoordTarget isEmpty) {
      val inputCoord = readLine()
      xCoordTarget = checkCoordinate(inputCoord)
    }

    println("Geben sie nun die Y - Koordinate des Ziels als ganze Zahl(1-8) ein");
    var yCoordTarget: Option[Int] = None
    while(yCoordTarget isEmpty) {
      val inputCoord = readLine()
      yCoordTarget = checkCoordinate(inputCoord)
    }

    //ToDo: Add Move Method
    println("Versuche Figur " + xCoordPiece.get + "|" + yCoordPiece.get + " nach " + xCoordTarget.get + "|" + yCoordTarget.get + " zu bewegen")
    val validMove : Boolean = moveController.move(xCoordPiece.get, yCoordPiece.get, xCoordTarget.get, yCoordTarget.get)
    if(!validMove){
       println("Zug ungültig! Bitte wählen sie einen anderen Zug)")
      readGameMoves(currentTurnPlayer);
    }

  }

  def printWin(winningPlayer: Player): Unit = {
    println("Glückwunsch, " + winningPlayer.name + " hat das Spiel gewonnen")
    println("Starte Neues Spiel")
    //ToDo Add choice to restart game?
    readPlayerAttributes()

  }

  def setupFields(): Board = {

    var board = new Board(BOARD_SIZE, new BoardCreator(BOARD_SIZE).setupFields().fields)
    for (i <- 0 until BOARD_SIZE; j <- 0 until BOARD_SIZE) {
      val field = new Field(row = i, column = j)
      if (field.getColour == Colour.BLACK && ((i >= 0 && i < 3) || (i >= BOARD_SIZE - 3 && i < BOARD_SIZE))) {
        val piece = new Man(getPieceColour(i))
        field.piece_(Some(piece))
      }
      board.fields(i)(j) = field
    }
    println(board.toString)
    board
  }

  def getPieceColour(row: Int): Colour.Value = {
    row match {
      case x if 0 to 2 contains x => Colour.BLACK
      case x if BOARD_SIZE - 3 until BOARD_SIZE contains x => Colour.WHITE
    }
  }
}