package aoc
package dayThree

import scala.util.Try
import scala.util.matching.Regex

case class Num(value: Int, startIdx: Int, endIdx: Int)

def solveOne(input: List[String]): Int =
  def check(s: String) =
    val special = "[^a-zA-Z0-9]+".r
    special.matches(s.filterNot(_.isDigit).replace(".", ""))

  def getSubString(s: String, first: Int, last: Int): String =
    val start = if s.isDefinedAt(first - 1) then first - 1 else first
    val end = if s.isDefinedAt(last + 1) then last + 1 else last
    s.substring(start, end)

  def handleNumber(
      ch: Seq[Char],
      number: Seq[Char],
      startIdx: Int,
      endIdx: Int
  ): (Seq[Char], Num) =
    ch match
      case head :: tail if head.isDigit =>
        handleNumber(tail, number :+ head, startIdx, endIdx + 1)
      case _ :: tail => (tail, Num(number.mkString.toInt, startIdx, endIdx + 1))
      case Nil       => (ch, Num(number.mkString.toInt, startIdx, endIdx))

  def loop(c: Seq[Char], parts: Seq[Num], idx: Int): Seq[Num] =
    c match
      case head :: tail if head.isDigit =>
        val num = handleNumber(tail, Seq(head), idx, idx)
        loop(num._1, parts :+ num._2, num._2.endIdx + 1)
      case _ :: tail => loop(tail, parts, idx + 1)
      case Nil       => parts

  input
    .map(s => loop(s.toList, Seq.empty, 0))
    .zipWithIndex
    .flatMap: (num, idx) =>
      num.filter: n =>
        val above =
          if input.isDefinedAt(idx - 1)
          then check(getSubString(input(idx - 1), n.startIdx, n.endIdx))
          else false
        val current =
          check(getSubString(input(idx), n.startIdx, n.endIdx))
        val below =
          if input.isDefinedAt(idx + 1)
          then check(getSubString(input(idx + 1), n.startIdx, n.endIdx))
          else false
        above || current || below
    .map(_.value)
    .sum

def solveTwo(input: List[String]): Int =
  def findAdjacentNumber(s: String, i: Int): List[Int] =
    "\\d+".r
      .findAllMatchIn(s)
      .map: m =>
        m.matched.toInt -> m.start
      .toList
      .filter: (n, idx) =>
        List.range(math.max(idx - 1, 0), math.min(idx + n.toString.length + 1, s.length)).contains(i)
      .map(_._1)

  def getNums(input: List[String], inputIndex: Int, lineIndex: Int) =
    if input.isDefinedAt(inputIndex) then findAdjacentNumber(input(inputIndex), lineIndex)
    else List.empty

  input.zipWithIndex
    .flatMap: (s, inputIndex) =>
      val position = s.zipWithIndex.filter((c, idx) => c == '*')
      position.map: (_, lineIndex) =>
        val above = getNums(input, inputIndex - 1, lineIndex)
        val current = getNums(input, inputIndex, lineIndex)
        val below = getNums(input, inputIndex + 1, lineIndex)
        val adjNums = above ++ current ++ below
        if adjNums.length == 2 then adjNums(0) * adjNums(1) else 0
    .sum
