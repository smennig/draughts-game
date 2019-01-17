package de.htwg.draughts

import com.google.inject.Inject
import controller.{CommandLineController, GameControllerFactory, MoveController}
import model.{Board, BoardCreator, Player}

class DraughtsTui @Inject()(clc: CommandLineController, val board: Board, gameControllerFactory: GameControllerFactory) extends Runnable {
  def run(): Unit = {

    val playerTuple: (Player, Player) = clc.readPlayerAttributes()

    val moveController = gameControllerFactory.create(playerTuple._1, playerTuple._2)

    clc.addController(moveController)
    clc.performTurn(playerTuple._1, playerTuple._2)
  }
}
