package de.htwg.draughts.controller

import de.htwg.draughts.model._
import org.scalatest.Matchers._
import org.scalatest.WordSpec

class ManControllerSpec extends WordSpec {
    "The Man Controller" should {
        "check if a man is able to move" in {
            val board = new BoardCreator(8).setupEmptyBoard()
            val blackMan1 = new Man(Colour.BLACK)
            val blackMan2 = new Man(Colour.BLACK)
            val whiteMan = new Man(Colour.WHITE)

            board.setPieceOnField(6)(0)(blackMan1)
            board.setPieceOnField(6)(2)(blackMan2)
            board.setPieceOnField(7)(1)(whiteMan)

            val manController1 = new ManController(blackMan1)
            manController1.canMakeValidMove(board.getField(6)(0).get, board) should be(true)

            val manController2 = new ManController(blackMan2)
            manController2.canMakeValidMove(board.getField(6)(2).get, board) should be(true)

            val manController3 = new ManController(whiteMan)
            manController3.canMakeValidMove(board.getField(7)(1).get, board) should be(false)
        }

        "check if a man is able to capture" in {
            val board = new BoardCreator(8).setupEmptyBoard()
            val blackMan1 = new Man(Colour.BLACK)
            val blackMan2 = new Man(Colour.BLACK)
            val whiteMan1 = new Man(Colour.WHITE)
            val whiteMan2 = new Man(Colour.WHITE)

            board.setPieceOnField(4)(0)(blackMan1)
            board.setPieceOnField(6)(0)(blackMan2)
            board.setPieceOnField(5)(1)(whiteMan1)
            board.setPieceOnField(4)(2)(whiteMan2)

            val manController1 = new ManController(blackMan1)
            manController1.canMakeValidMove(board.getField(4)(0).get, board) should be(true)

            val manController2 = new ManController(blackMan2)
            manController2.canMakeValidMove(board.getField(6)(0).get, board) should be(true)

            val manController3 = new ManController(whiteMan1)
            manController3.canMakeValidMove(board.getField(5)(1).get, board) should be(false)

            val manController4 = new ManController(whiteMan2)
            manController4.canMakeValidMove(board.getField(4)(2).get, board) should be(true)
        }
    }
}
