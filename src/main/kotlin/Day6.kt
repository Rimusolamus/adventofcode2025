fun main() {
    val testLines = listOf(
        "123 328  51 64  ",
        " 45 64  387 23  ",
        "  6 98  215 314 ",
        "*   +   *   +   "
    )
    val lines = readInput("Day6Input")

    val numbers = mutableListOf<String>()
    for (line in lines) {
        numbers.add(line)
    }

    var rowIndex = 0
    var action: ACTION? = null
    var result = 0L
    val listOfReadyNumbers = mutableListOf<Long>()

    while (true) {
        if (rowIndex == numbers.first().length) {
            // we are out of the string, it's time to go home
            if (action == ACTION.SUM) {
                result += sumAll(listOfReadyNumbers)
                listOfReadyNumbers.clear()
            } else if (action == ACTION.MULTIPLY) {
                result += multiplyAll(listOfReadyNumbers)
                listOfReadyNumbers.clear()
            }
            println("result is: $result")
            break
        }

        // on empty line we do calculation then reset everything
        if (areAllCharsAreWhitespaces(numbers, rowIndex)) {
            if (action == ACTION.SUM) {
                result += sumAll(listOfReadyNumbers)
                listOfReadyNumbers.clear()
            } else if (action == ACTION.MULTIPLY) {
                result += multiplyAll(listOfReadyNumbers)
                listOfReadyNumbers.clear()
            }
            rowIndex++
            continue
        }

        // if we found new action we change it
        if (numbers.last().getOrNull(rowIndex) == '*') {
            action = ACTION.MULTIPLY
        }
        if (numbers.last().getOrNull(rowIndex) == '+') {
            action = ACTION.SUM
        }

        var number = ""

        for (charIndex in 0..numbers.size - 2) {
            if (!numbers[charIndex][rowIndex].isWhitespace()) {
                number = number + numbers[charIndex][rowIndex].toString()
            }
        }
        if (number != "") {
            listOfReadyNumbers.add(number.toLong())
        }

        rowIndex++
    }
}

private fun sumAll(listOfReadyNumbers: List<Long>): Long {
    var res = 0L
    for (i in listOfReadyNumbers) {
        res += i
    }
    return res
}

private fun multiplyAll(listOfReadyNumbers: List<Long>): Long {
    var res = 1L
    for (i in listOfReadyNumbers) {
        res *= i
    }
    return res
}

private fun areAllCharsAreWhitespaces(numbers: MutableList<String>, rowIndex: Int): Boolean {
    var counter = 0
    for (i in 0..numbers.size-1) {
        if (numbers[i].getOrNull(rowIndex)?.isWhitespace() == true) counter++
    }
    return counter == numbers.size
}

private enum class ACTION {
    SUM, MULTIPLY
}