import java.io.File

fun main() {
    val map = File("2020/input/3.txt").run { Map.parse(readText()) }

    var trees = 0
    var currentPos = Coords(0, 0)
    while (true) {
        if (currentPos.second == map.height) break

        if (map.isTree(currentPos)) trees++

        currentPos += Coords(3, 1)
    }

    println("Part one: %d".format(trees)) // 211
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