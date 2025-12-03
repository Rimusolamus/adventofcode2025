fun main() {
    val lines = readInput("Day2Input")
    val ranges = lines[0].split(",")
//    val testLines =
//        listOf("11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124")
//    val ranges = testLines[0].split(",")
    var allResults = mutableListOf<Long>()
    for (range in ranges) {
        range.split("-").let {
            val start = it[0].toLong()
            val end = it[1].toLong()
            for (i in start..end) {
                val str = i.toString()
                var isInvalid = false

                // Check all possible pattern lengths from 1 to length/2
                for (patternLength in 1..str.length / 2) {
                    if (str.length % patternLength == 0) {
                        val chunks = str.chunked(patternLength)
                        if (chunks.distinct().size == 1) {
                            isInvalid = true
                            break
                        }
                    }
                }

                if (isInvalid) {
                    println(i)
                    allResults.add(i)
                }
            }
        }
    }
    println("Sum: ${allResults.distinct().sum()}")
}