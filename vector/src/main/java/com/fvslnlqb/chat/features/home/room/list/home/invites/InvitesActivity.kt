/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.home.room.list.home.invites

import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import com.fvslnlqb.chat.core.extensions.addFragment
import com.fvslnlqb.chat.core.platform.VectorBaseActivity
import com.fvslnlqb.chat.databinding.ActivitySimpleBinding

@AndroidEntryPoint
class InvitesActivity : VectorBaseActivity<ActivitySimpleBinding>() {

    override fun getBinding() = ActivitySimpleBinding.inflate(layoutInflater)

    override fun initUiAndData() {
        if (isFirstCreation()) {
            addFragment(views.simpleFragmentContainer, InvitesFragment::class.java)
        }
    }

    override fun getCoordinatorLayout() = views.coordinatorLayout
    override val rootView: View
        get() = views.coordinatorLayout
}
