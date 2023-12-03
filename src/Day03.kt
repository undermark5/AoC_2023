fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEachIndexed {lineIndex, line ->
            line.forEachIndexed { charIndex, char ->
                if (char != '.' && !char.isDigit()) {
                    if (lineIndex - 1 in input.indices) {
                        val adjacentLine = input[lineIndex - 1]
                        if (adjacentLine.substring(charIndex - 1..charIndex + 1).any(Char::isDigit)) {
                            val possibleNumbers = adjacentLine.substring(charIndex - 3..charIndex + 3)
                            val firstPossible = possibleNumbers.substring(0..2).takeLastWhile(Char::isDigit)

                            val secondPossible = possibleNumbers.substring(2..4).split(".")
                            val thirdPossible = possibleNumbers.substring(4..6).takeWhile(Char::isDigit)
                            if (secondPossible.size == 4) {
                                // not possible case
                            } else if (secondPossible.size == 1) {
                                sum += secondPossible.first().toIntOrNull() ?: 0
                            } else if (secondPossible.size == 3) {
                                if (secondPossible[1].isNotEmpty()) {
                                    sum += secondPossible[1].toInt()
                                } else {
                                    sum += firstPossible.toIntOrNull() ?: 0
                                    sum += thirdPossible.toIntOrNull() ?: 0
                                }
                            } else if (secondPossible.size == 2) {
                                if (secondPossible.all { it.isNotEmpty() }) {
                                    sum += firstPossible.toIntOrNull() ?: 0
                                    sum += thirdPossible.toIntOrNull() ?: 0
                                } else {
                                    sum += (firstPossible + (secondPossible.first().lastOrNull()?.toString() ?: "")).toIntOrNull() ?: 0
                                    sum += ((secondPossible.last().firstOrNull()?.toString() ?: "") + thirdPossible).toIntOrNull() ?: 0
                                }
                            }
                        }
                    }
                    if (lineIndex + 1 in input.indices) {
                        val adjacentLine = input[lineIndex + 1]
                        if (adjacentLine.substring(charIndex - 1..charIndex + 1).any(Char::isDigit)) {
                            val possibleNumbers = adjacentLine.substring(charIndex - 3..charIndex + 3)
                            val firstPossible = possibleNumbers.substring(0..2).takeLastWhile(Char::isDigit)

                            val secondPossible = possibleNumbers.substring(2..4).split(".")
                            val thirdPossible = possibleNumbers.substring(4..6).takeWhile(Char::isDigit)
                            if (secondPossible.size == 4) {
                                // not possible case
                            } else if (secondPossible.size == 1) {
                                sum += secondPossible.first().toIntOrNull() ?: 0
                            } else if (secondPossible.size == 3) {
                                if (secondPossible[1].isNotEmpty()) {
                                    sum += secondPossible[1].toInt()
                                } else {
                                    sum += firstPossible.toIntOrNull() ?: 0
                                    sum += thirdPossible.toIntOrNull() ?: 0
                                }
                            } else if (secondPossible.size == 2) {
                                if (secondPossible.all { it.isNotEmpty() }) {
                                    sum += firstPossible.toIntOrNull() ?: 0
                                    sum += thirdPossible.toIntOrNull() ?: 0
                                } else {
                                    sum += (firstPossible + (secondPossible.first().lastOrNull()?.toString() ?: "")).toIntOrNull() ?: 0
                                    sum += ((secondPossible.last().firstOrNull()?.toString() ?: "") + thirdPossible).toIntOrNull() ?: 0
                                }
                            }
                        }
                    }
                    sum += line.substring(charIndex - 3..<charIndex).takeLastWhile(Char::isDigit).toIntOrNull() ?: 0
                    sum += line.substring(charIndex + 1..charIndex + 3).takeWhile(Char::isDigit).toIntOrNull() ?: 0
                }
            }
        }
        return sum
    }


    fun part2(input: List<String>): Int {
        var sum = 0
        input.forEachIndexed {lineIndex, line ->
            line.forEachIndexed { charIndex, char ->
                if (char == '*') {
                    val numAdjacentNumbers = input.getOrElse(lineIndex - 1) { "" }
                        .substring(charIndex - 1..charIndex + 1)
                        .split(".")
                        .count {
                            it.any(Char::isDigit)
                        } + input.getOrElse(lineIndex + 1) { "" }
                        .substring(charIndex - 1..charIndex + 1)
                        .split(".")
                        .count {
                            it.any(Char::isDigit)
                        } + line.substring(charIndex - 1..charIndex + 1)
                        .replace("*", ".")
                        .split(".")
                        .count {
                            it.any(Char::isDigit)
                        }
                    if (numAdjacentNumbers == 2) {
                        val adjacentNumbers = mutableListOf<Int?>()
                        if (lineIndex - 1 in input.indices) {
                            val adjacentLine = input[lineIndex - 1]
                            if (adjacentLine.substring(charIndex - 1..charIndex + 1).any(Char::isDigit)) {
                                val possibleNumbers = adjacentLine.substring(charIndex - 3..charIndex + 3)
                                val firstPossible = possibleNumbers.substring(0..2).takeLastWhile(Char::isDigit)

                                val secondPossible = possibleNumbers.substring(2..4).split(".")
                                val thirdPossible = possibleNumbers.substring(4..6).takeWhile(Char::isDigit)
                                if (secondPossible.size == 4) {
                                    // not possible case
                                } else if (secondPossible.size == 1) {
                                    adjacentNumbers += secondPossible.first().toIntOrNull()
                                } else if (secondPossible.size == 3) {
                                    if (secondPossible[1].isNotEmpty()) {
                                        adjacentNumbers += secondPossible[1].toInt()
                                    } else {
                                        adjacentNumbers += firstPossible.toIntOrNull()
                                        adjacentNumbers += thirdPossible.toIntOrNull()
                                    }
                                } else if (secondPossible.size == 2) {
                                    if (secondPossible.all { it.isNotEmpty() }) {
                                        adjacentNumbers += firstPossible.toIntOrNull()
                                        adjacentNumbers += thirdPossible.toIntOrNull()
                                    } else {
                                        adjacentNumbers += (firstPossible + (secondPossible.first().lastOrNull()?.toString() ?: "")).toIntOrNull()
                                        adjacentNumbers += ((secondPossible.last().firstOrNull()?.toString() ?: "") + thirdPossible).toIntOrNull()
                                    }
                                }
                            }
                        }
                        if (lineIndex + 1 in input.indices) {
                            val adjacentLine = input[lineIndex + 1]
                            if (adjacentLine.substring(charIndex - 1..charIndex + 1).any(Char::isDigit)) {
                                val possibleNumbers = adjacentLine.substring(charIndex - 3..charIndex + 3)
                                val firstPossible = possibleNumbers.substring(0..2).takeLastWhile(Char::isDigit)

                                val secondPossible = possibleNumbers.substring(2..4).split(".")
                                val thirdPossible = possibleNumbers.substring(4..6).takeWhile(Char::isDigit)
                                if (secondPossible.size == 4) {
                                    // not possible case
                                } else if (secondPossible.size == 1) {
                                    adjacentNumbers += secondPossible.first().toIntOrNull()
                                } else if (secondPossible.size == 3) {
                                    if (secondPossible[1].isNotEmpty()) {
                                        adjacentNumbers += secondPossible[1].toInt()
                                    } else {
                                        adjacentNumbers += firstPossible.toIntOrNull()
                                        adjacentNumbers += thirdPossible.toIntOrNull()
                                    }
                                } else if (secondPossible.size == 2) {
                                    if (secondPossible.all { it.isNotEmpty() }) {
                                        adjacentNumbers += firstPossible.toIntOrNull()
                                        adjacentNumbers += thirdPossible.toIntOrNull()
                                    } else {
                                        adjacentNumbers += (firstPossible + (secondPossible.first().lastOrNull()?.toString() ?: "")).toIntOrNull()
                                        adjacentNumbers += ((secondPossible.last().firstOrNull()?.toString() ?: "") + thirdPossible).toIntOrNull()
                                    }
                                }
                            }
                        }
                        adjacentNumbers += line.substring(charIndex - 3..<charIndex).takeLastWhile(Char::isDigit).toIntOrNull()
                        adjacentNumbers += line.substring(charIndex + 1..charIndex + 3).takeWhile(Char::isDigit).toIntOrNull()
                        if (adjacentNumbers.filterNotNull().size != 2) {
                            error("error")
                        } else {
                            sum += adjacentNumbers.filterNotNull().reduce(Int::times)
                        }
                    }
                }
            }
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readExampleInput("Day03")
    check(part1(testInput).also(Any::println) == 4361)
    check(part2(testInput).also(Any::println) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
