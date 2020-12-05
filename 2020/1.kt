import java.io.File

fun main() {
    val numbers = mutableListOf<Int>()
    File("2020/input/1.txt").forEachLine { numbers.add(it.toInt()) }

    val match = 2020

    val partOne = productNElementsIfSumMatches(numbers, 2, match)
    val partTwo = productNElementsIfSumMatches(numbers, 3, match)

    println("Part one: %d".format(partOne)) // 157059
    println("Part two: %d".format(partTwo)) // 165080960
}


fun productNElementsIfSumMatches(numbers: List<Int>, depth: Int, match: Int): Int {
    val components = makeComponents(numbers, depth)

    for (component in components) {
        if (component.sum() == match) return component.reduce { acc, num -> acc * num }
    }

    throw Exception("No result")
}

fun makeComponents(numbers: List<Int>, depth: Int): List<List<Int>> {
    // It would be nice to make this one a sequence, but I'm not sure how

    if (depth == 1) return numbers.map { listOf(it) };

    return numbers.map { number ->
        makeComponents(numbers, depth - 1).map { listOf(number, *it.toTypedArray()) }
    }.flatten()

}