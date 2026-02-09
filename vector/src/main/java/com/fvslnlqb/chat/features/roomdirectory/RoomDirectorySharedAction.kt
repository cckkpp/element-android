/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.roomdirectory

import com.fvslnlqb.chat.core.platform.VectorSharedAction

/**
 * Supported navigation actions for [RoomDirectoryActivity].
 */
sealed class RoomDirectorySharedAction : VectorSharedAction {
    object Back : RoomDirectorySharedAction()
    object CreateRoom : RoomDirectorySharedAction()
    object Close : RoomDirectorySharedAction()
    data class CreateRoomSuccess(val createdRoomId: String) : RoomDirectorySharedAction()
    object ChangeProtocol : RoomDirectorySharedAction()
}
