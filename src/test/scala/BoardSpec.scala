import org.scalatest.Matchers._
import org.scalatest.WordSpec

class BoardSpec extends WordSpec {
  "A Board" should {
    "have a Array of fields with the passed size" in {
      val board: Board = new Board(8);
      board.fields should not be empty
      board.fields.size should be(8)
    }

    "have a Array of fields with the same coordinates in the filed and array" in {
      val board: Board = new Board(8);
      board.setupFields()
      board.fields(0)(0).getColumn should be(0)
      board.fields(0)(0).getRow should be(0)

      board.fields(7)(5).getColumn should be(5)
      board.fields(7)(5).getRow should be(7)
    }

  }
}
