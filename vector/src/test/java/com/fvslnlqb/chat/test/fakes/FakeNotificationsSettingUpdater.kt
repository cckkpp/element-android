/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.test.fakes

import com.fvslnlqb.chat.core.notification.NotificationsSettingUpdater
import io.mockk.justRun
import io.mockk.mockk
import org.matrix.android.sdk.api.session.Session

class FakeNotificationsSettingUpdater {

    val instance = mockk<NotificationsSettingUpdater>()

    fun givenOnSessionStarted(session: Session) {
        justRun { instance.onSessionStarted(session) }
    }
}
