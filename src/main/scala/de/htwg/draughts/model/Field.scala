package de.htwg.draughts.model

class Field(column: Int, row: Int, var highlighted: Boolean = false) {
    var piece: Option[Piece] = Option.empty

    def getColumn: Int = column

    def getRow: Int = row

    def piece_(piece: Some[Piece]): Unit = {
        this.piece = piece
    }

    def getPiece: Option[Piece] = piece

    def clearPiece(): Unit = {
        piece = None: Option[Piece]
    }

    def hasPiece: Boolean = {
        piece match {
            case Some(_) => true
            case None => false
        }
    }

    def getColour: Colour.Value = {
        (row % 2, column % 2) match {
            case (0, 0) => Colour.BLACK
            case (0, 1) => Colour.WHITE
            case (1, 0) => Colour.WHITE
            case (1, 1) => Colour.BLACK
            case _ => Colour.BLACK
        }
    }
}