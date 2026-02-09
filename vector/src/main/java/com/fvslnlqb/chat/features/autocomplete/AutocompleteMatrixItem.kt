/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.autocomplete

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
import com.fvslnlqb.chat.features.displayname.getBestName
import com.fvslnlqb.chat.features.home.AvatarRenderer
import org.matrix.android.sdk.api.util.MatrixItem

@EpoxyModelClass
abstract class AutocompleteMatrixItem : VectorEpoxyModel<AutocompleteMatrixItem.Holder>(R.layout.item_autocomplete_matrix_item) {

    @EpoxyAttribute lateinit var avatarRenderer: AvatarRenderer
    @EpoxyAttribute lateinit var matrixItem: MatrixItem
    @EpoxyAttribute var subName: String? = null
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash) var clickListener: ClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.view.onClick(clickListener)
        holder.nameView.text = matrixItem.getBestName()
        holder.subNameView.setTextOrHide(subName)
        avatarRenderer.render(matrixItem, holder.avatarImageView)
    }

    class Holder : VectorEpoxyHolder() {
        val nameView by bind<TextView>(R.id.matrixItemAutocompleteName)
        val subNameView by bind<TextView>(R.id.matrixItemAutocompleteSubname)
        val avatarImageView by bind<ImageView>(R.id.matrixItemAutocompleteAvatar)
    }
}
