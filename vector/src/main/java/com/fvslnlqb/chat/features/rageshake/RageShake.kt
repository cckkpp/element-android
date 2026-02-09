/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.rageshake

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.core.content.getSystemService
import androidx.fragment.app.FragmentActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.seismic.ShakeDetector
import com.fvslnlqb.chat.core.di.ActiveSessionHolder
import com.fvslnlqb.chat.core.hardware.vibrate
import com.fvslnlqb.chat.features.navigation.Navigator
import com.fvslnlqb.chat.features.settings.VectorPreferences
import com.fvslnlqb.chat.features.settings.VectorSettingsActivity
import im.vector.lib.strings.CommonStrings
import javax.inject.Inject

class RageShake @Inject constructor(
        private val activity: FragmentActivity,
        private val bugReporter: BugReporter,
        private val navigator: Navigator,
        private val sessionHolder: ActiveSessionHolder,
        private val vectorPreferences: VectorPreferences
) : ShakeDetector.Listener {

    private var shakeDetector: ShakeDetector? = null

    private var dialogDisplayed = false

    var interceptor: (() -> Unit)? = null

    fun start() {
        val sensorManager = activity.getSystemService<SensorManager>() ?: return

        shakeDetector = ShakeDetector(this).apply {
            setSensitivity(vectorPreferences.getRageshakeSensitivity())
            start(sensorManager, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    fun stop() {
        shakeDetector?.stop()
    }

    fun setSensitivity(sensitivity: Int) {
        shakeDetector?.setSensitivity(sensitivity)
    }

    override fun hearShake() {
        val i = interceptor
        if (i != null) {
            vibrate(activity)
            i.invoke()
        } else {
            if (dialogDisplayed) {
                // Filtered!
                return
            }

            vibrate(activity)
            dialogDisplayed = true

            MaterialAlertDialogBuilder(activity)
                    .setMessage(CommonStrings.send_bug_report_alert_message)
                    .setPositiveButton(CommonStrings.yes) { _, _ -> openBugReportScreen() }
                    .also {
                        if (sessionHolder.hasActiveSession()) {
                            it.setNeutralButton(CommonStrings.settings) { _, _ -> openSettings() }
                        } else {
                            it.setNeutralButton(CommonStrings.action_disable) { _, _ -> disableRageShake() }
                        }
                    }
                    .setOnDismissListener { dialogDisplayed = false }
                    .setNegativeButton(CommonStrings.no, null)
                    .show()
        }
    }

    private fun openBugReportScreen() {
        bugReporter.openBugReportScreen(activity)
    }

    private fun openSettings() {
        navigator.openSettings(activity, VectorSettingsActivity.EXTRA_DIRECT_ACCESS_ADVANCED_SETTINGS)
    }

    private fun disableRageShake() {
        vectorPreferences.setRageshakeEnabled(false)
        stop()
    }

    companion object {
        /**
         * Check if the feature is available.
         */
        fun isAvailable(context: Context): Boolean {
            return context.getSystemService<SensorManager>()?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null
        }
    }
}
