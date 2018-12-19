package de.htwg.draughts.model

import org.scalatest.Matchers._
import org.scalatest.WordSpec
import de.htwg.draughts.controller.CommandLineController

class PlayerSpec extends WordSpec {
  "A Player" when {
    "valid" should {
      val validPlayerBlack = new Player("TestPlayerBlack", Colour.BLACK)
      val validPlayerWhite = new Player("TestPlayerWhite", Colour.WHITE)
      "have a name" in {
        validPlayerBlack.name should be ("TestPlayerBlack")
        validPlayerWhite.name should be ("TestPlayerWhite")
      }
      "have a color" in {
        validPlayerBlack.color should be (Colour.BLACK)
        validPlayerWhite.color should be (Colour.WHITE)
      }
    }

    "created" should {
      val controller = new CommandLineController()
      "be able to choose the color black" in {
        val color = controller.chooseFirstColor("Schwarz")
        color should be (Option(Colour.BLACK))
      }
      "be able to choose the color white" in {
        val color = controller.chooseFirstColor("Weiß")
        color should be (Option(Colour.WHITE))
      }
      "not allow invalid string inputs" in {
        val color = controller.chooseFirstColor("asdasdasfrgdf")
        color should be (None)
      }
      "choose the color white if the first player is black" in {
        val color = controller.chooseSecondColor(Colour.BLACK)
        color should be (Colour.WHITE)
      }
      "choose the color black if the first player is white" in {
        val color = controller.chooseSecondColor(Colour.WHITE)
        color should be (Colour.BLACK)
      }
      "print the translated color for black" in {
        val translatedBlackColor = controller.translateEnumColour(Colour.BLACK)
        translatedBlackColor should be ("Schwarz")
      }
      "print the translated color for white" in {
        val translatedBlackColor = controller.translateEnumColour(Colour.WHITE)
        translatedBlackColor should be ("Weiß")
      }
    }
  }
}

