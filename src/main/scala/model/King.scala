package model


//TODO : oliver move logic to move controller
class King(colour: Colour.Value) extends Piece(colour) {
  override def move(oldField: Field, newField: Field): Boolean = {
    oldField.clearPiece()
    newField.piece_(Some(this))
    true
  }


  override def capture(oldField: Field, newField: Field, captureField: Option[Field]): Boolean = {
    val rowMove = newField.getRow - captureField.get.getRow
    val columnMove = newField.getColumn - captureField.get.getColumn
    (rowMove, columnMove) match {
      case (1, 1) => captureHelp(oldField, newField, captureField); true
      case (1, -1) => captureHelp(oldField, newField, captureField); true
      case (-1, 1) => captureHelp(oldField, newField, captureField); true
      case (-1, -1) => captureHelp(oldField, newField, captureField); true
      case (_, _) => false
    }
  }

  private def captureHelp(oldField: Field, newField: Field, captureField: Option[Field]) = {
    oldField.clearPiece()
    newField.piece_(Some(this))
    captureField.get.clearPiece()
  }

  override def toString: String = {
    this.colour match {
      case Colour.BLACK => "B"
      case Colour.WHITE => "W"
    }
  }
}
