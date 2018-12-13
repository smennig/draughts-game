package view

import scalafx.Includes._
import model.{Board, King, Man, Piece}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{BorderPane, GridPane, Region, StackPane}
import scalafx.scene.{Node, Scene}

class GameScene(val board: Board) extends Scene {
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
      val square = FieldRegion(position, color = colour)
      Tile(position, square, piece)
    }
  }
  val boardPane: BorderPane = new BorderPane() {
    center = new StackPane {
      children = buildBoard()
    }
    antialiasing
  }

  def buildBoard(): GridPane = {
    val guiBoard = new GridPane()
    fields.foreach(tile => {
      guiBoard.add(addClickHandlers(new FieldPane(row = tile.x, col = tile.y, piece = tile.piece) {
        children = tile.region :: getPieceOrElse(tile.piece)
      }), tile.x, tile.y)
    })
    guiBoard
  }

  //TODO implement reasonable onClick logic
  def addClickHandlers(fieldPane: FieldPane): FieldPane = {
    fieldPane.onMouseClicked = (e: MouseEvent) => {
      print("click" + fieldPane.row + fieldPane.col + fieldPane.piece)
    }
    fieldPane
  }

  def getPieceOrElse(piece: Option[Piece]): List[Node] = {
    piece match {
      case Some(p) => getPieceView(p)
      case None => List.empty[Node]
    }
  }

  def getPieceView(piece: Piece): List[Node] = {
    piece match {
      case piece: Man => List(GamePiece(piece.getColour).getView)
      case piece: King => List(GamePiece(piece.getColour, isKing = true).getView)
    }
  }


  case class Tile(position: (Int, Int), region: Region, piece: Option[Piece]) {
    def x: Int = position._1

    def y: Int = position._2
  }

  root = boardPane
}
