package de.htwg.draughts.model

class DraughtsBoard(override val size: Int, emptyBoard: Boolean = false) extends Board {

    val fields: Array[Array[Field]] = {
        if (emptyBoard) {
            Array.tabulate[Field](size, size) { case (row, col) => new Field(row =row, column = col) }
        }
        else {
            Array.tabulate[Field](size, size) { case (row, col) =>
                val field = new Field(column = col, row = row)
                if (field.getColour == Colour.BLACK && ((row >= 0 && row < 3) || (row >= size - 3 && row < size))) {
                    field.piece_(Some(new Man(getPieceColour(row))))
                }
                field
            }
        }
    }

    private def getPieceColour(row: Int): Colour.Value = {
        row match {
            case x if 0 to 2 contains x => Colour.BLACK
            case x if size - 3 until size contains x => Colour.WHITE
            case _ => Colour.WHITE
        }
    }

    override def toString: String = {
        val blank = " "
        var lineHead = blank

        for (i <- 1 to size) {
            lineHead += " " + i + "  "
        }
        val lineSeparator = ("|" + ("---|" * size)) + "\n"

        var output = lineHead + "\n" + " " + lineSeparator

        val line = ("|" + (" x |" * size)) + "\n"
        for (i <- 1 to size) {
            output += i + line + blank + lineSeparator
        }
        for {
            row <- 0 until size
            col <- 0 until size
        } output = output.replaceFirst("x", fields(row)(col).getPiece match {
            case Some(piece) => piece.toString
            case None => blank
        })
        output
    }

    def setPieceOnField(column: Int)(row: Int)(piece: Piece): Boolean = {
        if (column < 0 || column >= size || row < 0 || row >= size) false else {
            fields(row)(column).piece_(Some(piece))
            true
        }
    }

    def getField(column: Int)(row: Int): Option[Field] = {
        if (column < 0 || column >=size || row < 0 || row >= size ) None else Some(fields(row)(column))
    }

    override def iterator: Iterator[Field] = new BoardIterator(fields)
}
