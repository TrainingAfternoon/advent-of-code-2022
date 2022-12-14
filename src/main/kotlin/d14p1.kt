import java.lang.Math.min
import java.util.*
import kotlin.math.max

fun main() {
    val br = System.`in`.bufferedReader()

    data class Pos(val x: Int, val y: Int) {
        fun left(): Pos {
            return Pos(x-1,y)
        }
        fun right(): Pos {
            return Pos(x+1,y)
        }
        fun up(): Pos {
            return Pos(x,y-1)
        }
        fun down(): Pos {
            return Pos(x,y+1)
        }
    }

    fun parseLineToPos(line: String): Pos {
        val (x,y) = line.split(",").map(String::toInt)
        return Pos(x,y)
    }

    val grid = Array(600) { BooleanArray(600) { false } }

    fun get(p: Pos): Boolean {
        return grid[p.y][p.x]
    }

    fun set(p: Pos, value: Boolean) {
        grid[p.y][p.x] = value
    }

    for(line in br.lines()) {
        val components = line.split(" -> ")

        var prev = parseLineToPos(components[0])
        for(idx in 1 until components.size) {
            val curr = parseLineToPos(components[idx])

            val yLim = max(prev.y, curr.y)
            val yStart = min(prev.y, curr.y)
            val xLim = max(prev.x, curr.x)
            val xStart = min(prev.x, curr.x)

            for(y in yStart..yLim) {
                for(x in xStart..xLim) {
                    grid[y][x] = true
                }
            }

            prev = curr
        }
    }

    for(row in grid.take(10)) {
        for(col in row.drop(493).take(11)) {
            if(col) print('#') else print('.')
        }
        println()
    }

    var set = 0
    val sands = LinkedList<Pos>()
    sands.add(Pos(500,0))
    while(sands.isNotEmpty()) {
        var sand = sands.poll()
        var next = sand
        do {
            sand = next

            if(sand.y >= grid.size-1) {
                println(set)
                break
            }

            if(!get(sand.down())) {
                next = sand.down()
            } else if(!get(sand.down().left())) {
                next = sand.down().left()
            } else if(!get(sand.down().right())) {
                next = sand.down().right()
            } else { // at rest!
                ++set
                sands.addLast(Pos(500,0))
            }
            set(sand, false)
            set(next, true)
        } while(sand != next)
    }


}