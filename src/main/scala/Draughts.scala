package main

import model.Board

import scala.io.StdIn

object Draughts extends App {
  var board = new Board(9)
board.setupFields()
    var input: String = ""

    do {
      println("Board : \n " + board.toString)
      input =  StdIn.readLine()
      //      board = tui.processInputLine(input, board)
    } while (input != "q")
}
