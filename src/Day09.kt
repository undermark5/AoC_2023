fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            var history = it.split(" ").filter(String::isNotBlank).map(String::toInt)
            val lastNumbers = mutableListOf(history.last())
            do {
                val differences = history.drop(1).zip(history).map { (newValue, oldValue) -> newValue - oldValue }
                history = differences
                lastNumbers.add(history.last())
            } while (history.any { it != history.first() })
            lastNumbers.foldRight(0, Int::plus)
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            var history = it.split(" ").filter(String::isNotBlank).map(String::toInt)
            val firstNumbers = mutableListOf(history.first())
            do {
                val differences = history.drop(1).zip(history).map { (newValue, oldValue) -> newValue - oldValue }
                history = differences
                firstNumbers.add(history.first())
            } while (history.any { it != history.first() })
            firstNumbers.foldRight(0, Int::minus)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readExampleInput("Day09")
    check(part1(testInput).also(::println) == 114)
    check(part2(testInput).also(::println) == 2)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
