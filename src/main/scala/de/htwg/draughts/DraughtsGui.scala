package de.htwg.draughts

import javafx.embed.swing.JFXPanel
import scalafx.application.Platform
import de.htwg.draughts.view.gui.BeginGameGUI
import scalafx.stage.Stage

class DraughtsGui {

  def run(): Unit = {
    // Shortcut to initialize JavaFX, force initialization by creating JFXPanel() object
    // (we will not use it for anything else)
    new JFXPanel()

    // Create a dialog stage and display it on JavaFX Application Thread
    Platform.runLater {

      // Create dialog
      val gameStage: Stage = new Stage {
        title = "Draughts"

        resizable = false

      }
      gameStage.scene = new BeginGameGUI(gameStage).getStartGameScene
      // Show dialog and wait till it is closed
      gameStage.showAndWait()
      // Force application exit
      Platform.exit()
    }
  }
}