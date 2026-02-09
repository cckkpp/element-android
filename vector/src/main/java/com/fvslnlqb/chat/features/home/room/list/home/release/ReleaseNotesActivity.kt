/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.home.room.list.home.release

import android.view.View
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import com.fvslnlqb.chat.core.extensions.addFragment
import com.fvslnlqb.chat.core.platform.ScreenOrientationLocker
import com.fvslnlqb.chat.core.platform.VectorBaseActivity
import com.fvslnlqb.chat.databinding.ActivitySimpleBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReleaseNotesActivity : VectorBaseActivity<ActivitySimpleBinding>() {

    @Inject lateinit var orientationLocker: ScreenOrientationLocker
    @Inject lateinit var releaseNotesPreferencesStore: ReleaseNotesPreferencesStore

    override fun getBinding() = ActivitySimpleBinding.inflate(layoutInflater)

    override fun getCoordinatorLayout() = views.coordinatorLayout

    override val rootView: View
        get() = views.coordinatorLayout

    override fun initUiAndData() {
        orientationLocker.lockPhonesToPortrait(this)
        if (isFirstCreation()) {
            addFragment(views.simpleFragmentContainer, ReleaseNotesFragment::class.java)
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            releaseNotesPreferencesStore.setAppLayoutOnboardingShown(true)
        }
    }
}
