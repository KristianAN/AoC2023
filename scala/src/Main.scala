package aoc

import cats.effect.IOApp
import cats.effect.IO

object MainApp extends IOApp.Simple:
  val d1 =
    for
      data <- getNonEmptyLines[IO]("./resources/day1.txt")
      data2 <- getNonEmptyLines[IO]("./resources/day2.txt")
      _ <- IO.println(s"Day one one: ${dayOne.solveOne(data)}")
      _ <- IO.println(s"Day one two: ${dayOne.solveTwoRegexp(data)}")
      _ <- IO.println(s"Day two one: ${dayTwo.solveOne(data2)}")
      _ <- IO.println(s"Day two two: ${dayTwo.solveTwo(data2)}")
    yield ()

  override def run: IO[Unit] =
    d1
