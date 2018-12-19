package de.htwg.draughts

import de.htwg.draughts.controller.MoveController
import de.htwg.draughts.model.BoardCreator
import de.htwg.draughts.view.gui.GameScene
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage

object DraughtsGui extends JFXApp {

  stage = new PrimaryStage {
    title = "Draughts"
    val controller = new MoveController(new BoardCreator(8).setupFields())
    scene = new GameScene(controller)
    resizable = false
    //    maximized = true

  }

}
