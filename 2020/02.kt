import java.io.File

fun main() {
    val passwords = mutableListOf<Password>()
    File("2020/input/02.txt").forEachLine { passwords.add(Password.parse(it)) }

    val partOne = passwords.count { it.oldPolicyValid() }
    val partTwo = passwords.count { it.newPolicyValid() }

    println("Part one: %d".format(partOne)) // 640
    println("Part two: %d".format(partTwo)) // 472
}


class Password(val arg1: Int, val arg2: Int, val letter: Char, val password: String) {
    companion object {
        fun parse(line: String): Password {
            val result = Regex("""(\d+)-(\d+) (.): (.*)""").find(line)!!

            return Password(
                    arg1 = result.groupValues[1].toInt(),
                    arg2 = result.groupValues[2].toInt(),
                    letter = result.groupValues[3].first(),
                    password = result.groupValues[4],
            )
        }
    }

    /**
     * `arg1` is a minimum number of `char` occurrences in a `password`.
     * `arg2` is a maximum number of `char` occurrences in a `password`.
     */
    fun oldPolicyValid(): Boolean = password.count { it == letter } in arg1..arg2

    /**
     * `arg1` and `arg2` are two one-indexed positions of the `password` where **exactly one** of them
     * must contain the given letter.
     */
    fun newPolicyValid(): Boolean {
        val first = password[arg1 - 1]
        val second = password[arg2 - 1]

        return (first == letter) xor (second == letter)
    }
}