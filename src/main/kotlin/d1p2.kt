import java.util.*
import kotlin.math.max

fun main() {
    val br = System.`in`.bufferedReader()

    val pq = PriorityQueue<Int>(Collections.reverseOrder())
    var curr = 0
    for(line in br.lines()) {
        if(line.isEmpty()) {
            pq.add(curr)
            curr = 0
        } else {
            curr += line.toInt()
        }
    }
    var ans = 0
    repeat(3) { ans += pq.poll() }
    println(ans)
}