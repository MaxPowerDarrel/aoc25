sealed class Direction(val delta: Int)

object LEFT : Direction(-1)
object RIGHT : Direction(1)

data class Instruction(val direction: Direction, val steps: Int)

class SecurityDevice(
    private val maxPosition: Int = 99, initialPosition: Int = 50
) {
    var passcode = 0
        private set

    private var currentPosition = initialPosition

    fun turn(instruction: Instruction) = repeat(instruction.steps) {
        if (currentPosition == 0) passcode++
        currentPosition = (currentPosition + instruction.direction.delta).mod(maxPosition + 1)
    }
}

private fun String.toInstruction(): Instruction = when (this.firstOrNull()) {
    'R' -> Instruction(RIGHT, substring(1).toInt())
    'L' -> Instruction(LEFT, substring(1).toInt())
    else -> throw IllegalArgumentException("Invalid direction: $this")
}

fun main() {
    val device = SecurityDevice()

    ClassLoader.getSystemResource("Day1.txt").readText().lineSequence().filter { it.isNotBlank() }
        .map { it.toInstruction() }.forEach { device.turn(it) }

    println("Passcode: ${device.passcode}")
}
