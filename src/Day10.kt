import kotlin.math.roundToInt

fun main() {
    val validUpChars = listOf (
        '┌',
        '│',
        '┐',
    )

    val validDownChars = listOf(
        '┘',
        '│',
        '└',
    )
    val validLeftChars = listOf(
        '└',
        '┌',
        '─',
    )
    val validRightChars = listOf(
        '─',
        '┘',
        '┐',
    )
    fun computeStartChar(startNeighbors: List<Char?>): Char {
        val (up, right, down, left) = startNeighbors
        if (up in validUpChars) {
            if (right in validRightChars) {
                return '└'
            }
            if (down in validDownChars) {
                return '│'
            }
            if (left in validLeftChars) {
                return '┘'
            }
        }
        if (right in validRightChars) {
            if (down in validDownChars) {
                return '┌'
            }
            if (left in validLeftChars) {
                return '─'
            }
        }
        if (down in validDownChars) {
            if (left in validLeftChars) {
                return '┐'
            }
        }
        error("invalid")
    }

    fun part1(input: List<String>): Int {
        val fixedInput = input.map {
            it.replace("L", "└")
                .replace("F", "┌")
                .replace("-", "─")
                .replace("|", "│")
                .replace("7", "┐")
                .replace("J", "┘")
        }
        var start: Pair<Int,Int> = fixedInput.mapIndexedNotNull { index, s ->
            if (s.contains("S")) {
                index to s.indexOf('S')
            } else
                null
        }.first()
        val startNeighbors = listOf(
            fixedInput.getOrNull(start.up),
            fixedInput.getOrNull(start.right),
            fixedInput.getOrNull(start.down),
            fixedInput.getOrNull(start.left),
        )


        val startChar = computeStartChar(startNeighbors)


        val chars: List<List<Char>> = fixedInput.filter(String::isNotBlank).mapIndexed { index, line ->
            line.indexOf('S').takeIf { it >= 0 }?.let {
                start = index to it
            }
            line.replace('S', startChar)
                .toCharArray().toList()
        }

        var (curRow, curCol) = (start)


        var (inRow, inCol) = start
        when (startChar) {
            '┌' -> {
                inRow++
            }
            '│' -> {
                inRow++
            }
            '┐' -> {
                inRow++
            }
            '┘' -> {
                inCol--
            }
            '└' -> {
                inCol++
            }
            '─' -> {
                inCol--
            }
        }
        val targetRow = inRow
        val targetCol = inCol
        var stepCount = 0
        while (curRow != targetRow || curCol != targetCol) {
            stepCount++
            val curChar = chars[curRow][curCol]
            val old = curRow to curCol
            when(curChar) {
                '─' -> {
                    curCol += curCol - inCol
                }
                '│' -> {
                    curRow += curRow - inRow
                }
                '┐' -> {
                    if (inRow > curRow) {
                        curCol--
                    } else {
                        curRow++
                    }
                }
                '┘' -> {
                    if (inRow < curRow) {
                        curCol--
                    } else {
                        curRow--
                    }
                }
                '└' -> {
                    if (inRow < curRow) {
                        curCol++
                    } else {
                        curRow--
                    }
                }
                '┌' -> {
                    if (inRow > curRow) {
                        curCol++
                    } else {
                        curRow++
                    }
                }
            }
            inRow = old.first
            inCol = old.second
//            for (row in chars.indices) {
//                for (col in chars[row].indices) {
//                    if (row == curRow && col == curCol) {
//                        print("█")
//                    } else {
//                        print(chars[row to col])
//                    }
//                }
//                println()
//            }
//            println()
        }

        return (stepCount / 2f).roundToInt()

    }

    fun part2(input: List<String>): Int {
        val fixedInput = listOf("." * input.first().length) + input.map {
            "." + it.replace("L", "└")
                .replace("F", "┌")
                .replace("-", "─")
                .replace("|", "│")
                .replace("7", "┐")
                .replace("J", "┘") + "."
        } + listOf("." * input.last().length)
        var start: Pair<Int,Int> = fixedInput.mapIndexedNotNull { index, s ->
            if (s.contains("S")) {
                index to s.indexOf('S')
            } else
                null
        }.first()
        val startNeighbors = listOf(
            fixedInput.getOrNull(start.up),
            fixedInput.getOrNull(start.right),
            fixedInput.getOrNull(start.down),
            fixedInput.getOrNull(start.left),
        )


        val startChar = computeStartChar(startNeighbors)


        val pipes: List<List<Char>> = fixedInput.filter(String::isNotBlank).mapIndexed { index, line ->
            line.indexOf('S').takeIf { it >= 0 }?.let {
                start = index to it
            }
            line.replace('S', startChar)
                .toCharArray().toList()
        }

        var (curRow, curCol) = (start)


        var (inRow, inCol) = start
        when (startChar) {
            '┌' -> {
                inRow++
            }
            '│' -> {
                inRow++
            }
            '┐' -> {
                inRow++
            }
            '┘' -> {
                inCol--
            }
            '└' -> {
                inCol++
            }
            '─' -> {
                inCol--
            }
        }
        val targetRow = inRow
        val targetCol = inCol
        var stepCount = 0
        val path = mutableSetOf((curRow to curCol))

        while (curRow != targetRow || curCol != targetCol) {
            stepCount++
            val curChar = pipes[curRow][curCol]
            val old = curRow to curCol
            when(curChar) {
                '─' -> {
                    curCol += curCol - inCol
                }
                '│' -> {
                    curRow += curRow - inRow
                }
                '┐' -> {
                    if (inRow > curRow) {
                        curCol--
                    } else {
                        curRow++
                    }
                }
                '┘' -> {
                    if (inRow < curRow) {
                        curCol--
                    } else {
                        curRow--
                    }
                }
                '└' -> {
                    if (inRow < curRow) {
                        curCol++
                    } else {
                        curRow--
                    }
                }
                '┌' -> {
                    if (inRow > curRow) {
                        curCol++
                    } else {
                        curRow++
                    }
                }
            }
            inRow = old.first
            inCol = old.second
            path.add((curRow to curCol))
//            for (row in chars.indices) {
//                for (col in chars[row].indices) {
//                    if (row == curRow && col == curCol) {
//                        print("█")
//                    } else {
//                        print(chars[row to col])
//                    }
//                }
//                println()
//            }
//            println()
        }
        var insideCount = 0
        val stringBuilder = StringBuilder()
        pipes.forEachIndexed { row, chars ->
            var crossOverCount = 0
            chars.forEachIndexed { col, char ->
                if (row to col in path && char in validUpChars) {
                    crossOverCount++
                    stringBuilder.append(char)
                    print(char)
                } else {
                    if (row to col in path) {
                        stringBuilder.append(char)
                        print(char)
                    } else if (crossOverCount and 1 != 0) {
                        stringBuilder.append("I")
                        print("I")
                        insideCount++
                    } else {
                        stringBuilder.append(" ")
                        print(" ")
                    }
                }
            }
            stringBuilder.appendLine()
            println()
        }
        return insideCount

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readExampleInput("Day10")
    check(part1(testInput).also(::println) == 8)
    val testInput2 = readExampleInput("Day10_2")
    check(part2(testInput2).also(::println) == testInput2.sumOf { it.count{ it == 'I'} })

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}

private operator fun String.times(length: Int): String {
    return this.repeat(length)
}
