package model

import model.{Board, BoardCreator, Colour}
import org.scalatest.Matchers._
import org.scalatest.WordSpec

class BoardSpec extends WordSpec {
    "A Board" should {
        "have a Array of fields with the passed size" in {
            val board: Board = new Board(8)
            board.fields should not be empty
            board.fields.size should be(8)
        }

        "have a Array of fields with the same coordinates in the filed and array" in {
            val board: Board = new BoardCreator(8).setupFields()
            board.fields(0)(0).getColumn should be(0)
            board.fields(0)(0).getRow should be(0)

            board.fields(7)(5).getColumn should be(5)
            board.fields(7)(5).getRow should be(7)
        }

        "initialize the piece colour with black if it is on the first three rows" in {
            new BoardCreator(8).getPieceColour(0) should be(Colour.BLACK)
        }

        "initialize the piece colour with white if it is on the last three rows" in {
            new BoardCreator(8).getPieceColour(7) should be(Colour.WHITE)
        }

        "have pieces on their start positions" in {
            val board: Board =   new BoardCreator(8).setupFields()

            board.fields(0)(0).hasPiece should be(true)
            board.fields(0)(1).hasPiece should be(false)
            board.fields(0)(2).hasPiece should be(true)
            board.fields(0)(3).hasPiece should be(false)
            board.fields(0)(4).hasPiece should be(true)
            board.fields(0)(5).hasPiece should be(false)
            board.fields(0)(6).hasPiece should be(true)
            board.fields(0)(7).hasPiece should be(false)

            board.fields(1)(0).hasPiece should be(false)
            board.fields(1)(1).hasPiece should be(true)
            board.fields(1)(2).hasPiece should be(false)
            board.fields(1)(3).hasPiece should be(true)
            board.fields(1)(4).hasPiece should be(false)
            board.fields(1)(5).hasPiece should be(true)
            board.fields(1)(6).hasPiece should be(false)
            board.fields(1)(7).hasPiece should be(true)

            board.fields(2)(0).hasPiece should be(true)
            board.fields(2)(1).hasPiece should be(false)
            board.fields(2)(2).hasPiece should be(true)
            board.fields(2)(3).hasPiece should be(false)
            board.fields(2)(4).hasPiece should be(true)
            board.fields(2)(5).hasPiece should be(false)
            board.fields(2)(6).hasPiece should be(true)
            board.fields(2)(7).hasPiece should be(false)

            board.fields(3)(0).hasPiece should be(false)
            board.fields(3)(1).hasPiece should be(false)
            board.fields(3)(2).hasPiece should be(false)
            board.fields(3)(3).hasPiece should be(false)
            board.fields(3)(4).hasPiece should be(false)
            board.fields(3)(5).hasPiece should be(false)
            board.fields(3)(6).hasPiece should be(false)
            board.fields(3)(7).hasPiece should be(false)

            board.fields(4)(0).hasPiece should be(false)
            board.fields(4)(1).hasPiece should be(false)
            board.fields(4)(2).hasPiece should be(false)
            board.fields(4)(3).hasPiece should be(false)
            board.fields(4)(4).hasPiece should be(false)
            board.fields(4)(5).hasPiece should be(false)
            board.fields(4)(6).hasPiece should be(false)
            board.fields(4)(7).hasPiece should be(false)

            board.fields(5)(0).hasPiece should be(false)
            board.fields(5)(1).hasPiece should be(true)
            board.fields(5)(2).hasPiece should be(false)
            board.fields(5)(3).hasPiece should be(true)
            board.fields(5)(4).hasPiece should be(false)
            board.fields(5)(5).hasPiece should be(true)
            board.fields(5)(6).hasPiece should be(false)
            board.fields(5)(7).hasPiece should be(true)

            board.fields(6)(0).hasPiece should be(true)
            board.fields(6)(1).hasPiece should be(false)
            board.fields(6)(2).hasPiece should be(true)
            board.fields(6)(3).hasPiece should be(false)
            board.fields(6)(4).hasPiece should be(true)
            board.fields(6)(5).hasPiece should be(false)
            board.fields(6)(6).hasPiece should be(true)
            board.fields(6)(7).hasPiece should be(false)

            board.fields(7)(0).hasPiece should be(false)
            board.fields(7)(1).hasPiece should be(true)
            board.fields(7)(2).hasPiece should be(false)
            board.fields(7)(3).hasPiece should be(true)
            board.fields(7)(4).hasPiece should be(false)
            board.fields(7)(5).hasPiece should be(true)
            board.fields(7)(6).hasPiece should be(false)
            board.fields(7)(7).hasPiece should be(true)
        }
    }
}
