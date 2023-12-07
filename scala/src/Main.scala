package aoc

import cats.effect.IOApp
import cats.effect.IO

object MainApp extends IOApp.Simple:
  val d1 =
    for
      day1 <- getNonEmptyLines[IO]("./resources/day1.txt")
      day2 <- getNonEmptyLines[IO]("./resources/day2.txt")
      day3 <- getNonEmptyLines[IO]("./resources/day3.txt")
      day4 <- getNonEmptyLines[IO]("./resources/day4.txt")
      day5 <- getString[IO]("./resources/day5.txt")
      day7 <- getNonEmptyLines[IO]("./resources/day7.txt")
      start <- IO(System.nanoTime())
      _ <- IO.println(s"Day one one: ${dayOne.solveOne(day1)}")
      _ <- IO.println(s"Day one two: ${dayOne.solveTwoRegexp(day1)}")
      _ <- IO.println(s"Day two one: ${dayTwo.solveOne(day2)}")
      _ <- IO.println(s"Day two two: ${dayTwo.solveTwo(day2)}")
      _ <- IO.println(s"Day three one: ${dayThree.solveOne(day3)}")
      _ <- IO.println(s"Day three two: ${dayThree.solveTwo(day3)}")
      _ <- IO.println(s"Day four one: ${dayFour.solveOne(day4)}")
      _ <- IO.println(s"Day four one: ${dayFour.solveTwo(day4)}")
      _ <- IO.println(s"Day five one: ${dayFive.solveOne(day5)}")
      // _ <- IO.println(s"Day six: ${daySix.solveOneTwo}")
      _ <- IO.println(s"Day seven one: ${daySeven.solveOne(day7)}")
      _ <- IO.println(s"Day seven two: ${daySeven.solveTwo(day7)}")
      // _ <- IO.println(s"Day five two: ${dayFive.solveTwo(day5)}")
      _ <- IO.println(s"${(System.nanoTime() - start) / 1000000} ms")
    yield ()

  override def run: IO[Unit] =
    d1
    // IO.parReplicateAN(1)(10000, d1).map(_ => ())
