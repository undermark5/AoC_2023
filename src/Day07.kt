fun main() {
    fun part1(input: List<String>): Long {
        val sortedHands = input.map {
            val (hand, betString) = it.split(" ").filter(String::isNotBlank)
            CamelHand1(hand, betString.toInt())
        }.sortedDescending()
        return sortedHands.mapIndexed { index, camelHand ->
            camelHand.bet * (index + 1)
        }.sumOf { it.toLong() }
    }

    fun part2(input: List<String>): Long {
        val sortedHands = input.map {
            val (hand, betString) = it.split(" ").filter(String::isNotBlank)
            CamelHand2(hand, betString.toInt())
        }.sortedDescending()
        return sortedHands.mapIndexed { index, camelHand ->
            camelHand.bet * (index + 1)
        }.sumOf { it.toLong() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readExampleInput("Day07")
    check(part1(testInput).also(::println) == 6440L)
    check(part2(testInput).also(::println) == 5905L)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

private data class CamelHand1(val cards: String, val bet: Int) : Comparable<CamelHand1> {

    val strength: Int

    init {
        val grouping = cards.groupBy { it }
        val maxGroup = grouping.maxOf{(_,v) -> v.size }
        val minGroup = grouping.minOf{(_,v) -> v.size}
        val pairCount = grouping.count {(_, v) -> v.size == 2}
        strength = when {
            maxGroup == 5 -> 0
            maxGroup == 4 -> 1
            maxGroup == 3 && minGroup == 2 -> 2
            maxGroup == 3 -> 3
            pairCount == 2 -> 4
            pairCount == 1 -> 5
            else -> 6
        }
    }

    override fun compareTo(other: CamelHand1): Int {
        val baseCompare = strength.compareTo(other.strength)
        return if (baseCompare != 0) {
            baseCompare
        } else {
            val firstDifference = cards.zip(other.cards).indexOfFirst { (mine, yours) -> mine != yours }
            if (firstDifference == -1) {
                0
            } else {
                val mine = CardLabel1.valueOf("${cards[firstDifference]}")
                val yours = CardLabel1.valueOf("${other.cards[firstDifference]}")
                return mine.compareTo(yours)
            }
        }
    }
}

private data class CamelHand2(val cards: String, val bet: Int) : Comparable<CamelHand2> {

    val strength: Int

    init {
        val grouping = cards.groupBy { it }
        val jokerCount = grouping['J']?.size ?: 0
        val nonJokers = grouping.filterKeys { it != 'J' }
        strength = if (jokerCount == 5) {
            0
        } else {
            val maxGroup = nonJokers.maxOf{(_,v) -> v.size }
            val minGroup = nonJokers.minOf{(_,v) -> v.size}
            val pairCount = nonJokers.count {(_, v) -> v.size == 2}
            when {
                maxGroup + jokerCount == 5 -> 0
                maxGroup + jokerCount == 4 -> 1
                (maxGroup == 3 && minGroup == 2)|| (jokerCount == 1 && minGroup == 2) -> 2
                maxGroup + jokerCount == 3 -> 3
                pairCount == 2 || (jokerCount == 2 && maxGroup == 1) -> 4
                pairCount == 1 || (jokerCount == 1 && maxGroup == 1) -> 5
                else -> 6
            }
        }
    }

    override fun compareTo(other: CamelHand2): Int {
        val baseCompare = strength.compareTo(other.strength)
        return if (baseCompare != 0) {
            baseCompare
        } else {
            val firstDifference = cards.zip(other.cards).indexOfFirst { (mine, yours) -> mine != yours }
            if (firstDifference == -1) {
                0
            } else {
                val mine = CardLabel2.valueOf("${cards[firstDifference]}")
                val yours = CardLabel2.valueOf("${other.cards[firstDifference]}")
                return mine.compareTo(yours)
            }
        }
    }
}

enum class CardLabel1{
    A,
    K,
    Q,
    J,
    `T`,
    `9`,
    `8`,
    `7`,
    `6`,
    `5`,
    `4`,
    `3`,
    `2`,
    `1`,
}

enum class CardLabel2 {
    A,
    K,
    Q,
    `T`,
    `9`,
    `8`,
    `7`,
    `6`,
    `5`,
    `4`,
    `3`,
    `2`,
    `1`,
    J,
}
