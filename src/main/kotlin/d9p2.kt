import java.lang.Math.abs

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

    val visited = HashSet<Pos>()
    val moves = ArrayList<Pair<String, Int>>()
    for(line in br.lines()) {
        val (c, m) = line.split(" ")
        moves.add(c to m.toInt())
    }

    val rope = Array<Pos>(10) { Pos(0,0) }

    fun update(c: String, head: Pos, t: Pos): Pos {
        var tail = t

        val deltaX = abs(head.x - tail.x)
        val deltaY = abs(head.y - tail.y)

        if((deltaX > 1 || deltaY > 1) && deltaX > 0 && deltaY > 0) {
            if(head.x > tail.x) tail = tail.right()
            else tail = tail.left()

            if(head.y > tail.y) tail = tail.up()
            else tail = tail.down()
        } else if(deltaX > 1) {
            if(head.x > tail.x) tail = tail.right()
            else tail = tail.left()
        } else if(deltaY > 1) {
            if(head.y > tail.y) tail = tail.up()
            else tail = tail.down()
        }

        return tail
    }

    for((c, m) in moves) {
        var amount = m
        while(amount-- > 0) {
            when(c) {
                "R" -> {
                    rope[0] = rope[0].right()
                }
                "L" -> {
                    rope[0] = rope[0].left()
                }
                "U" -> {
                    rope[0] = rope[0].up()
                }
                "D" -> {
                    rope[0] = rope[0].down()
                }
            }
            for(idx in 1 until rope.size) {
                rope[idx] = update(c, rope[idx-1], rope[idx])
            }
            visited.add(rope.last())
        }
    }

    println(visited.size)
}