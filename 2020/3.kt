import java.io.File

fun main() {
    val map = File("2020/input/3.txt").run { Map.parse(readText()) }

    val partOne = treesEncountered(map, Coords(3, 1))

    var partTwo = 1L
    for (step in listOf(
            Coords(1, 1),
            Coords(3, 1),
            Coords(5, 1),
            Coords(7, 1),
            Coords(1, 2),
    )) {
        partTwo *= treesEncountered(map, step)
    }

    println("Part one: %d".format(partOne)) // 211
    println("Part two: %d".format(partTwo)) // 3584591857
}

fun treesEncountered(map: Map, step: Coords): Int {
    var trees = 0
    var currentPos = Coords(0, 0)
    while (true) {
        if (currentPos.second >= map.height) break

        if (map.isTree(currentPos)) trees++

        currentPos += step
    }

    return trees
}

typealias Coords = Pair<Int, Int>

operator fun Coords.plus(other: Coords) = Coords(first + other.first, second + other.second)

class Map(val width: Int, val height: Int, private val trees: List<Coords>) {
    companion object {
        fun parse(map: String): Map {
            val tree = '#'

            val trees = mutableListOf<Coords>()
            for ((y, row) in map.lines().withIndex()) {
                for ((x, char) in row.toCharArray().withIndex()) {
                    if (char == tree) trees.add(Coords(x, y))
                }
            }

            return Map(
                    width = map.lines().first().count(),
                    height = map.lines().count(),
                    trees = trees.toList(),
            )
        }
    }

    /**
     * Map repeats itself in the x axis.
     */
    private fun Coords.normalize() = Coords(first % width, second)

    fun isTree(coords: Coords): Boolean = trees.contains(coords.normalize())
}