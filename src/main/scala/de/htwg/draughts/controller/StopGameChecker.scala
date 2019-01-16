package de.htwg.draughts.controller

import akka.Done
import akka.actor.Actor
import akka.dispatch.sysmsg.Failed
import de.htwg.draughts.controller.StopGameChecker.{CheckPlayer, StopGame, ContinueGame}
import de.htwg.draughts.model.{Board, Colour}

object StopGameChecker {
  case class CheckPlayer(board: Board, colourTurn: Colour.Value) {

  }
  case class ContinueGame()
  case class StopGame()
}

class StopGameChecker extends Actor {
  override def receive: Receive = {
    case CheckPlayer(board, colourTurn) => sender ! checkIfValid(board, colourTurn)
    case _ => sender ! false
  }

  def checkIfValid(board: Board, colourTurn: Colour.Value): Boolean = {
    var i = 0
    for (field <- board.iterator) {
      if (field.hasPiece && field.getPiece.get.getColour == colourTurn) i += 1
    }

    if (i == 0) false else true
  }
}