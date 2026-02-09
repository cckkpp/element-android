/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.auth

import com.fvslnlqb.chat.core.platform.VectorViewModelAction

sealed class ReAuthActions : VectorViewModelAction {
    object StartSSOFallback : ReAuthActions()
    object FallBackPageLoaded : ReAuthActions()
    object FallBackPageClosed : ReAuthActions()
    data class ReAuthWithPass(val password: String) : ReAuthActions()
}
