/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.roomprofile.banned

import com.fvslnlqb.chat.core.platform.VectorViewModelAction
import org.matrix.android.sdk.api.session.room.model.RoomMemberSummary

sealed class RoomBannedMemberListAction : VectorViewModelAction {
    data class QueryInfo(val roomMemberSummary: RoomMemberSummary) : RoomBannedMemberListAction()
    data class UnBanUser(val roomMemberSummary: RoomMemberSummary) : RoomBannedMemberListAction()
    data class Filter(val filter: String) : RoomBannedMemberListAction()
}
