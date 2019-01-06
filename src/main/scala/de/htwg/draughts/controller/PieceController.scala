package de.htwg.draughts.controller

import de.htwg.draughts.model._

abstract class PieceController() {
  def move(oldField: Field, newField: Field): Boolean

  def capture(oldField: Field, newField: Field, captureField: Option[Field], player: Player): Boolean
}
