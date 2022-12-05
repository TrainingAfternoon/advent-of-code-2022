fun main() {
    val br = System.`in`.bufferedReader()

    fun getScore(move: Char): Int {
        return when(move) {
            'A' -> 1 // rock
            'X' -> 1
            'B' -> 2 // paper
            'Y' -> 2
            'C' -> 3 // scissor
            'Z' -> 3
            else -> -1
        }
    }

    fun winning(opponent: Char, self: Char): Boolean {
        return self == 'X' && opponent == 'C' ||
                self == 'Y' && opponent == 'A' ||
                self == 'Z' && opponent == 'B'
    }

    var ans = 0
    for(line in br.lines()) {
        val move = line[0]
        val response = line[2]

        if(winning(move, response)) ans += 6 //win
        else if((move - 'A') == (response - 'X')) ans += 3 //draw
        //do nothing on loss

        ans += getScore(response)
    }
    println(ans)
}