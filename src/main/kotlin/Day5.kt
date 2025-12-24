fun main() {
    val input = readInput("Day5Input1")

    val freshIngredients = mutableListOf<LongRange>()
    input.forEach { line ->
        val (from, to) = line.split("-").map { it.toLong() }
        freshIngredients.add(from..to)
    }

    freshIngredients.sortBy { it.start }

    var ingredientCounter = 0L

    var currentStart = freshIngredients.first().start
    var currentEnd = freshIngredients.first().last

    for (range in freshIngredients.drop(1)) {
        if (range.start > currentEnd + 1) {
            ingredientCounter += currentEnd - currentStart + 1
            currentStart = range.start
            currentEnd = range.last
        } else {
            currentEnd = maxOf(currentEnd, range.last)
        }
    }

    ingredientCounter += currentEnd - currentStart + 1

    println(ingredientCounter)
}