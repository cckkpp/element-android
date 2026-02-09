/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.spaces

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.airbnb.mvrx.Mavericks
import dagger.hilt.android.AndroidEntryPoint
import com.fvslnlqb.chat.core.extensions.replaceFragment
import com.fvslnlqb.chat.core.platform.VectorBaseActivity
import com.fvslnlqb.chat.databinding.ActivitySimpleBinding
import com.fvslnlqb.chat.features.spaces.preview.SpacePreviewArgs
import com.fvslnlqb.chat.features.spaces.preview.SpacePreviewFragment
import im.vector.lib.core.utils.compat.getParcelableExtraCompat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SpacePreviewActivity : VectorBaseActivity<ActivitySimpleBinding>() {

    lateinit var sharedActionViewModel: SpacePreviewSharedActionViewModel

    override fun getBinding(): ActivitySimpleBinding = ActivitySimpleBinding.inflate(layoutInflater)

    override fun getCoordinatorLayout() = views.coordinatorLayout

    override val rootView: View
        get() = views.coordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedActionViewModel = viewModelProvider.get(SpacePreviewSharedActionViewModel::class.java)
        sharedActionViewModel
                .stream()
                .onEach { action ->
                    when (action) {
                        SpacePreviewSharedAction.DismissAction -> finish()
                        SpacePreviewSharedAction.ShowModalLoading -> showWaitingView()
                        SpacePreviewSharedAction.HideModalLoading -> hideWaitingView()
                        is SpacePreviewSharedAction.ShowErrorMessage -> action.error?.let { showSnackbar(it) }
                    }
                }
                .launchIn(lifecycleScope)

        if (isFirstCreation()) {
            val args = intent?.getParcelableExtraCompat<SpacePreviewArgs>(Mavericks.KEY_ARG)
            replaceFragment(
                    views.simpleFragmentContainer,
                    SpacePreviewFragment::class.java,
                    args
            )
        }
    }

    companion object {
        fun newIntent(context: Context, spaceIdOrAlias: String): Intent {
            return Intent(context, SpacePreviewActivity::class.java).apply {
                putExtra(Mavericks.KEY_ARG, SpacePreviewArgs(spaceIdOrAlias))
            }
        }
    }
}
