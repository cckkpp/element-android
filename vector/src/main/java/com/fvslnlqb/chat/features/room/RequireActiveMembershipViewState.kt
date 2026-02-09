/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.room

import com.airbnb.mvrx.MavericksState
import com.fvslnlqb.chat.features.home.room.detail.arguments.TimelineArgs
import com.fvslnlqb.chat.features.roommemberprofile.RoomMemberProfileArgs
import com.fvslnlqb.chat.features.roomprofile.RoomProfileArgs

data class RequireActiveMembershipViewState(
        val roomId: String? = null
) : MavericksState {

    constructor(args: TimelineArgs) : this(roomId = args.roomId)

    constructor(args: RoomProfileArgs) : this(roomId = args.roomId)

    constructor(args: RoomMemberProfileArgs) : this(roomId = args.roomId)
}
