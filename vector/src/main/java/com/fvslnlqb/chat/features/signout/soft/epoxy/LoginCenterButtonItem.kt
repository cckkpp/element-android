/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.signout.soft.epoxy

import android.widget.Button
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.epoxy.ClickListener
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyHolder
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyModel
import com.fvslnlqb.chat.core.epoxy.onClick
import com.fvslnlqb.chat.core.extensions.setTextOrHide

@EpoxyModelClass
abstract class LoginCenterButtonItem : VectorEpoxyModel<LoginCenterButtonItem.Holder>(R.layout.item_login_centered_button) {

    @EpoxyAttribute var text: String? = null
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash) var listener: ClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)

        holder.button.setTextOrHide(text)
        holder.button.onClick(listener)
    }

    class Holder : VectorEpoxyHolder() {
        val button by bind<Button>(R.id.itemLoginCenteredButton)
    }
}
