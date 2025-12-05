package day5

data class IdRange(val start: Long, val end: Long) : Comparable<IdRange> {
    val length: Long get() = end - start + 1
    override fun compareTo(other: IdRange): Int = start.compareTo(other.start)
}

private fun List<IdRange>.merge(): List<IdRange> {
    if (isEmpty()) return emptyList()

    return sorted().fold(mutableListOf()) { acc, current ->
        val last = acc.lastOrNull()
        // Merge if overlapping or immediately adjacent (e.g., [1, 5] and [6, 10] becomes [1, 10])
        if (last != null && current.start <= last.end + 1) {
            acc[acc.lastIndex] = IdRange(last.start, maxOf(last.end, current.end))
        } else {
            acc.add(current)
        }
        acc
    }
}

private fun String.toIdRange(): IdRange {
    val (s, e) = split('-').map { it.toLong() }
    return IdRange(minOf(s, e), maxOf(s, e))
}

fun main() {
    val mergedRanges = ClassLoader.getSystemResource("Day5.txt").readText().lines()
        .asSequence()
        .filter { it.isNotBlank() && '-' in it }
        .map { it.toIdRange() }
        .toList() // Materialize for sorting
        .merge()

    println(mergedRanges.sumOf { it.length })
}