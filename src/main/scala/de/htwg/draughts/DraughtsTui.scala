package de.htwg.draughts

import com.google.inject.Inject
import de.htwg.draughts.controller.{CommandLineController, GameControllerFactory}
import de.htwg.draughts.model.{Board, Player}

class DraughtsTui @Inject()(clc: CommandLineController, val board: Board, gameControllerFactory: GameControllerFactory) extends Runnable {
    def run(): Unit = {

        val playerTuple: (Player, Player) = clc.readPlayerAttributes()

        val moveController = gameControllerFactory.create(playerTuple._1, playerTuple._2)

        clc.addController(moveController)
        clc.performTurn(playerTuple._1, playerTuple._2)
    }
}
