package de.htwg.draughts

import com.google.inject.name.Names
import com.google.inject.{Guice, Injector}
import net.codingwell.scalaguice.InjectorExtensions._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success, Try}
object Draughts {
  val injector: Injector = Guice.createInjector(new DraughtsModule())

  def main(args: Array[String]): Unit = {

    if (args.length == 0) {
      startBothUis()
    } else {

      args(0) match {
        case "tui" => startUi("tui")
        case "gui" => startUi("gui")
        case _ => startBothUis()
      }

    }

  }

  def startUi(uiType: String): Unit = {
    val ui = injector.instance[Runnable](Names.named(uiType))
    ui.run()
  }

  def startBothUis(): Unit = {
    println("Es wurde kein valider Parameter eingegeben, GUI und TUI werden gestartet!")
    val guiFuture = Future(startUi("gui"))
    val tuiFuture = Future(startUi("tui"))
    Await.ready(guiFuture zip tuiFuture, Duration.Inf).onComplete {
      case Success(_) => println("Das Spiel wird beendet!")
      case Failure(e) => println("Es ist ein Fehler aufgetreten!", e.getMessage)
    }
  }
}


