fun main() {
    val testInput = """
        svr: aaa bbb
        aaa: fft
        fft: ccc
        bbb: tty
        tty: ccc
        ccc: ddd eee
        ddd: hub
        hub: fff
        eee: dac
        dac: fff
        fff: ggg hhh
        ggg: out
        hhh: out
    """.trimIndent().lines()

    val input = readInput("Day11Input")

    val graph = mutableMapOf<String, List<String>>()
    for (line in input) {
        val parts = line.split(": ")
        val device = parts[0]
        val outputs = if (parts.size > 1 && parts[1].isNotBlank()) {
            parts[1].split(" ")
        } else {
            emptyList()
        }
        graph[device] = outputs
    }

    val memo = mutableMapOf<Triple<String, Boolean, Boolean>, Long>()

    fun countPaths(node: String, visitedDac: Boolean, visitedFft: Boolean): Long {
        val nowDac = visitedDac || (node == "dac")
        val nowFft = visitedFft || (node == "fft")

        if (node == "out") {
            return if (nowDac && nowFft) 1L else 0L
        }

        val key = Triple(node, nowDac, nowFft)
        if (key in memo) return memo[key]!!

        val outputs = graph[node] ?: return 0L
        val count = outputs.sumOf { countPaths(it, nowDac, nowFft) }
        memo[key] = count
        return count
    }

    println(countPaths("svr", false, false))
}