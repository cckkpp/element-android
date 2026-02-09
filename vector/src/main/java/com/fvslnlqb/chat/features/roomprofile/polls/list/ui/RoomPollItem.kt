/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.roomprofile.polls.list.ui

import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.epoxy.ClickListener
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyHolder
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyModel
import com.fvslnlqb.chat.core.epoxy.onClick
import com.fvslnlqb.chat.core.extensions.setTextOrHide
import com.fvslnlqb.chat.features.home.room.detail.timeline.item.PollOptionView
import com.fvslnlqb.chat.features.home.room.detail.timeline.item.PollOptionViewState

@EpoxyModelClass
abstract class RoomPollItem : VectorEpoxyModel<RoomPollItem.Holder>(R.layout.item_poll) {

    @EpoxyAttribute
    lateinit var formattedDate: String

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute
    var winnerOptions: List<PollOptionViewState.PollEnded> = emptyList()

    @EpoxyAttribute
    var totalVotesStatus: String? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: ClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.view.onClick(clickListener)
        holder.date.text = formattedDate
        holder.title.text = title
        holder.winnerOptions.removeAllViews()
        holder.winnerOptions.isVisible = winnerOptions.isNotEmpty()
        for (winnerOption in winnerOptions) {
            val optionView = PollOptionView(holder.view.context)
            holder.winnerOptions.addView(optionView)
            optionView.render(winnerOption)
        }
        holder.totalVotes.setTextOrHide(totalVotesStatus)
    }

    class Holder : VectorEpoxyHolder() {
        val date by bind<TextView>(R.id.pollDate)
        val title by bind<TextView>(R.id.pollTitle)
        val winnerOptions by bind<LinearLayout>(R.id.pollWinnerOptionsContainer)
        val totalVotes by bind<TextView>(R.id.pollTotalVotes)
    }
}
