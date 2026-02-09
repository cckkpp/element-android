/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.ui.robot

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.espresso.tools.selectTabAtPosition
import com.fvslnlqb.chat.espresso.tools.waitUntilActivityVisible
import com.fvslnlqb.chat.espresso.tools.waitUntilDialogVisible
import com.fvslnlqb.chat.espresso.tools.waitUntilViewVisible
import com.fvslnlqb.chat.features.home.HomeActivity
import com.fvslnlqb.chat.features.home.room.list.home.header.HomeRoomFilter
import com.fvslnlqb.chat.features.roomdirectory.RoomDirectoryActivity
import com.fvslnlqb.chat.ui.robot.settings.labs.LabFeaturesPreferences
import com.fvslnlqb.chat.waitForView
import im.vector.lib.strings.CommonStrings

class RoomListRobot(private val labsPreferences: LabFeaturesPreferences) {

    fun openRoom(roomName: String, block: RoomDetailRobot.() -> Unit) {
        onView(withId(R.id.roomListView))
                .perform(
                        RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                                hasDescendant(withText(roomName)),
                                ViewActions.click()
                        )
                )
        block(RoomDetailRobot())
        pressBack()
    }

    fun verifyCreatedRoom() {
        onView(withId(R.id.roomListView))
                .perform(
                        RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                                hasDescendant(withText(CommonStrings.room_displayname_empty_room)),
                                ViewActions.longClick()
                        )
                )
        pressBack()
    }

    fun newRoom(block: NewRoomRobot.() -> Unit) {
        if (labsPreferences.isNewAppLayoutEnabled) {
            clickOn(R.id.newLayoutCreateChatButton)
            waitUntilDialogVisible(withId(R.id.create_room))
            clickOn(R.id.create_room)
        } else {
            clickOn(R.id.createGroupRoomButton)
            waitUntilActivityVisible<RoomDirectoryActivity> {
                BaristaVisibilityAssertions.assertDisplayed(R.id.publicRoomsList)
            }
        }
        val newRoomRobot = NewRoomRobot(false, labsPreferences)
        block(newRoomRobot)
        if (!newRoomRobot.createdRoom) {
            pressBack()
        }
    }

    fun crawlTabs() {
        waitUntilActivityVisible<HomeActivity> {
            waitUntilViewVisible(withId(R.id.roomListContainer))
        }

        selectFilterTab(HomeRoomFilter.UNREADS)
        waitForView(withId(R.id.emptyTitleView))
        selectFilterTab(HomeRoomFilter.ALL)
        waitForView(withId(R.id.roomNameView))
    }

    fun selectFilterTab(filter: HomeRoomFilter) {
        onView(withId(R.id.home_filter_tabs_tabs)).perform(selectTabAtPosition(filter.ordinal))
    }
}
