fun main() {
    val br = System.`in`.bufferedReader()

    var ans = 0
    var first = br.readLine()
    while(first != null) {
        val second = br.readLine()
        val third = br.readLine()

        ans += first.toHashSet()
            .intersect(second.toHashSet())
            .intersect(third.toHashSet())
            .sumOf { if (it.isUpperCase()) it - '@' + 26 else it - '`' }
        first = br.readLine()
    }
    println(ans)
}