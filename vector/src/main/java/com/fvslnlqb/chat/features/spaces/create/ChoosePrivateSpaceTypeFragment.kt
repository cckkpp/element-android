/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.spaces.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.activityViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.fvslnlqb.chat.core.epoxy.onClick
import com.fvslnlqb.chat.core.platform.OnBackPressed
import com.fvslnlqb.chat.core.platform.VectorBaseFragment
import com.fvslnlqb.chat.core.resources.StringProvider
import com.fvslnlqb.chat.databinding.FragmentSpaceCreateChoosePrivateModelBinding
import im.vector.lib.strings.CommonStrings
import javax.inject.Inject

@AndroidEntryPoint
class ChoosePrivateSpaceTypeFragment :
        VectorBaseFragment<FragmentSpaceCreateChoosePrivateModelBinding>(),
        OnBackPressed {

    @Inject lateinit var stringProvider: StringProvider

    private val sharedViewModel: CreateSpaceViewModel by activityViewModel()

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?) =
            FragmentSpaceCreateChoosePrivateModelBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        views.justMeButton.onClick {
            sharedViewModel.handle(CreateSpaceAction.SetSpaceTopology(SpaceTopology.JustMe))
        }

        views.teammatesButton.onClick {
            sharedViewModel.handle(CreateSpaceAction.SetSpaceTopology(SpaceTopology.MeAndTeammates))
        }

        sharedViewModel.onEach { state ->
            views.accessInfoHelpText.text = stringProvider.getString(CommonStrings.create_spaces_make_sure_access, state.name ?: "")
        }
    }

    override fun onBackPressed(toolbarButton: Boolean): Boolean {
        sharedViewModel.handle(CreateSpaceAction.OnBackPressed)
        return true
    }
}
