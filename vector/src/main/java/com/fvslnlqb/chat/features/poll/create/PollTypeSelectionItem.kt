/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.poll.create

import android.widget.RadioGroup
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyHolder
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyModel
import org.matrix.android.sdk.api.session.room.model.message.PollType

@EpoxyModelClass
abstract class PollTypeSelectionItem : VectorEpoxyModel<PollTypeSelectionItem.Holder>(R.layout.item_poll_type_selection) {

    @EpoxyAttribute
    var pollType: PollType = PollType.DISCLOSED_UNSTABLE

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var pollTypeChangedListener: RadioGroup.OnCheckedChangeListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)

        holder.pollTypeRadioGroup.check(
                when (pollType) {
                    PollType.DISCLOSED_UNSTABLE, PollType.DISCLOSED -> R.id.openPollTypeRadioButton
                    PollType.UNDISCLOSED_UNSTABLE, PollType.UNDISCLOSED -> R.id.closedPollTypeRadioButton
                }
        )

        holder.pollTypeRadioGroup.setOnCheckedChangeListener(pollTypeChangedListener)
    }

    override fun unbind(holder: Holder) {
        super.unbind(holder)
        holder.pollTypeRadioGroup.setOnCheckedChangeListener(null)
    }

    class Holder : VectorEpoxyHolder() {
        val pollTypeRadioGroup by bind<RadioGroup>(R.id.pollTypeRadioGroup)
    }
}
