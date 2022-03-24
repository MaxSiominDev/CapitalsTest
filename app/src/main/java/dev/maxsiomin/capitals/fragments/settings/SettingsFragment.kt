package dev.maxsiomin.capitals.fragments.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import dagger.hilt.android.AndroidEntryPoint
import dev.maxsiomin.capitals.APK_LOCATION
import dev.maxsiomin.capitals.App
import dev.maxsiomin.capitals.R
import dev.maxsiomin.capitals.extensions.addOnPreferenceChangeListener
import dev.maxsiomin.capitals.extensions.setOnClickListener
import dev.maxsiomin.capitals.util.BaseViewModel

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel by viewModels<BaseViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.settings)

        /*findPreference(R.string.key_theme).addOnPreferenceChangeListener {
            val app = requireActivity().application as App
            app.setupTheme(it as String)
        }*/

        findPreference(R.string.key_help_and_feedback).setOnClickListener {
            sendEmail()
        }

        findPreference(R.string.key_share_this_app).setOnClickListener {
            shareThisApp()
        }

        findPreference(R.string.key_app_version).summary = App.VERSION
    }

    private fun findPreference(key: Int) = findPreference<Preference>(viewModel.getString(key))!!

    /**
     * Open mail client and write letter to me
     */
    private fun sendEmail() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("mailto:max@maxsiomin.dev")))
        } catch (e: ActivityNotFoundException) {
            viewModel.toast(R.string.mail_client_not_found, Toast.LENGTH_SHORT)
        }
    }

    /**
     * Share link to .apk
     */
    private fun shareThisApp() {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, APK_LOCATION)
            type = "text/plain"
        }

        try {
            startActivity(Intent.createChooser(intent, null))
        } catch (e: ActivityNotFoundException) {
            viewModel.toast(R.string.smth_went_wrong, Toast.LENGTH_LONG)
        }
    }
}
