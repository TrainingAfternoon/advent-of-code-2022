import java.util.LinkedList

fun main() {
    val br = System.`in`.bufferedReader()

    data class Monkey(val id: Int) {
        var op: (Int) -> Int = {x: Int -> x}
        var test: (Int) -> Boolean = {x: Int -> true }
        var items = LinkedList<Int>()
        val link = HashMap<Boolean,Int>()
    }

    val m = ArrayList<Monkey>()
    var line = br.readLine()
    while(line != null) {
        if(line.isEmpty()) {
            line = br.readLine()
            continue
        }
        val tokens = line.trim().split(" ")

        when(tokens[0]) {
            "Monkey" -> {
                m.add(Monkey(tokens.last().dropLast(1).toInt()))
            }
            "Starting" -> {
                for(item in tokens.drop(2)) {
                    if(item.last() == ',') m.last().items.add(item.dropLast(1).toInt())
                    else m.last().items.add(item.toInt())
                }
            }
            "Operation:" -> {
                val A = tokens[3]
                val op = tokens[4]
                val B = tokens[5]

                val func = when(op) {
                    "*" -> {a: Int, b: Int -> a * b}
                    else -> {a: Int, b: Int -> a + b}
                }

                m.last().op = { old: Int ->
                    func(if(A == "old") old else A.toInt(),
                        if(B == "old") old else B.toInt()
                    )
                }
            }
            "Test:" -> {
                val testInt = tokens.last().toInt()
                m.last().test = {x: Int -> x % testInt == 0}
            }
            "If" -> {
                val trueBranch = tokens[1] == "true:"
                m.last().link[trueBranch] = tokens.last().toInt()
            }
        }

        line = br.readLine()
    }

    val inspections = Array(m.size) { 0 }
    repeat(20) {
        for(idx in m.indices) {
            val monkey = m[idx]
            while(monkey.items.isNotEmpty()) {
                val item = monkey.items.poll()
                val newWorry = monkey.op(item) / 3
                inspections[idx]++

                val res = monkey.test(newWorry)
                m[monkey.link[res]!!].items.addLast(newWorry)
            }
        }
    }

    println(inspections.sorted().takeLast(2).reduce { acc, i -> acc * i })
}