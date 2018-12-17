package view.gui

import controller.MoveController
import model._
import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout._
import scalafx.scene.paint.Color
import scalafx.scene.text.{Font, FontWeight, Text}
import scalafx.scene.{Node, Scene}
import view.gui.styles.Styles

class GameScene(val controller: MoveController, val playerOne: Player = new Player(color = Colour.BLACK, name = "Player1"), val playerTwo: Player = new Player(color = Colour.WHITE, name = "Player2")) extends Scene {


  var lastClickedField: Option[FieldPane] = Option.empty

  def boardPane: BorderPane = new BorderPane() {
    center = new StackPane {
      children = buildBoard()
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

  def fields: Seq[Tile] = {
    val positions = for {
      row <- controller.board.fields.indices
      col <- controller.board.fields.indices
    } yield (row, col)
    positions.map { position =>
      val row = position._2
      val col = position._1
      val colour = controller.board.fields(row)(col).getColour
      val piece = controller.board.fields(row)(col).piece
      val square = FieldRegion(position, color = colour)
      Tile(position, square, piece)
    }
  }

  //TODO:simon fix make sure no clicks work correctly --> maybe test
  //TODO: simon check if color is valid
  def addClickHandlers(fieldPane: FieldPane): FieldPane = {
    fieldPane.onMouseClicked = (e: MouseEvent) => {
      println("last clickedField is: " + lastClickedField)
      lastClickedField match {
        case Some(oldField) =>
          oldField.piece match {
            case Some(piece) =>
              controller.move(oldField.row, oldField.col, fieldPane.row, fieldPane.col)
              lastClickedField = Option.empty
            case None => lastClickedField = Option.empty
          }
          controller.move(oldField.row, oldField.col, fieldPane.row, fieldPane.col)
          lastClickedField = Option.empty
        case None =>
          fieldPane.piece match {
            case Some(piece) => lastClickedField = Some(fieldPane)
            case None => lastClickedField = Option.empty
          }
      }

      repaint()

      println("click on row: " + fieldPane.row + "field :" + fieldPane.col + "piece: " + fieldPane.piece)
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

  private def repaint(): Unit = {
    root = boardPane
  }

  case class Tile(position: (Int, Int), region: Region, piece: Option[Piece]) {
    def x: Int = position._1

    def y: Int = position._2
  }

  repaint()
}
