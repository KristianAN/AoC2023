package aoc
package dayFive

import scala.io.Source

def solveOne(input: String) =
  val groups = input.split("\n\n").filter(_.nonEmpty)
  val seeds = groups.head.split(":").drop(1).head.split("\\s+").filter(_.nonEmpty).map(_.toLong)
  groups.tail
    .map: gr =>
      gr.split("\n")
        .tail
        .map: mapping =>
          val n = mapping.split("\\s+").filter(_.nonEmpty).map(_.toLong)
          val source = Range.Long(n(1), n(1) + n(2), 1)
          val target = Range.Long(n(0), n(0) + n(2), 1)
          (source, target)
    .foldLeft(seeds.toList): (acc, map) =>
      acc.map: seed =>
        map
          .find(_._1.contains(seed)) match
          case None                   => seed
          case Some((source, target)) => seed + (target.min - source.min)
    .min

// brute force
def solveTwo(input: String) =

  val groups = input.split("\n\n").filter(_.nonEmpty)
  val seeds = groups.head
    .split(":")
    .drop(1)
    .head
    .split("\\s+")
    .filter(_.nonEmpty)
    .map(_.toLong)
    .toList
    .grouped(2)
    .flatMap: g =>
      Range.Long(g(0), g(0) + g(1), 1)

  groups.tail
    .map: gr =>
      gr.split("\n")
        .tail
        .map: mapping =>
          val n = mapping.split("\\s+").filter(_.nonEmpty).map(_.toLong)
          val source = Range.Long(n(1), n(1) + n(2), 1)
          val target = Range.Long(n(0), n(0) + n(2), 1)
          (source, target)
    .foldLeft(seeds.toList): (acc, map) =>
      acc.map: seed =>
        map
          .find(_._1.contains(seed)) match
          case None                   => seed
          case Some((source, target)) => seed + (target.min - source.min)
    .min
