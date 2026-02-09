/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.home.room.list

import com.fvslnlqb.chat.features.home.RoomListDisplayMode
import com.fvslnlqb.chat.features.settings.FontScalePreferences
import javax.inject.Inject

class RoomSummaryPagedControllerFactory @Inject constructor(
        private val roomSummaryItemFactory: RoomSummaryItemFactory,
        private val fontScalePreferences: FontScalePreferences
) {

    fun createRoomSummaryPagedController(displayMode: RoomListDisplayMode): RoomSummaryPagedController {
        return RoomSummaryPagedController(roomSummaryItemFactory, displayMode, fontScalePreferences)
    }

    fun createRoomSummaryListController(displayMode: RoomListDisplayMode): RoomSummaryListController {
        return RoomSummaryListController(roomSummaryItemFactory, displayMode, fontScalePreferences)
    }

    fun createSuggestedRoomListController(): SuggestedRoomListController {
        return SuggestedRoomListController(roomSummaryItemFactory)
    }
}
