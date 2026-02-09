/*
 * Copyright 2018-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */
package com.fvslnlqb.chat.features.settings.troubleshoot

import com.fvslnlqb.chat.core.resources.StringProvider
import com.fvslnlqb.chat.features.settings.VectorPreferences
import im.vector.lib.strings.CommonStrings
import javax.inject.Inject

/**
 * Checks if notifications are enable in the system settings for this app.
 */
class TestDeviceSettings @Inject constructor(
        private val vectorPreferences: VectorPreferences,
        private val stringProvider: StringProvider
) :
        TroubleshootTest(CommonStrings.settings_troubleshoot_test_device_settings_title) {

    override fun perform(testParameters: TestParameters) {
        if (vectorPreferences.areNotificationEnabledForDevice()) {
            description = stringProvider.getString(CommonStrings.settings_troubleshoot_test_device_settings_success)
            quickFix = null
            status = TestStatus.SUCCESS
        } else {
            quickFix = object : TroubleshootQuickFix(CommonStrings.settings_troubleshoot_test_device_settings_quickfix) {
                override fun doFix() {
                    vectorPreferences.setNotificationEnabledForDevice(true)
                    manager?.retry(testParameters)
                }
            }
            description = stringProvider.getString(CommonStrings.settings_troubleshoot_test_device_settings_failed)
            status = TestStatus.FAILED
        }
    }
}
