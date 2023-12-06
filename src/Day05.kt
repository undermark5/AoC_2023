import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Long {
        val data = input.joinToString(separator = "\n").split("\n\n")
        val seeds = data.first().split(" ").filter(String::isNotBlank).mapNotNull { it.toLongOrNull() }
        val seedToSoil = data[1].split("\n").takeLastWhile { it.none { it.isLetter() } }.map {
            val(destStart, srcStart, length) =  it.split(" ").map { it.toLong() }
            val srcRange = srcStart..<srcStart+length
            srcRange to {item: Long -> destStart - srcStart + item }
        }
        val soilToFertilizer = data[2].split("\n").takeLastWhile { it.none { it.isLetter() } }.map {
            val(destStart, srcStart, length) =  it.split(" ").map { it.toLong() }
            val srcRange = srcStart..<srcStart+length
            srcRange to {item: Long -> destStart - srcStart + item }
        }
        val fertilizerToWater = data[3].split("\n").takeLastWhile { it.none { it.isLetter() } }.map {
            val(destStart, srcStart, length) =  it.split(" ").map { it.toLong() }
            val srcRange = srcStart..<srcStart+length
            srcRange to {item: Long -> destStart - srcStart + item }
        }
        val waterToLight = data[4].split("\n").takeLastWhile { it.none { it.isLetter() } }.map {
            val(destStart, srcStart, length) =  it.split(" ").map { it.toLong() }
            val srcRange = srcStart..<srcStart+length
            srcRange to {item: Long -> destStart - srcStart + item }
        }
        val lightToTemp = data[5].split("\n").takeLastWhile { it.none { it.isLetter() } }.map {
            val(destStart, srcStart, length) =  it.split(" ").map { it.toLong() }
            val srcRange = srcStart..<srcStart+length
            srcRange to {item: Long -> destStart - srcStart + item }
        }
        val tempToHumidity = data[6].split("\n").takeLastWhile { it.none { it.isLetter() } }.map {
            val(destStart, srcStart, length) =  it.split(" ").map { it.toLong() }
            val srcRange = srcStart..<srcStart+length
            srcRange to {item: Long -> destStart - srcStart + item }
        }
        val humidityToLocation = data[7].split("\n").takeLastWhile { it.none { it.isLetter() } }.map {
            val(destStart, srcStart, length) =  it.split(" ").map { it.toLong() }
            val srcRange = srcStart..<srcStart+length
            srcRange to {item: Long -> destStart - srcStart + item }
        }

        return seeds.minOf { seed ->
            val soilMap = seedToSoil.firstOrNull { seed in it.first }
            val soil = soilMap?.let { it.second(seed) } ?: seed
            val fertilizerMap = soilToFertilizer.firstOrNull { soil in it.first }
            val fertilizer = fertilizerMap?.let {it.second(soil)} ?: soil
            val waterMap = fertilizerToWater.firstOrNull{fertilizer in it.first}
            val water = waterMap?.let{it.second(fertilizer)} ?: fertilizer
            val lightMap = waterToLight.firstOrNull{water in it.first}
            val light = lightMap?.let{it.second(water)} ?: water
            val tempMap = lightToTemp.firstOrNull{light in it.first}
            val temp = tempMap?.let { it.second(light) } ?: light
            val humidityMap = tempToHumidity.firstOrNull{temp in it.first}
            val humidity = humidityMap?.let { it.second(temp) } ?: temp
            val locationMap = humidityToLocation.firstOrNull{humidity in it.first}
            locationMap?.let { it.second(humidity) } ?: humidity
        }
    }

    fun List<LongRange>.mergeRanges(): List<LongRange> {
        val unmerged = mutableListOf(*this.toTypedArray())
        val result = mutableListOf<LongRange>()
        while (unmerged.isNotEmpty()) {
            val current = unmerged.pop()
            val adjacent = unmerged.filter { it.first == current.last || it.last == current.first }.sortedBy { it.first }
            if (adjacent.isEmpty()) {
                result.add(current)
            } else {
                var temp = current
                adjacent.forEach {
                    temp = min(temp.first, it.first)..max(temp.last, it.last)
                }
                result.add(temp)
                unmerged.removeAll(adjacent)
            }
        }
        return result
    }

    fun mapData(
        mappingData: List<Pair<LongRange, (LongRange) -> LongRange>>,
        remainders: List<LongRange>
    ): List<LongRange> {
        var currentRemainders = remainders
        val mappedValues = mutableListOf<LongRange>()
        mappingData.forEach { (src, mapper) ->
            val newRemainders = currentRemainders.flatMap {
                val intersection = it.intersect(src)
                if (intersection.isNotEmpty()) {
                    mappedValues.add(mapper(intersection))
                }
                val remainder = it - intersection
                remainder
            }.filter(LongRange::isNotEmpty)
            currentRemainders = newRemainders
        }
        mappedValues.addAll(currentRemainders)
        return mappedValues.mergeRanges()
    }

    fun part2(input: List<String>): Long {
        val data = input.joinToString(separator = "\n").split("\n\n")
        val seeds = data.first().split(" ").filter(String::isNotBlank).mapNotNull { it.toLongOrNull() }.windowed(2, 2).map { it.first()..<it.first() + it.last() }
        val seedToSoil = data[1].split("\n").takeLastWhile { it.none { it.isLetter() } }.let {
            it.map {
                val (destStart, srcStart, length) = it.split(" ").map { it.toLong() }
                val srcRange = srcStart..<srcStart + length
                srcRange to { range: LongRange ->
                    val offset = destStart - srcStart
                    range + offset
                }
            }
        }
        val soilToFertilizer = data[2].split("\n").takeLastWhile { it.none { it.isLetter() } }.let {
            it.map {
                val (destStart, srcStart, length) = it.split(" ").map { it.toLong() }
                val srcRange = srcStart..<srcStart + length
                srcRange to { range: LongRange ->
                    val offset = destStart - srcStart
                    range + offset
                }
            }
        }
        val fertilizerToWater = data[3].split("\n").takeLastWhile { it.none { it.isLetter() } }.let {
            it.map {
                val (destStart, srcStart, length) = it.split(" ").map { it.toLong() }
                val srcRange = srcStart..<srcStart + length
                srcRange to { range: LongRange ->
                    val offset = destStart - srcStart
                    range + offset
                }
            }
        }
        val waterToLight = data[4].split("\n").takeLastWhile { it.none { it.isLetter() } }.let {
            it.map {
                val (destStart, srcStart, length) = it.split(" ").map { it.toLong() }
                val srcRange = srcStart..<srcStart + length
                srcRange to { range: LongRange ->
                    val offset = destStart - srcStart
                    range + offset
                }
            }
        }
        val lightToTemp = data[5].split("\n").takeLastWhile { it.none { it.isLetter() } }.let {
            it.map {
                val (destStart, srcStart, length) = it.split(" ").map { it.toLong() }
                val srcRange = srcStart..<srcStart + length
                srcRange to { range: LongRange ->
                    val offset = destStart - srcStart
                    range + offset
                }
            }
        }
        val tempToHumidity = data[6].split("\n").takeLastWhile { it.none { it.isLetter() } }.let {
            it.map {
                val (destStart, srcStart, length) = it.split(" ").map { it.toLong() }
                val srcRange = srcStart..<srcStart + length
                srcRange to { range: LongRange ->
                    val offset = destStart - srcStart
                    range + offset
                }
            }
        }
        val humidityToLocation = data[7].split("\n").takeLastWhile { it.none { it.isLetter() } }.let {
            it.map {
                val (destStart, srcStart, length) = it.split(" ").map { it.toLong() }
                val srcRange = srcStart..<srcStart + length
                srcRange to { range: LongRange ->
                    val offset = destStart - srcStart
                    range + offset
                }
            }
        }
        return seeds.minOf { seed ->
            var remainder = listOf(seed)
            remainder = mapData(seedToSoil, remainder)
            remainder = mapData(soilToFertilizer, remainder)
            remainder = mapData(fertilizerToWater, remainder)
            remainder = mapData(waterToLight, remainder)
            remainder = mapData(lightToTemp, remainder)
            remainder = mapData(tempToHumidity, remainder)
            remainder = mapData(humidityToLocation, remainder)
            remainder.minOf { it.first }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readExampleInput("Day05")
    check(part1(testInput).also(::println) == 35L)
    check(part2(testInput).also(::println) == 46L)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

private fun LongRange.overlaps(other: LongRange): Boolean {
    return first in other || last in other
}
