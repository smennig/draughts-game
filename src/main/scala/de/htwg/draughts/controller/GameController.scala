package de.htwg.draughts.controller

import de.htwg.draughts.model.{Board, Colour, Field, Player}

trait GameController {
  def colourTurn: Colour.Value

  def board: Board

  def checkIfGameIsOver(): Boolean

  def toggleHighlightField(col: Int, row: Int): Boolean

  def checkIfPieceIsValid(field: Field, player: Player): Boolean

  def move(oldColumn: Int, oldRow: Int, newColumn: Int, newRow: Int): Boolean

  def whitePlayer: Player

  def blackPlayer: Player

}
