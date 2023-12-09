package aoc
package nine

import scala.annotation.tailrec

def findLast(input: Vector[Int]) =
  @tailrec
  def loop(i: Vector[Int], acc: Vector[Vector[Int]]): Vector[Vector[Int]] =
    if i.forall(_ == 0) then acc
    else
      val d = i.tail
        .foldLeft((i.head, Vector[Int]())): (acc, v) =>
          (v, acc._2 :+ v - acc._1)
        ._2
      loop(d, acc :+ d)
  loop(input, Vector(input))

def getNums(input: String) = input.split(" ").map(_.toInt).toVector

def solveOne(input: Vector[String]) =
  input
    .map(getNums)
    .map: sequence =>
      findLast(sequence).map(_.last).sum
    .sum

def solveTwo(input: Vector[String]) =
  input
    .map(getNums)
    .map: sequence =>
      findLast(sequence.reverse).map(_.last).sum
    .sum
