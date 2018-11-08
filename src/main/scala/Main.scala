package main

import controller.CommandLineController

object Main {
  def main(args: Array[String]): Unit = {
    val clc = new CommandLineController
    clc.readPlayerAttributes()

  }
}
