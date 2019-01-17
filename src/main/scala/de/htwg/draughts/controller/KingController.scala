package de.htwg.draughts.controller

import de.htwg.draughts.model._

class KingController(piece: King) extends PieceController(piece) {
    def move(oldField: Field, newField: Field): Boolean = {
        oldField.clearPiece()
        newField.piece_(Some(piece))
        true
    }

    //  def canMove(currentField: Field, board: Board): Boolean = {
    //    val topRight = canMoveRec1(board.getField(currentField.getColumn + 1)(currentField.getRow + 1), board, currentField.getPiece.get.getColour, 1, 1)
    //    val topLeft = canMoveRec1(board.getField(currentField.getColumn - 1)(currentField.getRow + 1), board, currentField.getPiece.get.getColour, -1, 1)
    //    val bottomLeft = canMoveRec1(board.getField(currentField.getColumn - 1)(currentField.getRow - 1), board, currentField.getPiece.get.getColour, -1, -1)
    //    val bottomRight = canMoveRec1(board.getField(currentField.getColumn + 1)(currentField.getRow - 1), board, currentField.getPiece.get.getColour, 1, -1)
    //
    //    topRight || topLeft || bottomLeft || bottomRight
    //  }
    //
    //  def canMoveRec1(field: Option[Field], board: Board, ownColour: Colour.Value, stepColumn: Int, stepRow: Int): Boolean = {
    //    field match {
    //      case Some(f) => {
    //        if (f.hasPiece && f.getPiece.get.getColour == ownColour) false else {
    //          canMoveRec2(board.getField(f.getColumn + stepColumn)(f.getRow + stepRow), board, ownColour, stepColumn, stepRow)
    //        }
    //      }
    //      case None => false
    //    }
    //  }
    //
    //  def canMoveRec2(field: Option[Field], board: Board, ownColour: Colour.Value, stepColumn: Int, stepRow: Int): Boolean = {
    //    field match {
    //      case Some(f) => {
    //        if (f.hasPiece) {
    //          if (f.getPiece.get.getColour == ownColour) true else board.getField(f.getColumn + stepColumn)(f.getRow + stepRow).isEmpty
    //        } else {
    //          canMoveRec2(board.getField(f.getColumn + stepColumn)(f.getRow + stepRow), board, ownColour, stepColumn, stepRow)
    //        }
    //      }
    //      case None => true
    //    }
    //  }

    def capture(oldField: Field, newField: Field, captureField: Option[Field], player: Player): Boolean = {
        val rowMove = newField.getRow - captureField.get.getRow
        val columnMove = newField.getColumn - captureField.get.getColumn
        (rowMove, columnMove) match {
            case (1, 1) => captureHelp(oldField, newField, captureField, player); true
            case (1, -1) => captureHelp(oldField, newField, captureField, player); true
            case (-1, 1) => captureHelp(oldField, newField, captureField, player); true
            case (-1, -1) => captureHelp(oldField, newField, captureField, player); true
            case (_, _) => false
        }
    }

    private def captureHelp(oldField: Field, newField: Field, captureField: Option[Field], player: Player): Unit = {
        oldField.clearPiece()
        newField.piece_(Some(piece))
        captureField.get.clearPiece()
        player.removePiece()
    }

    //  override def canCapture(currentField: Field, board: Board): Boolean = {
    //    checkIfNextFieldHasOpponentPiece(board, currentField).nonEmpty
    //  }

    override def checkIfNextFieldHasOpponentPiece(board: Board, ownField: Field): List[Field] = {
        var fieldList: List[Field] = List()

        val startColumn = ownField.getColumn
        val startRow = ownField.getRow

        val topRightField = checkFieldsRec(board, startColumn + 1, startRow + 1, 1, 1)
        val topLeftField = checkFieldsRec(board, startColumn - 1, startRow + 1, -1, 1)
        val bottomRightField = checkFieldsRec(board, startColumn + 1, startRow - 1, 1, -1)
        val bottomLeftField = checkFieldsRec(board, startColumn - 1, startRow - 1, -1, -1)

        if (topRightField.isDefined && topRightField.get.getPiece.get.getColour != ownField.getPiece.get.getColour) {
            val nextAfterTopRightField = board.getField(topRightField.get.getColumn + 1)(topRightField.get.getRow + 1)
            if (nextAfterTopRightField.isDefined && !nextAfterTopRightField.get.hasPiece) {
                fieldList = nextAfterTopRightField.get :: fieldList
            }
        }

        if (topLeftField.isDefined && topLeftField.get.getPiece.get.getColour != ownField.getPiece.get.getColour) {
            val nextAfterTopLeftField = board.getField(topLeftField.get.getColumn - 1)(topLeftField.get.getRow + 1)
            if (nextAfterTopLeftField.isDefined && !nextAfterTopLeftField.get.hasPiece) {
                fieldList = nextAfterTopLeftField.get :: fieldList
            }
        }

        if (bottomRightField.isDefined && bottomRightField.get.getPiece.get.getColour != ownField.getPiece.get.getColour) {
            val nextAfterBottomRightField = board.getField(bottomRightField.get.getColumn + 1)(bottomRightField.get.getRow - 1)
            if (nextAfterBottomRightField.isDefined && !nextAfterBottomRightField.get.hasPiece) {
                fieldList = nextAfterBottomRightField.get :: fieldList
            }
        }

        if (bottomLeftField.isDefined && bottomLeftField.get.getPiece.get.getColour != ownField.getPiece.get.getColour) {
            val nextAfterBottomLeftField = board.getField(bottomLeftField.get.getColumn - 1)(bottomLeftField.get.getRow - 1)
            if (nextAfterBottomLeftField.isDefined && !nextAfterBottomLeftField.get.hasPiece) {
                fieldList = nextAfterBottomLeftField.get :: fieldList
            }
        }

        fieldList
    }

    private def checkFieldsRec(board: Board, column: Int, row: Int, columnMove: Int, rowMove: Int): Option[Field] = {
        val field = board.getField(column)(row)
        field match {
            case Some(f) => if (f.hasPiece) Some(f) else checkFieldsRec(board, column + columnMove, row + rowMove, columnMove, rowMove)
            case None => None
        }
    }

    override def canMakeValidMove(currentField: Field, board: Board): Boolean = {
        val topRightField = getNextField(currentField, board, 1, 1)
        val topLeftField = getNextField(currentField, board, -1, 1)
        val bottomLeftField = getNextField(currentField, board, -1, -1)
        val bottomRightField = getNextField(currentField, board, 1, -1)

        checkIfFieldExists(topRightField, board, 1, 1) || checkIfFieldExists(topLeftField, board, -1, 1) || checkIfFieldExists(bottomLeftField, board, -1, -1) || checkIfFieldExists(bottomRightField, board, 1, -1)
    }

    def getNextField(currentField: Field, board: Board, columnDirection: Int, rowDirection: Int): Option[Field] = {
        board.getField(currentField.getColumn + columnDirection)(currentField.getRow + rowDirection)
    }

    def checkIfFieldExists(field: Option[Field], board: Board, columnDirection: Int, rowDirection: Int): Boolean = {
        field match {
            case Some(f) => checkIfFieldHasPiece(f, board, columnDirection, rowDirection)
            case None => false
        }
    }

    def checkIfFieldHasPiece(field: Field, board: Board, columnDirection: Int, rowDirection: Int): Boolean = {
        field.getPiece match {
            case Some(p) => if (p.getColour == piece.getColour) false else checkIfCaptureIsPossible(field, board, columnDirection, rowDirection)
            case None => true
        }
    }

    def checkIfCaptureIsPossible(field: Field, board: Board, columnDirection: Int, rowDirection: Int) = {
        getNextField(field, board, columnDirection, rowDirection) match {
            case Some(f) => !f.hasPiece
            case None => false
        }
    }
}
