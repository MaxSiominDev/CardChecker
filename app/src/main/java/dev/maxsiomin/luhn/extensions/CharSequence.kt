package dev.maxsiomin.luhn.extensions

import android.text.SpannableStringBuilder

fun CharSequence.toEditable() = SpannableStringBuilder(this)

fun CharSequence?.isNotNullOrBlank() = !isNullOrBlank()
