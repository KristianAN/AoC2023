package aoc
package dayOne

import scala.annotation.tailrec

def solveOne(input: List[String]): Int =
  input.map(l => s"${l.find(_.isDigit).get}${l.findLast(_.isDigit).get}".toInt).sum

def solveTwo(input: List[String]): Int =
  def extract(chars: List[Char]): List[Int] =
    @tailrec
    def loop(c: List[Char], nums: List[Int]): List[Int] = c match
      case head :: tail if head.isDigit         => loop(c.tail, nums :+ head.asDigit)
      case 'o' :: 'n' :: 'e' :: _               => loop(c.tail, nums :+ 1)
      case 't' :: 'w' :: 'o' :: _               => loop(c.tail, nums :+ 2)
      case 't' :: 'h' :: 'r' :: 'e' :: 'e' :: _ => loop(c.tail, nums :+ 3)
      case 'f' :: 'o' :: 'u' :: 'r' :: _        => loop(c.tail, nums :+ 4)
      case 'f' :: 'i' :: 'v' :: 'e' :: _        => loop(c.tail, nums :+ 5)
      case 's' :: 'i' :: 'x' :: _               => loop(c.tail, nums :+ 6)
      case 's' :: 'e' :: 'v' :: 'e' :: 'n' :: _ => loop(c.tail, nums :+ 7)
      case 'e' :: 'i' :: 'g' :: 'h' :: 't' :: _ => loop(c.tail, nums :+ 8)
      case 'n' :: 'i' :: 'n' :: 'e' :: _        => loop(c.tail, nums :+ 9)
      case _ :: tail                            => loop(tail, nums)
      case Nil                                  => nums.toList
    loop(chars, List.empty)
  input
    .map: l =>
      val d = extract(l.toList)
      s"${d.head}${d.last}".toInt
    .sum
