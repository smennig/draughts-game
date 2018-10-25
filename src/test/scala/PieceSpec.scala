import org.scalatest.WordSpec
import org.scalatest.Matchers._

class PieceSpec extends WordSpec {
    "A Piece" should {
        "have a colour equal to the passed value" in {
            val piece: Piece = new Piece(Colour.WHITE, new Field(0, 0))
            piece.getColour should be(Colour.WHITE)
        }

        "have a start position column equal to the passed value" in {
            val piece: Piece = new Piece(Colour.WHITE, new Field(0, 0))
            piece.position.getColumn should be(0)
        }

        "have a start position row equal to the passed value" in {
            val piece: Piece = new Piece(Colour.WHITE, new Field(0, 0))
            piece.position.getRow should be(0)
        }
    }

    "A Man" should {
        "have a colour equal to the passed value" in {
            val man: Piece = new Man(Colour.WHITE, new Field(0, 0))
            man.getColour should be(Colour.WHITE)
        }

        "have a start position column equal to the passed value" in {
            val man: Piece = new Man(Colour.WHITE, new Field(0, 0))
            man.position.getColumn should be(0)
        }

        "have a start position row equal to the passed value" in {
            val man: Piece = new Man(Colour.WHITE, new Field(0, 0))
            man.position.getRow should be(0)
        }

        "be able to move one step diagonally forward if the target field is empty" in {
            val man: Piece = new Man(Colour.WHITE, new Field(2, 2))
            val newPosition = new Field(3, 3)
            man.move(newPosition)
            man.position should be(newPosition)
        }

        "not be able to move one step diagonally forward if the target field has a piece on it" in {
            val man1: Piece = new Man(Colour.WHITE, new Field(2, 2))
            val newPosition = new Field(3, 3)
            val man2: Piece = new Man(Colour.WHITE, newPosition)
            the [IllegalArgumentException] thrownBy man1.move(newPosition) should have message "The new position is already occupied by a piece"
        }
    }
}
