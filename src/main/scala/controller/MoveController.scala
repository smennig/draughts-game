package controller

import model.{Field, Piece, Player}

class MoveController {
    def checkIfPieceIsValid(field: Field, player: Player) = {
        field.hasPiece && field.getPiece.get.getColour == player.color
    }

    def move(oldField: Field, newField: Field) = {
        val piece = oldField.getPiece.get
        oldField.clearPiece()
        newField.piece_(Some(piece))
    }
}
