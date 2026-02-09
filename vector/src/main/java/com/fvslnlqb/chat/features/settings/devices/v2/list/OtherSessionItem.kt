/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.settings.devices.v2.list

import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.epoxy.ClickListener
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyHolder
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyModel
import com.fvslnlqb.chat.core.epoxy.onClick
import com.fvslnlqb.chat.core.extensions.setTextOrHide
import com.fvslnlqb.chat.core.resources.ColorProvider
import com.fvslnlqb.chat.core.resources.DrawableProvider
import com.fvslnlqb.chat.core.resources.StringProvider
import com.fvslnlqb.chat.core.ui.views.ShieldImageView
import org.matrix.android.sdk.api.session.crypto.model.RoomEncryptionTrustLevel

@EpoxyModelClass
abstract class OtherSessionItem : VectorEpoxyModel<OtherSessionItem.Holder>(R.layout.item_other_session) {

    @EpoxyAttribute
    var deviceType: DeviceType = DeviceType.UNKNOWN

    @EpoxyAttribute
    var roomEncryptionTrustLevel: RoomEncryptionTrustLevel? = null

    @EpoxyAttribute
    var sessionName: String? = null

    @EpoxyAttribute
    var sessionDescription: String? = null

    @EpoxyAttribute
    @ColorInt
    var sessionDescriptionColor: Int? = null

    @EpoxyAttribute
    var sessionDescriptionDrawable: Drawable? = null

    @EpoxyAttribute
    lateinit var stringProvider: StringProvider

    @EpoxyAttribute
    lateinit var colorProvider: ColorProvider

    @EpoxyAttribute
    lateinit var drawableProvider: DrawableProvider

    @EpoxyAttribute
    var selected: Boolean = false

    @EpoxyAttribute
    var ipAddress: String? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: ClickListener? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var onLongClickListener: OnLongClickListener? = null

    private val setDeviceTypeIconUseCase = SetDeviceTypeIconUseCase()

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.view.onClick(clickListener)
        holder.view.setOnLongClickListener(onLongClickListener)
        if (clickListener == null && onLongClickListener == null) {
            holder.view.isClickable = false
        }

        holder.otherSessionDeviceTypeImageView.isSelected = selected
        if (selected) {
            val drawableColor = colorProvider.getColorFromAttribute(android.R.attr.colorBackground)
            val drawable = drawableProvider.getDrawable(R.drawable.ic_check_on, drawableColor)
            holder.otherSessionDeviceTypeImageView.setImageDrawable(drawable)
        } else {
            setDeviceTypeIconUseCase.execute(deviceType, holder.otherSessionDeviceTypeImageView, stringProvider)
        }
        holder.otherSessionVerificationStatusImageView.renderDeviceShield(roomEncryptionTrustLevel)
        holder.otherSessionNameTextView.text = sessionName
        holder.otherSessionDescriptionTextView.text = sessionDescription
        sessionDescriptionColor?.let {
            holder.otherSessionDescriptionTextView.setTextColor(it)
        }
        holder.otherSessionDescriptionTextView.setCompoundDrawablesWithIntrinsicBounds(sessionDescriptionDrawable, null, null, null)
        holder.otherSessionIpAddressTextView.setTextOrHide(ipAddress)
        holder.otherSessionItemBackgroundView.isSelected = selected
    }

    class Holder : VectorEpoxyHolder() {
        val otherSessionDeviceTypeImageView by bind<ImageView>(R.id.otherSessionDeviceTypeImageView)
        val otherSessionVerificationStatusImageView by bind<ShieldImageView>(R.id.otherSessionVerificationStatusImageView)
        val otherSessionNameTextView by bind<TextView>(R.id.otherSessionNameTextView)
        val otherSessionDescriptionTextView by bind<TextView>(R.id.otherSessionDescriptionTextView)
        val otherSessionIpAddressTextView by bind<TextView>(R.id.otherSessionIpAddressTextView)
        val otherSessionItemBackgroundView by bind<View>(R.id.otherSessionItemBackground)
    }
}
