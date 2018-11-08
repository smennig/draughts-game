package model

import org.scalatest.Matchers._
import org.scalatest.WordSpec

class PieceSpec extends WordSpec {
    "A Piece" should {
        "have a colour equal to the passed value" in {
            val piece: Piece = new Piece(Colour.WHITE)
            piece.getColour should be(Colour.WHITE)
        }
    }

    "A Man" should {
        "have a colour equal to the passed value" in {
            val man: Piece = new Man(Colour.WHITE)
            man.getColour should be(Colour.WHITE)
        }
    }
}
