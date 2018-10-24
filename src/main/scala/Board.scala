class Board(size: Int) {

  var fields = Array.ofDim[Field](size, size)


  def setupFields() {
     for (i <- 0 until size; j <- 0 until  size) {
      val field = new Field(row=i, column = j)
      fields(i)(j) = field
    }
  }
}
