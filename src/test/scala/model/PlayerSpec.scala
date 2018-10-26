package model

import org.scalatest.Matchers._
import org.scalatest.WordSpec

class PlayerSpec extends WordSpec {
    "A Player" should {
        "exist if created" in {
            val player = new Player()
        }
    }
}
