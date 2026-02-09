/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.home.room.threads.list.views

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.airbnb.mvrx.parentFragmentViewModel
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.platform.VectorBaseBottomSheetDialogFragment
import com.fvslnlqb.chat.databinding.BottomSheetThreadListBinding
import com.fvslnlqb.chat.features.home.room.threads.list.viewmodel.ThreadListViewModel
import com.fvslnlqb.chat.features.home.room.threads.list.viewmodel.ThreadListViewState
import com.fvslnlqb.chat.features.themes.ThemeUtils

class ThreadListBottomSheet : VectorBaseBottomSheetDialogFragment<BottomSheetThreadListBinding>() {

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): BottomSheetThreadListBinding {
        return BottomSheetThreadListBinding.inflate(inflater, container, false)
    }

    private val threadListViewModel: ThreadListViewModel by parentFragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        threadListViewModel.onEach {
            renderState(it)
        }
        views.threadListModalAllThreads.views.bottomSheetActionClickableZone.debouncedClicks {
            threadListViewModel.applyFiltering(false)
            dismiss()
        }
        views.threadListModalMyThreads.views.bottomSheetActionClickableZone.debouncedClicks {
            threadListViewModel.applyFiltering(true)
            dismiss()
        }
    }

    private fun renderState(state: ThreadListViewState) {
        val radioOffDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_radio_off)
        val radioOnDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_radio_on)

        if (state.shouldFilterThreads) {
            setRightIconDrawableAllThreads(radioOffDrawable, im.vector.lib.ui.styles.R.attr.vctr_content_secondary)
            setRightIconDrawableMyThreads(radioOnDrawable, com.google.android.material.R.attr.colorPrimary)
        } else {
            setRightIconDrawableAllThreads(radioOnDrawable, com.google.android.material.R.attr.colorPrimary)
            setRightIconDrawableMyThreads(radioOffDrawable, im.vector.lib.ui.styles.R.attr.vctr_content_secondary)
        }
    }

    private fun setRightIconDrawableAllThreads(drawable: Drawable?, @AttrRes tint: Int) {
        views.threadListModalAllThreads.rightIcon = drawable
        views.threadListModalAllThreads.rightIcon?.setTintFromAttribute(tint)
    }

    private fun setRightIconDrawableMyThreads(drawable: Drawable?, @AttrRes tint: Int) {
        views.threadListModalMyThreads.rightIcon = drawable
        views.threadListModalMyThreads.rightIcon?.setTintFromAttribute(tint)
    }

    private fun Drawable.setTintFromAttribute(@AttrRes tint: Int) {
        DrawableCompat.setTint(this, ThemeUtils.getColor(requireContext(), tint))
    }
}
