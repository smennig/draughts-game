package de.htwg.draughts

import de.htwg.draughts.view.gui.GameScene
import de.htwg.draughts.controller.MoveController
import de.htwg.draughts.model.{BoardCreator, Colour, Player}
import javafx.embed.swing.JFXPanel
import scalafx.application.{JFXApp, Platform}
import scalafx.application.JFXApp.PrimaryStage
import scalafx.stage.Stage

class DraughtsGui {

  def run(): Unit = {
    // Shortcut to initialize JavaFX, force initialization by creating JFXPanel() object
    // (we will not use it for anything else)
    new JFXPanel()

    // Create a dialog stage and display it on JavaFX Application Thread
    Platform.runLater {

      // Create dialog
      val dialogStage: Stage = new Stage {
        title = "Draughts"
        val blackPlayer = new Player(color = Colour.BLACK, name = "Player1", turn = true)
        val whitePlayer = new Player(color = Colour.WHITE, name = "Player2", turn = false)
        val controller = new MoveController(new BoardCreator(8).setupFields(), blackPlayer, whitePlayer)
        scene = new GameScene(controller)
        resizable = false
        //    maximized = true

      }
      // Show dialog and wait till it is closed
      dialogStage.showAndWait()
      // Force application exit
      Platform.exit()
    }
  }
}
