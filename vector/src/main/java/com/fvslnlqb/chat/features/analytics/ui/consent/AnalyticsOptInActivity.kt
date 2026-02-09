/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.analytics.ui.consent

import android.view.View
import com.airbnb.mvrx.viewModel
import dagger.hilt.android.AndroidEntryPoint
import com.fvslnlqb.chat.core.extensions.addFragment
import com.fvslnlqb.chat.core.platform.ScreenOrientationLocker
import com.fvslnlqb.chat.core.platform.VectorBaseActivity
import com.fvslnlqb.chat.databinding.ActivitySimpleBinding
import javax.inject.Inject

/**
 * Simple container for AnalyticsOptInFragment.
 */
@AndroidEntryPoint
class AnalyticsOptInActivity : VectorBaseActivity<ActivitySimpleBinding>() {

    @Inject lateinit var orientationLocker: ScreenOrientationLocker

    private val viewModel: AnalyticsConsentViewModel by viewModel()

    override fun getBinding() = ActivitySimpleBinding.inflate(layoutInflater)

    override fun getCoordinatorLayout() = views.coordinatorLayout

    override val rootView: View
        get() = views.coordinatorLayout

    override fun initUiAndData() {
        orientationLocker.lockPhonesToPortrait(this)
        if (isFirstCreation()) {
            addFragment(views.simpleFragmentContainer, AnalyticsOptInFragment::class.java)
        }

        viewModel.observeViewEvents {
            when (it) {
                AnalyticsOptInViewEvents.OnDataSaved -> finish()
            }
        }
    }
}
