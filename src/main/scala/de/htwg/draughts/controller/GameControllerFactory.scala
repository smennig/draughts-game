package de.htwg.draughts.controller

import com.google.inject.assistedinject.Assisted
import de.htwg.draughts.model.Player

trait GameControllerFactory {

  def create(@Assisted("blackPlayer") blackPlayer: Player, @Assisted("whitePlayer") whitePlayer: Player): GameController

}
