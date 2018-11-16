package view


import model.Board
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.layout.{BorderPane, GridPane, Region, StackPane}
import scalafx.scene.paint.Color

class GameScene(val board: Board) extends Scene {
  val boardPane: BorderPane = new BorderPane() {
    center = new StackPane() {
      children = buildBoard()
    }
  }

  lazy val fields: Seq[Tile] = {
    val positions = for {
      row <- board.fields.indices
      col <- board.fields.indices
    } yield (row, col)
    positions.map { position =>
      val row = position._1
      val col = position._2
      val square = FieldRegion(position)
      Tile(position, square)
    }
  }

  def buildBoard(): GridPane = {
    val guiBoard = new GridPane()
    fields.foreach(tile => {
      guiBoard.add(new StackPane() {
        // Pieces are just added for testing purposes
        children = Seq(tile.region, new GamePiece(Color.White) {
          alignment = Pos.Center
        })

      }, tile.x, tile.y)
    })
    guiBoard
  }


  case class Tile(position: (Int, Int), region: Region) {
    def x: Int = position._1

    def y: Int = position._2
  }

  root = boardPane
}
