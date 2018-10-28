package model

class Field(column: Int, row: Int) {
    var piece = None: Option[Piece]
    def getColumn: Int = column
    def getRow: Int = row

    def piece_(piece: Some[Piece]): Unit = { this.piece = piece }
    def getPiece: Option[Piece] = piece
    def hasPiece: Boolean = {
        piece match {
            case Some(_) => true
            case None => false
        }
    }

    def getColour: Colour.Value = {
        (row % 2, column %2) match {
            case (0, 0) => Colour.BLACK
            case (0, 1) => Colour.WHITE
            case (1, 0) => Colour.WHITE
            case (1, 1) => Colour.BLACK
        }
    }
}