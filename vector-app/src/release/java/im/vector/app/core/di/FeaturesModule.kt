/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.fvslnlqb.chat.features.DefaultVectorFeatures
import com.fvslnlqb.chat.features.DefaultVectorOverrides
import com.fvslnlqb.chat.features.VectorFeatures
import com.fvslnlqb.chat.features.VectorOverrides

@InstallIn(SingletonComponent::class)
@Module
object FeaturesModule {

    @Provides
    fun providesFeatures(): VectorFeatures {
        return DefaultVectorFeatures()
    }

    @Provides
    fun providesOverrides(): VectorOverrides {
        return DefaultVectorOverrides()
    }
}
