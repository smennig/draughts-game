package model

class Board(size: Int) {
    var fields = Array.ofDim[Field](size, size)

    def setupFields() {
        for (i <- 0 until size; j <- 0 until  size) {
            val field = new Field(row=i, column = j)
            if (field.getColour == Colour.BLACK && ((i >= 0 && i < 3) || (i >= 5 && i < 8))) {
                val piece = new Man(getPieceColour(i), field)
            }
            fields(i)(j) = field
        }
    }

    def getPieceColour(row: Int): Colour.Value = {
        row match {
            case x if 0 to 2 contains x => Colour.BLACK
            case x if 5 to 7 contains x => Colour.WHITE
        }
    }
}
