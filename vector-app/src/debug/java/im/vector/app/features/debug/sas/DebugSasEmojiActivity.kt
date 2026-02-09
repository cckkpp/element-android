/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.debug.sas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fvslnlqb.chat.core.extensions.cleanup
import com.fvslnlqb.chat.core.extensions.configureWith
import com.fvslnlqb.chat.databinding.FragmentGenericRecyclerBinding
import org.matrix.android.sdk.api.crypto.getAllVerificationEmojis

class DebugSasEmojiActivity : AppCompatActivity() {

    private lateinit var views: FragmentGenericRecyclerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        views = FragmentGenericRecyclerBinding.inflate(layoutInflater)
        setContentView(views.root)
        val controller = SasEmojiController()
        views.genericRecyclerView.configureWith(controller)
        controller.setData(SasState(getAllVerificationEmojis()))
    }

    override fun onDestroy() {
        views.genericRecyclerView.cleanup()
        super.onDestroy()
    }
}
