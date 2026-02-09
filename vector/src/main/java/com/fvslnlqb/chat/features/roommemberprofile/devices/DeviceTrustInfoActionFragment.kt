/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */
package com.fvslnlqb.chat.features.roommemberprofile.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.parentFragmentViewModel
import com.airbnb.mvrx.withState
import dagger.hilt.android.AndroidEntryPoint
import com.fvslnlqb.chat.core.extensions.cleanup
import com.fvslnlqb.chat.core.extensions.configureWith
import com.fvslnlqb.chat.core.platform.VectorBaseFragment
import com.fvslnlqb.chat.core.utils.DimensionConverter
import com.fvslnlqb.chat.databinding.BottomSheetGenericListBinding
import javax.inject.Inject

@AndroidEntryPoint
class DeviceTrustInfoActionFragment :
        VectorBaseFragment<BottomSheetGenericListBinding>(),
        DeviceTrustInfoEpoxyController.InteractionListener {

    @Inject lateinit var dimensionConverter: DimensionConverter
    @Inject lateinit var epoxyController: DeviceTrustInfoEpoxyController

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): BottomSheetGenericListBinding {
        return BottomSheetGenericListBinding.inflate(inflater, container, false)
    }

    private val viewModel: DeviceListBottomSheetViewModel by parentFragmentViewModel(DeviceListBottomSheetViewModel::class)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views.bottomSheetRecyclerView.setPadding(0, dimensionConverter.dpToPx(16), 0, dimensionConverter.dpToPx(16))
        views.bottomSheetRecyclerView.configureWith(
                epoxyController,
                hasFixedSize = false
        )
        epoxyController.interactionListener = this
    }

    override fun onDestroyView() {
        views.bottomSheetRecyclerView.cleanup()
        super.onDestroyView()
    }

    override fun invalidate() = withState(viewModel) {
        epoxyController.setData(it)
    }
}
