package aoc
package daySeven

import scala.annotation.tailrec
import scala.util.Sorting

val letterCardMapP1 = Map(
  'T' -> 10,
  'J' -> 11,
  'Q' -> 12,
  'K' -> 13,
  'A' -> 14
)

val letterCardMapP2 = Map(
  'T' -> 10,
  'J' -> 0,
  'Q' -> 12,
  'K' -> 13,
  'A' -> 14
)

val hands = Map(
  "fiveoak" -> 1,
  "fouroak" -> 2,
  "house" -> 3,
  "threeoak" -> 4,
  "twopair" -> 5,
  "onepair" -> 6,
  "highest" -> 7
)

@tailrec
def compareByNumbers(x: List[Int], y: List[Int]): Int =
  if x.isEmpty && y.isEmpty then 0
  else if x.head > y.head then 1
  else if x.head < y.head then -1
  else compareByNumbers(x.tail, y.tail)

type Card = Int

type Cards = List[MappedCard]

case class MappedCard(cards: List[Card], hand: String, bid: Int)

given Ordering[MappedCard] with
  // negative it x < y
  override def compare(x: MappedCard, y: MappedCard): Int =
    val xHandRank = hands(x.hand)
    val yHandRank = hands(y.hand)
    if xHandRank < yHandRank then 1
    else if xHandRank > yHandRank then -1
    else compareByNumbers(x.cards, y.cards)

def findHand(cards: List[Card]): String =
  if cards.toSet.size == cards.size then "highest"
  else if cards.toSet.size == 1 then "fiveoak"
  else
    val groups = cards.groupBy(identity)
    if groups.find((k, v) => v.size == 4).isDefined then "fouroak"
    else if groups.find((k, v) => v.size == 3).isDefined && groups.find(_._2.size == 2).isDefined then "house"
    else if groups.find((k, v) => v.size == 3).isDefined && groups.find(_._2.size == 2).isEmpty then "threeoak"
    else if groups.filter(_._2.size == 2).size == 2 then "twopair"
    else if groups.filter(_._2.size == 2).size == 1 then "onepair"
    else "highest"

def getCardValues(cards: String, map: Map[Char, Int]): List[Int] =
  cards
    .map: c =>
      if c.isDigit then c.asDigit else map(c)
    .toList

def solveOne(input: List[String]) =
  val playerHands = input.map: l =>
    l match
      case s"$cards $bid" =>
        val cardValues = getCardValues(cards, letterCardMapP1)
        val hand = findHand(cardValues)
        MappedCard(cardValues, hand, bid.toInt)
  playerHands.sorted.zipWithIndex
    .map: (hand, i) =>
      hand.bid * (i + 1)
    .sum

def solveTwo(input: List[String]) =
  val playerHands = input.map: l =>
    l match
      case s"$cards $bid" =>
        if cards.contains('J') then
          val cardValues = getCardValues(cards, letterCardMapP2)

          val hand = "1 2 3 4 5 6 7 8 9 T Q K A"
            .split(" ")
            .map: c =>
              findHand(getCardValues(cards.replace('J', c.toList.head), letterCardMapP2))
            .minBy(c => hands(c))
          MappedCard(cardValues, hand, bid.toInt)
        else
          val cardValues = getCardValues(cards, letterCardMapP2)
          val hand = findHand(cardValues)
          MappedCard(cardValues, hand, bid.toInt)
  playerHands.sorted.zipWithIndex
    .map: (hand, i) =>
      hand.bid * (i + 1)
    .sum
