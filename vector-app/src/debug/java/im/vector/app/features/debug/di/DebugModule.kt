/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.debug.di

import android.content.Context
import android.content.Intent
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.fvslnlqb.chat.core.debug.DebugNavigator
import com.fvslnlqb.chat.core.debug.DebugReceiver
import com.fvslnlqb.chat.core.debug.LeakDetector
import com.fvslnlqb.chat.features.debug.DebugMenuActivity
import com.fvslnlqb.chat.leakcanary.LeakCanaryLeakDetector
import com.fvslnlqb.chat.receivers.VectorDebugReceiver

@InstallIn(SingletonComponent::class)
@Module
abstract class DebugModule {

    companion object {

        @Provides
        fun providesDebugNavigator() = object : DebugNavigator {
            override fun openDebugMenu(context: Context) {
                context.startActivity(Intent(context, DebugMenuActivity::class.java))
            }
        }
    }

    @Binds
    abstract fun bindsDebugReceiver(receiver: VectorDebugReceiver): DebugReceiver

    @Binds
    abstract fun bindsLeakDetector(leakDetector: LeakCanaryLeakDetector): LeakDetector
}
