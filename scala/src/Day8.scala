package aoc
package dayEight

import scala.annotation.tailrec
import scala.io.Source

def solveOne(input: List[String]) =
  val path = input.head

  case class Edges(left: String, right: String)

  val data = input.tail
    .map: l =>
      l match
        case s"$key = ($left, $right)" =>
          key -> Edges(left, right)
    .toMap

  def recur(current: String, currentPath: Seq[Char], steps: Int): Int =
    if current == "ZZZ" then steps
    else
      val p = if currentPath.isEmpty then path.toList else currentPath
      p match
        case 'L' :: tail => recur(data(current).left, tail, steps + 1)
        case 'R' :: tail => recur(data(current).right, tail, steps + 1)

  recur("AAA", path.toList, 0)

def solveTwo(input: List[String]) =
  val path = input.head

  type Edges = (String, String)

  val data: Map[String, Edges] = input.tail
    .map: l =>
      l match
        case s"$key = ($left, $right)" =>
          key -> (left, right)
    .toMap

  def recur(currentPath: Seq[Char], current: String): String =
    val node = data(current)
    currentPath match
      case Nil         => current
      case 'L' :: tail => recur(tail, node._1)
      case 'R' :: tail => recur(tail, node._2)

  val endsA = data.keys.filter(k => k.last == 'A').toList
  val startAndEnd = data.keysIterator
    .map: k =>
      k -> recur(path.toList, k)
    .toMap

  def findZ(current: String, index: Int): Int =
    if current.endsWith("Z") then index else findZ(startAndEnd(current), index + 1)

  val steps = endsA.toList.map: v =>
    findZ(v, 0)

  steps.map(_.toLong).product * path.length
