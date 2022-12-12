import java.lang.Math.abs
import kotlin.math.min
import java.util.*
import kotlin.collections.ArrayList

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
            return Pos(x,y+1)
        }
        fun down(): Pos {
            return Pos(x,y-1)
        }
    }

    val starts = ArrayList<Pos>()

    var end = Pos(-1,-1)
    val grid = ArrayList<String>()

    var row = 0
    for(line in br.lines()) {
        if(line.contains("S")) {
            starts.add(Pos(line.indexOf('S'), row))
        }

        var i = line.indexOf('a')
        while(i != -1) {
            starts.add(Pos(i, row))
            i = line.indexOf('a', i + 1)
        }

        if(line.contains("E")) {
            end = Pos(line.indexOf('E'), row)
        }
        grid.add(line)
        row++
    }

    var ans = Int.MAX_VALUE
    for(start in starts) {
        val cost = Array(grid.size) { IntArray(grid[0].length) { 0 } }
        val visited = HashSet<Pos>()
        val q = LinkedList<Pos>()

        fun get(p: Pos): Char {
            if(p.x == start.x && p.y == start.y) return 'a'
            else if(p.x == end.x && p.y == end.y) return 'z'
            else return grid[p.y][p.x]
        }

        fun add(parent: Pos, p: Pos) {
            if(p.x in grid[0].indices && p.y in grid.indices) {
                val parentC = get(parent)
                val pC = get(p)

                if(pC - parentC <= 1) {
                    cost[p.y][p.x] = cost[parent.y][parent.x] + 1
                    q.addLast(p)
                }
            }
        }

        q.add(start)
        cost[start.y][start.x] = 0
        while(q.isNotEmpty()) {
            val curr = q.poll()

            if(visited.contains(curr)) continue
            visited.add(curr)

            if(grid[curr.y][curr.x] == 'E') break

            add(curr,curr.up())
            add(curr, curr.down())
            add(curr, curr.right())
            add(curr, curr.left())
        }

        if(cost[end.y][end.x] > 0) {
            ans = min(ans, cost[end.y][end.x])
        }
    }

    println(ans)
}