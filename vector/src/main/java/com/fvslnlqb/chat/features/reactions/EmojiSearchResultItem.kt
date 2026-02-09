/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */
package com.fvslnlqb.chat.features.reactions

import android.graphics.Typeface
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.epoxy.ClickListener
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyHolder
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyModel
import com.fvslnlqb.chat.core.epoxy.onClick
import com.fvslnlqb.chat.core.extensions.setTextOrHide
import com.fvslnlqb.chat.features.reactions.data.EmojiItem

@EpoxyModelClass
abstract class EmojiSearchResultItem : VectorEpoxyModel<EmojiSearchResultItem.Holder>(R.layout.item_emoji_result) {

    @EpoxyAttribute
    lateinit var emojiItem: EmojiItem

    @EpoxyAttribute
    var currentQuery: String? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var onClickListener: ClickListener? = null

    @EpoxyAttribute
    var emojiTypeFace: Typeface? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        // TODO use query string to highlight the matched query in name and keywords?
        holder.emojiText.text = emojiItem.emoji
        holder.emojiText.typeface = emojiTypeFace ?: Typeface.DEFAULT
        holder.emojiNameText.text = emojiItem.name
        holder.emojiKeywordText.setTextOrHide(emojiItem.keywords.joinToString())
        holder.view.onClick(onClickListener)
    }

    class Holder : VectorEpoxyHolder() {
        val emojiText by bind<TextView>(R.id.item_emoji_tv)
        val emojiNameText by bind<TextView>(R.id.item_emoji_name)
        val emojiKeywordText by bind<TextView>(R.id.item_emoji_keyword)
    }
}
