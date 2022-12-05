import java.util.*
import kotlin.collections.ArrayList

fun main() {
    val br = System.`in`.bufferedReader()

    var line = br.readLine()
    val lines = ArrayList<String>()
    while(line.isNotEmpty()) {
        lines.add(line)
        line = br.readLine()
    }

    //println(lines)
    val N = lines.removeLast().split(" ").last { it.isNotEmpty() }.toInt()
    val stacks = Array(N) { LinkedList<Char>() }

    for(l in lines) {
        //println("l = $l")
        var charIdx = 1
        var stackIdx = 0
        while(stackIdx < N) {
            //println("$charIdx |${l[charIdx]}| $stackIdx")
            if(l[charIdx] != ' ') stacks[stackIdx].addFirst(l[charIdx])
            charIdx += 4
            stackIdx += 1
        }
    }

    //for(stack in stacks) println(stack)

    line = br.readLine()
    while(line != null) {
        val tokens = line.split(' ')
        //println("tokens = $tokens")
        val amt = tokens[1].toInt()
        val src = tokens[3].toInt()-1
        val dst = tokens[5].toInt()-1

        //println("move $amt from $src to $dst")

        val ministack = LinkedList<Char>()
        repeat(amt) {
            if(stacks[src].isNotEmpty()) {
                ministack.addLast(stacks[src].removeLast())
            }
        }
        while(ministack.isNotEmpty()) {
            stacks[dst].addLast(ministack.removeLast())
        }
        line = br.readLine()
    }

    for(stack in stacks) print(stack.peekLast()); println()
}