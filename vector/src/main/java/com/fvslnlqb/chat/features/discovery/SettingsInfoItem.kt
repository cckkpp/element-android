/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */
package com.fvslnlqb.chat.features.discovery

import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.epoxy.ClickListener
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyHolder
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyModel
import com.fvslnlqb.chat.core.epoxy.onClick
import com.fvslnlqb.chat.core.extensions.setTextOrHide

@EpoxyModelClass
abstract class SettingsInfoItem : VectorEpoxyModel<SettingsInfoItem.Holder>(R.layout.item_settings_helper_info) {

    @EpoxyAttribute
    var helperText: String? = null

    @EpoxyAttribute
    @StringRes
    var helperTextResId: Int? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var itemClickListener: ClickListener? = null

    @EpoxyAttribute
    @DrawableRes
    var compoundDrawable: Int = R.drawable.vector_warning_red

    @EpoxyAttribute
    var showCompoundDrawable: Boolean = false

    override fun bind(holder: Holder) {
        super.bind(holder)

        if (helperTextResId != null) {
            holder.text.setText(helperTextResId!!)
        } else {
            holder.text.setTextOrHide(helperText)
        }

        holder.view.onClick(itemClickListener)

        if (showCompoundDrawable) {
            holder.text.setCompoundDrawablesWithIntrinsicBounds(compoundDrawable, 0, 0, 0)
        } else {
            holder.text.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }
    }

    class Holder : VectorEpoxyHolder() {
        val text by bind<TextView>(R.id.settings_helper_text)
    }
}
