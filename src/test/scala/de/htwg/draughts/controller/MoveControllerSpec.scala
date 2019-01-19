package de.htwg.draughts.controller

import de.htwg.draughts.model._
import org.scalatest.Matchers._
import org.scalatest.WordSpec

class MoveControllerSpec extends WordSpec {
    "The MoveController" should {
        "simulate a game" in {
            val board = new BoardCreator(8).setupFields()
            val blackPlayer = new Player(color = Colour.BLACK, name = "Player1", turn = true)
            val whitePlayer = new Player(color = Colour.WHITE, name = "Player2", turn = false)
            val controller = new MoveController(board, blackPlayer, whitePlayer)

            // Black: e3 - d4
            board.getField(3)(1).get.hasPiece should be(true)
            controller.colourTurn should be(Colour.BLACK)
            controller.checkForcedCapture() should be(Map())
            controller.move(4, 2, 3, 3)._1 should be(true)
            board.getField(4)(2).get.hasPiece should be(false)
            board.getField(3)(3).get.hasPiece should be(true)
            board.getField(3)(3).get.getPiece.get shouldBe a [Man]
            blackPlayer.pieces should be(12)
            whitePlayer.pieces should be(12)

            // White: d6 - e5
            board.getField(3)(1).get.hasPiece should be(true)
            controller.colourTurn should be(Colour.WHITE)
            controller.checkForcedCapture() should be(Map())
            controller.move(3, 5, 4, 4)._1 should be(true)
            board.getField(3)(5).get.hasPiece should be(false)
            board.getField(4)(4).get.hasPiece should be(true)
            board.getField(4)(4).get.getPiece.get shouldBe a [Man]
            blackPlayer.pieces should be(12)
            whitePlayer.pieces should be(12)

            // Black: d2 - e3
            board.getField(3)(1).get.hasPiece should be(true)
            controller.colourTurn should be(Colour.BLACK)
            controller.checkForcedCapture() should be(Map())
            controller.move(3, 1, 4, 2)._1 should be(true)
            board.getField(3)(1).get.hasPiece should be(false)
            board.getField(4)(2).get.hasPiece should be(true)
            board.getField(4)(2).get.getPiece.get shouldBe a [Man]
            blackPlayer.pieces should be(12)
            whitePlayer.pieces should be(12)

            // White: h6 - g5
//            board.getField(3)(1).hasPiece should be(true)
            controller.colourTurn should be(Colour.WHITE)
            controller.checkForcedCapture() should be(Map())
            controller.move(7, 5, 6, 4)._1 should be(true)
            board.getField(7)(5).get.hasPiece should be(false)
            board.getField(6)(4).get.hasPiece should be(true)
            board.getField(6)(4).get.getPiece.get shouldBe a [Man]
            blackPlayer.pieces should be(12)
            whitePlayer.pieces should be(12)

            // Black: g3 - h4
//            board.getField(3)(1).hasPiece should be(true)
            controller.colourTurn should be(Colour.BLACK)
            controller.checkForcedCapture() should be(Map())
            controller.move(6, 2, 7, 3)._1 should be(true)
            board.getField(6)(2).get.hasPiece should be(false)
            board.getField(7)(3).get.hasPiece should be(true)
            board.getField(7)(3).get.getPiece.get shouldBe a [Man]
            blackPlayer.pieces should be(12)
            whitePlayer.pieces should be(12)

            // White: b6 - a5
//            board.getField(3)(1).hasPiece should be(true)
            controller.colourTurn should be(Colour.WHITE)
            controller.checkForcedCapture() should be(Map())
            controller.move(1, 5, 0, 4)._1 should be(true)
            board.getField(1)(5).get.hasPiece should be(false)
            board.getField(0)(4).get.hasPiece should be(true)
            board.getField(0)(4).get.getPiece.get shouldBe a [Man]
            blackPlayer.pieces should be(12)
            whitePlayer.pieces should be(12)

            // Black: d4 - c5
//            board.getField(3)(1).hasPiece should be(true)
            controller.colourTurn should be(Colour.BLACK)
            controller.checkForcedCapture() should be(Map())
            controller.move(3, 3, 2, 4)._1 should be(true)
            board.getField(3)(3).get.hasPiece should be(false)
            board.getField(2)(4).get.hasPiece should be(true)
            board.getField(2)(4).get.getPiece.get shouldBe a [Man]
            blackPlayer.pieces should be(12)
            whitePlayer.pieces should be(12)

            // White: e5 - f4
            controller.colourTurn should be(Colour.WHITE)
            controller.checkForcedCapture() should be(Map())
            controller.move(4, 4, 5, 3)._1 should be(true)
            board.getField(4)(4).get.hasPiece should be(false)
            board.getField(5)(3).get.hasPiece should be(true)
            board.getField(5)(3).get.getPiece.get shouldBe a [Man]
            blackPlayer.pieces should be(12)
            whitePlayer.pieces should be(12)

            // Black: c3 - d4
            controller.colourTurn should be(Colour.BLACK)
            controller.checkForcedCapture() should be(Map())
            controller.move(2, 2, 3, 3)._1 should be(true)
            board.getField(2)(2).get.hasPiece should be(false)
            board.getField(3)(3).get.hasPiece should be(true)
            board.getField(3)(3).get.getPiece.get shouldBe a [Man]
            blackPlayer.pieces should be(12)
            whitePlayer.pieces should be(12)

            // White: f4 x e3 - d2
            // Zugzwang!!!
            controller.colourTurn should be(Colour.WHITE)
            val list = List(board.getField(3)(1).get)
            controller.checkForcedCapture() should be(Map(board.getField(5)(3).get -> list))
            controller.move(5, 3, 6, 2)._1 should be(false)
            controller.move(0, 4, 1, 3)._1 should be(false)
            controller.move(5, 3, 3, 1)._1 should be(true)
            board.getField(5)(3).get.hasPiece should be(false)
            board.getField(4)(2).get.hasPiece should be(false)
            board.getField(3)(1).get.hasPiece should be(true)
            board.getField(3)(1).get.getPiece.get shouldBe a [Man]

            // Black: c1 x d2 - e3
            controller.colourTurn should be(Colour.BLACK)
            val map = controller.checkForcedCapture()
            controller.checkForcedCapture() should be(Map(board.getField(4)(0).get -> List(board.getField(2)(2).get), board.getField(2)(0).get -> List(board.getField(4)(2).get)))
            controller.move(2, 0, 4, 2)._1 should be(true)
            board.getField(2)(0).get.hasPiece should be(false)
            board.getField(3)(1).get.hasPiece should be(false)
            board.getField(4)(2).get.hasPiece should be(true)
            board.getField(4)(2).get.getPiece.get shouldBe a [Man]

            // White: a5 - b4
            controller.colourTurn should be(Colour.WHITE)
            controller.checkForcedCapture() should be(Map())
            controller.move(0, 4, 1, 3)._1 should be(true)
            board.getField(0)(4).get.hasPiece should be(false)
            board.getField(1)(3).get.hasPiece should be(true)
            board.getField(1)(3).get.getPiece.get shouldBe a [Man]

            // Black: b2 - c3
            controller.colourTurn should be(Colour.BLACK)
            controller.checkForcedCapture() should be(Map())
            controller.move(1, 1, 2, 2)._1 should be(true)
            board.getField(1)(1).get.hasPiece should be(false)
            board.getField(2)(2).get.hasPiece should be(true)
            board.getField(2)(2).get.getPiece.get shouldBe a [Man]

            // White: b4 x c3 - d2
            controller.colourTurn should be(Colour.WHITE)
            controller.checkForcedCapture() should be(Map(board.getField(1)(3).get -> List(board.getField(3)(1).get)))
            controller.move(1, 3, 3, 1)._1 should be(true)
            board.getField(1)(3).get.hasPiece should be(false)
            board.getField(3)(1).get.hasPiece should be(true)
            board.getField(3)(1).get.getPiece.get shouldBe a [Man]

            // Black: e1 x d2 - c3
            controller.colourTurn should be(Colour.BLACK)
            controller.checkForcedCapture() should be(Map(board.getField(4)(0).get -> List(board.getField(2)(2).get)))
            controller.move(4, 0, 2, 2)._1 should be(true)
            board.getField(4)(0).get.hasPiece should be(false)
            board.getField(3)(1).get.hasPiece should be(false)
            board.getField(2)(2).get.hasPiece should be(true)
            board.getField(2)(2).get.getPiece.get shouldBe a [Man]

            // White: c7 - b6
            controller.colourTurn should be(Colour.WHITE)
            controller.checkForcedCapture() should be(Map())
            controller.move(2, 6, 1, 5)._1 should be(true)
            board.getField(2)(6).get.hasPiece should be(false)
            board.getField(1)(5).get.hasPiece should be(true)
            board.getField(1)(5).get.getPiece.get shouldBe a [Man]

            // Black: f2 - g3
            controller.colourTurn should be(Colour.BLACK)
            controller.checkForcedCapture() should be(Map())
            controller.move(5, 1, 6, 2)._1 should be(true)
            board.getField(5)(1).get.hasPiece should be(false)
            board.getField(6)(2).get.hasPiece should be(true)
            board.getField(6)(2).get.getPiece.get shouldBe a [Man]

            // White: e7 - d6
            controller.colourTurn should be(Colour.WHITE)
            controller.checkForcedCapture() should be(Map())
            controller.move(4, 6, 3, 5)._1 should be(true)
            board.getField(4)(6).get.hasPiece should be(false)
            board.getField(3)(5).get.hasPiece should be(true)
            board.getField(3)(5).get.getPiece.get shouldBe a [Man]

            // Black: c5 x d6 - e7
            controller.colourTurn should be(Colour.BLACK)
            controller.checkForcedCapture() should be(Map(board.getField(2)(4).get -> List(board.getField(4)(6).get)))
            controller.move(2, 4, 4, 6)._1 should be(true)
            board.getField(2)(4).get.hasPiece should be(false)
            board.getField(4)(6).get.hasPiece should be(true)
            board.getField(4)(6).get.getPiece.get shouldBe a [Man]

            // White: f8 x e7 - d6
            controller.colourTurn should be(Colour.WHITE)
            controller.checkForcedCapture() should be(Map(board.getField(5)(7).get -> List(board.getField(3)(5).get)))
            controller.move(5, 7, 3, 5)._1 should be(true)
            board.getField(5)(7).get.hasPiece should be(false)
            board.getField(4)(6).get.hasPiece should be(false)
            board.getField(3)(5).get.hasPiece should be(true)
            board.getField(3)(5).get.getPiece.get shouldBe a [Man]

            // Black: g1 - f2
            controller.colourTurn should be(Colour.BLACK)
            controller.checkForcedCapture() should be(Map())
            controller.move(6, 0, 5, 1)._1 should be(true)
            board.getField(6)(0).get.hasPiece should be(false)
            board.getField(5)(1).get.hasPiece should be(true)
            board.getField(5)(1).get.getPiece.get shouldBe a [Man]

            // White: d8 - c7
            controller.colourTurn should be(Colour.WHITE)
            controller.checkForcedCapture() should be(Map())
            controller.move(3, 7, 2, 6)._1 should be(true)
            board.getField(3)(7).get.hasPiece should be(false)
            board.getField(2)(6).get.hasPiece should be(true)
            board.getField(2)(6).get.getPiece.get shouldBe a [Man]

            // Black: g3 - f4
            controller.colourTurn should be(Colour.BLACK)
            controller.checkForcedCapture() should be(Map())
            controller.move(6, 2, 5, 3)._1 should be(true)
            board.getField(6)(2).get.hasPiece should be(false)
            board.getField(5)(3).get.hasPiece should be(true)
            board.getField(5)(3).get.getPiece.get shouldBe a [Man]

            // White: d6 - e5
            controller.colourTurn should be(Colour.WHITE)
            controller.checkForcedCapture() should be(Map())
            controller.move(3, 5, 4, 4)._1 should be(true)
            board.getField(3)(5).get.hasPiece should be(false)
            board.getField(4)(4).get.hasPiece should be(true)
            board.getField(4)(4).get.getPiece.get shouldBe a [Man]

            // Black: f4 x g5 - h6, h6 x g7 - f8D
            controller.colourTurn should be(Colour.BLACK)
            controller.checkForcedCapture() should be(Map(board.getField(5)(3).get -> List(board.getField(7)(5).get, board.getField(3)(5).get)))
            board.getField(5)(3).get.getPiece.get shouldBe a [Man]
            controller.move(5, 3, 7, 5)._1 should be(true)
            controller.colourTurn should be(Colour.BLACK)
            controller.move(7, 5, 5, 7)._1 should be(true)
            board.getField(5)(3).get.hasPiece should be(false)
            board.getField(6)(4).get.hasPiece should be(false)
            board.getField(6)(6).get.hasPiece should be(false)
            board.getField(5)(7).get.hasPiece should be(true)
            board.getField(5)(7).get.getPiece.get shouldBe a [King]

            // White: b6 - a5
            controller.colourTurn should be(Colour.WHITE)
            controller.checkForcedCapture() should be(Map())
            controller.move(1, 5, 0, 4)._1 should be(true)
            board.getField(1)(5).get.hasPiece should be(false)
            board.getField(0)(4).get.hasPiece should be(true)
            board.getField(0)(4).get.getPiece.get shouldBe a [Man]

            // Black: f8 - d6
            controller.colourTurn should be(Colour.BLACK)
            controller.checkForcedCapture() should be(Map())
            controller.move(5, 7, 3, 5)._1 should be(true)
            board.getField(5)(7).get.hasPiece should be(false)
            board.getField(3)(5).get.hasPiece should be(true)
            board.getField(3)(5).get.getPiece.get shouldBe a [King]

            // White: h8 - g7
            controller.colourTurn should be(Colour.WHITE)
            controller.checkForcedCapture() should be(Map())
            controller.move(7, 7, 6, 6)._1 should be(true)
            board.getField(7)(7).get.hasPiece should be(false)
            board.getField(6)(6).get.hasPiece should be(true)
            board.getField(6)(6).get.getPiece.get shouldBe a [Man]

            // Black: d6 x e5 - f4
            controller.colourTurn should be(Colour.BLACK)
            val fieldList = controller.checkForcedCapture()
            controller.checkForcedCapture() should be(Map(board.getField(3)(5).get -> List(board.getField(5)(3).get)))
            controller.move(3, 5, 5, 3)._1 should be(true)
            board.getField(3)(5).get.hasPiece should be(false)
            board.getField(5)(3).get.hasPiece should be(true)
            board.getField(5)(3).get.getPiece.get shouldBe a [King]

            // White: c7 - d6
            controller.colourTurn should be(Colour.WHITE)
            controller.checkForcedCapture() should be(Map())
            controller.move(2, 6, 3, 5)._1 should be(true)
            board.getField(2)(6).get.hasPiece should be(false)
            board.getField(3)(5).get.hasPiece should be(true)
            board.getField(3)(5).get.getPiece.get shouldBe a [Man]

            // Black: f4 x d6 - c7
            controller.colourTurn should be(Colour.BLACK)
            controller.checkForcedCapture() should be(Map(board.getField(5)(3).get -> List(board.getField(2)(6).get)))
            controller.move(5, 3, 2, 6)._1 should be(true)
            board.getField(5)(3).get.hasPiece should be(false)
            board.getField(2)(6).get.hasPiece should be(true)
            board.getField(2)(6).get.getPiece.get shouldBe a [King]

            // White: b8 x c7 - d6
            controller.colourTurn should be(Colour.WHITE)
            controller.checkForcedCapture() should be(Map(board.getField(1)(7).get -> List(board.getField(3)(5).get)))
            controller.move(1, 7, 3, 5)._1 should be(true)
            board.getField(1)(7).get.hasPiece should be(false)
            board.getField(3)(5).get.hasPiece should be(true)
            board.getField(3)(5).get.getPiece.get shouldBe a [Man]

            // Black: a1 - b2
            controller.colourTurn should be(Colour.BLACK)
            controller.checkForcedCapture() should be(Map())
            controller.move(0, 0, 1, 1)._1 should be(true)
            board.getField(0)(0).get.hasPiece should be(false)
            board.getField(1)(1).get.hasPiece should be(true)
            board.getField(1)(1).get.getPiece.get shouldBe a [Man]

            // White: d6 - e5
            controller.colourTurn should be(Colour.WHITE)
            controller.checkForcedCapture() should be(Map())
            controller.move(3, 5, 4, 4)._1 should be(true)
            board.getField(3)(5).get.hasPiece should be(false)
            board.getField(4)(4).get.hasPiece should be(true)
            board.getField(4)(4).get.getPiece.get shouldBe a [Man]

            // Black: h4 - g5
            controller.colourTurn should be(Colour.BLACK)
            controller.checkForcedCapture() should be(Map())
            controller.move(7, 3, 6, 4)._1 should be(true)
            board.getField(7)(3).get.hasPiece should be(false)
            board.getField(6)(4).get.hasPiece should be(true)
            board.getField(6)(4).get.getPiece.get shouldBe a [Man]

            // White: f6 x g5 - h4
            controller.colourTurn should be(Colour.WHITE)
            controller.checkForcedCapture() should be(Map(board.getField(5)(5).get -> List(board.getField(7)(3).get)))
            controller.move(5, 5, 7, 3)._1 should be(true)
            board.getField(5)(5).get.hasPiece should be(false)
            board.getField(7)(3).get.hasPiece should be(true)
            board.getField(7)(3).get.getPiece.get shouldBe a [Man]

            // Black: d4 x e5 - f6, f6 x g7 - h8D
            controller.colourTurn should be(Colour.BLACK)
            controller.checkForcedCapture() should be(Map(board.getField(3)(3).get -> List(board.getField(5)(5).get)))
            board.getField(3)(3).get.getPiece.get shouldBe a [Man]
            controller.move(3, 3, 5, 5)._1 should be(true)
            controller.colourTurn should be(Colour.BLACK)
            controller.move(5, 5, 7, 7)._1 should be(true)
            board.getField(3)(3).get.hasPiece should be(false)
            board.getField(4)(4).get.hasPiece should be(false)
            board.getField(5)(5).get.hasPiece should be(false)
            board.getField(6)(6).get.hasPiece should be(false)
            board.getField(7)(7).get.hasPiece should be(true)
            board.getField(7)(7).get.getPiece.get shouldBe a [King]

            // White: a7 - b6
            controller.colourTurn should be(Colour.WHITE)
            controller.checkForcedCapture() should be(Map())
            controller.move(0, 6, 1, 5)._1 should be(true)
            board.getField(0)(6).get.hasPiece should be(false)
            board.getField(1)(5).get.hasPiece should be(true)
            board.getField(1)(5).get.getPiece.get shouldBe a [Man]

            // Black: a3 - b4
            controller.colourTurn should be(Colour.BLACK)
            controller.checkForcedCapture() should be(Map())
            controller.move(0, 2, 1, 3)._1 should be(true)
            board.getField(0)(2).get.hasPiece should be(false)
            board.getField(1)(3).get.hasPiece should be(true)
            board.getField(1)(3).get.getPiece.get shouldBe a [Man]

            // White: h4 - g3
            controller.colourTurn should be(Colour.WHITE)
            controller.checkForcedCapture() should be(Map())
            controller.move(7, 3, 6, 2)._1 should be(true)
            board.getField(7)(3).get.hasPiece should be(false)
            board.getField(6)(2).get.hasPiece should be(true)
            board.getField(6)(2).get.getPiece.get shouldBe a [Man]

            // Black: f2 x g3 - h4
            controller.colourTurn should be(Colour.BLACK)
            controller.checkForcedCapture() should be(Map(board.getField(7)(1).get -> List(board.getField(5)(3).get), board.getField(5)(1).get -> List(board.getField(7)(3).get)))
            controller.move(5, 1, 7, 3)._1 should be(true)
            board.getField(5)(1).get.hasPiece should be(false)
            board.getField(7)(3).get.hasPiece should be(true)
            board.getField(7)(3).get.getPiece.get shouldBe a [Man]

            // White: b6 - c5
            controller.colourTurn should be(Colour.WHITE)
            controller.checkForcedCapture() should be(Map())
            controller.move(1, 5, 2, 4)._1 should be(true)
            board.getField(1)(5).get.hasPiece should be(false)
            board.getField(2)(4).get.hasPiece should be(true)
            board.getField(2)(4).get.getPiece.get shouldBe a [Man]

            // Black: b4 x c5 - d6
            controller.colourTurn should be(Colour.BLACK)
            controller.checkForcedCapture() should be(Map(board.getField(1)(3).get -> List(board.getField(3)(5).get)))
            controller.move(1, 3, 3, 5)._1 should be(true)
            board.getField(1)(3).get.hasPiece should be(false)
            board.getField(3)(5).get.hasPiece should be(true)
            board.getField(3)(5).get.getPiece.get shouldBe a [Man]

            // White: a5 - b4
            controller.colourTurn should be(Colour.WHITE)
            controller.checkForcedCapture() should be(Map())
            controller.move(0, 4, 1, 3)._1 should be(true)
            board.getField(0)(4).get.hasPiece should be(false)
            board.getField(1)(3).get.hasPiece should be(true)
            board.getField(1)(3).get.getPiece.get shouldBe a [Man]

            // Black: c3 x b4 - a5#
            controller.colourTurn should be(Colour.BLACK)
            controller.checkForcedCapture() should be(Map(board.getField(2)(2).get -> List(board.getField(0)(4).get)))
            controller.move(2, 2, 0, 4)._1 should be(true)
            board.getField(2)(2).get.hasPiece should be(false)
            board.getField(0)(4).get.hasPiece should be(true)
            board.getField(0)(4).get.getPiece.get shouldBe a [Man]
        }


//        "move a king to a new position if the new Field is empty" in {
//            val board = new BoardCreator(8).setupFields()
      //            val de.htwg.draughts.controller = new MoveController(board)
//
//            board.getField(2)(2).hasPiece should be(true)
//            board.getField(4)(4).hasPiece should be(false)
//
      //            de.htwg.draughts.controller.move(2, 2, 4, 4)
//
//            board.getField(2)(2).hasPiece should be(false)
//            board.getField(4)(4).hasPiece should be(true)
//        }
//
//        "return false if the target field has a piece on it" in {
//            val board = new BoardCreator(8).setupFields()
      //            val de.htwg.draughts.controller = new MoveController(board)
//
//            board.getField(2)(2).hasPiece should be(true)
//            board.getField(5)(5).hasPiece should be(true)
//
      //            de.htwg.draughts.controller.move(2, 2, 5, 5)
//
//            board.getField(1)(1).hasPiece should be(true)
//            board.getField(2)(2).hasPiece should be(true)
//        }
//
//        "return false if the move of a king is not diagonal" in {
//            val board = new BoardCreator(8).setupFields()
      //            val de.htwg.draughts.controller = new MoveController(board)
//
//            board.getField(2)(2).hasPiece should be(true)
//            board.getField(3)(4).hasPiece should be(false)
//
      //            de.htwg.draughts.controller.move(2, 2, 3, 4) should be(false)
//
//            board.getField(2)(2).hasPiece should be(true)
//            board.getField(3)(4).hasPiece should be(false)
//        }
//
//        "return true if the move of a king is backwards" in {
//            val board = new BoardCreator(8).setupFields()
      //            val de.htwg.draughts.controller = new MoveController(board)
      //
      //            de.htwg.draughts.controller.move(2, 2, 4, 4) should be(true)
//
//            board.getField(4)(4).hasPiece should be(true)
//            board.getField(2)(2).hasPiece should be(false)
//
      //            de.htwg.draughts.controller.move(4, 4, 2, 2) should be(true)
//
//            board.getField(4)(4).hasPiece should be(false)
//            board.getField(2)(2).hasPiece should be(true)
//        }
//
//        "return true if one opponents piece is on the inbetween field" in {
//            val board = new BoardCreator(8).setupFields()
      //            val de.htwg.draughts.controller = new MoveController(board)
//
//            board.getField(2)(2).hasPiece should be(true)
//            board.getField(3)(3).hasPiece should be(false)
//            board.getField(4)(4).hasPiece should be(false)
//            board.getField(5)(5).hasPiece should be(true)
//
      //            de.htwg.draughts.controller.move(2, 2, 4, 4) should be(true)
      //            de.htwg.draughts.controller.move(5, 5, 3, 3) should be(true)
//
//            board.getField(2)(2).hasPiece should be(false)
//            board.getField(3)(3).hasPiece should be(true)
//            board.getField(4)(4).hasPiece should be(false)
//            board.getField(5)(5).hasPiece should be(false)
//        }
//
//        "return false if own king moves to far if it captures" in {
//            val board = new BoardCreator(8).setupFields()
      //            val de.htwg.draughts.controller = new MoveController(board)
//
//            board.getField(2)(2).hasPiece should be(true)
//            board.getField(3)(3).hasPiece should be(false)
//            board.getField(4)(4).hasPiece should be(false)
//            board.getField(5)(5).hasPiece should be(true)
//
      //            de.htwg.draughts.controller.move(2, 2, 4, 4) should be(true)
      //            de.htwg.draughts.controller.move(5, 5, 2, 2) should be(false)
//
//            board.getField(2)(2).hasPiece should be(false)
//            board.getField(3)(3).hasPiece should be(false)
//            board.getField(4)(4).hasPiece should be(true)
//            board.getField(5)(5).hasPiece should be(true)
//        }
    }
}
