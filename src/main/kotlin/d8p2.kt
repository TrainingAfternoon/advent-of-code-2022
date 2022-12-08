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

    val lines = ArrayList<String>()
    for(line in br.lines()) lines.add(line)

    fun calcScore(p: Pos): Int {
        //left
        var lScore = 0
        var left = p.left()
        while(left.x >= 0 && lines[left.y][left.x] < lines[p.y][p.x]) {
            lScore += 1
            left = left.left()
        }
        if(left.x >= 0) lScore += 1

        //right
        var rScore = 0
        var right = p.right()
        while(right.x <= lines[p.y].lastIndex && lines[right.y][right.x] < lines[p.y][p.x]) {
            rScore += 1
            right = right.right()
        }
        if(right.x <= lines[p.y].lastIndex) rScore += 1

        //up
        var uScore = 0
        var up = p.up()
        while(up.y >= 0 && lines[up.y][up.x] < lines[p.y][p.x]) {
            uScore += 1
            up = up.up()
        }
        if(up.y >= 0) uScore += 1

        //down
        var dScore = 0
        var down = p.down()
        while(down.y <= lines.lastIndex && lines[down.y][down.x] < lines[p.y][p.x]) {
            dScore += 1
            down = down.down()
        }
        if(down.y <= lines.lastIndex) dScore += 1

        return lScore * rScore * uScore * dScore
    }

    var ans = 0
    for(r in lines.indices) {
        for(c in lines[r].indices) {
            ans = max(ans, calcScore(Pos(r,c)))
        }
    }
    println(ans)
}