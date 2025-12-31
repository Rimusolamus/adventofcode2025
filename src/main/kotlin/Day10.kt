fun main() {
    val testInput = listOf(
        "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}",
        "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}",
        "[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}"
    )

    val input = readInput("Day10Input")

    val buttons = input.map {
        it.split(" ").dropLast(1).drop(1).map { button ->
            button.removeSurrounding("(", ")").split(",").map(String::toInt)
        }
    }

    val lightSwitchPositions = input.map {
        it.split(" ").first().removeSurrounding("[", "]").map { singleLight ->
            if (singleLight == '#') 1 else 0
        }
    }

    var totalPresses = 0

    for (machineIndex in buttons.indices) {
        val machineButtons = buttons[machineIndex]
        val target = lightSwitchPositions[machineIndex]

        val minPresses = findMinPresses(target, machineButtons)
        totalPresses += minPresses
    }

    println("Total: $totalPresses")
}

fun combinations(n: Int, k: Int): List<List<Int>> {
    if (k == 0) return listOf(emptyList())
    if (k > n) return emptyList()

    val result = mutableListOf<List<Int>>()
    val indices = IntArray(k) { it }  // [0, 1, 2, ..., k-1]

    while (true) {
        result.add(indices.toList())

        var i = k - 1
        while (i >= 0 && indices[i] == n - k + i) {
            i--
        }

        if (i < 0) break

        indices[i]++
        for (j in i + 1 until k) {
            indices[j] = indices[j - 1] + 1
        }
    }

    return result
}

private fun tryCombo(target: List<Int>, buttons: List<List<Int>>, buttonIndices: List<Int>): Boolean {
    val lights = MutableList(target.size) { 0 }

    for (buttonIndex in buttonIndices) {
        for (lightIndex in buttons[buttonIndex]) {
            lights[lightIndex] = 1 - lights[lightIndex]  // toggle: 0 -> 1, 1 -> 0
        }
    }

    return lights == target
}

private fun findMinPresses(target: List<Int>, buttons: List<List<Int>>): Int {
    for (k in 0..buttons.size) {
        for (combo in combinations(buttons.size, k)) {
            if (tryCombo(target, buttons, combo)) {
                return k
            }
        }
    }
    return -1
}