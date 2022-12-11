import java.util.LinkedList

fun main() {
    var MOD = 1L // temp value
    val br = System.`in`.bufferedReader()

    data class Monkey(val id: Long) {
        var op: (Long) -> Long = {x: Long -> x}
        var test: (Long) -> Boolean = {x: Long -> true }
        var testNum: Long = 0L
        var items = LinkedList<Long>()
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
                m.add(Monkey(tokens.last().dropLast(1).toLong()))
            }
            "Starting" -> {
                for(item in tokens.drop(2)) {
                    if(item.last() == ',') m.last().items.add(item.dropLast(1).toLong())
                    else m.last().items.add(item.toLong())
                }
            }
            "Operation:" -> {
                val A = tokens[3]
                val op = tokens[4]
                val B = tokens[5]

                val func = when(op) {
                    "*" -> {a: Long, b: Long -> ((a.mod(MOD)) * (b.mod(MOD))).mod(MOD)}
                    else -> {a: Long, b: Long -> ((a.mod(MOD)) + (b.mod(MOD))).mod(MOD)}
                }

                m.last().op = { old: Long ->
                    func(if(A == "old") old else A.toLong(),
                        if(B == "old") old else B.toLong()
                    )
                }
            }
            "Test:" -> {
                val testInt = tokens.last().toLong()
                m.last().testNum = testInt
                m.last().test = {x: Long -> x % testInt == 0L}
            }
            "If" -> {
                val trueBranch = tokens[1] == "true:"
                m.last().link[trueBranch] = tokens.last().toInt()
            }
        }

        line = br.readLine()
    }

    MOD = m.map { it.testNum  }.reduce { acc, i -> acc * i }
    val inspections = Array(m.size) { 0L }
    repeat(10000) {
        for(idx in m.indices) {
            val monkey = m[idx]
            while(monkey.items.isNotEmpty()) {
                val item = monkey.items.poll()
                val newWorry = monkey.op(item)
                inspections[idx]++

                val res = monkey.test(newWorry)
                m[monkey.link[res]!!].items.addLast(newWorry)
            }
        }
    }

    println(inspections.sorted().takeLast(2).reduce { acc, i -> acc * i })
}