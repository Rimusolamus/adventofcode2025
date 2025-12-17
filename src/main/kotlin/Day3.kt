fun main() {
    val banksJoltage = 12
    val testLines = listOf("987654321111111", "811111111111119", "234234234234278", "818181911112111")
    val lines = readInput("Day3Input")
    var finalSum = 0L
    for (line in lines) {
        // iterate over line
        var numbersFound = 0
        var lastNumberPosition = 0
        var currentList = ""
        while (numbersFound < banksJoltage) {
            var currentMax = 0
            for (position in lastNumberPosition..(line.length - banksJoltage + numbersFound)) {
                if (line[position].toString().toInt() > currentMax) {
                    currentMax = line[position].toString().toInt()
                    lastNumberPosition = position + 1
                }
            }
            currentList += currentMax
            numbersFound++
        }
        // now we have numbers generated as lists
        finalSum += currentList.toLong()
    }
    println(finalSum)
}