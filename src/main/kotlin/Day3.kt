fun main() {
//    val testLines = listOf("987654321111111", "811111111111119", "234234234234278", "818181911112111")
    val lines = readInput("Day3Input")
    var sum = 0
    for (line in lines) {
        // iterate over line
        var currentMax = 0
        for (position in 0..line.length) {
            for (othersPosition in position + 1..line.length - 1) {
                val resultNumber = line[position].toString() + line[othersPosition]
                if (resultNumber.toInt() > currentMax) {
                    currentMax = resultNumber.toInt()
                }
            }
        }
        sum += currentMax
    }
    println(sum)
}