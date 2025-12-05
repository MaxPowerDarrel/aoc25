package day4

enum class Direction(val dRow: Int, val dCol: Int) {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    UPLEFT(-1, -1),
    UPRIGHT(-1, 1),
    DOWNLEFT(1, -1),
    DOWNRIGHT(1, 1);
}

class Warehouse(val inventory: List<CharArray>) {
    private val roll = '@'
    private val empty = '.'
    private val markedForRemoval: MutableList<Pair<Int, Int>> = mutableListOf()

    fun countReachable(): Int = inventory.indices.sumOf { row ->
        inventory[row].indices.count { col -> isReachable(row, col) }
    }

    fun isReachable(row: Int, col: Int): Boolean {
        val reachable =  inventory[row][col] == roll && countAdjacentRolls(row, col) < 4
        if (reachable) markedForRemoval.add(Pair(row, col))
        return reachable
    }

    fun countAdjacentRolls(row: Int, col: Int): Int =
        Direction.entries.count { dir ->
            val nextRow = row + dir.dRow
            val nextCol = col + dir.dCol
            inbounds(nextRow, nextCol) && inventory[nextRow][nextCol] == roll
        }

    fun removeMarkedForRemoval() {
        markedForRemoval.forEach { (row, col) -> inventory[row][col] = empty }
    }

    fun inbounds(row: Int, col: Int): Boolean =
        inventory.isNotEmpty() &&
        row in inventory.indices && col in inventory[0].indices
}

fun main() {
    val warehouse = Warehouse(ClassLoader.getSystemResource("Day4.txt").readText().lines().map { it.toCharArray() })
    var count = 0
    while (true) {
        val reachable = warehouse.countReachable()
        count += reachable
        warehouse.removeMarkedForRemoval()
        if (reachable == 0) break
    }
    println(count)
}