package controller

import model._

abstract class PieceController() {
  //    def movePiece(piece: Piece, oldField: Field, newField: Field): Boolean = {
  //        piece match {
  //            case m: Man => moveMan(piece, oldField, newField)
  //            case k: King => moveKing(piece, oldField, newField)
  //            case _ => false
  //        }
  //    }

  def move(oldField: Field, newField: Field): Boolean

  def capture(oldField: Field, newField: Field, captureField: Option[Field], player: Player): Boolean


  //    def capturePiece(piece: Piece, oldField: Field, newField: Field, captureField: Option[Field]): Boolean = {
  //        piece match {
  //            case m: Man => captureMan(piece, oldField, newField, captureField)
  //            case k: King => captureKing(piece, oldField, newField, captureField)
  //            case _ => false
  //        }
  //    }


}
