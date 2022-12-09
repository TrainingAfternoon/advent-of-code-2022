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

    var head = Pos(0, 0)
    var tail = Pos(0, 0)

    for((c, m) in moves) {
        var amount = m
        while(amount-- > 0) {
            val deltaX = abs(head.x - tail.x)
            val deltaY = abs(head.y - tail.y)

            print("$deltaX $deltaY ")
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

            println("$head, $tail")

            for(row in 4 downTo 0) {
                for(col in 0 until 6) {
                    if(row == head.y && col == head.x) print("H")
                    else if(row == tail.y && col == tail.x) print("T")
                    else print(".")
                }
                println()
            }

            when(c) {
                "R" -> {
                    head = head.right()
                }
                "L" -> {
                    head = head.left()
                }
                "U" -> {
                    head = head.up()
                }
                "D" -> {
                    head = head.down()
                }
            }

            visited.add(tail)
        }
    }

    println(visited.size)
}