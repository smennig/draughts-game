package de.htwg.draughts

import com.google.inject.AbstractModule
import de.htwg.draughts.controller.{GameController, MoveController}
import net.codingwell.scalaguice.ScalaModule


class DraughtsModule extends AbstractModule with ScalaModule {
  def configure(): Unit = {
    //    bind[GameController].to[MoveController]
  }
}
