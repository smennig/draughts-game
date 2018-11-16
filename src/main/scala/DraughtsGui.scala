import model.Board
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import view.GameScene

object DraughtsGui extends JFXApp {

  stage = new PrimaryStage {
    title = "Draughts"
    scene = new GameScene(new Board(8))
  }

}