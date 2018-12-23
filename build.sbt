name := "Draughts"
organization := "de.htwg.draughts"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12"

//TODO make env Variable to choose GUI,TUI WUI
mainClass in(Compile, run) := Some("de.htwg.draughts.DraughtsTui")