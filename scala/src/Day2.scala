package aoc
package dayTwo

case class Game(
    id: Int,
    red: Int,
    green: Int,
    blue: Int
)

object Game:
  extension (g: Game)
    def isValid(limit: (Int, Int, Int)): Boolean =
      g.red <= limit._1 && g.green <= limit._2 && g.blue <= limit._3

def parseGame(input: String): Game =
  val data = input.split(":")
  val gameNumber = data(0) match
    case s"Game $game" => game
  val sets = data(1)
    .split(";")
    .flatMap: s =>
      s.split(",")
        .map(_.trim match
          case s"$num $color" => color -> num.toInt
        )
    .groupBy(_._1)
    .mapValues(_.maxBy(_._2))
    .mapValues(_._2)
  Game(gameNumber.toInt, sets.getOrElse("red", 1), sets.getOrElse("green", 1), sets.getOrElse("blue", 1))

def solveTwo(input: List[String]): Int =
  input
    .map: l =>
      val g = parseGame(l)
      g.red * g.green * g.blue
    .sum

def solveOne(input: List[String]): Int =
  input
    .map(parseGame)
    .filter(_.isValid(12, 13, 14))
    .map(_.id)
    .sum
