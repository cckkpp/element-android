/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.userdirectory

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyHolder
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyModel

@EpoxyModelClass
abstract class UserDirectoryLetterHeaderItem : VectorEpoxyModel<UserDirectoryLetterHeaderItem.Holder>(R.layout.item_user_directory_letter_header) {

    @EpoxyAttribute var letter: String = ""

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.letterView.text = letter
    }

    class Holder : VectorEpoxyHolder() {
        val letterView by bind<TextView>(R.id.userDirectoryLetterView)
    }
}
