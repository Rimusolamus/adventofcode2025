fun main() {
    val testInput = listOf(
        "7,1",
        "11,1",
        "11,7",
        "9,7",
        "9,5",
        "2,5",
        "2,3",
        "7,3"
    )
    val input = readInput("Day9Input")
    val coordinates = input.map { it.split(",").toCoordinate() }

    val edges = coordinates.zipWithNext().map { Edge(it.first, it.second) } +
            Edge(coordinates.last(), coordinates.first())

    var maxSquare = 0L

    for (i in coordinates.indices) {
        for (j in 0 until i) {
            val a = coordinates[i]
            val b = coordinates[j]

            val minX = minOf(a.x, b.x)
            val maxX = maxOf(a.x, b.x)
            val minY = minOf(a.y, b.y)
            val maxY = maxOf(a.y, b.y)

            val isCut = edges.any { edgeCutsRectangle(it, minX, maxX, minY, maxY) }
            if (isCut) continue

            val center = Coordinate((minX + maxX) / 2, (minY + maxY) / 2)
            if (!isInsidePolygon(center, edges)) continue

            val area = (maxX - minX + 1).toLong() * (maxY - minY + 1).toLong()
            if (area > maxSquare) maxSquare = area
        }
    }

    println(maxSquare)
}

private fun edgeCutsRectangle(edge: Edge, minX: Int, maxX: Int, minY: Int, maxY: Int): Boolean {
    val (a, b) = edge

    if (a.y == b.y) {
        // horizontal edge
        val y = a.y
        if (y <= minY || y >= maxY) return false
        val edgeMinX = minOf(a.x, b.x)
        val edgeMaxX = maxOf(a.x, b.x)
        return edgeMaxX > minX && edgeMinX < maxX
    } else {
        // vertical edge
        val x = a.x
        if (x <= minX || x >= maxX) return false
        val edgeMinY = minOf(a.y, b.y)
        val edgeMaxY = maxOf(a.y, b.y)
        return edgeMaxY > minY && edgeMinY < maxY
    }
}

private fun isInsidePolygon(point: Coordinate, edges: List<Edge>): Boolean {
    var crossings = 0
    val (px, py) = point

    for (edge in edges) {
        val (a, b) = edge
        if (a.x == b.x) {
            if (a.x > px) {
                val minY = minOf(a.y, b.y)
                val maxY = maxOf(a.y, b.y)
                if (py in minY until maxY) crossings++
            }
        }
    }
    return crossings % 2 == 1
}

private data class Edge(
    val start: Coordinate,
    val end: Coordinate
)

private fun List<String>.toCoordinate() = Coordinate(this[0].toInt(), this[1].toInt())