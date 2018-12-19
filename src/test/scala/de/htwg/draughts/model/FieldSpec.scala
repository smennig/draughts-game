package de.htwg.draughts.model

import org.scalatest.Matchers._
import org.scalatest.WordSpec

class FieldSpec extends WordSpec {
    "A Field" should {
        "have a column coordinate equal to the passed value" in {
            val field: Field = new Field(1, 2)
            field.getColumn should be(1)
        }

        "have a row coordinate equal to the passed value" in {
            val field: Field = new Field(1, 2)
            field.getRow should be(2)
        }

        "not have a piece if not specified" in {
            val field: Field = new Field(1, 2)
            field.getPiece should be(None)
        }

        "have a piece if specified" in {
            val field: Field = new Field(1, 2)
            val piece: Piece = new Man(Colour.BLACK)
            field.piece_(Some(piece))
            field.getPiece should be(Some(piece))
        }

        "have a colour based on its coordinates" in {
            val field1: Field = new Field(0, 0)
            val field2: Field = new Field(1, 0)
            val field3: Field = new Field(0, 1)
            val field4: Field = new Field(1, 1)
            field1.getColour should be(Colour.BLACK)
            field2.getColour should be(Colour.WHITE)
            field3.getColour should be(Colour.WHITE)
            field4.getColour should be(Colour.BLACK)
        }
    }
}
