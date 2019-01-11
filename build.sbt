name := "Draughts"
organization := "de.htwg.draughts"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12"

libraryDependencies += "com.google.inject" % "guice" % "4.1.0"
libraryDependencies += "com.google.inject.extensions" % "guice-assistedinject" % "4.1.0"
libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0"

mainClass in(Compile, run) := Some("de.htwg.draughts.Draughts")