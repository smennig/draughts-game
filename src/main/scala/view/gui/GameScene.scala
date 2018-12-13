package view.gui

import model._
import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout._
import scalafx.scene.paint.Color
import scalafx.scene.text.{Font, FontWeight, Text}
import scalafx.scene.{Node, Scene}
import view.gui.styles.Styles

class GameScene(val board: Board, val playerOne: Player = new Player(color = Colour.BLACK, name = "Player1"), val playerTwo: Player = new Player(color = Colour.WHITE, name = "Player2")) extends Scene {
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
  lazy val boardGrid: GridPane = buildBoard()

  val boardPane: BorderPane = new BorderPane() {
    center = new StackPane {
      children = boardGrid
      style = Styles.boardBorder
    }
    top = new VBox {
      style = Styles.lightGrayBackground
      children = Seq(
        new HBox {
          children = Seq(
            new Text {
              margin = Insets(0, 10, 0, 0)
              text = playerOne.name
              fill = getNameColor(playerOne)
              font = Font.font(null, FontWeight.Bold, 32)
            },
            //TODO:simon make current Turn indicator Dynamic
            new Text {
              text = "ist dran"
              fill = getNameColor(playerOne)
              font = Font.font(null, FontWeight.Bold, 32)
            })
        },
        new HBox {
          children = Seq(
            new Text {
              margin = Insets(0, 10, 0, 0)
              text = playerTwo.name
              fill = getNameColor(playerTwo)
              font = Font.font(null, FontWeight.Bold, 32)
            },
            new Text {
              //TODO:simon make current Turn indicator Dynamic
              text = "ist dran"
              fill = getNameColor(playerTwo)
              font = Font.font(null, FontWeight.Bold, 32)
            })
        })

      padding = Insets(20)
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

  //TODO:simon implement reasonable onClick logic
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

  def getNameColor(player: Player): Color = {
    player.color match {
      case Colour.WHITE => Color.White
      case Colour.BLACK => Color.Black
    }
  }


  case class Tile(position: (Int, Int), region: Region, piece: Option[Piece]) {
    def x: Int = position._1

    def y: Int = position._2
  }

  root = boardPane
}
