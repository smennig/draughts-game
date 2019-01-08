package de.htwg.draughts

import controller.{CommandLineController, MoveController}
import model.{BoardCreator, Player}

import scala.io.StdIn

class DraughtsTui {
  def run(): Unit = {

    val clc = new CommandLineController
    val playerTuple: (Player, Player) = clc.readPlayerAttributes()

    val board = new BoardCreator().setupFields()
    val moveController = new MoveController(board, playerTuple._1, playerTuple._2)
    clc.performTurn(playerTuple._1, playerTuple._2, board)
  }
}
