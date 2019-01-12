package de.htwg.draughts.model


class DraughtsBoard(override val size: Int, val fields: Array[Array[Field]]) extends Board {

  override def toString(): String = {
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

  def getField(column: Int)(row: Int): Option[Field] = {
    if (column < 0 || column > 7 || row < 0 || row > 7) None else Some(fields(row)(column))
  }

  override def iterator: Iterator[Field] = {
    new BoardIterator(fields)
  }
}