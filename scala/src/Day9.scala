package aoc
package nine

import scala.annotation.tailrec

def diff(input: List[Int]): List[Int] =
  input.tail
    .foldLeft((input.head, List[Int]())): (acc, v) =>
      (v, acc._2 :+ v - acc._1)
    ._2

def findLast(input: List[Int]): List[List[Int]] =
  @tailrec
  def recur(i: List[Int], acc: List[List[Int]]): List[List[Int]] =
    if i.forall(_ == 0) then acc
    else
      val d = diff(i)
      recur(d, acc :+ d)
  recur(input, List(input))

def solveOne(input: List[String]) =
  input
    .map(_.split(" ").map(_.toInt).toList)
    .map: sequence =>
      val s = findLast(sequence)
      s.map(_.last).sum
    .sum

def solveTwo(input: List[String]) =
  input
    .map(_.split(" ").map(_.toInt).toList)
    .map: sequence =>
      val s = findLast(sequence)
      s.dropRight(1)
        .foldRight(s.last.head): (v, acc) =>
          v.head - acc
    .sum
