fun main() {
    fun part1(input: List<String>): Int {
        val(times, records) = input.map { it.split(" ").mapNotNull { it.trim().toIntOrNull() } }
        val races = times.zip(records)
        return races.map { (time, record) ->
            (1..<time).count {timePressing ->
                val timeMoving = time - timePressing
                timeMoving * timePressing > record
            }
        }.reduce(Int::times)
    }

    fun part2(input: List<String>): Int {
        val(time, record) = input.map { it.split(" ").mapNotNull { it.trim().toIntOrNull() }.joinToString("").toLong() }

        return (1..<time).count {timePressing ->
            val timeMoving = time - timePressing
            timeMoving * timePressing > record
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readExampleInput("Day06")
    check(part1(testInput).also(::println) == 288)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
