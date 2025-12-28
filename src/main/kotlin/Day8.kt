fun main() {
    val testInput = listOf(
        "162,817,812",
        "57,618,57",
        "906,360,560",
        "592,479,940",
        "352,342,300",
        "466,668,158",
        "542,29,236",
        "431,825,988",
        "739,650,466",
        "52,470,668",
        "216,146,977",
        "819,987,18",
        "117,168,530",
        "805,96,715",
        "346,949,466",
        "970,615,88",
        "941,993,340",
        "862,61,35",
        "984,92,344",
        "425,690,689"
    )

    val input = readInput("Day8Input")
    val boxes = input.map { it.split(",").toBox() }

    val matches = mutableListOf<Match>()
    for (i in boxes.indices) {
        for (j in 0 until i) {
            matches.add(Match(boxes[i], boxes[j], calculateDistance(boxes[i], boxes[j])))
        }
    }
    matches.sortBy { it.distance }

    var lastPair: Pair<Box, Box>? = null
    val chains = mutableListOf<MutableSet<Box>>()

    for (match in matches) {
        if (chains.size == 1 && chains[0].size == boxes.size) break

        val chainA = chains.find { match.first in it }
        val chainB = chains.find { match.second in it }

        when {
            chainA != null && chainA === chainB -> continue

            chainA != null && chainB != null -> {
                chainA.addAll(chainB)
                chains.remove(chainB)
            }

            chainA != null -> chainA.add(match.second)
            chainB != null -> chainB.add(match.first)

            else -> chains.add(mutableSetOf(match.first, match.second))
        }

        lastPair = match.first to match.second
    }

    println(lastPair!!.first.x.toLong() * lastPair.second.x.toLong())
}

private fun calculateDistance(a: Box, b: Box): Long {
    fun Int.sqr() = this.toLong() * this.toLong()
    return (a.x - b.x).sqr() + (a.y - b.y).sqr() + (a.z - b.z).sqr()
}

private data class Box(
    val x: Int,
    val y: Int,
    val z: Int
)

private data class Match(
    val first: Box,
    val second: Box,
    val distance: Long
)

private fun List<String>.toBox() = Box(this[0].toInt(), this[1].toInt(), this[2].toInt())