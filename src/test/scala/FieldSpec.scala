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
    }
}
