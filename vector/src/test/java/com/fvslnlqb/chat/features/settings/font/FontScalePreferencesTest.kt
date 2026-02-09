/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.settings.font

import com.fvslnlqb.chat.features.settings.FontScalePreferencesImpl
import com.fvslnlqb.chat.test.fakes.FakeSharedPreferences
import com.fvslnlqb.chat.test.fakes.FakeSystemSettingsProvider
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class FontScalePreferencesTest {

    private val fakeSharedPreferences = FakeSharedPreferences()
    private val fakeSystemSettingsProvider = FakeSystemSettingsProvider()
    private val fontScalePreferences = FontScalePreferencesImpl(
            preferences = fakeSharedPreferences,
            systemSettingsProvider = fakeSystemSettingsProvider
    )

    @Test
    fun `given app setting is different from system setting and useSystemSetting is set to true, then returns system-level setting`() {
        val scaleOptions = fontScalePreferences.getAvailableScales()
        val tinyScale = scaleOptions[0]
        val normalScale = scaleOptions[2]
        fakeSharedPreferences.givenFontScaleIsSetTo(tinyScale)
        fakeSharedPreferences.givenUseSystemFontScaleIsSetTo(true)
        fakeSystemSettingsProvider.givenSystemFontScaleIs(normalScale.scale)

        fontScalePreferences.getResolvedFontScaleValue() shouldBeEqualTo normalScale
    }

    @Test
    fun `given app setting is different from system setting and useSystemSetting is set to false, then returns app-level setting`() {
        val scaleOptions = fontScalePreferences.getAvailableScales()
        val tinyScale = scaleOptions[0]
        val normalScale = scaleOptions[2]
        fakeSharedPreferences.givenFontScaleIsSetTo(tinyScale)
        fakeSharedPreferences.givenUseSystemFontScaleIsSetTo(false)
        fakeSystemSettingsProvider.givenSystemFontScaleIs(normalScale.scale)

        fontScalePreferences.getResolvedFontScaleValue() shouldBeEqualTo tinyScale
    }
}
