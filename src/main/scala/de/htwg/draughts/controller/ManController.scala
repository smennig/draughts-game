package de.htwg.draughts.controller

import de.htwg.draughts.model._

class ManController(piece: Man) extends PieceController(piece) {
    /**
      * Moves a man from one field of the board to another
      * @param oldField current position of the Man
      * @param newField the new position of the Man after the move
      * @return true if the move was valid, otherwise false
      */
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

    /**
      * Performs the actual move
      * @param oldField current position of the Man
      * @param newField the new position of the Man after the move
      */
    private def moveHelp(oldField: Field, newField: Field): Unit = {
        oldField.clearPiece()
        if (isFieldKingsRow(newField.getRow, piece.getColour)) {
            val king: Piece = new King(piece.getColour)
            newField.piece_(Some(king))
        } else {
            newField.piece_(Some(piece))
        }
    }

    /**
      * Lets a Man capture a piece of his opponent
      * @param oldField current position of the Man
      * @param newField the new position of the Man after the move
      * @param captureField the position of piece that is about to be captured
      * @return true if the move was successful, otherwise false
      */
    def capture(oldField: Field, newField: Field, captureField: Option[Field]): Boolean = {
        val rowMove = newField.getRow - oldField.getRow
        val columnMove = newField.getColumn - oldField.getColumn
        (piece.getColour, rowMove, columnMove) match {
            case (Colour.BLACK, 2, 2) => captureHelp(oldField, newField, captureField); true
            case (Colour.BLACK, 2, -2) => captureHelp(oldField, newField, captureField); true
            case (Colour.WHITE, -2, 2) => captureHelp(oldField, newField, captureField); true
            case (Colour.WHITE, -2, -2) => captureHelp(oldField, newField, captureField); true
            case (_, _, _) => false
        }
    }

    /**
      * Performs the actual capture
      * @param oldField current position of the Man
      * @param newField the new position of the Man after the move
      * @param captureField the position of piece that is about to be captured
      */
    private def captureHelp(oldField: Field, newField: Field, captureField: Option[Field]): Unit = {
        oldField.clearPiece()
        if (isFieldKingsRow(newField.getRow, piece.getColour)) {
            val king: Piece = new King(piece.getColour)
            newField.piece_(Some(king))
        } else {
            newField.piece_(Some(piece))
        }
        captureField.get.clearPiece()
    }

    /**
      * Checks if the field is on the opposite row of the board
      *
      * @param moveRow the row number of the field
      * @param colour  colour of the current piece
      * @return true if yes, false if no
      */
    private def isFieldKingsRow(moveRow: Int, colour: Colour.Value): Boolean = {
        if (moveRow == 0 && colour == Colour.WHITE || moveRow == 7 && colour == Colour.BLACK) true else false
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

    def checkIfFieldExists(field: Option[Field], board: Board, direction: Int): Boolean = {
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

    def checkIfCaptureIsPossible(field: Field, board: Board, direction: Int): Boolean = {
        getNextField(field, board, direction) match {
            case Some(f) => !f.hasPiece
            case None => false
        }
    }

    def getNextField(currentField: Field, board: Board, direction: Int): Option[Field] = {
        piece.getColour match {
            case Colour.BLACK => board.getField(currentField.getColumn + direction)(currentField.getRow + 1)
            case Colour.WHITE => board.getField(currentField.getColumn + direction)(currentField.getRow - 1)
        }
    }
}
