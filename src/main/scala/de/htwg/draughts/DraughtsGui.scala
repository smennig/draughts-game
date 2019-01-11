package de.htwg.draughts

import com.google.inject.Inject
import de.htwg.draughts.controller.GameControllerFactory
import de.htwg.draughts.model.Player
import javafx.embed.swing.JFXPanel
import scalafx.application.Platform
import de.htwg.draughts.view.gui.{BeginGameGUI, GameScene}
import scalafx.stage.Stage

class DraughtsGui @Inject()(gameControllerFactory: GameControllerFactory) extends Runnable {


  def run(): Unit = {
    // Shortcut to initialize JavaFX, force initialization by creating JFXPanel() object
    // (we will not use it for anything else)
    new JFXPanel()

    // Create a dialog stage and display it on JavaFX Application Thread
    Platform.runLater {

      //       Create dialog
      val gameStage: Stage = new Stage {
        title = "Draughts"

        resizable = false

      }

      gameStage.scene = new BeginGameGUI((player1: Player, player2: Player) => {
        val controller = gameControllerFactory.create(player1, player2)
        gameStage.scene = new GameScene(controller)
      }).getStartGameScene
      // Show dialog and wait till it is closed
      gameStage.showAndWait()
      // Force application exit
      Platform.exit()
    }
  }


}