fun main() {
    val testLines = listOf(
        "123 328  51 64",
        "45 64  387 23",
        "6 98  215 314",
        "*   +   *   +"
    )
    val lines = readInput("Day6Input")

    val numbers = mutableListOf<List<String>>()
    for (line in lines) {
        numbers.add(line.split(Regex("\\s+")))
    }

    var finalResult = 0L
    for (i in 0..numbers.first().size - 1) {
        if (numbers.last()[i] == "*") {
            var result = 1L
            for (numberIndex in 0..numbers.size - 2) {
                result *= numbers[numberIndex][i].toInt()
            }
            finalResult += result
        }
        if (numbers.last()[i] == "+") {
            var result = 0L
            for (numberIndex in 0..numbers.size - 2) {
                result += numbers[numberIndex][i].toInt()
            }
            finalResult += result
        }
    }
    println(finalResult)
}