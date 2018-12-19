package controller

import model._
import org.scalatest.Matchers._
import org.scalatest.WordSpec

class MoveControllerSpec extends WordSpec {
    "The MoveController" should {
        "check if the selected field has a valid piece" in {
            val board = new BoardCreator(8).setupFields()
            val controller = new MoveController(board)
            val player = new Player("Player 1", Colour.BLACK, true)
            val field = new Field(0, 0)
            val piece = new Man(Colour.BLACK)
            field.piece_(Some(piece))

            controller.checkIfPieceIsValid(field, player) should be(true)
        }

        "move a man to a new position if the new Field is empty" in {
            val board = new BoardCreator(8).setupFields()
            val controller = new MoveController(board)

            board.getField(2)(2).hasPiece should be(true)
            board.getField(3)(3).hasPiece should be(false)

            controller.move(2, 2, 3, 3)

            board.getField(2)(2).hasPiece should be(false)
            board.getField(3)(3).hasPiece should be(true)
        }

        "return false if the target field has a piece on it" in {
            val board = new BoardCreator(8).setupFields()
            val controller = new MoveController(board)

            board.getField(1)(1).hasPiece should be(true)
            board.getField(2)(2).hasPiece should be(true)

            controller.move(1, 1, 2, 2)

            board.getField(1)(1).hasPiece should be(true)
            board.getField(2)(2).hasPiece should be(true)
        }

        "return false if the move of a man is not diagonal" in {
            val board = new BoardCreator(8).setupFields()
            val controller = new MoveController(board)

            board.getField(2)(2).hasPiece should be(true)
            board.getField(2)(3).hasPiece should be(false)

            controller.move(2, 2, 2, 3) should be(false)

            board.getField(2)(2).hasPiece should be(true)
            board.getField(2)(3).hasPiece should be(false)
        }

        "return false if the move of a man is backwards" in {
            val board = new BoardCreator(8).setupFields()
            val controller = new MoveController(board)

            controller.move(2, 2, 3, 3) should be(true)

            board.getField(3)(3).hasPiece should be(true)
            board.getField(2)(2).hasPiece should be(false)

            controller.move(3, 3, 2, 2) should be(false)

            board.getField(3)(3).hasPiece should be(true)
            board.getField(2)(2).hasPiece should be(false)
        }

        "return true if one opponents piece is on the inbetween field" in {
            val board = new BoardCreator(8).setupFields()
            val controller = new MoveController(board)

            board.getField(2)(2).hasPiece should be(true)
            board.getField(3)(3).hasPiece should be(false)
            board.getField(4)(4).hasPiece should be(false)
            board.getField(5)(5).hasPiece should be(true)

            controller.move(2, 2, 3, 3) should be(true)
            controller.move(5, 5, 4, 4) should be(true)

            board.getField(2)(2).hasPiece should be(false)
            board.getField(3)(3).hasPiece should be(true)
            board.getField(4)(4).hasPiece should be(true)
            board.getField(5)(5).hasPiece should be(false)

            controller.move(3, 3, 5, 5) should be(true)

            board.getField(2)(2).hasPiece should be(false)
            board.getField(3)(3).hasPiece should be(false)
            board.getField(4)(4).hasPiece should be(false)
            board.getField(5)(5).hasPiece should be(true)
        }

        "return false if the move of a man is bigger than one step" in {
            val board = new BoardCreator(8).setupFields()
            val controller = new MoveController(board)

            board.getField(2)(2).hasPiece should be(true)
            board.getField(4)(4).hasPiece should be(false)

            controller.move(2, 2, 4, 4) should be(false)

            board.getField(2)(2).hasPiece should be(true)
            board.getField(4)(4).hasPiece should be(false)
        }

        "simulate a game" in {
            val board = new BoardCreator(8).setupFields()
            val controller = new MoveController(board)

            // Black: e3 - d4
            controller.move(4, 2, 3, 3) should be(true)
            board.getField(4)(2).hasPiece should be(false)
            board.getField(3)(3).hasPiece should be(true)
            board.getField(3)(3).getPiece.get shouldBe a [Man]

            // White: d6 - e5
            controller.move(3, 5, 4, 4) should be(true)
            board.getField(3)(5).hasPiece should be(false)
            board.getField(4)(4).hasPiece should be(true)
            board.getField(4)(4).getPiece.get shouldBe a [Man]

            // Black: d2 - e3
            controller.move(3, 1, 4, 2) should be(true)
            board.getField(3)(1).hasPiece should be(false)
            board.getField(4)(2).hasPiece should be(true)
            board.getField(4)(2).getPiece.get shouldBe a [Man]

            // White: h6 - g5
            controller.move(7, 5, 6, 4) should be(true)
            board.getField(7)(5).hasPiece should be(false)
            board.getField(6)(4).hasPiece should be(true)
            board.getField(6)(4).getPiece.get shouldBe a [Man]

            // Black: g3 - h4
            controller.move(6, 2, 7, 3) should be(true)
            board.getField(6)(2).hasPiece should be(false)
            board.getField(7)(3).hasPiece should be(true)
            board.getField(7)(3).getPiece.get shouldBe a [Man]

            // White: b6 - a5
            controller.move(1, 5, 0, 4) should be(true)
            board.getField(1)(5).hasPiece should be(false)
            board.getField(0)(4).hasPiece should be(true)
            board.getField(0)(4).getPiece.get shouldBe a [Man]

            // Black: d4 - c5
            controller.move(3, 3, 2, 4) should be(true)
            board.getField(3)(3).hasPiece should be(false)
            board.getField(2)(4).hasPiece should be(true)
            board.getField(2)(4).getPiece.get shouldBe a [Man]

            // White: e5 - f4
            controller.move(4, 4, 5, 3) should be(true)
            board.getField(4)(4).hasPiece should be(false)
            board.getField(5)(3).hasPiece should be(true)
            board.getField(5)(3).getPiece.get shouldBe a [Man]

            // Black: c3 - d4
            controller.move(2, 2, 3, 3) should be(true)
            board.getField(2)(2).hasPiece should be(false)
            board.getField(3)(3).hasPiece should be(true)
            board.getField(3)(3).getPiece.get shouldBe a [Man]

            // White: f4 x e3 - d2
            controller.move(5, 3, 3, 1) should be(true)
            board.getField(5)(3).hasPiece should be(false)
            board.getField(4)(2).hasPiece should be(false)
            board.getField(3)(1).hasPiece should be(true)
            board.getField(3)(1).getPiece.get shouldBe a [Man]

            // Black: c1 x d2 - e3
            controller.move(2, 0, 4, 2) should be(true)
            board.getField(2)(0).hasPiece should be(false)
            board.getField(3)(1).hasPiece should be(false)
            board.getField(4)(2).hasPiece should be(true)
            board.getField(4)(2).getPiece.get shouldBe a [Man]

            // White: a5 - b4
            controller.move(0, 4, 1, 3) should be(true)
            board.getField(0)(4).hasPiece should be(false)
            board.getField(1)(3).hasPiece should be(true)
            board.getField(1)(3).getPiece.get shouldBe a [Man]

            // Black: b2 - c3
            controller.move(1, 1, 2, 2) should be(true)
            board.getField(1)(1).hasPiece should be(false)
            board.getField(2)(2).hasPiece should be(true)
            board.getField(2)(2).getPiece.get shouldBe a [Man]

            // White: b4 x c3 - d2
            controller.move(1, 3, 3, 1) should be(true)
            board.getField(1)(3).hasPiece should be(false)
            board.getField(3)(1).hasPiece should be(true)
            board.getField(3)(1).getPiece.get shouldBe a [Man]

            // Black: e1 x d2 - c3
            controller.move(4, 0, 2, 2) should be(true)
            board.getField(4)(0).hasPiece should be(false)
            board.getField(3)(1).hasPiece should be(false)
            board.getField(2)(2).hasPiece should be(true)
            board.getField(2)(2).getPiece.get shouldBe a [Man]

            // White: c7 - b6
            controller.move(2, 6, 1, 5) should be(true)
            board.getField(2)(6).hasPiece should be(false)
            board.getField(1)(5).hasPiece should be(true)
            board.getField(1)(5).getPiece.get shouldBe a [Man]

            // Black: f2 - g3
            controller.move(5, 1, 6, 2) should be(true)
            board.getField(5)(1).hasPiece should be(false)
            board.getField(6)(2).hasPiece should be(true)
            board.getField(6)(2).getPiece.get shouldBe a [Man]

            // White: e7 - d6
            controller.move(4, 6, 3, 5) should be(true)
            board.getField(4)(6).hasPiece should be(false)
            board.getField(3)(5).hasPiece should be(true)
            board.getField(3)(5).getPiece.get shouldBe a [Man]

            // Black: c5 x d6 - e7
            controller.move(2, 4, 4, 6) should be(true)
            board.getField(2)(4).hasPiece should be(false)
            board.getField(4)(6).hasPiece should be(true)
            board.getField(4)(6).getPiece.get shouldBe a [Man]

            // White: f8 x e7 - d6
            controller.move(5, 7, 3, 5) should be(true)
            board.getField(5)(7).hasPiece should be(false)
            board.getField(4)(6).hasPiece should be(false)
            board.getField(3)(5).hasPiece should be(true)
            board.getField(3)(5).getPiece.get shouldBe a [Man]

            // Black: g1 - f2
            controller.move(6, 0, 5, 1) should be(true)
            board.getField(6)(0).hasPiece should be(false)
            board.getField(5)(1).hasPiece should be(true)
            board.getField(5)(1).getPiece.get shouldBe a [Man]

            // White: d8 - c7
            controller.move(3, 7, 2, 6) should be(true)
            board.getField(3)(7).hasPiece should be(false)
            board.getField(2)(6).hasPiece should be(true)
            board.getField(2)(6).getPiece.get shouldBe a [Man]

            // Black: g3 - f4
            controller.move(6, 2, 5, 3) should be(true)
            board.getField(6)(2).hasPiece should be(false)
            board.getField(5)(3).hasPiece should be(true)
            board.getField(5)(3).getPiece.get shouldBe a [Man]

            // White: d6 - e5
            controller.move(3, 5, 4, 4) should be(true)
            board.getField(3)(5).hasPiece should be(false)
            board.getField(4)(4).hasPiece should be(true)
            board.getField(4)(4).getPiece.get shouldBe a [Man]

            // Black: f4 x g5 - h6, h6 x g7 - f8D
            board.getField(5)(3).getPiece.get shouldBe a [Man]
            controller.move(5, 3, 7, 5) should be(true)
            controller.move(7, 5, 5, 7) should be(true)
            board.getField(5)(3).hasPiece should be(false)
            board.getField(6)(4).hasPiece should be(false)
            board.getField(6)(6).hasPiece should be(false)
            board.getField(5)(7).hasPiece should be(true)
            board.getField(5)(7).getPiece.get shouldBe a [King]

            // White: b6 - a5

            // Black: f8 - d6

            // White: h8 - g7

            // Black: d6 x e5 - f4

            // White: c7 - d6

            // Black: f4 x d6 - c7

            // White: b8 x c7 - d6

            // Black: a1 - b2

            // White: d6 - e5

            // Black: h4 - g5

            // White: f6 x g5 - h4

            // Black: d4 x e5 - f6, f6 x g7 - h8D

            // White: a7 - b6

            // Black: a3 - b4

            // White: h4 - g3

            // Black: f2 x g3 - h4

            // White: b6 - c5

            // Black: b4 x c5 - d6

            // White: a5 - b4

            // Black: c3 x b4 - a5#
        }




//        "move a king to a new position if the new Field is empty" in {
//            val board = new BoardCreator(8).setupFields()
//            val controller = new MoveController(board)
//
//            board.getField(2)(2).hasPiece should be(true)
//            board.getField(4)(4).hasPiece should be(false)
//
//            controller.move(2, 2, 4, 4)
//
//            board.getField(2)(2).hasPiece should be(false)
//            board.getField(4)(4).hasPiece should be(true)
//        }
//
//        "return false if the target field has a piece on it" in {
//            val board = new BoardCreator(8).setupFields()
//            val controller = new MoveController(board)
//
//            board.getField(2)(2).hasPiece should be(true)
//            board.getField(5)(5).hasPiece should be(true)
//
//            controller.move(2, 2, 5, 5)
//
//            board.getField(1)(1).hasPiece should be(true)
//            board.getField(2)(2).hasPiece should be(true)
//        }
//
//        "return false if the move of a king is not diagonal" in {
//            val board = new BoardCreator(8).setupFields()
//            val controller = new MoveController(board)
//
//            board.getField(2)(2).hasPiece should be(true)
//            board.getField(3)(4).hasPiece should be(false)
//
//            controller.move(2, 2, 3, 4) should be(false)
//
//            board.getField(2)(2).hasPiece should be(true)
//            board.getField(3)(4).hasPiece should be(false)
//        }
//
//        "return true if the move of a king is backwards" in {
//            val board = new BoardCreator(8).setupFields()
//            val controller = new MoveController(board)
//
//            controller.move(2, 2, 4, 4) should be(true)
//
//            board.getField(4)(4).hasPiece should be(true)
//            board.getField(2)(2).hasPiece should be(false)
//
//            controller.move(4, 4, 2, 2) should be(true)
//
//            board.getField(4)(4).hasPiece should be(false)
//            board.getField(2)(2).hasPiece should be(true)
//        }
//
//        "return true if one opponents piece is on the inbetween field" in {
//            val board = new BoardCreator(8).setupFields()
//            val controller = new MoveController(board)
//
//            board.getField(2)(2).hasPiece should be(true)
//            board.getField(3)(3).hasPiece should be(false)
//            board.getField(4)(4).hasPiece should be(false)
//            board.getField(5)(5).hasPiece should be(true)
//
//            controller.move(2, 2, 4, 4) should be(true)
//            controller.move(5, 5, 3, 3) should be(true)
//
//            board.getField(2)(2).hasPiece should be(false)
//            board.getField(3)(3).hasPiece should be(true)
//            board.getField(4)(4).hasPiece should be(false)
//            board.getField(5)(5).hasPiece should be(false)
//        }
//
//        "return false if own king moves to far if it captures" in {
//            val board = new BoardCreator(8).setupFields()
//            val controller = new MoveController(board)
//
//            board.getField(2)(2).hasPiece should be(true)
//            board.getField(3)(3).hasPiece should be(false)
//            board.getField(4)(4).hasPiece should be(false)
//            board.getField(5)(5).hasPiece should be(true)
//
//            controller.move(2, 2, 4, 4) should be(true)
//            controller.move(5, 5, 2, 2) should be(false)
//
//            board.getField(2)(2).hasPiece should be(false)
//            board.getField(3)(3).hasPiece should be(false)
//            board.getField(4)(4).hasPiece should be(true)
//            board.getField(5)(5).hasPiece should be(true)
//        }
    }
}
