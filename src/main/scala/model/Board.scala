package model

class Board(size: Int, val fields: Array[Array[Field]]) {


  override def toString: String = {
    val blank = " "
    var lineHead = blank

    for (i <- 1 to size) {
      lineHead += " " + i + "  "
    }
    val lineseparator = ("|" + ("---|" * size)) + "\n"

    var output = lineHead + "\n" + " " + lineseparator

    var line = ("|" + (" x |" * size)) + "\n"
    for (i <- 1 to size) {
      output += i + line + blank + lineseparator
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

  def getField(column: Int)(row: Int): Field = {
    fields(row)(column)
  }
}
