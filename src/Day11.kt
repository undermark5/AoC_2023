import kotlin.math.abs

fun main() {
    fun part1(input: List<String>, growthFactor: Int = 2): Long {
        val galaxies = mutableListOf<MutablePair<Long,Long>>()
        input.forEachIndexed {row, line ->
            line.forEachIndexed {col, char ->
                if (char == '#') {
                    galaxies.add((row.toLong() to col.toLong()).toMutablePair())
                }
            }
        }
        val galaxyRows = galaxies.map(MutablePair<Long, Long>::first)
        val galaxyCols = galaxies.map(MutablePair<Long, Long>::second)
        val emptyRows = input.indices.mapNotNull {
            if (it.toLong() !in galaxyRows) it else null
        }
        val emptyCols = input.first().indices.mapNotNull {
            if (it.toLong() !in galaxyCols) it else null
        }
        galaxies.forEach { galaxy ->
            galaxy.first += emptyRows.count { it < galaxy.first } * (growthFactor - 1)
            galaxy.second += emptyCols.count { it < galaxy.second } * (growthFactor - 1)
        }
//        val universeHeight = input.size + emptyRows.size
//        val universeWidth = input.first().length + emptyCols.size
//        val firstGalaxy = galaxies.random()
//        val secondGalaxy = (galaxies - firstGalaxy).random()
//        var currentBestDistance =
//            abs(firstGalaxy.first - secondGalaxy.first) + abs(firstGalaxy.second - secondGalaxy.second)
//        val bestDistance = galaxies.minOfOrNull { galaxy ->
//            val otherGalaxies = galaxies - galaxy
//            val closestGalaxyDistance = otherGalaxies.minOfOrNull { otherGalaxy ->
//                abs(galaxy.first - otherGalaxy.first) + abs(galaxy.second - otherGalaxy.second)
//            }
//            (closestGalaxyDistance ?: Int.MAX_VALUE)
//        }
//        val remainingGalaxies = galaxies.toMutableList()
        return galaxies.flatMapIndexed() { index, galaxy ->
            galaxies.drop(index + 1).map { otherGalaxy ->
                abs(galaxy.first - otherGalaxy.first) + abs(galaxy.second - otherGalaxy.second)
            }
        }.sum()
    }

    fun part2(input: List<String>): Long {
        return part1(input, 1000000)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readExampleInput("Day11")
    check(part1(testInput).also(::println) == 374L)
    check(part1(testInput, 10).also(::println) == 1030L)
    check(part1(testInput, 100).also(::println) == 8410L)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
