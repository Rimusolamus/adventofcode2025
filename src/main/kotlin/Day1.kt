val testLines = listOf("L68", "L30", "R48", "L5", "R60", "L55", "L1", "L99", "R14", "L82")

fun main() {
    val lines = readInput("Day1Input")
    val gauge = (0 until 100).toList()
    var cursor = 50

    var numberOfZeros = 0

    for (line in lines) {
        val direction = if (line.startsWith("R")) Direction.RIGHT else Direction.LEFT
        val steps = line.substring(1).toInt()

        for (i in 1..steps) {
            if (direction == Direction.RIGHT) {
                cursor++
                if (cursor >= 100) {
                    cursor = 0
                }
            } else {
                cursor--
                if (cursor < 0) {
                    cursor = gauge.size - 1
                }
            }
            if (gauge.getOrNull(cursor) == 0) {
                numberOfZeros++
            }
        }
    }
    println("Number of zeroes: $numberOfZeros")
}

enum class Direction {
    RIGHT, LEFT
}