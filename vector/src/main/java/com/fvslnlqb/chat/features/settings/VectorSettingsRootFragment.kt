/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.settings

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.preference.VectorPreference
import com.fvslnlqb.chat.features.analytics.plan.MobileScreen
import im.vector.lib.strings.CommonStrings

@AndroidEntryPoint
class VectorSettingsRootFragment :
        VectorSettingsBaseFragment() {

    override var titleRes: Int = CommonStrings.title_activity_settings
    override val preferenceXmlRes = R.xml.vector_settings_root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsScreenName = MobileScreen.ScreenName.Settings
    }

    override fun bindPref() {
        tintIcons()
    }

    private fun tintIcons() {
        for (i in 0 until preferenceScreen.preferenceCount) {
            (preferenceScreen.getPreference(i) as? VectorPreference)?.let { it.tintIcon = true }
        }
    }
}
