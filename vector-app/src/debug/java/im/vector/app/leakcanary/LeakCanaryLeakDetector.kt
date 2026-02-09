/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.leakcanary

import com.fvslnlqb.chat.core.debug.LeakDetector
import leakcanary.LeakCanary
import javax.inject.Inject

class LeakCanaryLeakDetector @Inject constructor() : LeakDetector {
    override fun enable(enable: Boolean) {
        LeakCanary.config = LeakCanary.config.copy(dumpHeap = enable)
    }
}
