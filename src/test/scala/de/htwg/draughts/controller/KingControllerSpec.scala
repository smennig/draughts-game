package de.htwg.draughts.controller

import de.htwg.draughts.model._
import org.scalatest.Matchers._
import org.scalatest.WordSpec

class KingControllerSpec extends WordSpec {
    "The King Controller" should {
        "check if a king is able to move" in {
            val board = new BoardCreator(8).setupEmptyBoard()

            val blackKing = new King(Colour.BLACK)
            val blackMan1 = new King(Colour.BLACK)
            val blackMan2 = new King(Colour.BLACK)
            val blackMan3 = new King(Colour.BLACK)
            val whiteKing = new King(Colour.WHITE)
            val whiteMan = new Man(Colour.WHITE)

            board.setPieceOnField(4)(4)(blackKing)
            board.setPieceOnField(2)(4)(blackMan1)
            board.setPieceOnField(6)(6)(blackMan2)
            board.setPieceOnField(3)(5)(blackMan3)
            board.setPieceOnField(1)(5)(whiteKing)
            board.setPieceOnField(7)(1)(whiteMan)

            val kingController1 = new KingController(blackKing)
            kingController1.canMakeValidMove(board.getField(4)(4).get, board) should be(true)

            val kingController2 = new KingController(whiteKing)
            kingController2.canMakeValidMove(board.getField(1)(5).get, board) should be(true)
        }

        "check if a king is able to capture" in {
            val board = new BoardCreator(8).setupEmptyBoard()

            val blackKing = new King(Colour.BLACK)
            val blackMan = new King(Colour.BLACK)
            val whiteKing = new King(Colour.WHITE)
            val whiteMan = new Man(Colour.WHITE)

            board.setPieceOnField(4)(4)(blackKing)
            board.setPieceOnField(2)(4)(blackMan)
            board.setPieceOnField(1)(5)(whiteKing)
            board.setPieceOnField(7)(1)(whiteMan)

            val kingController1 = new KingController(blackKing)
            kingController1.canMakeValidMove(board.getField(4)(4).get, board) should be(true)

            val kingController2 = new KingController(whiteKing)
            kingController2.canMakeValidMove(board.getField(1)(5).get, board) should be(true)
        }

        "check if a king is able to move 2" in {
            val board = new BoardCreator(8).setupEmptyBoard()

            val blackKing = new King(Colour.BLACK)
            val blackMan = new King(Colour.BLACK)
            val whiteKing = new King(Colour.WHITE)
            val whiteMan1 = new Man(Colour.WHITE)
            val whiteMan2 = new Man(Colour.WHITE)

            board.setPieceOnField(7)(1)(blackKing)
            board.setPieceOnField(5)(1)(blackMan)
            board.setPieceOnField(6)(0)(whiteKing)
            board.setPieceOnField(6)(2)(whiteMan1)
            board.setPieceOnField(5)(3)(whiteMan2)

            val kingController1 = new KingController(blackKing)
            kingController1.canMakeValidMove(board.getField(7)(1).get, board) should be(false)

            val kingController2 = new KingController(whiteKing)
            kingController2.canMakeValidMove(board.getField(6)(0).get, board) should be(true)
        }
    }
}
