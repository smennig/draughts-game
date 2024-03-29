package de.htwg.draughts.controller

import akka.actor.Actor
import de.htwg.draughts.controller.StopGameChecker.CheckPlayer
import de.htwg.draughts.model.{Board, Colour}

object StopGameChecker {

    case class CheckPlayer(board: Board, colourTurn: Colour.Value)
}

class StopGameChecker extends Actor {
    override def receive: Receive = {
        case CheckPlayer(board, colourTurn) => sender ! checkIfValid(board, colourTurn)
        case _ => sender ! false
    }

    def checkIfValid(board: Board, colourTurn: Colour.Value): Boolean = {
        var i = 0
        var canMove = false
        val factory = new PieceControllerFactory
        for (field <- board.iterator) {
            if (field.hasPiece && field.getPiece.get.getColour == colourTurn) {
                i += 1
                val pieceController: PieceController = factory.getPieceController(field.getPiece.get)
                if (pieceController.canMakeValidMove(field, board)) canMove = true
            }
        }

        if (i == 0 || !canMove) true else false
    }
}