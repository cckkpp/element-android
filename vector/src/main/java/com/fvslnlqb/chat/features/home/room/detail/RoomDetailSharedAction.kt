/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.home.room.detail

import com.fvslnlqb.chat.core.platform.VectorSharedAction

/**
 * Supported navigation actions for [RoomDetailActivity].
 */
sealed class RoomDetailSharedAction : VectorSharedAction {
    data class SwitchToRoom(val roomId: String) : RoomDetailSharedAction()
}
