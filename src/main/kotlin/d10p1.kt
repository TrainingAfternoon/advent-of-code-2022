fun main() {
    val br = System.`in`.bufferedReader()

    var cycle = 0
    var X = 1
    var target = 20
    val signals = ArrayList<Int>()

    fun incrementCycle(times: Int) {
        for(i in 0 until times) {
            cycle += 1
            if(cycle == target) {
                println("$target $cycle $X ${cycle * X}")
                signals.add(cycle * X)
                target += 40
            }
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