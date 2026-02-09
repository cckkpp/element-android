/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.settings.notifications.usecase

import com.fvslnlqb.chat.core.pushers.PushersManager
import com.fvslnlqb.chat.core.pushers.UnregisterUnifiedPushUseCase
import javax.inject.Inject

class DisableNotificationsForCurrentSessionUseCase @Inject constructor(
        private val pushersManager: PushersManager,
        private val toggleNotificationsForCurrentSessionUseCase: ToggleNotificationsForCurrentSessionUseCase,
        private val unregisterUnifiedPushUseCase: UnregisterUnifiedPushUseCase,
) {

    suspend fun execute() {
        toggleNotificationsForCurrentSessionUseCase.execute(enabled = false)
        unregisterUnifiedPushUseCase.execute(pushersManager)
    }
}
