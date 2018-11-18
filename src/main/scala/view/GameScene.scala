package view


import model.{Board, Piece}
import scalafx.scene.layout.{BorderPane, GridPane, Region, StackPane}
import scalafx.scene.{Node, Scene}

class GameScene(val board: Board) extends Scene {
  val boardPane: BorderPane = new BorderPane() {
    center = new StackPane() {
      children = buildBoard()
    }
    antialiasing
  }

  lazy val fields: Seq[Tile] = {
    val positions = for {
      row <- board.fields.indices
      col <- board.fields.indices
    } yield (row, col)
    positions.map { position =>
      val row = position._2
      val col = position._1
      val colour = board.fields(row)(col).getColour
      val piece = board.fields(row)(col).piece
      val square = FieldRegion(position, colour)
      Tile(position, square, piece)
    }
  }

  def buildBoard(): GridPane = {
    val guiBoard = new GridPane()
    fields.foreach(tile => {
      guiBoard.add(new StackPane() {
        children = tile.region
      }, tile.x, tile.y)
    })

    fields.foreach(tile => {
      guiBoard.add(new StackPane() {
        children = getPiceOrElse(tile.piece)
      }, tile.x, tile.y)
    })
    guiBoard
  }


  case class Tile(position: (Int, Int), region: Region, piece: Option[Piece]) {
    def x: Int = position._1

    def y: Int = position._2
  }

  def getPiceOrElse(piece: Option[Piece]): List[Node] = {

    val gamePieces = List.empty[Node]
    piece match {
      case Some(piece) => GamePiece(piece.getColour) :: gamePieces //      case None => Option.empty
      case None => gamePieces
    }
  }

  root = boardPane
}
