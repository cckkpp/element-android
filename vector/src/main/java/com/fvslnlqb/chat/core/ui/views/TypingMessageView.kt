/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.core.ui.views

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import dagger.hilt.android.AndroidEntryPoint
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.databinding.TypingMessageLayoutBinding
import com.fvslnlqb.chat.features.home.AvatarRenderer
import com.fvslnlqb.chat.features.home.room.typing.TypingHelper
import org.matrix.android.sdk.api.session.room.sender.SenderInfo
import javax.inject.Inject

@AndroidEntryPoint
class TypingMessageView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val views: TypingMessageLayoutBinding

    @Inject
    lateinit var typingHelper: TypingHelper

    init {
        inflate(context, R.layout.typing_message_layout, this)
        views = TypingMessageLayoutBinding.bind(this)
    }

    fun render(typingUsers: List<SenderInfo>, avatarRenderer: AvatarRenderer) {
        views.typingUserText.text = typingHelper.getNotificationTypingMessage(typingUsers)
        views.typingUserAvatars.render(typingUsers, avatarRenderer)
    }
}
