/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.home.room.list

import android.view.ViewGroup
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
import com.fvslnlqb.chat.core.platform.ButtonStateView
import com.fvslnlqb.chat.features.displayname.getBestName
import com.fvslnlqb.chat.features.home.AvatarRenderer
import com.fvslnlqb.chat.features.invite.InviteButtonStateBinder
import org.matrix.android.sdk.api.session.room.members.ChangeMembershipState
import org.matrix.android.sdk.api.util.MatrixItem

@EpoxyModelClass
abstract class RoomInvitationItem : VectorEpoxyModel<RoomInvitationItem.Holder>(R.layout.item_room_invitation) {

    @EpoxyAttribute lateinit var avatarRenderer: AvatarRenderer
    @EpoxyAttribute lateinit var matrixItem: MatrixItem
    @EpoxyAttribute var secondLine: String? = null
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash) var listener: ClickListener? = null
    @EpoxyAttribute lateinit var changeMembershipState: ChangeMembershipState
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash) var acceptListener: ClickListener? = null
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash) var rejectListener: ClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.rootView.onClick(listener)
        holder.acceptView.commonClicked = acceptListener
        holder.rejectView.commonClicked = rejectListener
        InviteButtonStateBinder.bind(holder.acceptView, holder.rejectView, changeMembershipState)
        holder.titleView.text = matrixItem.getBestName()
        holder.subtitleView.setTextOrHide(secondLine)
        avatarRenderer.render(matrixItem, holder.avatarImageView)
    }

    class Holder : VectorEpoxyHolder() {
        val titleView by bind<TextView>(R.id.roomInvitationNameView)
        val subtitleView by bind<TextView>(R.id.roomInvitationSubTitle)
        val acceptView by bind<ButtonStateView>(R.id.roomInvitationAccept)
        val rejectView by bind<ButtonStateView>(R.id.roomInvitationReject)
        val avatarImageView by bind<ImageView>(R.id.roomInvitationAvatarImageView)
        val rootView by bind<ViewGroup>(R.id.itemRoomInvitationLayout)
    }
}
