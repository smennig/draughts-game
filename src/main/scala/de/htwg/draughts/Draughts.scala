package de.htwg.draughts

import com.google.inject.name.Names
import com.google.inject.{Guice, Injector}
import net.codingwell.scalaguice.InjectorExtensions._

object Draughts {
    val injector: Injector = Guice.createInjector(new DraughtsModule())

    def main(args: Array[String]): Unit = {

        if (args.length == 0) {
            startUi("gui")
        } else {
            args(0) match {
                case "gui" => startUi("gui")
                case _ => startUi("tui")
            }
        }
    }

    def startUi(uiType: String): Unit = {
        val ui = injector.instance[Runnable](Names.named(uiType))
        ui.run()
    }

}


