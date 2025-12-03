class Range(val start: Long, val end: Long) {
    init {
        require(start <= end) { "Start must be less than or equal to end" }
    }

    fun filter(f: (Long) -> Boolean): Sequence<Long> = sequence {
        for (i in start..end) if (f(i)) yield(i)
    }
}

fun isValidId(id: Long): Boolean {
    val s = id.toString()
    val length = s.length
    return (1..length / 2).any { patternLength ->
        length % patternLength == 0 && (patternLength until length step patternLength).all { offset ->
            s.regionMatches(offset, s, 0, patternLength)
        }
    }
}


fun main() {
    ClassLoader.getSystemResource("Day2.txt").readText().splitToSequence(",").flatMap { range ->
        Range(
            range.substringBefore("-").toLong(), range.substringAfter("-").toLong()
        ).filter(::isValidId)

    }.sum().also(::println)
}
