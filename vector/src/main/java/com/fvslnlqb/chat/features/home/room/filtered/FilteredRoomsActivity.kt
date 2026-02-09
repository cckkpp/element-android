/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.home.room.filtered

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import dagger.hilt.android.AndroidEntryPoint
import com.fvslnlqb.chat.core.extensions.replaceFragment
import com.fvslnlqb.chat.core.platform.VectorBaseActivity
import com.fvslnlqb.chat.databinding.ActivityFilteredRoomsBinding
import com.fvslnlqb.chat.features.analytics.plan.MobileScreen
import com.fvslnlqb.chat.features.home.RoomListDisplayMode
import com.fvslnlqb.chat.features.home.room.list.RoomListFragment
import com.fvslnlqb.chat.features.home.room.list.RoomListParams

@AndroidEntryPoint
class FilteredRoomsActivity : VectorBaseActivity<ActivityFilteredRoomsBinding>() {

    private val roomListFragment: RoomListFragment?
        get() {
            return supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) as? RoomListFragment
        }

    override fun getBinding() = ActivityFilteredRoomsBinding.inflate(layoutInflater)

    override fun getCoordinatorLayout() = views.coordinatorLayout

    override val rootView: View
        get() = views.coordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsScreenName = MobileScreen.ScreenName.RoomFilter
        setupToolbar(views.filteredRoomsToolbar)
                .allowBack()
        if (isFirstCreation()) {
            val params = RoomListParams(RoomListDisplayMode.FILTERED)
            replaceFragment(views.filteredRoomsFragmentContainer, RoomListFragment::class.java, params, FRAGMENT_TAG)
        }
        views.filteredRoomsSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                roomListFragment?.filterRoomsWith(newText)
                return true
            }
        })
        // Open the keyboard immediately
        views.filteredRoomsSearchView.requestFocus()
    }

    companion object {
        private const val FRAGMENT_TAG = "RoomListFragment"

        fun newIntent(context: Context): Intent {
            return Intent(context, FilteredRoomsActivity::class.java)
        }
    }
}
