package aoc
package ten

import scala.util.Try

enum Pipe:
  case `|`
  case `-`
  case L
  case J
  case `7`
  case `.`
  case S
  case F

object Pipe:
  def fromChar(c: Char) =
    c match
      case '|' => Pipe.|
      case '-' => Pipe.-
      case 'L' => Pipe.L
      case 'J' => Pipe.J
      case '7' => Pipe.`7`
      case '.' => Pipe.`.`
      case 'S' => Pipe.S
      case 'F' => Pipe.F

enum Direction:
  case N
  case S
  case E
  case W

def moveSouth(current: Point, pipes: Vector[Vector[Pipe]]) =
  val y = current.y + 1
  val x = current.x
  Point(pipes(y)(x), y, x)

def moveNorth(current: Point, pipes: Vector[Vector[Pipe]]) =
  val y = current.y - 1
  val x = current.x
  Point(pipes(y)(x), y, x)

def moveEast(current: Point, pipes: Vector[Vector[Pipe]]) =
  val y = current.y
  val x = current.x + 1
  Point(pipes(y)(x), y, x)

def moveWest(current: Point, pipes: Vector[Vector[Pipe]]) =
  val y = current.y
  val x = current.x - 1
  Point(pipes(y)(x), y, x)

case class Point(pipe: Pipe, y: Int, x: Int)

def move(
    direction: Direction,
    current: Point,
    pipes: Vector[Vector[Pipe]],
    moves: Int,
    points: Vector[Point]
): (Int, Vector[Point]) =
  current.pipe match
    case Pipe.`|` =>
      direction match
        case Direction.N => move(Direction.N, moveNorth(current, pipes), pipes, moves + 1, points :+ current)
        case Direction.S => move(Direction.S, moveSouth(current, pipes), pipes, moves + 1, points :+ current)
        case _           => throw Exception("Boom")
    case Pipe.- =>
      direction match
        case Direction.E => move(Direction.E, moveEast(current, pipes), pipes, moves + 1, points :+ current)
        case Direction.W => move(Direction.W, moveWest(current, pipes), pipes, moves + 1, points :+ current)
        case _           => throw Exception("Boom")
    case Pipe.L =>
      direction match
        case Direction.S => move(Direction.E, moveEast(current, pipes), pipes, moves + 1, points :+ current)
        case Direction.W => move(Direction.N, moveNorth(current, pipes), pipes, moves + 1, points :+ current)
        case _           => throw Exception("Boom")
    case Pipe.J =>
      direction match
        case Direction.S => move(Direction.W, moveWest(current, pipes), pipes, moves + 1, points :+ current)
        case Direction.E => move(Direction.N, moveNorth(current, pipes), pipes, moves + 1, points :+ current)
        case _           => throw Exception("Boom")
    case Pipe.`7` =>
      direction match
        case Direction.N => move(Direction.W, moveWest(current, pipes), pipes, moves + 1, points :+ current)
        case Direction.E => move(Direction.S, moveSouth(current, pipes), pipes, moves + 1, points :+ current)
        case _           => throw Exception("Boom")
    case Pipe.F =>
      direction match
        case Direction.N => move(Direction.E, moveEast(current, pipes), pipes, moves + 1, points :+ current)
        case Direction.W => move(Direction.S, moveSouth(current, pipes), pipes, moves + 1, points :+ current)
        case _           => throw Exception("Boom")
    case Pipe.S =>
      (moves, points :+ current)
    case Pipe.`.` => throw Exception("Boom")

def findSecond(start: Point, matrix: Vector[Vector[Pipe]]): (Direction, Point) =
  val ups = List(Pipe.F, Pipe.|, Pipe.`7`)
  val downs = List(Pipe.J, Pipe.|, Pipe.L)
  val easts = List(Pipe.-, Pipe.`7`, Pipe.J)
  val wests = List(Pipe.F, Pipe.-, Pipe.L)
  val above = Try(
    ups.find(_ == matrix(start.y - 1)(start.x)).map(p => (Direction.N, Point(p, start.y - 1, start.x)))
  ).toOption.flatten
  val below = Try(
    downs.find(_ == matrix(start.y + 1)(start.x)).map(p => (Direction.S, Point(p, start.y + 1, start.x)))
  ).toOption.flatten
  val east = Try(
    easts.find(_ == matrix(start.y)(start.x + 1)).map(p => (Direction.E, Point(p, start.y, start.x + 1)))
  ).toOption.flatten
  val west = Try(
    wests.find(_ == matrix(start.y)(start.x - 1)).map(p => (Direction.W, Point(p, start.y, start.x - 1)))
  ).toOption.flatten
  List(above, below, east, west).find(_.isDefined).flatten.get

def findStart(matrix: Vector[Vector[Pipe]]) =
  val st = matrix.zipWithIndex.map((l, i) => (l.zipWithIndex.find(_._1 == Pipe.S), i)).find(_._1.isDefined).get
  Point(st._1.get._1, st._2, st._1.get._2)

// Simply goes through the loop, nothing fancy
def solveOne(input: List[String]) =
  val matrix = input.toVector.map: l =>
    l.toVector.map(Pipe.fromChar)
  val start = findStart(matrix)
  val second = findSecond(start, matrix)
  val moves = move(second._1, second._2, matrix, 1, Vector(start, second._2))
  moves._1 / 2
