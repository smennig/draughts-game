package de.htwg.draughts

import com.google.inject.Guice
import de.htwg.draughts.{DraughtsGui, DraughtsModule}
import de.htwg.draughts.controller.{CommandLineController, GameController}
import net.codingwell.scalaguice.InjectorExtensions._

object Draughts {


  //  val injector = Guice.createInjector(new DraughtsModule())

  //  val c = injector.instance[GameController];


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
