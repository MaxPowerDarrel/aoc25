package day3

/**
 * Finds the index of the largest digit within the specified range [startIndex, endIndex).
 * Returns -1 if no digit is found.
 */
fun CharSequence.findIndexOfLargestDigit(startIndex: Int, endIndex: Int): Int {
    var maxDigit = -1
    var maxIndex = -1

    for (i in startIndex until endIndex) {
        val char = this[i]
        if (char.isDigit()) {
            val digit = char.digitToInt()
            if (digit > maxDigit) {
                maxDigit = digit
                maxIndex = i
                // Optimization: 9 is the max possible digit, no need to search further
                if (maxDigit == 9) break
            }
        }
    }
    return maxIndex
}

fun findMaxJoltage(bank: String, totalSteps: Int): Long {
    var currentJoltage = 0L
    var currentOffset = 0

    for (stepsLeft in totalSteps downTo 1) {

        val searchEndIndex = bank.length - stepsLeft + 1
        if (currentOffset >= searchEndIndex) break
        val maxIndex = bank.findIndexOfLargestDigit(currentOffset, searchEndIndex)
        if (maxIndex == -1) break

        currentJoltage = (currentJoltage * 10) + bank[maxIndex].digitToInt()
        currentOffset = maxIndex + 1
    }
    return currentJoltage
}

fun main() {
    ClassLoader.getSystemResource("Day3.txt")
        .readText()
        .lineSequence()
        .filter { it.isNotBlank() }
        .sumOf { bank -> findMaxJoltage(bank, 12) }
        .also(::println)
}