package dev.maxsiomin.luhn.util

import android.content.Context
import android.os.IBinder
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.MainThread
import androidx.annotation.StringRes
import dev.maxsiomin.luhn.annotations.ToastDuration
import dev.maxsiomin.luhn.extensions.getDefaultSharedPrefs

typealias SharedPrefs = android.content.SharedPreferences

/**
 * Use this class in order to get access to actions of user interface
 */
interface UiActions {

    val context: Context

    val sharedPrefs: SharedPrefs

    /** Shows string from resources as toast */
    @MainThread
    fun toast(@StringRes resId: Int, @ToastDuration length: Int)

    /** Show [message] as toast */
    @MainThread
    fun toast(message: String, @ToastDuration length: Int)

    /** Gets string from resources */
    fun getString(@StringRes resId: Int, vararg args: Any): String

    /** Gets color from resources */
    fun getColor(@ColorRes resId: Int): Int

    /** Hides keyboard */
    fun hideKeyboard(windowToken: IBinder)
}

class UiActionsImpl(override val context: Context) : UiActions {

    override val sharedPrefs = context.getDefaultSharedPrefs()

    private val inputMethodManager
        get() = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    override fun toast(resId: Int, length: Int) =
        toast(getString(resId), length)

    override fun toast(message: String, length: Int) =
        Toast.makeText(context, message, length).show()

    private data class ResIdWithArgs(
        @StringRes val resId: Int,
        val args: List<Any>,
    )

    /**
     * Contains strings that were already loaded from resources
     */
    private val strings = mutableMapOf<ResIdWithArgs, String>()

    /**
     * If string were already loaded returns it from [strings] else uses context to get the string from resources
     */
    override fun getString(resId: Int, vararg args: Any): String {
        val resIdWithArgs = ResIdWithArgs(resId, args.toList())

        if (strings[resIdWithArgs] == null)
            strings[resIdWithArgs] = context.getString(resId)

        return strings[resIdWithArgs]!!
    }

    private val colors = mutableMapOf<Int, Int>()

    override fun getColor(resId: Int): Int {
        if (colors[resId] == null)
            colors[resId] = context.resources.getColor(resId, null)

        return colors[resId]!!
    }

    override fun hideKeyboard(windowToken: IBinder) {
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}
