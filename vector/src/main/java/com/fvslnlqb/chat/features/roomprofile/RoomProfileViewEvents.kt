/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.roomprofile

import androidx.core.content.pm.ShortcutInfoCompat
import com.fvslnlqb.chat.core.platform.VectorViewEvents

/**
 * Transient events for RoomProfile.
 */
sealed class RoomProfileViewEvents : VectorViewEvents {
    data class Loading(val message: CharSequence? = null) : RoomProfileViewEvents()
    object DismissLoading : RoomProfileViewEvents()
    data class Failure(val throwable: Throwable) : RoomProfileViewEvents()
    data class Success(val message: CharSequence) : RoomProfileViewEvents()

    data class ShareRoomProfile(val permalink: String) : RoomProfileViewEvents()
    data class OnShortcutReady(val shortcutInfo: ShortcutInfoCompat) : RoomProfileViewEvents()
}
