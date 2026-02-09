/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.contactsbook

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.epoxy.ClickListener
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyHolder
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyModel
import com.fvslnlqb.chat.core.epoxy.onClick
import com.fvslnlqb.chat.core.extensions.setTextOrHide

@EpoxyModelClass
abstract class ContactDetailItem : VectorEpoxyModel<ContactDetailItem.Holder>(R.layout.item_contact_detail) {

    @EpoxyAttribute lateinit var threePid: String
    @EpoxyAttribute var matrixId: String? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: ClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.view.onClick(clickListener)
        holder.nameView.text = threePid
        holder.matrixIdView.setTextOrHide(matrixId)
    }

    class Holder : VectorEpoxyHolder() {
        val nameView by bind<TextView>(R.id.contactDetailName)
        val matrixIdView by bind<TextView>(R.id.contactDetailMatrixId)
    }
}
