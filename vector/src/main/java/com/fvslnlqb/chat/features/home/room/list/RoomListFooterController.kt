/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.home.room.list

import com.airbnb.epoxy.TypedEpoxyController
import com.fvslnlqb.chat.core.epoxy.helpFooterItem
import com.fvslnlqb.chat.core.resources.StringProvider
import com.fvslnlqb.chat.core.resources.UserPreferencesProvider
import com.fvslnlqb.chat.features.home.RoomListDisplayMode
import com.fvslnlqb.chat.features.home.room.filtered.FilteredRoomFooterItem
import com.fvslnlqb.chat.features.home.room.filtered.filteredRoomFooterItem
import im.vector.lib.strings.CommonStrings
import javax.inject.Inject

class RoomListFooterController @Inject constructor(
        private val stringProvider: StringProvider,
        private val userPreferencesProvider: UserPreferencesProvider
) : TypedEpoxyController<RoomListViewState>() {

    var listener: FilteredRoomFooterItem.Listener? = null

    override fun buildModels(data: RoomListViewState?) {
        val host = this
        when (data?.displayMode) {
            RoomListDisplayMode.FILTERED -> {
                filteredRoomFooterItem {
                    id("filter_footer")
                    listener(host.listener)
                    currentFilter(data.roomFilter)
                    inSpace(data.asyncSelectedSpace.invoke() != null)
                }
            }
            else -> {
                if (userPreferencesProvider.shouldShowLongClickOnRoomHelp()) {
                    helpFooterItem {
                        id("long_click_help")
                        text(host.stringProvider.getString(CommonStrings.help_long_click_on_room_for_more_options))
                    }
                }
            }
        }
    }
}
