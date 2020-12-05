package `4`

import java.io.File

fun main() {
    val passports = mutableListOf<Passport>()
    File("2020/input/4.txt").run {
        passports.addAll(
                readText()
                        .replace("\r", "")
                        .split("\n\n")
                        .map(Passport::parse)
        )
    }

    val partOne = passports.count { it.containsRequiredFields() }
    val partTwo = passports.count { it.containsRequiredFields() && it.hasValidFields() }

    println("Part one: %d".format(partOne)) // 216
    println("Part two: %d".format(partTwo)) // 150
}

class Passport(
        val birthYear: String?, // byr
        val issueYear: String?, // iyr
        val expirationYear: String?, // eyr
        val height: String?, // hgt
        val hairColor: String?, // hcl
        val eyeColor: String?, // ecl
        val passportId: String?, // pid
        val countryId: String?, // cid
) {
    companion object {
        fun parse(passport: String): Passport {
            val fields = parseFields(passport)

            return Passport(
                    birthYear = fields["byr"],
                    issueYear = fields["iyr"],
                    expirationYear = fields["eyr"],
                    height = fields["hgt"],
                    hairColor = fields["hcl"],
                    eyeColor = fields["ecl"],
                    passportId = fields["pid"],
                    countryId = fields["cid"],
            )
        }

        private fun parseFields(input: String): Map<String, String> {
            val rawFields = input.split(' ', '\n')

            return rawFields.map {
                val (field, value) = it.split(':')

                field to value
            }.toMap()
        }
    }

    fun containsRequiredFields(): Boolean = !listOf(
            birthYear, issueYear, expirationYear, height, hairColor, eyeColor, passportId
    ).contains(null)

    fun hasValidFields(): Boolean {
        if (birthYear!!.toInt() !in 1920..2002) return false
        if (issueYear!!.toInt() !in 2010..2020) return false
        if (expirationYear!!.toInt() !in 2020..2030) return false

        if (height!!.endsWith("cm")) {
            if (height.removeSuffix("cm").toInt() !in 150..193) return false
        } else if (height.endsWith("in")) {
            if (height.removeSuffix("in").toInt() !in 59..76) return false
        } else return false

        if (!Regex("""^#[0-9a-f]{6}$""").matches(hairColor!!)) return false
        if (!listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(eyeColor)) return false
        if (!Regex("""^\d{9}$""").matches(passportId!!)) return false

        return true
    }
}