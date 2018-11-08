package controller

import model.{Field, Piece, Player}

class MoveController {
    def checkIfPieceIsValid(field: Field, player: Player) = {
        field.hasPiece && field.getPiece.get.getColour == player.color
    }

    def move(oldField: Field, newField: Field): Boolean = {
        if (newField.hasPiece) {
            return false
        }

        val piece = oldField.getPiece.get

        if (piece.move(oldField, newField)) {
            oldField.clearPiece()
            newField.piece_(Some(piece))

            true
        } else {
            false
        }
    }
}
