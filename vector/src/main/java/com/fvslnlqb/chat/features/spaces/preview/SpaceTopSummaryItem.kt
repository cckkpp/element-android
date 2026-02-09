/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.spaces.preview

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyHolder
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyModel
import com.fvslnlqb.chat.core.extensions.setTextOrHide

@EpoxyModelClass
abstract class SpaceTopSummaryItem : VectorEpoxyModel<SpaceTopSummaryItem.Holder>(R.layout.item_space_top_summary) {

    @EpoxyAttribute
    var topic: String? = null

    @EpoxyAttribute
    lateinit var formattedMemberCount: String

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.spaceTopicText.setTextOrHide(topic)
        holder.memberCountText.text = formattedMemberCount
    }

    class Holder : VectorEpoxyHolder() {
        val memberCountText by bind<TextView>(R.id.spaceSummaryMemberCountText)
        val spaceTopicText by bind<TextView>(R.id.spaceSummaryTopic)
    }
}
