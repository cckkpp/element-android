/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.ui.robot

import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.espresso.tools.waitUntilViewVisible
import com.fvslnlqb.chat.features.DefaultVectorFeatures
import com.fvslnlqb.chat.features.VectorFeatures
import com.fvslnlqb.chat.ui.robot.settings.labs.LabFeaturesPreferences
import im.vector.lib.strings.CommonStrings

class NewRoomRobot(
        var createdRoom: Boolean = false,
        private val labsPreferences: LabFeaturesPreferences
) {
    private val features: VectorFeatures = DefaultVectorFeatures()

    fun createNewRoom(block: CreateNewRoomRobot.() -> Unit) {
        clickOn(CommonStrings.create_new_room)
        waitUntilViewVisible(withId(R.id.createRoomForm))
        val createNewRoomRobot = CreateNewRoomRobot()
        block(createNewRoomRobot)
        createdRoom = createNewRoomRobot.createdRoom
        if (!createNewRoomRobot.createdRoom) {
            pressBack()
        }
    }
}
