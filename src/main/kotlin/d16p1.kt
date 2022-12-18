import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.max

fun main() {
    val br = System.`in`.bufferedReader()

    data class Valve(val id: Int, val name: String, val flow: Int) {
        val edges = ArrayList<String>()
    }

    var nextId = 0
    val valvesById = HashMap<Int,Valve>()
    val valves = HashMap<String,Valve>()
    for(line in br.lines()) {
        val tokens = line.split(" ")
        val valve = Valve(
            nextId++,
            tokens[1],
            tokens[4].split("=").last().dropLast(1).toInt()
        )

        val edges = tokens.drop(max(tokens.indexOf("valve"), tokens.indexOf("valves"))+1)
        for(edge in edges) {
            if(edge.last() == ',') valve.edges.add(edge.dropLast(1))
            else valve.edges.add(edge)
        }

        valvesById[valve.id] = valve
        valves[valve.name] = valve
    }

    fun simulate(bitset: BitSet): Int {
        var ans = 0
        var idx = bitset.nextSetBit(0)
        while(idx > -1) {
            ans += valvesById[idx]!!.flow
            idx = bitset.nextSetBit(idx+1)
        }
        return ans
    }

    data class State(val time: Int, val valve: Valve, val flow: Int, val opened: BitSet)

    val known = HashMap<Pair<Int,Valve>,Int>()
    val queue = LinkedList<State>()
    queue.add(State(
        1,
        valves["AA"]!!,
        0,
        BitSet(valves.size)
    ))

    var ans = 0
    while(queue.isNotEmpty()) {
        val (time, valve, flow, opened) = queue.poll()

        if(known.getOrDefault(time to valve, -1) >= flow) continue
        known[time to valve] = flow

        if(time == 30) {
            ans = max(ans, flow)
            continue
        }

        // open a valve
        if(valve.flow > 0 && !opened.get(valve.id)) {
            val newOpened = opened.clone() as BitSet
            newOpened.set(valve.id)
            val newFlow = flow + simulate(newOpened)
            val newState = State(time+1, valve, newFlow, newOpened)
            queue.addLast(newState)
        }

        // don't open a valve here
        val newFlow = flow + simulate(opened)
        for(edge in valve.edges) {
            val newState = State(time+1, valves[edge]!!, newFlow, opened)
            queue.addLast(newState)
        }
    }

    println(ans)
}