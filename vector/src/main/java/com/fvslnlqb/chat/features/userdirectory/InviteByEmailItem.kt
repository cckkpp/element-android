/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.userdirectory

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.epoxy.ClickListener
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyHolder
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyModel

@EpoxyModelClass
abstract class InviteByEmailItem : VectorEpoxyModel<InviteByEmailItem.Holder>(R.layout.item_invite_by_mail) {

    @EpoxyAttribute lateinit var foundItem: ThreePidUser
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash) var clickListener: ClickListener? = null
    @EpoxyAttribute var selected: Boolean = false

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.itemTitleText.text = foundItem.email
        holder.checkedImageView.isVisible = false
        holder.avatarImageView.isVisible = true
        holder.view.setOnClickListener(clickListener)
        if (selected) {
            holder.checkedImageView.isVisible = true
            holder.avatarImageView.isVisible = false
        } else {
            holder.checkedImageView.isVisible = false
            holder.avatarImageView.isVisible = true
        }
    }

    class Holder : VectorEpoxyHolder() {
        val itemTitleText by bind<TextView>(R.id.itemTitle)
        val avatarImageView by bind<ImageView>(R.id.itemAvatar)
        val checkedImageView by bind<ImageView>(R.id.itemAvatarChecked)
    }
}
