/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.home.room.detail.timeline.style

import com.fvslnlqb.chat.features.settings.VectorPreferences
import javax.inject.Inject

class TimelineLayoutSettingsProvider @Inject constructor(private val vectorPreferences: VectorPreferences) {

    fun getLayoutSettings(): TimelineLayoutSettings {
        return if (vectorPreferences.useMessageBubblesLayout()) {
            TimelineLayoutSettings.BUBBLE
        } else {
            TimelineLayoutSettings.MODERN
        }
    }
}
