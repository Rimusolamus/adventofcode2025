fun main() {
    val testLines = listOf(
        "..@@.@@@@.",
        "@@@.@.@.@@",
        "@@@@@.@.@@",
        "@.@@@@..@.",
        "@@.@@@@.@@",
        ".@@@@@@@.@",
        ".@.@.@.@@@",
        "@.@@@.@@@@",
        ".@@@@@@@@.",
        "@.@.@@@.@."
    )
    val lines = readInput("Day4Input")

    // making 2 dim matrix filled with data from input
    val grid = Array(lines.size) { CharArray(lines.first().length) }
    for (lineIndex in lines.indices) {
        for (charIndex in lines[lineIndex].indices) {
            grid[lineIndex][charIndex] = lines[lineIndex][charIndex]
        }
    }

    var overallRollsCount = 0
    while (true) {
        val countResult = countBars(grid)
        if (countResult.count > 0) {
            overallRollsCount += countResult.count
            removeBars(grid, countResult.indexes)
        } else {
            break
        }
    }

    println(overallRollsCount)
}

private fun countBars(grid: Array<CharArray>): RollCount {
    var rollsCount = 0
    val indexes = mutableListOf<RollIndex>()

    // iterate over all chars
    for (lineIndex in grid.indices) {
        for (charIndex in grid[lineIndex].indices) {
            if (grid[lineIndex][charIndex] == '@') {
                var countAts = 0
                if (grid.getOrNull(lineIndex - 1)?.getOrNull(charIndex - 1) == '@') {
                    countAts++
                }
                if (grid.getOrNull(lineIndex - 1)?.getOrNull(charIndex) == '@') {
                    countAts++
                }
                if (grid.getOrNull(lineIndex - 1)?.getOrNull(charIndex + 1) == '@') {
                    countAts++
                }
                if (grid.getOrNull(lineIndex)?.getOrNull(charIndex - 1) == '@') {
                    countAts++
                }
                if (grid.getOrNull(lineIndex)?.getOrNull(charIndex + 1) == '@') {
                    countAts++
                }
                if (grid.getOrNull(lineIndex + 1)?.getOrNull(charIndex - 1) == '@') {
                    countAts++
                }
                if (grid.getOrNull(lineIndex + 1)?.getOrNull(charIndex) == '@') {
                    countAts++
                }
                if (grid.getOrNull(lineIndex + 1)?.getOrNull(charIndex + 1) == '@') {
                    countAts++
                }

                if (countAts < 4) {
                    rollsCount++
                    indexes.add(RollIndex(lineIndex, charIndex))
                }
            }
        }
    }
    return RollCount(
        count = rollsCount,
        indexes = indexes
    )
}

private fun removeBars(grid: Array<CharArray>, indexes: List<RollIndex>) {
    indexes.map {
        grid[it.x][it.y] = '.'
    }
}

private data class RollCount(
    val count: Int,
    val indexes: List<RollIndex>
)

private data class RollIndex(
    val x: Int,
    val y: Int
)