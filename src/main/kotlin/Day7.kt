fun main() {
    val input = readInput("Day7Input")
    val testInput = listOf(
        ".......S.......",
        "...............",
        ".......^.......",
        "...............",
        "......^.^......",
        "...............",
        ".....^.^.^.....",
        "...............",
        "....^.^...^....",
        "...............",
        "...^.^...^.^...",
        "...............",
        "..^...^.....^..",
        "...............",
        ".^.^.^.^.^...^.",
        "..............."
    )
    val matrix = input.toMutableList()
    val x = matrix.first().indexOf("S")
    val y = 0

    for (i in matrix) {
        println(i)
    }
    val splitters = mutableSetOf<Coordinate>()

    val toProcess = mutableListOf(Coordinate(x, y))
    while (toProcess.isNotEmpty()) {
        val obstacle = drawPathUntilObstacle(toProcess.first(), matrix)
        toProcess.removeAt(0)
        if (!obstacle.isFinal) {
            val obstacleCoordinates = Coordinate(obstacle.x, obstacle.y)
            if (obstacleCoordinates in splitters)
                continue
            splitters.add(obstacleCoordinates)
            val continueFromLeft = obstacle.copy(obstacle.x - 1, obstacle.y - 1)
            val continueFromRight = obstacle.copy(obstacle.x + 1, obstacle.y - 1)
            if (obstacle.x > 0) {
                toProcess.add(continueFromLeft)
            }
            if (obstacle.x < matrix[0].length - 1) {
                toProcess.add(continueFromRight)
            }
        }
    }
    for (i in matrix) {
        println(i)
    }

    println("-------------------------")
    println(splitters.size)
}

private fun drawPathUntilObstacle(coordinate: Coordinate, matrix: MutableList<String>): Coordinate {
    var y = coordinate.y
    while (true) {
        if (matrix.getOrNull(y + 1)?.getOrNull(coordinate.x) != '^' && matrix.size != y + 1) {
            val str = matrix[y + 1].toCharArray()
            str[coordinate.x] = '|'
            matrix[y + 1] = String(str)
            y++
        } else if (matrix.size == y + 1) {
            return Coordinate(coordinate.x, y + 1, isFinal = true)
        } else {
            return Coordinate(coordinate.x, y + 1)
        }
    }
}

private data class Coordinate(
    val x: Int,
    val y: Int,
    val isFinal: Boolean = false
)