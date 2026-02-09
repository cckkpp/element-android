/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */
package com.fvslnlqb.chat.features.settings.ignored

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.epoxy.ClickListener
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyHolder
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyModel
import com.fvslnlqb.chat.core.epoxy.onClick
import com.fvslnlqb.chat.core.extensions.setTextOrHide
import com.fvslnlqb.chat.features.home.AvatarRenderer
import org.matrix.android.sdk.api.util.MatrixItem

/**
 * A list item for User.
 */
@EpoxyModelClass
abstract class UserItem : VectorEpoxyModel<UserItem.Holder>(R.layout.item_user) {

    @EpoxyAttribute
    lateinit var avatarRenderer: AvatarRenderer

    @EpoxyAttribute
    lateinit var matrixItem: MatrixItem

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var itemClickAction: ClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.root.onClick(itemClickAction)

        avatarRenderer.render(matrixItem, holder.avatarImage)
        holder.userIdText.setTextOrHide(matrixItem.id)
        holder.displayNameText.setTextOrHide(matrixItem.displayName)
    }

    class Holder : VectorEpoxyHolder() {
        val root by bind<View>(R.id.itemUserRoot)
        val avatarImage by bind<ImageView>(R.id.itemUserAvatar)
        val userIdText by bind<TextView>(R.id.itemUserId)
        val displayNameText by bind<TextView>(R.id.itemUserName)
    }
}
