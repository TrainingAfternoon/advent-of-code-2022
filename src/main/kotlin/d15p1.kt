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

    val targetY = 2000000

    fun parseLineToPos(line: String, prefix: Int): Pos {
        val tokens = line.split(" ").drop(prefix)
        val x = tokens.first().dropLast(1).split("=").last().toInt()
        val y = tokens[1].split("=").last().toInt()
        return Pos(x,y)
    }

    var gMinX = Int.MAX_VALUE
    var gMaxX = 0
    val marked = HashSet<Pos>()
    for(line in br.lines()) {
        val (sensorL, beaconL) = line.split(": ")
        val sensor = parseLineToPos(sensorL, 2)
        val beacon = parseLineToPos(beaconL, 4)

        gMaxX = max(sensor.x, max(beacon.x, gMaxX))
        gMinX = min(sensor.x, min(beacon.x, gMinX))

        val dist = sensor.dist(beacon)
        val minX = sensor.x - dist
        val maxX = sensor.x + dist

        for(x in minX..maxX) {
            val test = Pos(x, targetY)
            if(test.dist(sensor) <= dist && test != beacon) {
                marked.add(test)
            }
        }
    }
    println(marked.size)
}