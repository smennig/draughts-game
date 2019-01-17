package de.htwg.draughts.model

trait Board extends Iterable[Field] {

    override def toString: String

    def fields: Array[Array[Field]]

    def getField(column: Int)(row: Int): Option[Field]

}
