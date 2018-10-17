import org.scalatest.WordSpec
import org.scalatest.Matchers._

class PlayerSpec extends WordSpec {
    "A Player" should {
        "exist if created" in {
            val player = new Player()
        }
    }
}
