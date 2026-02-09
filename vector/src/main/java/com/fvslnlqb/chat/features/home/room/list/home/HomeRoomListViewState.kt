/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.home.room.list.home

import com.airbnb.mvrx.MavericksState
import com.fvslnlqb.chat.core.platform.StateView
import com.fvslnlqb.chat.features.home.room.list.home.header.RoomsHeadersData

data class HomeRoomListViewState(
        val emptyState: StateView.State.Empty? = null,
        val headersData: RoomsHeadersData = RoomsHeadersData(),
) : MavericksState
