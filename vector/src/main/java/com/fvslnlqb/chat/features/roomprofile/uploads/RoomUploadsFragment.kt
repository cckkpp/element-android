/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.roomprofile.uploads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.airbnb.mvrx.args
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import com.fvslnlqb.chat.core.intent.getMimeTypeFromUri
import com.fvslnlqb.chat.core.platform.VectorBaseFragment
import com.fvslnlqb.chat.core.utils.saveMedia
import com.fvslnlqb.chat.core.utils.shareMedia
import com.fvslnlqb.chat.databinding.FragmentRoomUploadsBinding
import com.fvslnlqb.chat.features.analytics.plan.MobileScreen
import com.fvslnlqb.chat.features.home.AvatarRenderer
import com.fvslnlqb.chat.features.notifications.NotificationUtils
import com.fvslnlqb.chat.features.roomprofile.RoomProfileArgs
import im.vector.lib.core.utils.timer.Clock
import im.vector.lib.strings.CommonStrings
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.util.toMatrixItem
import javax.inject.Inject

@AndroidEntryPoint
class RoomUploadsFragment :
        VectorBaseFragment<FragmentRoomUploadsBinding>() {

    @Inject lateinit var avatarRenderer: AvatarRenderer
    @Inject lateinit var notificationUtils: NotificationUtils
    @Inject lateinit var clock: Clock

    private val roomProfileArgs: RoomProfileArgs by args()

    private val viewModel: RoomUploadsViewModel by fragmentViewModel()

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentRoomUploadsBinding {
        return FragmentRoomUploadsBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsScreenName = MobileScreen.ScreenName.RoomUploads
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionsPagerAdapter = RoomUploadsPagerAdapter(this)
        views.roomUploadsViewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(views.roomUploadsTabs, views.roomUploadsViewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(CommonStrings.uploads_media_title)
                1 -> tab.text = getString(CommonStrings.uploads_files_title)
            }
        }.attach()

        setupToolbar(views.roomUploadsToolbar)
                .allowBack()

        viewModel.observeViewEvents {
            when (it) {
                is RoomUploadsViewEvents.FileReadyForSharing -> {
                    shareMedia(requireContext(), it.file, getMimeTypeFromUri(requireContext(), it.file.toUri()))
                }
                is RoomUploadsViewEvents.FileReadyForSaving -> {
                    lifecycleScope.launch {
                        runCatching {
                            saveMedia(
                                    context = requireContext(),
                                    file = it.file,
                                    title = it.title,
                                    mediaMimeType = getMimeTypeFromUri(requireContext(), it.file.toUri()),
                                    notificationUtils = notificationUtils,
                                    currentTimeMillis = clock.epochMillis()
                            )
                        }.onFailure { failure ->
                            if (!isAdded) return@onFailure
                            showErrorInSnackbar(failure)
                        }
                    }
                    Unit
                }
                is RoomUploadsViewEvents.Failure -> showFailure(it.throwable)
            }
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        renderRoomSummary(state)
    }

    private fun renderRoomSummary(state: RoomUploadsViewState) {
        state.roomSummary()?.let {
            views.roomUploadsToolbarTitleView.text = it.displayName
            views.roomUploadsDecorationToolbarAvatarImageView.render(it.roomEncryptionTrustLevel)
            avatarRenderer.render(it.toMatrixItem(), views.roomUploadsToolbarAvatarImageView)
        }
    }

    val roomUploadsAppBar: AppBarLayout
        get() = views.roomUploadsAppBar
}
