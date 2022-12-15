import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    val br = System.`in`.bufferedReader()

    data class Pos(val x: Int, val y: Int) {
        fun dist(other: Pos): Int {
            return abs(this.y - other.y) + abs(this.x - other.x)
        }
    }

    val bound = 4000000

    fun parseLineToPos(line: String, prefix: Int): Pos {
        val tokens = line.split(" ").drop(prefix)
        val x = tokens.first().dropLast(1).split("=").last().toInt()
        val y = tokens[1].split("=").last().toInt()
        return Pos(x,y)
    }

    val gaps = Array(bound+1) { ArrayList<IntRange>() }
    for(line in br.lines()) {
        val (sensorL, beaconL) = line.split(": ")
        val sensor = parseLineToPos(sensorL, 2)
        val beacon = parseLineToPos(beaconL, 4)
        val dist = sensor.dist(beacon)

        for(y in 0 .. bound) {
            val width = dist - abs(sensor.y - y)
            if(width > 0) {
                gaps[y].add(sensor.x-width..sensor.x+width)
            }
        }
    }

    for(y in gaps.indices) {
        val ranges = gaps[y].sortedBy { it.first }
        var max = 0

        for(range in ranges) {
            if(range.first > max && range.first in 0..bound) {
                println((range.first - 1).toLong()*4000000L + y.toLong())
                return
            } else if(range.last > max) max = range.last+1
        }
    }
}