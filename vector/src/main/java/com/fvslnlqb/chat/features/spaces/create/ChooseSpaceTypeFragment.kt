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
import com.fvslnlqb.chat.core.platform.VectorBaseFragment
import com.fvslnlqb.chat.databinding.FragmentSpaceCreateChooseTypeBinding
import com.fvslnlqb.chat.features.analytics.plan.MobileScreen

@AndroidEntryPoint
class ChooseSpaceTypeFragment :
        VectorBaseFragment<FragmentSpaceCreateChooseTypeBinding>() {

    private val sharedViewModel: CreateSpaceViewModel by activityViewModel()

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?) =
            FragmentSpaceCreateChooseTypeBinding.inflate(layoutInflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsScreenName = MobileScreen.ScreenName.CreateSpace
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        views.publicButton.onClick {
            sharedViewModel.handle(CreateSpaceAction.SetRoomType(SpaceType.Public))
        }

        views.privateButton.onClick {
            sharedViewModel.handle(CreateSpaceAction.SetRoomType(SpaceType.Private))
        }
    }
}
