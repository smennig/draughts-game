import org.scalatest.WordSpec
import org.scalatest.Matchers._

class PlayerSpec extends WordSpec {
  "A Player" when {
    "valid" should{
      val validPlayerBlack = new Player("TestPlayerBlack", PlayerColor.Schwarz)
      val validPlayerWhite = new Player("TestPlayerWhite", PlayerColor.Weiss)
      "have a name" in {
        validPlayerBlack.name should be ("TestPlayerBlack")
        validPlayerWhite.name should be ("TestPlayerWhite")
      }
      "have a color" in {
        validPlayerBlack.color should be (PlayerColor.Schwarz)
        validPlayerWhite.color should be (PlayerColor.Weiss)
      }
    }
  }
}