package de.htwg.draughts

import com.google.inject.Inject
import controller.CommandLineController
import model.{Board, BoardCreator, Player}

class DraughtsTui @Inject()(clc: CommandLineController, val board: Board) extends Runnable {
  def run(): Unit = {

    val playerTuple: (Player, Player) = clc.readPlayerAttributes()


    clc.performTurn(playerTuple._1, playerTuple._2, board)
  }
}
