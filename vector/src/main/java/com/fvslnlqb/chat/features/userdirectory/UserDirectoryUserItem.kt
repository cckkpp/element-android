/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.userdirectory

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.amulyakhare.textdrawable.TextDrawable
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.epoxy.ClickListener
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyHolder
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyModel
import com.fvslnlqb.chat.core.epoxy.onClick
import com.fvslnlqb.chat.features.home.AvatarRenderer
import com.fvslnlqb.chat.features.themes.ThemeUtils
import org.matrix.android.sdk.api.util.MatrixItem

@EpoxyModelClass
abstract class UserDirectoryUserItem : VectorEpoxyModel<UserDirectoryUserItem.Holder>(R.layout.item_known_user) {

    @EpoxyAttribute lateinit var avatarRenderer: AvatarRenderer
    @EpoxyAttribute lateinit var matrixItem: MatrixItem
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash) var clickListener: ClickListener? = null
    @EpoxyAttribute var selected: Boolean = false

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.view.onClick(clickListener)
        // If name is empty, use userId as name and force it being centered
        if (matrixItem.displayName.isNullOrEmpty()) {
            holder.userIdView.visibility = View.GONE
            holder.nameView.text = matrixItem.id
        } else {
            holder.userIdView.visibility = View.VISIBLE
            holder.nameView.text = matrixItem.displayName
            holder.userIdView.text = matrixItem.id
        }
        renderSelection(holder, selected)
    }

    private fun renderSelection(holder: Holder, isSelected: Boolean) {
        if (isSelected) {
            holder.avatarCheckedImageView.visibility = View.VISIBLE
            val backgroundColor = ThemeUtils.getColor(holder.view.context, com.google.android.material.R.attr.colorPrimary)
            val backgroundDrawable = TextDrawable.builder().buildRound("", backgroundColor)
            holder.avatarImageView.setImageDrawable(backgroundDrawable)
        } else {
            holder.avatarCheckedImageView.visibility = View.GONE
            avatarRenderer.render(matrixItem, holder.avatarImageView)
        }
    }

    class Holder : VectorEpoxyHolder() {
        val userIdView by bind<TextView>(R.id.knownUserID)
        val nameView by bind<TextView>(R.id.knownUserName)
        val avatarImageView by bind<ImageView>(R.id.knownUserAvatar)
        val avatarCheckedImageView by bind<ImageView>(R.id.knownUserAvatarChecked)
    }
}
