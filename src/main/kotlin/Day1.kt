enum class Direction { LEFT, RIGHT }

data class Instruction(val direction: Direction, val steps: Int)

class SecurityDevice(
    private val maxPosition: Int = 99,
    initialPosition: Int = 50
) {
    var passcode = 0
        private set

    private var currentPosition = initialPosition

    fun turn(instruction: Instruction) {
        val delta = when (instruction.direction) {
            Direction.LEFT -> -1
            Direction.RIGHT -> 1
        }

        repeat(instruction.steps) {
            if (currentPosition == 0) passcode++

            currentPosition = (currentPosition + delta).mod(maxPosition + 1)
        }
    }
}

private fun String.toInstruction(): Instruction = when (this.firstOrNull()) {
    'R' -> Instruction(Direction.RIGHT, substring(1).toInt())
    'L' -> Instruction(Direction.LEFT, substring(1).toInt())
    else -> throw IllegalArgumentException("Invalid direction: $this")
}

fun main() {
    val device = SecurityDevice()

    ClassLoader.getSystemResource("Day1.txt")
        .readText()
        .lineSequence()
        .filter { it.isNotBlank() }
        .map { it.toInstruction() }
        .forEach { device.turn(it) }

    println("Passcode: ${device.passcode}")
}
