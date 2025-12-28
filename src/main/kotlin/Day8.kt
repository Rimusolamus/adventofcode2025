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
    val boxes = input.map {
        it.split(",").toBox()
    }
    val matches = mutableListOf<Match>()
    for (boxIndex in boxes.indices) {
        for (matchBox in boxes.indices) {
            if (boxIndex > matchBox) {
                val match = Match(
                    Pair(
                        boxes[boxIndex],
                        boxes[matchBox]
                    ),
                    distance = calculateDistance(boxes[boxIndex], boxes[matchBox])
                )
                matches.add(match)
            }
        }
    }
    matches.sortBy { it.distance }
    var matchCount = 0
    val chains = mutableListOf<MutableList<Box>>()
    for (match in matches) {
        if (matchCount == 1000) {
            break
        }
        val chainIndex = getChainIndexByMatch(chains, match)
        if (chainIndex == -666) {
            // both boxes are in chain go next
            matchCount++
            continue
        }
        if (chainIndex.isNonNegative()) {
            if (match.pair.first in chains[chainIndex]) {
                val secondBoxIndex = getChainIndexByBox(chains, match.pair.second)
                if (secondBoxIndex.isNonNegative()) {
                    val firstIndex = minOf(chainIndex, secondBoxIndex)
                    val secondIndex = maxOf(chainIndex, secondBoxIndex)
                    for (box in chains[secondIndex]) {
                        chains[firstIndex].add(box)
                    }
                    chains.removeAt(secondIndex)
                } else {
                    chains[chainIndex].add(match.pair.second)
                }
            } else {
                val secondBoxIndex = getChainIndexByBox(chains, match.pair.first)
                if (secondBoxIndex.isNonNegative()) {
                    val firstIndex = minOf(chainIndex, secondBoxIndex)
                    val secondIndex = maxOf(chainIndex, secondBoxIndex)
                    for (box in chains[secondIndex]) {
                        chains[firstIndex].add(box)
                    }
                    chains.removeAt(secondIndex)
                } else {
                    chains[chainIndex].add(match.pair.first)
                }
            }
        } else {
            chains.add(mutableListOf(match.pair.first, match.pair.second))
        }
        matchCount++
    }

    chains.sortByDescending { it.size }
    println(chains[0].size * chains[1].size * chains[2].size)
}

private fun getChainIndexByBox(chains: List<List<Box>>, box: Box): Int {
    for (chain in chains) {
        if (box in chain) {
            return chains.indexOf(chain)
        }
    }
    return -1
}

private fun getChainIndexByMatch(chains: List<List<Box>>, match: Match): Int {
    for (chain in chains) {
        if (match.pair.first in chain && match.pair.second in chain) {
            return -666
        } else if (match.pair.first in chain || match.pair.second in chain) {
            return chains.indexOf(chain)
        }
    }
    return -1
}

// there also should be sqrt but idc
private fun calculateDistance(boxA: Box, boxB: Box): Long {
    return (boxA.x - boxB.x).sqr() + (boxA.y - boxB.y).sqr() + (boxA.z - boxB.z).sqr()
}

private data class Box(
    val x: Int,
    val y: Int,
    val z: Int
)

private data class Match(
    val pair: Pair<Box, Box>,
    val distance: Long
)

private fun List<String>.toBox(): Box {
    return if (this.size == 3) {
        Box(this[0].toInt(), this[1].toInt(), this[2].toInt())
    } else {
        Box(0, 0, 0) // shouldn't happen
    }
}

private fun Int.sqr(): Long {
    return this.toLong() * this.toLong()
}

private fun Int.isNonNegative(): Boolean {
    return this >= 0
}