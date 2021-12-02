package dev.maxsiomin.luhn

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import dagger.hilt.android.HiltAndroidApp
import dev.maxsiomin.luhn.extensions.getDefaultSharedPrefs
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    private val keyTheme get() = getString(R.string.key_theme)

    init {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    override fun onCreate() {
        super.onCreate()
        setupTheme()
    }

    fun setupTheme(givenTheme: String? = null) {
        Timber.d("setupTheme called")

        val theme = givenTheme ?: getDefaultSharedPrefs().getString(keyTheme, THEME_FOLLOW_SYSTEM)!!

        Timber.d(theme)
        AppCompatDelegate.setDefaultNightMode(theme.toInt())
    }

    companion object {
        const val VERSION = "v${BuildConfig.VERSION_NAME}"
    }
}

// "-1"
private const val THEME_FOLLOW_SYSTEM = MODE_NIGHT_FOLLOW_SYSTEM.toString()

const val APK_LOCATION = "https://maxsiomin.dev/apps/card_checker/card_checker.apk"
