/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.home

import com.fvslnlqb.chat.features.settings.VectorPreferences
import javax.inject.Inject

class IsNewLoginAlertShownUseCase @Inject constructor(
        private val vectorPreferences: VectorPreferences,
) {

    fun execute(deviceId: String?): Boolean {
        deviceId ?: return false

        return vectorPreferences.isNewLoginAlertShownForDevice(deviceId)
    }
}
