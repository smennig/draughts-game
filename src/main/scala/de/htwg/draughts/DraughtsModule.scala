package de.htwg.draughts

import com.google.inject.assistedinject.FactoryModuleBuilder
import com.google.inject.name.Names
import com.google.inject.{AbstractModule, Provides}
import de.htwg.draughts.controller.{CommandLineController, GameController, GameControllerFactory, MoveController}
import de.htwg.draughts.model.{Board, BoardCreator}
import net.codingwell.scalaguice.ScalaModule

class DraughtsModule extends AbstractModule with ScalaModule {
    override def configure(): Unit = {
        bind[Runnable].annotatedWith(Names.named("tui")).to[DraughtsTui]
        bind[Runnable].annotatedWith(Names.named("gui")).to[DraughtsGui]
        bind[CommandLineController]

        install(new FactoryModuleBuilder()
            .implement(classOf[GameController], classOf[MoveController])
            .build(classOf[GameControllerFactory]))
    }

    @Provides
    def provideBoard(): Board = {
        new BoardCreator(8).setupFields()
    }


    def provideWinBoard(): Board = {
        new BoardCreator(8).setupWinFields()
    }

}
