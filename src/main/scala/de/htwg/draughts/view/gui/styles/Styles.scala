package de.htwg.draughts.view.gui.styles

object Styles {
    val blackFieldColor: String = "rgba(117, 117, 117, 0.7)"
    val whiteFieldColor: String = "rgb(230,230,230)"
    val boardBorder: String = "-fx-background-color: white;" +
        "-fx-border-color: black;" +
        "-fx-border-width: 1;" +
        "-fx-padding: 6;"
    val lightGrayBackground: String = "-fx-background-color: rgba(117, 117, 117, 0.5);"

    val highlightField: String = "-fx-border-color: red;" +
        "-fx-border-width: 1;"
    val fieldBoarderBlack: String = s"-fx-border-color: $blackFieldColor;" + "-fx-border-width: 1;"
    val fieldBoarderWhite: String = s"-fx-border-color: $whiteFieldColor;" + "-fx-border-width: 1;"
}
