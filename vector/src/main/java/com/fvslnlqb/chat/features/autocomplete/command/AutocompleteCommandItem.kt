/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.autocomplete.command

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.epoxy.ClickListener
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyHolder
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyModel
import com.fvslnlqb.chat.core.epoxy.onClick

@EpoxyModelClass
abstract class AutocompleteCommandItem : VectorEpoxyModel<AutocompleteCommandItem.Holder>(R.layout.item_autocomplete_command) {

    @EpoxyAttribute
    var name: String? = null

    @EpoxyAttribute
    var parameters: String? = null

    @EpoxyAttribute
    var description: String? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: ClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.view.onClick(clickListener)
        holder.nameView.text = name
        holder.parametersView.text = parameters
        holder.descriptionView.text = description
    }

    class Holder : VectorEpoxyHolder() {
        val nameView by bind<TextView>(R.id.commandName)
        val parametersView by bind<TextView>(R.id.commandParameter)
        val descriptionView by bind<TextView>(R.id.commandDescription)
    }
}
