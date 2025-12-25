fun main() {
    val numbers = readInput("Day6Input")

    val digitRows = numbers.dropLast(1)
    val operatorRow = numbers.last()

    var action: ACTION? = null
    var result = 0L
    val buffer = mutableListOf<Long>()

    for (col in digitRows.first().indices) {

        if (digitRows.all { it.getOrNull(col)?.isWhitespace() == true }) {
            result += apply(action, buffer)
            buffer.clear()
            continue
        }

        when (operatorRow.getOrNull(col)) {
            '+' -> action = ACTION.SUM
            '*' -> action = ACTION.MULTIPLY
        }

        val number = digitRows
            .mapNotNull { it.getOrNull(col)?.takeIf { ch -> !ch.isWhitespace() } }
            .joinToString("")

        if (number.isNotEmpty()) {
            buffer.add(number.toLong())
        }
    }

    result += apply(action, buffer)
    println(result)
}

private fun apply(action: ACTION?, values: List<Long>): Long =
    when (action) {
        ACTION.SUM -> values.sum()
        ACTION.MULTIPLY -> values.fold(1L, Long::times)
        else -> 0
    }

private enum class ACTION { SUM, MULTIPLY }