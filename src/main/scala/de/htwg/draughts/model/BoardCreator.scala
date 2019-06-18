package de.htwg.draughts.model

class BoardCreator(size: Int = 8) {


    def setupFields(): DraughtsBoard = {

        new DraughtsBoard(size)
    }

    def getPieceColour(row: Int): Colour.Value = {
        row match {
            case x if 0 to 2 contains x => Colour.BLACK
            case x if size - 3 until size contains x => Colour.WHITE
        }
    }

    def setupEmptyBoard(): DraughtsBoard = {
        new DraughtsBoard(size, true)
    }


    def setupCustomBoard(): DraughtsBoard = {
        val board = new DraughtsBoard(size, true)


        // TODO  generate this setup
        board.setPieceOnField(1)(5)(new Man(Colour.WHITE))
        board.setPieceOnField(4)(0)(new Man(Colour.BLACK))
        board.setPieceOnField(4)(2)(new Man(Colour.BLACK))
        board
    }

}
