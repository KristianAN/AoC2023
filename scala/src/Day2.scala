package aoc
package dayTwo

case class Game(id: Int, red: Int, green: Int, blue: Int)

object Game:
  extension (g: Game)
    def isValid(limit: (Int, Int, Int)) =
      g.red <= limit._1 && g.green <= limit._2 && g.blue <= limit._3
    def power = g.red * g.green * g.blue

def parseGame(input: String): Game =
  val data = input.split(":")
  val gameNumber = data(0) match
    case s"Game $game" => game.toInt
  val sets = data(1)
    .split("[;,]")
    .toList
    .map(_.trim match
      case s"$num $color" => color -> num.toInt
    )
    .groupBy(_._1)
    .map((k, v) => (k, v.maxBy(_._2)._2))
  Game(
    gameNumber,
    sets.getOrElse("red", 1),
    sets.getOrElse("green", 1),
    sets.getOrElse("blue", 1)
  )

def solveTwo(input: List[String]): Int =
  input.map(l => parseGame(l).power).sum

def solveOne(input: List[String]): Int =
  input
    .map(parseGame)
    .filter(_.isValid(12, 13, 14))
    .map(_.id)
    .sum
