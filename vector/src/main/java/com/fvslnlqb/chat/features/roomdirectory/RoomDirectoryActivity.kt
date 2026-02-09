/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.roomdirectory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.airbnb.mvrx.viewModel
import com.airbnb.mvrx.withState
import dagger.hilt.android.AndroidEntryPoint
import com.fvslnlqb.chat.core.extensions.addFragment
import com.fvslnlqb.chat.core.extensions.addFragmentToBackstack
import com.fvslnlqb.chat.core.extensions.popBackstack
import com.fvslnlqb.chat.core.platform.VectorBaseActivity
import com.fvslnlqb.chat.databinding.ActivitySimpleBinding
import com.fvslnlqb.chat.features.analytics.plan.MobileScreen
import com.fvslnlqb.chat.features.analytics.plan.ViewRoom
import com.fvslnlqb.chat.features.matrixto.MatrixToBottomSheet
import com.fvslnlqb.chat.features.navigation.Navigator
import com.fvslnlqb.chat.features.roomdirectory.createroom.CreateRoomArgs
import com.fvslnlqb.chat.features.roomdirectory.createroom.CreateRoomFragment
import com.fvslnlqb.chat.features.roomdirectory.picker.RoomDirectoryPickerFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class RoomDirectoryActivity : VectorBaseActivity<ActivitySimpleBinding>(), MatrixToBottomSheet.InteractionListener {

    @Inject lateinit var roomDirectoryViewModelFactory: RoomDirectoryViewModel.Factory
    private val roomDirectoryViewModel: RoomDirectoryViewModel by viewModel()
    private lateinit var sharedActionViewModel: RoomDirectorySharedActionViewModel

    override fun getBinding() = ActivitySimpleBinding.inflate(layoutInflater)

    override fun getCoordinatorLayout() = views.coordinatorLayout

    override val rootView: View
        get() = views.coordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsScreenName = MobileScreen.ScreenName.RoomDirectory
        sharedActionViewModel = viewModelProvider.get(RoomDirectorySharedActionViewModel::class.java)

        if (isFirstCreation()) {
            roomDirectoryViewModel.handle(RoomDirectoryAction.FilterWith(intent?.getStringExtra(INITIAL_FILTER) ?: ""))
        }

        sharedActionViewModel
                .stream()
                .onEach { sharedAction ->
                    when (sharedAction) {
                        is RoomDirectorySharedAction.Back -> popBackstack()
                        is RoomDirectorySharedAction.CreateRoom -> {
                            // Transmit the filter to the CreateRoomFragment
                            withState(roomDirectoryViewModel) {
                                addFragmentToBackstack(
                                        views.simpleFragmentContainer,
                                        CreateRoomFragment::class.java,
                                        CreateRoomArgs(it.currentFilter)
                                )
                            }
                        }
                        is RoomDirectorySharedAction.ChangeProtocol ->
                            addFragmentToBackstack(views.simpleFragmentContainer, RoomDirectoryPickerFragment::class.java)
                        is RoomDirectorySharedAction.Close -> finish()
                        is RoomDirectorySharedAction.CreateRoomSuccess -> Unit
                    }
                }
                .launchIn(lifecycleScope)
    }

    override fun initUiAndData() {
        if (isFirstCreation()) {
            addFragment(views.simpleFragmentContainer, PublicRoomsFragment::class.java)
        }
    }

    override fun mxToBottomSheetNavigateToRoom(roomId: String, trigger: ViewRoom.Trigger?) {
        navigator.openRoom(this, roomId, trigger = trigger)
    }

    override fun mxToBottomSheetSwitchToSpace(spaceId: String) {
        navigator.switchToSpace(this, spaceId, Navigator.PostSwitchSpaceAction.None)
    }

    companion object {
        private const val INITIAL_FILTER = "INITIAL_FILTER"

        fun getIntent(context: Context, initialFilter: String = ""): Intent {
            val intent = Intent(context, RoomDirectoryActivity::class.java)
            intent.putExtra(INITIAL_FILTER, initialFilter)
            return intent
        }
    }
}
