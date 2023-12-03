package aoc
package dayFour

import scala.annotation.tailrec

def getNMatches(input: String) =
  val card = input.split(":").drop(1).head.split('|').map(_.split("\\s+").filter(_.nonEmpty).map(_.toInt).toList).toList
  card(0).intersect(card(1)).size

def getSum(input: String) =
  val matches = getNMatches(input)
  if matches == 0 then 0 else (math.pow(2, matches - 1)).toInt

def solveOne(input: List[String]) =
  input.map(getSum).sum

def solveTwo(input: List[String]) =
  val wins = input.map(getNMatches)
  @tailrec
  def loop(winsOnCards: List[Int], instances: List[Int], winsPerCard: List[Int]): List[Int] =
    winsOnCards match
      case Nil => winsPerCard
      case wins :: next =>
        loop(
          next,
          List.range(0, wins).map(idx => instances(idx + 1) + instances.head) ++ instances.drop(wins + 1),
          winsPerCard :+ instances.head
        )
  loop(wins, List.fill(wins.size)(1), Nil).sum
