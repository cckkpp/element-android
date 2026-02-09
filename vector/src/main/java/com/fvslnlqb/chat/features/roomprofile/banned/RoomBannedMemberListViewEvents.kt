/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.roomprofile.banned

import com.fvslnlqb.chat.core.platform.VectorViewEvents
import org.matrix.android.sdk.api.session.room.model.RoomMemberSummary

sealed class RoomBannedMemberListViewEvents : VectorViewEvents {
    data class ShowBannedInfo(val bannedByUserId: String, val banReason: String, val roomMemberSummary: RoomMemberSummary) : RoomBannedMemberListViewEvents()
    data class ToastError(val info: String) : RoomBannedMemberListViewEvents()
}
