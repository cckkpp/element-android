/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.test.fakes

import com.fvslnlqb.chat.core.pushers.FcmHelper
import com.fvslnlqb.chat.core.pushers.PushersManager
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify

class FakeFcmHelper {

    val instance = mockk<FcmHelper>()

    fun givenEnsureFcmTokenIsRetrieved(pushersManager: PushersManager) {
        justRun { instance.ensureFcmTokenIsRetrieved(pushersManager, any()) }
    }

    fun verifyEnsureFcmTokenIsRetrieved(
            pushersManager: PushersManager,
            registerPusher: Boolean,
            inverse: Boolean = false,
    ) {
        verify(inverse = inverse) { instance.ensureFcmTokenIsRetrieved(pushersManager, registerPusher) }
    }
}
