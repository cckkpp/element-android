/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.home.room.detail.timeline.item

import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.epoxy.ClickListener
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyHolder
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyModel
import com.fvslnlqb.chat.core.epoxy.onClick
import com.fvslnlqb.chat.core.ui.views.ReadReceiptsView
import com.fvslnlqb.chat.features.home.AvatarRenderer

@EpoxyModelClass
abstract class ReadReceiptsItem : VectorEpoxyModel<ReadReceiptsItem.Holder>(R.layout.item_timeline_event_read_receipts), ItemWithEvents {

    @EpoxyAttribute lateinit var eventId: String
    @EpoxyAttribute lateinit var readReceipts: List<ReadReceiptData>
    @EpoxyAttribute var shouldHideReadReceipts: Boolean = false
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash) lateinit var avatarRenderer: AvatarRenderer
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash) lateinit var clickListener: ClickListener

    override fun canAppendReadMarker(): Boolean = false

    override fun getEventIds(): List<String> = listOf(eventId)

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.readReceiptsView.onClick(clickListener)
        holder.readReceiptsView.render(readReceipts, avatarRenderer)
        holder.readReceiptsView.isVisible = !shouldHideReadReceipts
    }

    override fun unbind(holder: Holder) {
        holder.readReceiptsView.unbind(avatarRenderer)
        super.unbind(holder)
    }

    class Holder : VectorEpoxyHolder() {
        val readReceiptsView by bind<ReadReceiptsView>(R.id.readReceiptsView)
    }
}
