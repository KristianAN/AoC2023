package aoc

import cats.effect.IOApp
import cats.effect.IO

object MainApp extends IOApp.Simple:
  val d1 =
    for
      data <- getNonEmptyLines[IO]("./resources/day1.txt")
      _ <- IO.println(s"Day one one: ${dayOne.solveOne(data)}")
      _ <- IO.println(s"Day one two: ${dayOne.solveTwoRegexp(data)}")
    yield ()

  override def run: IO[Unit] =
    d1
