package de.htwg.draughts

import com.google.inject.name.Names
import com.google.inject.{Guice, Injector}
import net.codingwell.scalaguice.InjectorExtensions._
object Draughts {
  val injector: Injector = Guice.createInjector(new DraughtsModule())

  def main(args: Array[String]): Unit = {

    if (args.length == 0) {
      println("no parameter given, starting gui by default")
      startUi()
    } else {

      args(0) match {
        case "tui" => startUi("tui")
        case "gui" => startUi()
        case _ => startUi()
      }
    }


  }

  def startUi(uiType: String = "gui"): Unit = {
    val ui = injector.instance[Runnable](Names.named(uiType))
    ui.run()
  }


}
