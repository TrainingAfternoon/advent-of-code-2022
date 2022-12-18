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

    data class State(val time: Int,
                     val valve: Valve,
                     val elephant: Valve,
                     val flow: Int,
                     val opened: BitSet)

    val known = HashMap<Triple<Int,Valve,Valve>,Int>()
    val queue = LinkedList<State>()
    queue.add(State(
        1,
        valves["AA"]!!,
        valves["AA"]!!,
        0,
        BitSet(valves.size)
    ))

    var ans = 0
    while(queue.isNotEmpty()) {
        val (time, valve, elephant, flow, opened) = queue.poll()

        if(known.getOrDefault(Triple(time,valve,elephant), -1) >= flow) continue
        known[Triple(time, valve, elephant)] = flow

        if(time == 26) {
            ans = max(ans, flow)
            continue
        }

        // prune branches where all valves are opened, so it's useless to move around
        if(opened.cardinality() == valves.size) {
            var currFlow = simulate(opened)
            var newFlow = flow + currFlow
            repeat(25 - time) {
                newFlow += currFlow
            }
            queue.addLast(State(
                26,
                valve,
                elephant,
                newFlow,
                opened
            ))
            continue
        }

        // we open a valve
        if(valve.flow > 0 && !opened.get(valve.id)) {
            val newOpened = opened.clone() as BitSet
            newOpened.set(valve.id)

            // elephant opens!
            if(elephant.flow > 0 && !newOpened.get(elephant.id)) {
                val newNewOpened = newOpened.clone() as BitSet
                newNewOpened.set(elephant.id)

                val newFlow = flow + simulate(newNewOpened)
                val newState = State(time+1, valve, elephant, newFlow, newNewOpened)
                queue.addLast(newState)
            }

            // elephant moves!
            val newFlow = flow + simulate(newOpened)
            for(elephantEdge in elephant.edges) {
                queue.addLast(State(
                    time+1,
                    valve,
                    valves[elephantEdge]!!,
                    newFlow,
                    newOpened
                ))
            }

            queue.addLast(State(
                time+1,
                valve,
                elephant,
                newFlow,
                newOpened
            ))
        }

        // we don't open a valve here
        val newFlow = flow + simulate(opened)
        for(edge in valve.edges) {
            // elephant opens its valve
            if(elephant.flow > 0 && !opened.get(elephant.id)) {
                val newOpened = opened.clone() as BitSet
                newOpened.set(elephant.id)

                queue.addLast(State(
                    time+1,
                    valves[edge]!!,
                    elephant,
                    flow + simulate(newOpened),
                    newOpened
                ))
            }

            // elephant also moves!
            for(elephantEdge in elephant.edges) {
                queue.addLast(State(
                    time+1,
                    valves[edge]!!,
                    valves[elephantEdge]!!,
                    newFlow,
                    opened
                ))
            }
        }
    }

    println(ans)
}