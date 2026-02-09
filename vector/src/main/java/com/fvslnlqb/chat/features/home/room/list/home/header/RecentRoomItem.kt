/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.home.room.list.home.header

import android.view.HapticFeedbackConstants
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.epoxy.ClickListener
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyHolder
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyModel
import com.fvslnlqb.chat.core.epoxy.onClick
import com.fvslnlqb.chat.features.displayname.getBestName
import com.fvslnlqb.chat.features.home.AvatarRenderer
import com.fvslnlqb.chat.features.home.room.list.UnreadCounterBadgeView
import org.matrix.android.sdk.api.util.MatrixItem

@EpoxyModelClass
abstract class RecentRoomItem : VectorEpoxyModel<RecentRoomItem.Holder>(R.layout.item_recent_room) {

    @EpoxyAttribute lateinit var avatarRenderer: AvatarRenderer
    @EpoxyAttribute lateinit var matrixItem: MatrixItem
    @EpoxyAttribute var unreadNotificationCount: Int = 0
    @EpoxyAttribute var showHighlighted: Boolean = false

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var itemLongClickListener: View.OnLongClickListener? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var itemClickListener: ClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)

        holder.rootView.onClick(itemClickListener)
        holder.rootView.setOnLongClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            itemLongClickListener?.onLongClick(it) ?: false
        }

        avatarRenderer.render(matrixItem, holder.avatarImageView)
        holder.avatarImageView.contentDescription = matrixItem.getBestName()
        holder.unreadCounterBadgeView.render(UnreadCounterBadgeView.State.Count(unreadNotificationCount, showHighlighted))
        holder.title.text = matrixItem.getBestName()
    }

    override fun unbind(holder: Holder) {
        holder.rootView.setOnClickListener(null)
        holder.rootView.setOnLongClickListener(null)
        avatarRenderer.clear(holder.avatarImageView)
        super.unbind(holder)
    }

    class Holder : VectorEpoxyHolder() {
        val unreadCounterBadgeView by bind<UnreadCounterBadgeView>(R.id.recentUnreadCounterBadgeView)
        val avatarImageView by bind<ImageView>(R.id.recentImageView)
        val title by bind<TextView>(R.id.recentTitle)
        val rootView by bind<ViewGroup>(R.id.recentRoot)
    }
}
