package dev.maxsiomin.luhn.activity

import android.app.Dialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import dagger.hilt.android.AndroidEntryPoint
import dev.maxsiomin.luhn.APK_LOCATION
import dev.maxsiomin.luhn.BuildConfig
import dev.maxsiomin.luhn.R
import dev.maxsiomin.luhn.extensions.openInBrowser
import dev.maxsiomin.luhn.util.SharedPrefsConfig.DATE_UPDATE_SUGGESTED
import java.time.LocalDate

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Updater {

    private val mViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        mViewModel.checkForUpdates { latestVersionName ->
            suggestUpdating(latestVersionName)
        }
    }

    private fun suggestUpdating(latestVersionName: String) {
        // Save when update was suggested last time
        mViewModel.sharedPrefs.edit().apply {
            putString(DATE_UPDATE_SUGGESTED, LocalDate.now().toString())
        }.apply()

        UpdateDialog.newInstance(latestVersionName).show(supportFragmentManager)
    }

    /**
     * Opens direct uri to .apk in browser. .apk should be automatically downloaded
     */
    override fun update() {
        openInBrowser(APK_LOCATION)
    }

    class UpdateDialog : DialogFragment() {

        private val updater get() = activity as Updater

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

            val currentVersionName = BuildConfig.VERSION_NAME
            val latestVersionName = requireArguments().getString(LATEST_VERSION_NAME)

            val dialog = DialogBuilder(requireContext())
                .setMessage(getString(R.string.update_app, currentVersionName, latestVersionName))
                .setNegativeButton(R.string.no_thanks) { _, _ -> }
                .setPositiveButton(R.string.update) { _, _ ->
                    updater.update()
                }
                .create()

            dialog.setCanceledOnTouchOutside(false)

            return dialog
        }

        fun show(manager: FragmentManager) {
            show(manager, TAG)
        }

        companion object {

            const val TAG = "UpdateDialog"

            /** Key for args */
            private const val LATEST_VERSION_NAME = "latestVersion"

            /**
             * Puts [latestVersionName] to args
             */
            @JvmStatic
            fun newInstance(latestVersionName: String) = UpdateDialog().apply {
                arguments = bundleOf(LATEST_VERSION_NAME to latestVersionName)
            }
        }
    }
}

typealias DialogBuilder = AlertDialog.Builder

interface Updater {

    /**
     * Called when user submits they'd like to update
     */
    fun update()
}
