/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.settings.devices.v2.details

import com.fvslnlqb.chat.core.platform.VectorViewModelAction

sealed class SessionDetailsAction : VectorViewModelAction {
    data class CopyToClipboard(val content: String) : SessionDetailsAction()
}
