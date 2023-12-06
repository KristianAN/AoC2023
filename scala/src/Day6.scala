package aoc
package daySix

def solveOneTwo =
  def distance(speed: Long, lim: Long) =
    val dist = speed * (lim - speed)
    dist
  case class Race(time: Long, record: Long)
  val r = Race(49877895L, 356137815021882L)
  (0L to r.time).count(i => i * (r.time - i) > r.record)
