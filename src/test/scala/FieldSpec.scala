import org.scalatest.WordSpec
import org.scalatest.Matchers._

class FieldSpec extends WordSpec {
    "A Field" should {
        "have a column coordinate equal to the passed value" in {
            val field: Field = new Field(1, 2)
            field.getColumn should be(1)
        }

        "have a row coordinate equal to the passed value" in {
            val field: Field = new Field(1, 2)
            field.getRow should be(2)
        }

        "not have a piece if not specified" in {
            val field: Field = new Field(1, 2)
            field.getPiece should be(None)
        }

        "have a piece if specified" in {
            val field: Field = new Field(1, 2)
            val piece: Piece = new Man(Colour.BLACK, field)
            field.getPiece should be(Some(piece))
        }
    }
}
