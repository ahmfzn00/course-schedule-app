package com.dicoding.courseschedule.ui.setting

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationManagerCompat
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NOTIFICATION_ID

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        val themePreference = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        themePreference?.setOnPreferenceChangeListener { preference, newValue ->
            setTheme(newValue.toString())
            true
        }

        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        val reminderPreference = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        reminderPreference?.setOnPreferenceChangeListener { preference, newValue ->
            reminder(requireContext(), newValue.toString())
            true
        }

    }

    private fun reminder(ctx: Context, value: String) {
        if (value == "true") {
            DailyReminder().setDailyReminder(ctx)
        } else {
            DailyReminder().cancelAlarm(ctx)
        }
    }

    private fun setTheme(newTheme: String) {
        if (newTheme.toString() == "auto") {
            updateTheme(0)
        } else if (newTheme.toString() == "on") {
            updateTheme(2)
        } else {
            updateTheme(1)
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}