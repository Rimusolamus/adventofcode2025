fun main() {
    val testInput = listOf(
        "3-5",
        "10-14",
        "16-20",
        "12-18"
    )
    val input = readInput("Day5Input1")
    val freshIngredients = mutableListOf<LongRange>()
    input.map {
        val fromTo = it.split("-")
        freshIngredients.add(fromTo.first().toLong()..fromTo.last().toLong())
    }

    val testInput2 = listOf(
        "1",
        "5",
        "8",
        "11",
        "17",
        "32"
    )
    val input2 = readInput("Day5Input2")

    var freshCounter = 0
    input2.map { indexToCheck ->
        val longIndex = indexToCheck.toLong()
        for (range in freshIngredients) {
            if (longIndex in range) {
                freshCounter++
                break
            }
        }
    }

    println(freshCounter)
}