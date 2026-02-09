/*
 * Copyright 2018-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */
package com.fvslnlqb.chat.fdroid.features.settings.troubleshoot

import androidx.fragment.app.FragmentActivity
import com.fvslnlqb.chat.core.resources.StringProvider
import com.fvslnlqb.chat.core.utils.isIgnoringBatteryOptimizations
import com.fvslnlqb.chat.core.utils.requestDisablingBatteryOptimization
import com.fvslnlqb.chat.features.settings.troubleshoot.TroubleshootTest
import im.vector.lib.strings.CommonStrings
import javax.inject.Inject

class TestBatteryOptimization @Inject constructor(
        private val context: FragmentActivity,
        private val stringProvider: StringProvider
) : TroubleshootTest(CommonStrings.settings_troubleshoot_test_battery_title) {

    override fun perform(testParameters: TestParameters) {
        if (context.isIgnoringBatteryOptimizations()) {
            description = stringProvider.getString(CommonStrings.settings_troubleshoot_test_battery_success)
            status = TestStatus.SUCCESS
            quickFix = null
        } else {
            description = stringProvider.getString(CommonStrings.settings_troubleshoot_test_battery_failed)
            quickFix = object : TroubleshootQuickFix(CommonStrings.settings_troubleshoot_test_battery_quickfix) {
                override fun doFix() {
                    requestDisablingBatteryOptimization(context, testParameters.activityResultLauncher)
                }
            }
            status = TestStatus.FAILED
        }
    }
}
