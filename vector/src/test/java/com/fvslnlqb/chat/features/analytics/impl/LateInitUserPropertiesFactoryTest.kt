/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.analytics.impl

import com.fvslnlqb.chat.features.analytics.plan.UserProperties
import com.fvslnlqb.chat.features.onboarding.FtueUseCase
import com.fvslnlqb.chat.test.fakes.FakeActiveSessionDataSource
import com.fvslnlqb.chat.test.fakes.FakeContext
import com.fvslnlqb.chat.test.fakes.FakeSession
import com.fvslnlqb.chat.test.fakes.FakeVectorStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

@ExperimentalCoroutinesApi
class LateInitUserPropertiesFactoryTest {

    private val fakeActiveSessionDataSource = FakeActiveSessionDataSource()
    private val fakeVectorStore = FakeVectorStore()
    private val fakeContext = FakeContext()
    private val fakeSession = FakeSession().also {
        it.givenVectorStore(fakeVectorStore.instance)
    }

    private val lateInitUserProperties = LateInitUserPropertiesFactory(
            fakeActiveSessionDataSource.instance,
            fakeContext.instance
    )

    @Test
    fun `given no active session when creating properties then returns null`() = runTest {
        val result = lateInitUserProperties.createUserProperties()

        result shouldBeEqualTo null
    }

    @Test
    fun `given no use case set on an active session when creating properties then returns null`() = runTest {
        fakeVectorStore.givenUseCase(null)
        fakeSession.givenVectorStore(fakeVectorStore.instance)
        fakeActiveSessionDataSource.setActiveSession(fakeSession)

        val result = lateInitUserProperties.createUserProperties()

        result shouldBeEqualTo null
    }

    @Test
    fun `given use case set on an active session when creating properties then includes the use case`() = runTest {
        fakeVectorStore.givenUseCase(FtueUseCase.TEAMS)
        fakeActiveSessionDataSource.setActiveSession(fakeSession)
        val result = lateInitUserProperties.createUserProperties()

        result shouldBeEqualTo UserProperties(
                ftueUseCaseSelection = UserProperties.FtueUseCaseSelection.WorkMessaging
        )
    }
}
