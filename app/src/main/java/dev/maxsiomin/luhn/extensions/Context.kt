package dev.maxsiomin.luhn.extensions

import android.content.Context
import androidx.preference.PreferenceManager
import dev.maxsiomin.luhn.util.SharedPrefs

fun Context.getDefaultSharedPrefs(): SharedPrefs =
    PreferenceManager.getDefaultSharedPreferences(this)
