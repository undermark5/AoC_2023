import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

suspend fun main() {
    val regex = Regex("(?<node>\\w+) = \\((?<left>\\w+), (?<right>\\w+)\\)")
    fun part1(input: List<String>): Int {
        val directions = input.first()
        val remainder = input.drop(2)
        val mapping: Map<String, Pair<String, String>> = remainder.mapNotNull {
            (regex.matchEntire(it)?.groups) as? MatchNamedGroupCollection
        }
            .associate {
                val node = it["node"]!!.value
                val left = it["left"]!!.value
                val right = it["right"]!!.value
                node to (left to right)
            }
        var currentLocation = "AAA"
        var currentInstructionIdx = 0
        var stepCount = 0
        while (currentLocation != "ZZZ") {
            val (left, right) = mapping[currentLocation] ?: error("invalid")
            val direction = directions[currentInstructionIdx]
            currentLocation = if(direction == 'L') {
                left
            } else {
                right
            }
            currentInstructionIdx++
            if (currentInstructionIdx > directions.lastIndex) {
                currentInstructionIdx = 0
            }
            stepCount++
        }
        return stepCount
    }

    suspend fun part2(input: List<String>): Long {
        val directions = input.first()
        val remainder = input.drop(2)
        val mapping: Map<String, Pair<String, String>> = remainder.mapNotNull {
            (regex.matchEntire(it)?.groups) as? MatchNamedGroupCollection
        }
            .associate {
                val node = it["node"]!!.value
                val left = it["left"]!!.value
                val right = it["right"]!!.value
                node to (left to right)
            }
        val startLocations = mapping.keys.filter { it.endsWith("A") }
        val distances = coroutineScope {
            startLocations.map { startLocation ->
                async {
                    var currentLocation = startLocation
                    var currentInstructionIdx = 0
                    var stepCount = 0
                    while (!currentLocation.endsWith("Z")) {
                        val (left, right) = mapping[currentLocation] ?: error("invalid")
                        val direction = directions[currentInstructionIdx]
                        currentLocation = if(direction == 'L') {
                            left
                        } else {
                            right
                        }
                        currentInstructionIdx++
                        if (currentInstructionIdx > directions.lastIndex) {
                            currentInstructionIdx = 0
                        }
                        stepCount++
                    }
                    stepCount
                }
            }.awaitAll()
        }
        return lcm(distances.map(Int::toLong))
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readExampleInput("Day08")
    check(part1(testInput).also(::println) == 2)
    check(part2(readExampleInput("Day08_2")).also(::println) == 6L)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
