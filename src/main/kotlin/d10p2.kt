import java.lang.StrictMath.abs

fun main() {
    val br = System.`in`.bufferedReader()

    var cycle = 0
    var X = 1
    var target = 20
    val signals = ArrayList<Int>()
    val CRT = Array(6) { CharArray(40) { '.' } }
    var x = 0
    var y = 0

    fun incrementCycle(times: Int) {
        for(i in 0 until times) {
            cycle += 1
            if(abs(X - y) <= 1) CRT[x][y] = '#'
            ++y
            if(y >= 40) {
                y = 0
                ++x
            }
        }
        println("----------")
        for(row in CRT) {
            for(c in row) print(c)
            println()
        }
    }

    for(line in br.lines()) {
        val tokens = line.split(" ")
        when(tokens[0]) {
            "noop" -> incrementCycle(1)
            "addx" -> {
                incrementCycle(2)
                X += tokens[1].toInt()
            }
        }
    }

    println("ans = ${signals.take(6).sum()}")
}