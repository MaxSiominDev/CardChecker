package dev.maxsiomin.luhn.extensions

/**
 * If string == null, returns ""
 */
fun String?.notNull(): String = this ?: ""
