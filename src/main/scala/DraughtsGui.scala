import controller.MoveController
import model.BoardCreator
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import view.gui.GameScene

object DraughtsGui extends JFXApp {

  stage = new PrimaryStage {
    title = "Draughts"
    val controller = new MoveController(new BoardCreator(8).setupFields())
    scene = new GameScene(controller)
    resizable = false
    //    maximized = true

  }

}