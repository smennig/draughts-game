package de.htwg.draughts

import controller.{CommandLineController, MoveController}
import model.{BoardCreator, Player}

import scala.io.StdIn

object DraughtsTui extends App {
  /*
  var board = new BoardCreator(8).setupFields()
  var input: String = ""

  //TODO: tim player cmdLineController hiinzufügen
  //TODO anzeigen des Players, der am zug ist
  do {
    println("Board : \n " + board.toString)
    input = StdIn.readLine()
    //      board = tui.processInputLine(input, board)
  } while (input != "q")
  */

  val clc = new CommandLineController
  val playerTuple : (Player, Player) = clc.readPlayerAttributes()
  val board = new BoardCreator().setupFields()
  val moveController = new MoveController(board, playerTuple._1, playerTuple._2)
  clc.addController(moveController)
  clc.performTurn(playerTuple._1, playerTuple._2, board)

}
