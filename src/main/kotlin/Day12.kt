fun main() {
    val input = readInput("Day12Input").joinToString("\n")

    val shapeAreas = mutableMapOf<Int, Int>()
    val sections = input.split("\n\n")

    for (section in sections) {
        val lines = section.lines()
        if (lines[0].matches(Regex("\\d+:"))) {
            val index = lines[0].removeSuffix(":").toInt()
            val area = lines.drop(1).sumOf { line -> line.count { it == '#' } }
            shapeAreas[index] = area
        }
    }

    var count = 0
    for (section in sections) {
        for (line in section.lines()) {
            if (line.contains("x") && line.contains(":")) {
                val (dims, counts) = line.split(": ")
                val (w, h) = dims.split("x").map { it.toInt() }
                val quantities = counts.split(" ").map { it.toInt() }

                val regionArea = w * h
                val presentsArea = quantities.withIndex().sumOf { (idx, qty) ->
                    (shapeAreas[idx] ?: 0) * qty
                }
                val totalPresents = quantities.sum()

                if (presentsArea > regionArea) continue

                val blocks3x3 = (w / 3) * (h / 3)
                if (blocks3x3 >= totalPresents) {
                    count++
                }
            }
        }
    }

    println(count)
}