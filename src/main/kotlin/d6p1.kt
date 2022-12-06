fun main() {
    val br = System.`in`.bufferedReader()

    val distinct = 4
    for(line in br.lines()) {
        for(idx in 0 until line.lastIndex-(distinct/2)) {
            val unique = line.substring(idx,idx+4).toCharArray().distinct().count()
            if(unique == distinct) {
                println(idx + distinct)
                break
            }
        }
    }
}