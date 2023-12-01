fun main() {
    fun part1(input: List<String>): Int {
        return input.map {
            it.filter(Char::isDigit)
        }.sumOf {
            "${it.first()}${it.last()}".toInt()
        }
    }


    val digits = (1..9).map(Int::toString) + (englishDigits - "zero").keys
    fun part2(input: List<String>): Int {
        return input.mapNotNull {
            val first = it.findAnyOf(
                digits
            )?.let {(_, value) ->
                value.toDigit()
            }
            val last = it.findLastAnyOf(
                digits
            )?.let {(_, value) ->
                value.toDigit()
            }
            "$first$last".toIntOrNull()
        }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readExampleInput("Day01")
    check(part1(testInput).also(Any::println) == 142)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
