fun main() {
    val br = System.`in`.bufferedReader()

    var ans = 0
    for(elves in br.lines()) {
        val (first, second) = elves.split(',')
        val (a, b) = first.split('-').map(String::toInt)
        val (c, d) = second.split('-').map(String::toInt)

        if(a in c..d || b in c..d || c in a..b || d in a..b) {
            ans++
        }
    }
    println(ans)
}