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

        "move a piece to a new position if the new Field is empty" in {
            val controller = new MoveController()
            val oldField =  new Field(0, 0)
            val newField =  new Field(1, 1)
            val piece = new Man(Colour.BLACK)
            oldField.piece_(Some(piece))

            oldField.hasPiece should be(true)
            newField.hasPiece should be(false)

            controller.move(oldField, newField) should be(true)

            oldField.hasPiece should be(false)
            newField.hasPiece should be(true)
        }

        "return false if the target field has a piece on it" in {
            val controller = new MoveController()
            val oldField =  new Field(0, 0)
            val newField =  new Field(1, 1)
            val piece1 = new Man(Colour.BLACK)
            val piece2 = new Man(Colour.WHITE)
            oldField.piece_(Some(piece1))
            newField.piece_(Some(piece2))

            oldField.hasPiece should be(true)
            newField.hasPiece should be(true)

            controller.move(oldField, newField) should be(false)

            oldField.hasPiece should be(true)
            newField.hasPiece should be(true)
        }
    }
}
