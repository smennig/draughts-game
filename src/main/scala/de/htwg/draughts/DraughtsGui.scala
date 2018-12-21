package de.htwg.draughts

import de.htwg.draughts.view.gui.GameScene
import de.htwg.draughts.controller.MoveController
import de.htwg.draughts.model.{BoardCreator, Colour, Player}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage

object DraughtsGui extends JFXApp {

  stage = new PrimaryStage {
    title = "Draughts"
    val blackPlayer = new Player(color = Colour.BLACK, name = "Player1", turn = true)
    val whitePlayer = new Player(color = Colour.WHITE, name = "Player2", turn = false)
    val controller = new MoveController(new BoardCreator(8).setupFields(), blackPlayer, whitePlayer)
    scene = new GameScene(controller, blackPlayer, whitePlayer)
    resizable = false
    //    maximized = true

  }

}
