/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */
package com.fvslnlqb.chat.features.discovery

import android.widget.Button
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.epoxy.ClickListener
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyHolder
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyModel
import com.fvslnlqb.chat.core.epoxy.onClick

@EpoxyModelClass
abstract class SettingsContinueCancelItem : VectorEpoxyModel<SettingsContinueCancelItem.Holder>(R.layout.item_settings_continue_cancel) {

    @EpoxyAttribute
    var continueText: String? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var continueOnClick: ClickListener? = null

    @EpoxyAttribute
    var canContinue: Boolean = true

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var cancelOnClick: ClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)

        holder.cancelButton.onClick(cancelOnClick)

        continueText?.let { holder.continueButton.text = it }
        holder.continueButton.onClick(continueOnClick)
        holder.continueButton.isEnabled = canContinue
    }

    class Holder : VectorEpoxyHolder() {
        val cancelButton by bind<Button>(R.id.settings_item_cancel_button)
        val continueButton by bind<Button>(R.id.settings_item_continue_button)
    }
}
