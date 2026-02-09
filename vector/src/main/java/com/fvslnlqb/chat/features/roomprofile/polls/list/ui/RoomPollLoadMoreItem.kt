/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.roomprofile.polls.list.ui

import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.epoxy.ClickListener
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyHolder
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyModel
import com.fvslnlqb.chat.core.epoxy.onClick

@EpoxyModelClass
abstract class RoomPollLoadMoreItem : VectorEpoxyModel<RoomPollLoadMoreItem.Holder>(R.layout.item_poll_load_more) {

    @EpoxyAttribute
    var loadingMore: Boolean = false

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: ClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.loadMoreButton.isEnabled = loadingMore.not()
        holder.loadMoreButton.onClick(clickListener)
        holder.loadMoreProgressBar.isVisible = loadingMore
    }

    class Holder : VectorEpoxyHolder() {
        val loadMoreButton by bind<Button>(R.id.roomPollsLoadMore)
        val loadMoreProgressBar by bind<ProgressBar>(R.id.roomPollsLoadMoreProgress)
    }
}
