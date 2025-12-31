import java.math.BigInteger

fun main() {
    val testInput = listOf(
        "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}",
        "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}",
        "[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}"
    )

    val input = readInput("Day10Input")

    val machines = input.map { line ->
        val buttons = Regex("""\(([^)]*)\)""").findAll(line).map { match ->
            match.groupValues[1].split(",").map { it.toInt() }
        }.toList()
        val target = Regex("""\{([^}]*)\}""").find(line)!!.groupValues[1]
            .split(",").map { it.toInt() }
        buttons to target
    }

    val total = machines.sumOf { (buttons, target) ->
        solveWithGauss(buttons, target)
    }
    println(total)
}

private data class Frac(val num: BigInteger, val den: BigInteger = BigInteger.ONE) {
    constructor(n: Int) : this(n.toBigInteger(), BigInteger.ONE)

    fun reduce(): Frac {
        if (num == BigInteger.ZERO) return Frac(BigInteger.ZERO, BigInteger.ONE)
        val g = num.gcd(den)
        val newDen = den / g
        return if (newDen < BigInteger.ZERO) Frac(-num / g, -newDen) else Frac(num / g, newDen)
    }

    operator fun plus(o: Frac) = Frac(num * o.den + o.num * den, den * o.den).reduce()
    operator fun minus(o: Frac) = Frac(num * o.den - o.num * den, den * o.den).reduce()
    operator fun times(o: Frac) = Frac(num * o.num, den * o.den).reduce()
    operator fun div(o: Frac) = Frac(num * o.den, den * o.num).reduce()
    fun isZero() = num == BigInteger.ZERO
    fun toIntOrNull(): Int? = if (den == BigInteger.ONE) num.toInt() else null
}

private fun solveWithGauss(buttons: List<List<Int>>, target: List<Int>): Int {
    val nCounters = target.size
    val nButtons = buttons.size

    val matrix = Array(nCounters) { row ->
        Array(nButtons + 1) { col ->
            if (col < nButtons) {
                if (row in buttons[col]) Frac(1) else Frac(0)
            } else {
                Frac(target[row])
            }
        }
    }

    var pivotRow = 0
    val pivotCols = mutableListOf<Int>()

    for (col in 0 until nButtons) {
        var found = -1
        for (row in pivotRow until nCounters) {
            if (!matrix[row][col].isZero()) {
                found = row
                break
            }
        }
        if (found == -1) continue

        val tmp = matrix[pivotRow]
        matrix[pivotRow] = matrix[found]
        matrix[found] = tmp

        val pivot = matrix[pivotRow][col]
        for (j in 0..nButtons) {
            matrix[pivotRow][j] = matrix[pivotRow][j] / pivot
        }

        for (row in 0 until nCounters) {
            if (row != pivotRow && !matrix[row][col].isZero()) {
                val factor = matrix[row][col]
                for (j in 0..nButtons) {
                    matrix[row][j] = matrix[row][j] - factor * matrix[pivotRow][j]
                }
            }
        }

        pivotCols.add(col)
        pivotRow++
    }

    val freeCols = (0 until nButtons).filter { it !in pivotCols }

    val maxFreeVal = target.max()
    var minPresses = Int.MAX_VALUE

    fun search(freeIdx: Int, freeVals: List<Int>) {
        if (freeIdx == freeCols.size) {
            val x = IntArray(nButtons)
            for ((i, col) in freeCols.withIndex()) {
                x[col] = freeVals[i]
            }

            for ((rowIdx, pivotCol) in pivotCols.withIndex()) {
                var value = matrix[rowIdx][nButtons]
                for (j in 0 until nButtons) {
                    if (j != pivotCol) {
                        value = value - matrix[rowIdx][j] * Frac(x[j])
                    }
                }
                val intVal = value.toIntOrNull()
                if (intVal == null || intVal < 0) return
                x[pivotCol] = intVal
            }

            for (i in 0 until nCounters) {
                var sum = 0
                for (j in 0 until nButtons) {
                    if (i in buttons[j]) sum += x[j]
                }
                if (sum != target[i]) return
            }

            val total = x.sum()
            if (total < minPresses) minPresses = total
            return
        }

        val maxVal = minOf(maxFreeVal, 200)
        for (v in 0..maxVal) {
            search(freeIdx + 1, freeVals + v)
        }
    }

    search(0, emptyList())
    return minPresses
}