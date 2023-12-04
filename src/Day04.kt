import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Int {
        val cardDelimitersRegex = Regex("[:|]")
        return input.sumOf {
            val (winningNumbers, myNumbers) = it.split(cardDelimitersRegex).takeLast(2)
                .map { it.split(" ").filter(String::isNotBlank).toSet() }
            val numMatches = myNumbers.count { it in winningNumbers }
            if (numMatches >= 1) {
                2.0.pow(numMatches - 1).roundToInt()
            } else {
                0
            }
        }
    }


    fun part2(input: List<String>): Int {
        val cardDelimitersRegex = Regex("[:|]")
        val data = input.mapIndexed { index, line ->
            val cardData = line.split(cardDelimitersRegex)
            val (winningNumbers, myNumbers) = cardData.takeLast(2).map { it.split(" ").filter(String::isNotBlank).toSet() }
            val numMatches = myNumbers.count { it in winningNumbers }
            index to ((1 to numMatches).toMutablePair())
        }.toMap()
        data.forEach { (key, value) ->
            val (count, numWinners) = value
            for (i in 1..numWinners) {
                data[key + i]?.incFirst(count)
            }
        }
        return data.values.sumOf { it.first }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readExampleInput("Day04")
    check(part1(testInput).also(Any::println) == 13)
    check(part2(testInput).also(Any::println) == 30)

    val input = readInput("Day04")
    measureTime{
        part1(input).println()
        part2(input).println()
    }.println()
}
