import model.{Board, BoardCreator}

import scala.io.StdIn

object Draughts extends App {
  var board =   new BoardCreator(8).setupFields()
    var input: String = ""

    do {
      println("Board : \n " + board.toString)
      input =  StdIn.readLine()
      //      board = tui.processInputLine(input, board)
    } while (input != "q")
}
