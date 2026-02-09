/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.home

import com.fvslnlqb.chat.core.platform.VectorViewModelAction

sealed interface HomeActivityViewActions : VectorViewModelAction {
    object ViewStarted : HomeActivityViewActions
    object PushPromptHasBeenReviewed : HomeActivityViewActions
    data class RegisterPushDistributor(val distributor: String) : HomeActivityViewActions
}
