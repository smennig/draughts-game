package de.htwg.draughts.controller

import de.htwg.draughts.model.{King, Man, Piece}

class PieceControllerFactory {
    def getPieceController(piece: Piece): PieceController = {
        piece match {
            case m: Man => new ManController(m)
            case k: King => new KingController(k)
        }
    }
}
