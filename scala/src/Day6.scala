package aoc
package daySix

def solveOneTwo =
  def distance(speed: Long, lim: Long) =
    val dist = speed * (lim - speed)
    dist
  case class Race(time: Long, record: Long)

  val races = List(
    Race(49877895L, 356137815021882L)
  )

  races
    .map: r =>
      (0L to r.time).map(i => distance(i, r.time)).filter(_ > r.record).size
    .product
