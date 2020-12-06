package `6`

import java.io.File

fun main() {
    val groups = mutableListOf<Group>()
    File("2020/input/06.txt").apply {
        readText()
                .replace("\r", "")
                .split("\n\n")
                .forEach { groups.add(Group.parse(it)) }
    }

    val partOne = groups.map { it.allLetters().count() }.sum()
    val partTwo = groups.map { it.everyoneLetters().count() }.sum()

    println("Part one: %d".format(partOne)) // 6878
    println("Part two: %d".format(partTwo)) // 3464
}

class Group(val people: List<List<Char>>) {
    companion object {
        fun parse(input: String): Group {
            return Group(
                    people = input.lines().map { it.toList() }
            )
        }
    }

    fun allLetters(): List<Char> = people.flatten().distinct()

    fun everyoneLetters(): List<Char> {
        val chars = mutableListOf<Char>()
        chars@ for (char in people.flatten()) {
            for (person in people) {
                if (!person.contains(char)) continue@chars
            }

            chars.add(char)
        }

        return chars.distinct()
    }
}