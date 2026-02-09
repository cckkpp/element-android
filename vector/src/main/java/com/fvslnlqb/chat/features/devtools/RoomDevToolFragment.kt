/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.devtools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.activityViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.extensions.cleanup
import com.fvslnlqb.chat.core.extensions.configureWith
import com.fvslnlqb.chat.core.platform.VectorBaseFragment
import com.fvslnlqb.chat.databinding.FragmentGenericRecyclerBinding
import javax.inject.Inject

@AndroidEntryPoint
class RoomDevToolFragment :
        VectorBaseFragment<FragmentGenericRecyclerBinding>(),
        DevToolsInteractionListener {

    @Inject lateinit var epoxyController: RoomDevToolRootController

    private val sharedViewModel: RoomDevToolViewModel by activityViewModel()

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGenericRecyclerBinding {
        return FragmentGenericRecyclerBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views.genericRecyclerView.configureWith(epoxyController, dividerDrawable = R.drawable.divider_horizontal)
        epoxyController.interactionListener = this

//        sharedViewModel.observeViewEvents {
//            when (it) {
//                is DevToolsViewEvents.showJson -> {
//                    JSonViewerDialog.newInstance(it.jsonString, -1, createJSonViewerStyleProvider(colorProvider))
//                            .show(childFragmentManager, "JSON_VIEWER")
//
//                }
//            }
//        }
    }

    override fun onDestroyView() {
        views.genericRecyclerView.cleanup()
        epoxyController.interactionListener = null
        super.onDestroyView()
    }

    override fun processAction(action: RoomDevToolAction) {
        sharedViewModel.handle(action)
    }
}
