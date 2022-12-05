import kotlin.math.max

fun main() {
    val br = System.`in`.bufferedReader()

    var most = 0
    var curr = 0
    for(line in br.lines()) {
        if(line.isEmpty()) {
            most = max(curr, most)
            curr = 0
        } else {
            curr += line.toInt()
        }
    }
    println(most)
}