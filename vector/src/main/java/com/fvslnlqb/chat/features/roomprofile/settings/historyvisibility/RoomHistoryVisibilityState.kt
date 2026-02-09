/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.roomprofile.settings.historyvisibility

import com.fvslnlqb.chat.core.ui.bottomsheet.BottomSheetGenericState
import org.matrix.android.sdk.api.session.room.model.RoomHistoryVisibility

data class RoomHistoryVisibilityState(
        val currentRoomHistoryVisibility: RoomHistoryVisibility = RoomHistoryVisibility.SHARED
) : BottomSheetGenericState() {

    constructor(args: RoomHistoryVisibilityBottomSheetArgs) : this(currentRoomHistoryVisibility = args.currentRoomHistoryVisibility)
}
