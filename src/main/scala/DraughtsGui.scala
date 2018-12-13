import model.BoardCreator
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import view.gui.GameScene

object DraughtsGui extends JFXApp {

  stage = new PrimaryStage {
    title = "Draughts"
    scene = new GameScene(new BoardCreator(8).setupFields())
  }

}