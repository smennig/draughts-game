package controller

import model._
import org.scalatest.Matchers._
import org.scalatest.WordSpec

class MoveControllerSpec extends WordSpec {
    "The MoveController" should {
        "check if the selected field has a valid piece" in {
            val controller = new MoveController()
            val player = new Player("Player 1", Colour.BLACK)
            val field = new Field(0, 0)
            val piece = new Man(Colour.BLACK)
            field.piece_(Some(piece))
            controller.checkIfPieceIsValid(field, player) should be(true)
        }
    }
}
