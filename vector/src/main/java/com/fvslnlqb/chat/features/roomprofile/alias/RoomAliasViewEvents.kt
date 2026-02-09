/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.roomprofile.alias

import com.fvslnlqb.chat.core.platform.VectorViewEvents

/**
 * Transient events for room settings screen.
 */
sealed class RoomAliasViewEvents : VectorViewEvents {
    data class Failure(val throwable: Throwable) : RoomAliasViewEvents()
    object Success : RoomAliasViewEvents()
}
