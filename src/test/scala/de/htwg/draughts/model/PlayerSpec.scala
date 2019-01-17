package de.htwg.draughts.model

import com.google.inject.{Guice, Injector}
import de.htwg.draughts.DraughtsModule
import de.htwg.draughts.controller.CommandLineController
import org.scalatest.Matchers._
import org.scalatest.WordSpec
import net.codingwell.scalaguice.InjectorExtensions._

class PlayerSpec extends WordSpec {
  val injector: Injector = Guice.createInjector(new DraughtsModule())
  val white = "w"
  val black = "b"

  "A Player" when {
    "valid" should {
      val validPlayerBlack = new Player("TestPlayerBlack", Colour.BLACK, true)
      val validPlayerWhite = new Player("TestPlayerWhite", Colour.WHITE, false)
      "have a name" in {
        validPlayerBlack.name should be ("TestPlayerBlack")
        validPlayerWhite.name should be ("TestPlayerWhite")
      }
      "have a color" in {
        validPlayerBlack.color should be (Colour.BLACK)
        validPlayerWhite.color should be (Colour.WHITE)
      }
      "have a turn" in {
        validPlayerBlack.turn should be (true)
        validPlayerWhite.turn should be (false)
      }
      "invert its turn" in {
        validPlayerBlack.turn_()
        validPlayerWhite.turn_()
        validPlayerBlack.turn should be (false)
        validPlayerWhite.turn should be (true)
      }
    }

    "created" should {
      val controller = injector.instance[CommandLineController]
      "be able to choose the color black" in {
        val color = controller.chooseFirstColor(black)
        color should be (Option(Colour.BLACK))
      }
      "be able to choose the color white" in {
        val color = controller.chooseFirstColor(white)
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
        translatedBlackColor should be(black)
      }
      "print the translated color for white" in {
        val translatedBlackColor = controller.translateEnumColour(Colour.WHITE)
        translatedBlackColor should be(white)
      }
    }
  }
}

