import java.util.*
import kotlin.math.max
import kotlin.math.min

fun main() {
    val br = System.`in`.bufferedReader()

    data class Resources(val ore: Int, val clay: Int, val obsidian: Int, val geodes: Int) {
        fun satisfies(cost: Resources): Boolean {
            return this.ore >= cost.ore && this.clay >= cost.clay && this.obsidian >= cost.obsidian && this.geodes >= cost.geodes
        }

        fun subtract(cost: Resources): Resources {
            return Resources(this.ore-cost.ore,
                this.clay-cost.clay,
                this.obsidian-cost.obsidian,
                this.geodes-cost.geodes)
        }
    }
    data class Blueprint(val id: Int, val pricePerOreRobot: Resources, val pricePerClayRobot: Resources, val pricePerObRobot: Resources, val pricePerGeodeRobot: Resources) {
        fun totalClay(): Int {
            return pricePerClayRobot.clay + pricePerObRobot.clay + pricePerGeodeRobot.clay + pricePerOreRobot.clay
        }

        fun totalOre(): Int {
            return pricePerClayRobot.ore + pricePerObRobot.ore + pricePerGeodeRobot.ore + pricePerOreRobot.ore
        }

        fun totalObs(): Int {
            return pricePerClayRobot.obsidian + pricePerObRobot.obsidian + pricePerGeodeRobot.obsidian + pricePerOreRobot.obsidian
        }
    }
    data class State(val time: Int, val resources: Resources, val obRobots: Int, val orRobots: Int, val clRobots: Int, val geoRobots: Int)
    data class DPState(val resources: Resources, val obRobots: Int, val orRobots: Int, val clRobots: Int, val geoRobots: Int)

    fun solveBlueprint(blueprint: Blueprint): Int {
        val FUDGE_FACTOR = 3
        var ans = 0

        val queue = LinkedList<State>()
        val seen = HashSet<DPState>()
        queue.addLast(State(1, Resources(0,0,0,0), 0, 1, 0, 0))
        while(queue.isNotEmpty()) {
            val state = queue.poll()
            val (time, resources, obRobots, orRobots, clRobots, geoRobots) = state
            val (ore, clay, obsidian, geodes) = resources

            if(seen.contains(DPState(resources, obRobots, orRobots, clRobots, geoRobots))) continue
            seen.add(DPState(resources, obRobots, orRobots, clRobots, geoRobots))

            if(time == 25) {
                ans = max(ans, geodes)
                continue
            }

            val genOre = min(ore + orRobots, blueprint.totalOre()*FUDGE_FACTOR)
            val genClay = min(clay + clRobots, blueprint.totalClay()*FUDGE_FACTOR)
            val genObs = min(obsidian + obRobots, blueprint.totalObs()*FUDGE_FACTOR)
            val genGeodes = geodes + geoRobots
            val newResources = Resources(genOre, genClay, genObs, genGeodes)

            // noop universe - build nothing
            queue.addLast(State(
                time+1,
                newResources,
                obRobots, orRobots, clRobots, geoRobots
            ))

            // build clay robot
            if(resources.satisfies(blueprint.pricePerClayRobot) && clRobots < blueprint.totalClay()) {
                queue.addLast(State(
                    time+1,
                    newResources.subtract(blueprint.pricePerClayRobot),
                    obRobots, orRobots, clRobots+1, geoRobots
                ))
            }

            // build obsidian robot
            if(resources.satisfies(blueprint.pricePerObRobot) && obRobots < blueprint.totalObs()) {
                queue.addLast(State(
                    time+1,
                    newResources.subtract(blueprint.pricePerObRobot),
                    obRobots+1, orRobots, clRobots, geoRobots
                ))
            }

            // build ore robot
            if(resources.satisfies(blueprint.pricePerOreRobot) && orRobots < blueprint.totalOre()) {
                queue.addLast(State(
                    time+1,
                    newResources.subtract(blueprint.pricePerOreRobot),
                    obRobots, orRobots+1, clRobots, geoRobots
                ))
            }

            // build geo robot
            if(resources.satisfies(blueprint.pricePerGeodeRobot)) {
                queue.addLast(State(
                    time+1,
                    newResources.subtract(blueprint.pricePerGeodeRobot),
                    obRobots, orRobots, clRobots, geoRobots+1
                ))
            }
        }

        return ans * blueprint.id
    }

    fun parseRobot(tokens: LinkedList<String>): Resources {
        repeat(4) { tokens.poll() } // poll 'Each <type> robot costs'

        val resources = IntArray(4) { 0 }
        while(tokens.isNotEmpty() && tokens.peek() != "Each") {
            val curr = tokens.poll()
            if(curr[0] in '0'..'9') {
                val cost = curr.toInt()
                val type = tokens.poll().filter { it.isLetter() }
                when(type) {
                    "ore" -> resources[0] = cost
                    "clay" -> resources[1] = cost
                    "obsidian" -> resources[2] = cost
                    "geode" -> resources[3] = cost
                }
            } else if(curr == "and") continue
        }

        return Resources(resources[0], resources[1], resources[2], resources[3])
    }

    fun parseBlueprint(line: String): Blueprint {
        val tokens = LinkedList<String>(line.split(" ").toList())

        tokens.poll() // drop 'Blueprint'
        val id = tokens.poll().dropLast(1).toInt()
        val costs = Array<Resources>(4) { parseRobot(tokens) }

        return Blueprint(id, costs[0], costs[1], costs[2], costs[3])
    }

    val qualityLevels = ArrayList<Int>()
    for(line in br.lines()) {
        val blueprint = parseBlueprint(line)
        println(blueprint)
        val qualityLevel = solveBlueprint(blueprint)
        qualityLevels.add(qualityLevel)
    }

    println(qualityLevels)
    println(qualityLevels.sum())
}