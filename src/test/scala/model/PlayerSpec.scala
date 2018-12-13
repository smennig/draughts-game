package model

import org.scalatest.Matchers._
import org.scalatest.WordSpec
import java.io.ByteArrayInputStream
import controller.CommandLineController

class PlayerSpec extends WordSpec {
  "A Player" when {
    "valid" should {
      val validPlayerBlack = new Player("TestPlayerBlack", PlayerColor.Schwarz)
      val validPlayerWhite = new Player("TestPlayerWhite", PlayerColor.Weiß)
      "have a name" in {
        validPlayerBlack.name should be ("TestPlayerBlack")
        validPlayerWhite.name should be ("TestPlayerWhite")
      }
      "have a color" in {
        validPlayerBlack.color should be (PlayerColor.Schwarz)
        validPlayerWhite.color should be (PlayerColor.Weiß)
      }
    }

    "created" should {
      val controller = new CommandLineController()
      "be able to choose the color black" in {
        val in = new ByteArrayInputStream("Schwarz".getBytes)
        System.setIn(in)
        val color = controller.chooseFirstColor()
        color should be (PlayerColor.Schwarz)
      }
    }
  }
}
