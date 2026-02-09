/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.ui.robot

import androidx.test.espresso.matcher.ViewMatchers.withId
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.espresso.tools.waitUntilActivityVisible
import com.fvslnlqb.chat.espresso.tools.waitUntilViewVisible
import com.fvslnlqb.chat.features.analytics.ui.consent.AnalyticsOptInActivity
import im.vector.lib.strings.CommonStrings

class AnalyticsRobot {

    fun optIn() {
        answerOptIn(true)
    }

    fun optOut() {
        answerOptIn(false)
    }

    private fun answerOptIn(optIn: Boolean) {
        waitUntilActivityVisible<AnalyticsOptInActivity> {
            waitUntilViewVisible(withId(R.id.title))
        }
        assertDisplayed(R.id.title, CommonStrings.analytics_opt_in_title)
        if (optIn) {
            clickOn(R.id.submit)
        } else {
            clickOn(R.id.later)
        }
    }
}
