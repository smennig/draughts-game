package de.htwg.draughts.model

import org.scalatest.Matchers._
import org.scalatest.WordSpec

class PieceSpec extends WordSpec {
    "A Man" should {
        "have a colour equal to the passed value" in {
            val man: Piece = new Man(Colour.WHITE)
            man.getColour should be(Colour.WHITE)
        }
    }

    "A King" should {
        "have a colour equal to the passed value" in {
            val king: Piece = new King(Colour.WHITE)
            king.getColour should be(Colour.WHITE)
        }
    }
}
