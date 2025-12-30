fun main() {
    val input = readInput("Day7Input")
    val matrix = input.toMutableList()

    val x = matrix.first().indexOf('S')
    val y = 0

    val active = mutableMapOf<Coordinate, Long>()
    active[Coordinate(x, y)] = 1L

    var result = 0L

    while (active.isNotEmpty()) {
        val (coord, timelines) = active.entries.first()
        active.remove(coord)

        val obstacle = drawPathUntilObstacle(coord, matrix)

        if (obstacle.isFinal) {
            result += timelines
            continue
        }

        val left = obstacle.copy(x = obstacle.x - 1, y = obstacle.y - 1)
        val right = obstacle.copy(x = obstacle.x + 1, y = obstacle.y - 1)

        if (obstacle.x > 0) {
            active[left] = (active[left] ?: 0L) + timelines
        }

        if (obstacle.x < matrix[0].length - 1) {
            active[right] = (active[right] ?: 0L) + timelines
        }
    }

    println("Timelines: $result")

    println("---------------------")
    for (i in matrix) {
        println(i)
    }
}

private fun drawPathUntilObstacle(
    coordinate: Coordinate,
    matrix: MutableList<String>
): Coordinate {
    var y = coordinate.y
    while (true) {
        val nextY = y + 1
        if (nextY >= matrix.size) {
            return Coordinate(coordinate.x, nextY, isFinal = true)
        }
        if (matrix[nextY][coordinate.x] == '^') {
            return Coordinate(coordinate.x, nextY)
        }
        val str = matrix[nextY].toCharArray()
        str[coordinate.x] = '|'
        matrix[nextY] = String(str)
        y++
    }
}