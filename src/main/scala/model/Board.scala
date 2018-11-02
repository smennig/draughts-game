package model

class Board(size: Int) {
    var fields :Array[Array[Field]] = Array.ofDim[Field](size, size)
//TODO move to Controller
    def setupFields() {
        for (i <- 0 until size; j <- 0 until  size) {
            val field = new Field(row=i, column = j)
            if (field.getColour == Colour.BLACK && ((i >= 0 && i < 3) || (i >= size - 3 && i < size))) {
                val piece = new Man(getPieceColour(i), field)
            }
            fields(i)(j) = field
        }
    }

    def getPieceColour(row: Int): Colour.Value = {
        row match {
            case x if 0 to 2 contains x => Colour.BLACK
            case x if size - 3 until size contains x => Colour.WHITE
        }
    }

    override def toString: String = {
        val blank = " "
        var lineHead = blank

        for (i <- 1 to size){
            lineHead += " "+ i +"  "
        }
        val lineseparator = ("|" + ("---|" * size))  + "\n"

        var output = lineHead +"\n" + " " + lineseparator

        var line = ("|" + (" x |" * size))+ "\n"
        for (i <-1 to size) {
            output += i +line +  blank +lineseparator
        }
        for {
            row <- 0 until size
            col <- 0 until size
        } output = output.replaceFirst("x", fields(row)(col).getPiece match{
            case Some(piece) => piece.toString
            case None => blank
        })
        output
    }
}
