/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.crypto.recover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.toSpannable
import com.airbnb.mvrx.parentFragmentViewModel
import com.airbnb.mvrx.withState
import dagger.hilt.android.AndroidEntryPoint
import com.fvslnlqb.chat.core.platform.VectorBaseFragment
import com.fvslnlqb.chat.core.resources.ColorProvider
import com.fvslnlqb.chat.core.utils.colorizeMatchingText
import com.fvslnlqb.chat.databinding.FragmentBootstrapConclusionBinding
import im.vector.lib.strings.CommonStrings
import javax.inject.Inject

@AndroidEntryPoint
class BootstrapConclusionFragment :
        VectorBaseFragment<FragmentBootstrapConclusionBinding>() {

    @Inject lateinit var colorProvider: ColorProvider

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentBootstrapConclusionBinding {
        return FragmentBootstrapConclusionBinding.inflate(inflater, container, false)
    }

    val sharedViewModel: BootstrapSharedViewModel by parentFragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        views.bootstrapConclusionContinue.views.bottomSheetActionClickableZone.debouncedClicks { sharedViewModel.handle(BootstrapActions.Completed) }
    }

    override fun invalidate() = withState(sharedViewModel) { state ->
        if (state.step !is BootstrapStep.DoneSuccess) return@withState

        views.bootstrapConclusionText.text = getString(
                CommonStrings.bootstrap_cross_signing_success,
                getString(CommonStrings.recovery_passphrase),
                getString(CommonStrings.message_key)
        )
                .toSpannable()
                .colorizeMatchingText(getString(CommonStrings.recovery_passphrase), colorProvider.getColorFromAttribute(android.R.attr.textColorLink))
                .colorizeMatchingText(getString(CommonStrings.message_key), colorProvider.getColorFromAttribute(android.R.attr.textColorLink))
        views.bootstrapConclusionText.giveAccessibilityFocusOnce()
    }
}
