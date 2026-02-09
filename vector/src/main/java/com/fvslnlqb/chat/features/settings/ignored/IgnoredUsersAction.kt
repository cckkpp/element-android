/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.settings.ignored

import com.fvslnlqb.chat.core.platform.VectorViewModelAction

sealed class IgnoredUsersAction : VectorViewModelAction {
    data class UnIgnore(val userId: String) : IgnoredUsersAction()
}
