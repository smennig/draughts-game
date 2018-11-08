package model

import org.scalatest.Matchers._
import org.scalatest.WordSpec

class PieceSpec extends WordSpec {
    "A Man" should {
        "have a colour equal to the passed value" in {
            val man: Piece = new Man(Colour.WHITE)
            man.getColour should be(Colour.WHITE)
        }
    }
}
