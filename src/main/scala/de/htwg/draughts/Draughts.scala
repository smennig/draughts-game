package de.htwg.draughts

object Draughts {
  val gui = new DraughtsGui()
  val tui = new DraughtsTui()


  def main(args: Array[String]): Unit = {

    if (args.length == 0) {
      println("no parameter given, starting gui by default")
      gui.run()
    } else {

      args(0) match {
        case "tui" => tui.run()
        case "gui" => gui.run()
        case _ => gui.run()
      }
    }

  }


}
