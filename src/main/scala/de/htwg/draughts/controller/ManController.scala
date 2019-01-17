package de.htwg.draughts.controller

import de.htwg.draughts.model._

class ManController(piece: Man) extends PieceController(piece) {
    // TODO: Write test case
    def move(oldField: Field, newField: Field): Boolean = {
        val rowMove = newField.getRow - oldField.getRow
        val columnMove = newField.getColumn - oldField.getColumn
        (piece.getColour, rowMove, columnMove) match {
            case (Colour.BLACK, 1, 1) => moveHelp(oldField, newField); true
            case (Colour.BLACK, 1, -1) => moveHelp(oldField, newField); true
            case (Colour.WHITE, -1, 1) => moveHelp(oldField, newField); true
            case (Colour.WHITE, -1, -1) => moveHelp(oldField, newField); true
            case (_, _, _) => false
        }
    }

    private def moveHelp(oldField: Field, newField: Field): Unit = {
        oldField.clearPiece()
        if (isFieldKingsRow(newField.getRow, piece.getColour)) {
            val king: Piece = new King(piece.getColour)
            newField.piece_(Some(king))
        } else {
            newField.piece_(Some(piece))
        }
    }

    //  def canMove(currentField: Field, board: Board): Boolean = {
    //    val (firstField: Option[Field], secondField: Option[Field]) = man.getColour match {
    //      case Colour.BLACK => (board.getField(currentField.getColumn - 1)(currentField.getRow + 1), board.getField(currentField.getColumn + 1)(currentField.getRow + 1))
    //      case Colour.WHITE => (board.getField(currentField.getColumn - 1)(currentField.getRow - 1), board.getField(currentField.getColumn + 1)(currentField.getRow - 1))
    //    }
    //
    //    (firstField, secondField) match {
    //      case (Some(f1), Some(f2)) => !f1.hasPiece || !f2.hasPiece
    //      case (Some(f1), None) => !f1.hasPiece
    //      case (None, Some(f2)) => !f2.hasPiece
    //      case (None, None) => false
    //    }
    //  }

    // TODO: Write test case
    def capture(oldField: Field, newField: Field, captureField: Option[Field], player: Player): Boolean = {
        val rowMove = newField.getRow - oldField.getRow
        val columnMove = newField.getColumn - oldField.getColumn
        (piece.getColour, rowMove, columnMove) match {
            case (Colour.BLACK, 2, 2) => captureHelp(oldField, newField, captureField, player); true
            case (Colour.BLACK, 2, -2) => captureHelp(oldField, newField, captureField, player); true
            case (Colour.WHITE, -2, 2) => captureHelp(oldField, newField, captureField, player); true
            case (Colour.WHITE, -2, -2) => captureHelp(oldField, newField, captureField, player); true
            case (_, _, _) => false
        }
    }

    private def captureHelp(oldField: Field, newField: Field, captureField: Option[Field], player: Player): Unit = {
        oldField.clearPiece()
        if (isFieldKingsRow(newField.getRow, piece.getColour)) {
            val king: Piece = new King(piece.getColour)
            newField.piece_(Some(king))
        } else {
            newField.piece_(Some(piece))
        }
        captureField.get.clearPiece()
        player.removePiece()
    }

    //  override def canCapture(currentField: Field, board: Board): Boolean = {
    //    checkIfNextFieldHasOpponentPiece(board, currentField).nonEmpty
    //  }

    // TODO: Write test case
    private def isFieldKingsRow(moveRow: Int, colour: Colour.Value): Boolean = {
        if (moveRow == 0 && colour == Colour.WHITE || moveRow == 7 && colour == Colour.BLACK) {
            true
        } else {
            false
        }
    }

    // TODO: Write test case
    override def checkIfNextFieldHasOpponentPiece(board: Board, ownField: Field): List[Field] = {
        var fieldList: List[Field] = List()

        val nextLeftField = board.getField(ownField.getColumn - 1)(ownField.getRow + getCaptureMoveDependingOnColour(ownField.getPiece.get.getColour))
        val nextRightField = board.getField(ownField.getColumn + 1)(ownField.getRow + getCaptureMoveDependingOnColour(ownField.getPiece.get.getColour))

        if (nextLeftField.isDefined) {
            val overnextLeftField = board.getField(ownField.getColumn - 2)(ownField.getRow + 2 * getCaptureMoveDependingOnColour(ownField.getPiece.get.getColour))
            if (nextLeftField.get.hasPiece && nextLeftField.get.getPiece.get.getColour != ownField.getPiece.get.getColour && overnextLeftField.isDefined && !overnextLeftField.get.hasPiece) {
                fieldList = overnextLeftField.get :: fieldList
            }
        }
        if (nextRightField.isDefined) {
            val overnextRightField = board.getField(ownField.getColumn + 2)(ownField.getRow + 2 * getCaptureMoveDependingOnColour(ownField.getPiece.get.getColour))
            if (nextRightField.get.hasPiece && nextRightField.get.getPiece.get.getColour != ownField.getPiece.get.getColour && overnextRightField.isDefined && !overnextRightField.get.hasPiece) {
                fieldList = overnextRightField.get :: fieldList
            }
        }

        fieldList
    }

    private def getCaptureMoveDependingOnColour(colour: Colour.Value): Int = {
        colour match {
            case Colour.BLACK => 1
            case Colour.WHITE => -1
        }
    }

    override def canMakeValidMove(currentField: Field, board: Board): Boolean = {
        val nextLeftField = getNextField(currentField, board, -1)
        val nextRightField = getNextField(currentField, board, 1)

        checkIfFieldExists(nextLeftField, board, -1) || checkIfFieldExists(nextRightField, board, 1)
    }

    def getNextField(currentField: Field, board: Board, direction: Int): Option[Field] = {
        piece.getColour match {
            case Colour.BLACK => board.getField(currentField.getColumn + direction)(currentField.getRow  + 1)
            case Colour.WHITE => board.getField(currentField.getColumn + direction)(currentField.getRow  - 1)
        }
    }

    def checkIfFieldExists(field: Option[Field], board: Board, direction: Int) = {
        field match {
            case Some(f) => checkIfFieldHasPiece(f, board, direction)
            case None => false
        }
    }

    def checkIfFieldHasPiece(field: Field, board: Board, direction: Int): Boolean = {
        field.getPiece match {
            case Some(p) => if (p.getColour == piece.getColour) false else checkIfCaptureIsPossible(field, board, direction)
            case None => true
        }
    }

    def checkIfCaptureIsPossible(field: Field, board: Board, direction: Int) = {
        getNextField(field, board, direction) match {
            case Some(f) => !f.hasPiece
            case None => false
        }
    }
}
