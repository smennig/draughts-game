package de.htwg.draughts

import de.htwg.draughts.view.gui.BeginGameGUI
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage

object DraughtsGui extends JFXApp {

  stage = new PrimaryStage {
    title = "Draughts"
    resizable = false
    maximized = true
  }

  stage.scene = new BeginGameGUI(stage).getStartGameScene
}
