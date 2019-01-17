package de.htwg.draughts.controller

import de.htwg.draughts.model._

abstract class PieceController(val piece: Piece) {
    def move(oldField: Field, newField: Field): Boolean

    def canMakeValidMove(currentField: Field, board: Board): Boolean

    def capture(oldField: Field, newField: Field, captureField: Option[Field], player: Player): Boolean

    def checkIfNextFieldHasOpponentPiece(board: Board, ownField: Field): List[Field]
}
