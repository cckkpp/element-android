/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.autocomplete.room

import com.airbnb.epoxy.TypedEpoxyController
import com.fvslnlqb.chat.features.autocomplete.AutocompleteClickListener
import com.fvslnlqb.chat.features.autocomplete.autocompleteMatrixItem
import com.fvslnlqb.chat.features.home.AvatarRenderer
import org.matrix.android.sdk.api.session.room.model.RoomSummary
import org.matrix.android.sdk.api.util.toMatrixItem
import javax.inject.Inject

class AutocompleteRoomController @Inject constructor(private val avatarRenderer: AvatarRenderer) : TypedEpoxyController<List<RoomSummary>>() {

    var listener: AutocompleteClickListener<RoomSummary>? = null

    override fun buildModels(data: List<RoomSummary>?) {
        if (data.isNullOrEmpty()) {
            return
        }
        val host = this
        data.forEach { roomSummary ->
            autocompleteMatrixItem {
                id(roomSummary.roomId)
                matrixItem(roomSummary.toMatrixItem())
                subName(roomSummary.canonicalAlias)
                avatarRenderer(host.avatarRenderer)
                clickListener { host.listener?.onItemClick(roomSummary) }
            }
        }
    }
}
