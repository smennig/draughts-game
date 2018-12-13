package model

class BoardCreator(size:Int) {

  def setupFields():Board = {

    var board = new Board(size)
    for (i <- 0 until size; j <- 0 until  size) {
      val field = new Field(row=i, column = j)
      if (field.getColour == Colour.BLACK && ((i >= 0 && i < 3) || (i >= size - 3 && i < size))) {
        val piece = new Man(getPieceColour(i))
        field.piece_(Some(piece))
      }
      board.fields(i)(j) = field
    }
    board
  }

  def getPieceColour(row: Int): Colour.Value = {
    row match {
      case x if 0 to 2 contains x => Colour.BLACK
      case x if size - 3 until size contains x => Colour.WHITE
    }
  }

}
