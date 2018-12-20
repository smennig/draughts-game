import controller.MoveController
import model.{BoardCreator, Colour, Player}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import view.gui.GameScene

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