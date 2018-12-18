package view.gui

import model.{King, Man, Piece}
import scalafx.scene.Node
import scalafx.scene.layout.StackPane
import view.gui.styles.Styles

class FieldPane(tile: Tile) extends StackPane {

  def piece: Option[Piece] = tile.field.piece

  def highlight: Boolean = tile.field.highlighted

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


  children = tile.region :: getPieceOrElse(tile.piece)

  if (highlight) {
    style = Styles.highlightField
  } else {
    style = blackOrWhite
  }

  def blackOrWhite: String = {
    (row % 2, col % 2) match {
      case (0, 0) => Styles.fieldBoarderBlack
      case (0, 1) => Styles.fieldBoarderWhite
      case (1, 0) => Styles.fieldBoarderWhite
      case (1, 1) => Styles.fieldBoarderBlack
    }
  }

  def row: Int = tile.field.getRow

  def col: Int = tile.field.getColumn
}
