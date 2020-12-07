package `7`

import java.io.File

fun main() {
    val rules = mutableListOf<Rule>()
    File("2020/input/07.txt").forEachLine { rules.add(Rule.parse(it)) }

    println("sdfs")
}

fun buildTree(rules: List<Rule>): TreeBranch {
    // todo
}

class Rule(val target: String, val contents: List<Pair<Int, String>>) {
    companion object {
        fun parse(input: String): Rule {
            val (target, contents) = input.split(" bags contain ")

            val listContents = if (contents == "no other bags.")
                listOf()
            else
                contents.split(Regex(" bags?[, .]+")).filter { it.isNotBlank() }

            return Rule(
                    target = target,
                    contents = listContents.map {
                        val results = Regex("""^(\d+) (.*)$""").find(it)!!

                        Pair(
                                results.groupValues[1].toInt(),
                                results.groupValues[2]
                        )
                    }
            )
        }
    }
}

class TreeBranch(val color: String, val quantity: Int, val children: List<TreeBranch>)