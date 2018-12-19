package de.htwg.draughts.controller

import de.htwg.draughts.model.{Board, Field, Player}

trait GameController {
  def board: Board


  def toggleHighlightField(col: Int, row: Int): Boolean

  def checkIfPieceIsValid(field: Field, player: Player): Boolean

  def move(oldColumn: Int, oldRow: Int, newColumn: Int, newRow: Int): Boolean

}
