name := "Draughts"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12"

mainClass in (Compile, run) := Some("Main")