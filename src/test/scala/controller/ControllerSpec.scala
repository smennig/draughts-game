package controller

import model.{Colour, Field, Man, Player}
import org.scalatest.Matchers._
import org.scalatest.WordSpec

class ControllerSpec extends WordSpec {
    "The Controller" should {
        "check if the selected field has a valid piece" in {
            val controller = new Controller()
            val player = new Player(Colour.BLACK)
            val field = new Field(0, 0)
            val piece = new Man(Colour.BLACK)
            field.piece_(Some(piece))
            controller.checkIfPieceIsValid(field, player) should be(true)
        }
    }
}
