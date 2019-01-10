package de.htwg.draughts.model

class BoardIterator(board: Array[Array[Field]]) extends Iterator[Field] {
    var position = (0, -1)

    override def hasNext: Boolean = {
        val length = board.length
        if (position._1 < length && position._2 < length - 1) {
            position = (position._1, position._2 + 1)
            true
        } else if (position._1 < length - 1 && position._2 >= length - 1) {
            position = (position._1 + 1, 0)
            true
        }
        else false
    }

    override def next(): Field = {
        board(position._1)(position._2)
    }
}
