package de.htwg.draughts

import de.htwg.draughts.model.BoardCreator

import scala.io.StdIn

object DraughtsTui extends App {
  var board = new BoardCreator(8).setupFields()
  var input: String = ""

  //TODO: tim player cmdLineController hiinzufügen
  //TODO anzeigen des Players, der am zug ist
  do {
    println("Board : \n " + board.toString)
    input = StdIn.readLine()
    //      board = tui.processInputLine(input, board)
  } while (input != "q")
}
