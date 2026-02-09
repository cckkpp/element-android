/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.home.room.list.home

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyHolder
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyModel
import com.fvslnlqb.chat.core.platform.StateView

@EpoxyModelClass
abstract class RoomListEmptyItem : VectorEpoxyModel<RoomListEmptyItem.Holder>(R.layout.item_state_view) {

    @EpoxyAttribute
    lateinit var emptyData: StateView.State.Empty

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.stateView.state = emptyData
    }

    class Holder : VectorEpoxyHolder() {
        val stateView by bind<StateView>(R.id.stateView)
    }
}
