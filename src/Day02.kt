fun main() {
    fun part1(input: List<String>): Int {
        val greenRegex = Regex("(?<count>\\d+) green")
        val blueRegex = Regex("(?<count>\\d+) blue")
        val redRegex = Regex("(?<count>\\d+) red")
        return input.mapNotNull {
            val split = it.split(":")
            val gameString = split.first()
            val gameData = split.drop(1)
            val rounds = gameData.first().split(";")
            rounds.map {
                val greenCount = (greenRegex.find(it)?.groups as? MatchNamedGroupCollection)?.get("count")?.value?.toIntOrNull() ?: 0
                val blueCount = (blueRegex.find(it)?.groups as? MatchNamedGroupCollection)?.get("count")?.value?.toIntOrNull() ?: 0
                val redCount = (redRegex.find(it)?.groups as? MatchNamedGroupCollection)?.get("count")?.value?.toIntOrNull() ?: 0
                if (redCount > 12 || greenCount > 13 || blueCount > 14) {
                    return@mapNotNull null
                }
            }
            gameString.filter { it.isDigit() }.toInt()
        }.sum()
    }


    val digits = (1..9).map(Int::toString) + (englishDigits - "zero").keys
    fun part2(input: List<String>): Int {
        val greenRegex = Regex("(?<count>\\d+) green")
        val blueRegex = Regex("(?<count>\\d+) blue")
        val redRegex = Regex("(?<count>\\d+) red")
        return input.map {
            val split = it.split(":")
            val gameString = split.first()
            val gameData = split.drop(1)
            val rounds = gameData.first().split(";")
            var greenMax = 0
            var blueMax = 0
            var redMax = 0
            rounds.forEach {
                val greenCount = (greenRegex.find(it)?.groups as? MatchNamedGroupCollection)?.get("count")?.value?.toIntOrNull() ?: 0
                val blueCount = (blueRegex.find(it)?.groups as? MatchNamedGroupCollection)?.get("count")?.value?.toIntOrNull() ?: 0
                val redCount = (redRegex.find(it)?.groups as? MatchNamedGroupCollection)?.get("count")?.value?.toIntOrNull() ?: 0
                if (redCount > redMax) {
                    redMax = redCount
                }

                if (blueCount > blueMax) {
                    blueMax = blueCount
                }

                if (greenCount > greenMax) {
                    greenMax = greenCount
                }
            }
            redMax * greenMax * blueMax
        }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readExampleInput("Day02")
    check(part1(testInput).also(Any::println) == 8)
    check(part2(testInput).also(Any::println) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
