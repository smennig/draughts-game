package controller

import model._
import org.scalatest.Matchers._
import org.scalatest.WordSpec

class MoveControllerSpec extends WordSpec {
    "The MoveController" should {
        "check if the selected field has a valid piece" in {
            val board = new BoardCreator(8).setupFields()
            val controller = new MoveController(board)
            val player = new Player("Player 1", Colour.BLACK)
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
