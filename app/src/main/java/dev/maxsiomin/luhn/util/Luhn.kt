package dev.maxsiomin.luhn.util

object Luhn {
    fun validate(string: String): Boolean = string.reversed()
        .map(Character::getNumericValue)
        .mapIndexed { index, digit ->
            when {
                index % 2 == 0 -> digit
                digit < 5 -> digit * 2
                else -> digit * 2 - 9
            }
        }.sum() % 10 == 0
}
