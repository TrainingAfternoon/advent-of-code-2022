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

    fun getComplement(opponent: Char, opcode: Char): Char {
        return when(opcode) {
            'X' -> { // lose
                return when(opponent) {
                    'A' -> 'Z'
                    'B' -> 'X'
                    'C' -> 'Y'
                    else -> 'X'
                }
            }
            'Y' -> { // draw
                return when(opponent) {
                    'A' -> 'X'
                    'B' -> 'Y'
                    'C' -> 'Z'
                    else -> 'X'
                }
            }
            'Z' -> { // win
                return when(opponent) {
                    'A' -> 'Y'
                    'B' -> 'Z'
                    'C' -> 'X'
                    else -> 'X'
                }
            }
            else -> 'X'
        }
    }

    var ans = 0
    for(line in br.lines()) {
        val move = line[0]
        val directive = line[2]
        val response = getComplement(move, directive)

        if(winning(move, response)) ans += 6 //win
        else if((move - 'A') == (response - 'X')) ans += 3 //draw
        //do nothing on loss

        ans += getScore(response)
    }
    println(ans)
}