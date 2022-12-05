fun main() {
    val br = System.`in`.bufferedReader()

    var ans = 0
    for(rucksack in br.lines()) {
        val N = rucksack.length / 2
        val first = rucksack.take(N).toHashSet()
        ans += rucksack.drop(N)
            .filter(first::contains)
            .toHashSet()
            .sumOf { if (it.isUpperCase()) it - '@' + 26 else it - '`' }
    }
    println(ans)
}