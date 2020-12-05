package `5`

import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.min

fun main() {
    val boardingPasses = mutableListOf<BoardingPass>()
    File("2020/input/5.txt").forEachLine {
        boardingPasses.add(decode(it))
    }

    val partOne = boardingPasses.map(BoardingPass::id).maxOrNull()
    val partTwo = boardingPasses
            .sortedBy { it.id }
            .zipWithNext()
            .first { (it.first.id - it.second.id).absoluteValue == 2 }
            .run { min(first.id, second.id) + 1 }

    println("Part one: %d".format(partOne)) // 866
    println("Part two: %d".format(partTwo)) // 583
}

typealias BoardingPass = Pair<Int, Int>

val BoardingPass.id: Int
    get() = first * 8 + second

fun decode(boardingPass: String): BoardingPass {
    return BoardingPass(
            decodeAxis(boardingPass.substring(0..6), 'F'),
            decodeAxis(boardingPass.substring(7..9), 'L'),
    )
}

fun decodeAxis(axis: String, lower: Char): Int {
    var result = 0
    for (char in axis) {
        result = (result shl 1) + if (char == lower) 0 else 1
    }

    return result
}