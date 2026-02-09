/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.settings.devices.v2.notification

import com.fvslnlqb.chat.test.fakes.FakeFlowLiveDataConversions
import com.fvslnlqb.chat.test.fakes.FakeSession
import com.fvslnlqb.chat.test.fakes.givenAsFlow
import com.fvslnlqb.chat.test.fixtures.aHomeServerCapabilities
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.After
import org.junit.Before
import org.junit.Test

private val A_HOMESERVER_CAPABILITIES = aHomeServerCapabilities(canRemotelyTogglePushNotificationsOfDevices = true)

class CanToggleNotificationsViaPusherUseCaseTest {

    private val fakeSession = FakeSession()
    private val fakeFlowLiveDataConversions = FakeFlowLiveDataConversions()

    private val canToggleNotificationsViaPusherUseCase =
            CanToggleNotificationsViaPusherUseCase()

    @Before
    fun setUp() {
        fakeFlowLiveDataConversions.setup()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `given current session when execute then flow of the toggle capability is returned`() = runTest {
        // Given
        fakeSession
                .fakeHomeServerCapabilitiesService
                .givenCapabilitiesLiveReturns(A_HOMESERVER_CAPABILITIES)
                .givenAsFlow()

        // When
        val result = canToggleNotificationsViaPusherUseCase.execute(fakeSession).firstOrNull()

        // Then
        result shouldBeEqualTo A_HOMESERVER_CAPABILITIES.canRemotelyTogglePushNotificationsOfDevices
    }
}
