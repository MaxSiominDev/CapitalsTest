package dev.maxsiomin.capitals.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.annotation.StringRes
import dev.maxsiomin.capitals.annotations.ToastDuration
import dev.maxsiomin.capitals.extensions.getDefaultSharedPrefs

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
}

class UiActionsImpl(override val context: Context) : UiActions {

    override val sharedPrefs = context.getDefaultSharedPrefs()

    private val inputMethodManager
        get() = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    override fun toast(resId: Int, length: Int) =
        toast(getString(resId), length)

    override fun toast(message: String, length: Int) =
        Toast.makeText(context, message, length).show()

    override fun getString(resId: Int, vararg args: Any): String = context.getString(resId, args)
}

