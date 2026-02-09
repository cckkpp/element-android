/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.crypto.keysbackup.settings

import android.widget.Button
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
abstract class KeysBackupSettingFooterItem : VectorEpoxyModel<KeysBackupSettingFooterItem.Holder>(R.layout.item_keys_backup_settings_button_footer) {

    @EpoxyAttribute
    var textButton1: String? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickOnButton1: ClickListener? = null

    @EpoxyAttribute
    var textButton2: String? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickOnButton2: ClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.button1.setTextOrHide(textButton1)
        holder.button1.onClick(clickOnButton1)

        holder.button2.setTextOrHide(textButton2)
        holder.button2.onClick(clickOnButton2)
    }

    class Holder : VectorEpoxyHolder() {
        val button1 by bind<Button>(R.id.keys_backup_settings_footer_button1)
        val button2 by bind<TextView>(R.id.keys_backup_settings_footer_button2)
    }
}
