/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.core.epoxy.profiles

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyModelClass
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyHolder
import com.fvslnlqb.chat.core.ui.views.PresenceStateImageView
import com.fvslnlqb.chat.core.ui.views.ShieldImageView

@EpoxyModelClass
abstract class ProfileMatrixItem : BaseProfileMatrixItem<ProfileMatrixItem.Holder>(R.layout.item_profile_matrix_item) {

    open class Holder : VectorEpoxyHolder() {
        val titleView by bind<TextView>(R.id.matrixItemTitle)
        val subtitleView by bind<TextView>(R.id.matrixItemSubtitle)
        val ignoredUserView by bind<ImageView>(R.id.matrixItemIgnored)
        val powerLabel by bind<TextView>(R.id.matrixItemPowerLevelLabel)
        val presenceImageView by bind<PresenceStateImageView>(R.id.matrixItemPresenceImageView)
        val avatarImageView by bind<ImageView>(R.id.matrixItemAvatar)
        val avatarDecorationImageView by bind<ShieldImageView>(R.id.matrixItemAvatarDecoration)
        val editableView by bind<View>(R.id.matrixItemEditable)
    }
}
