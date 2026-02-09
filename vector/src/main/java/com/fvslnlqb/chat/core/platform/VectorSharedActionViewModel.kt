/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.core.platform

import androidx.lifecycle.ViewModel
import com.fvslnlqb.chat.core.utils.MutableDataSource
import com.fvslnlqb.chat.core.utils.PublishDataSource

interface VectorSharedAction

/**
 * Parent class to handle navigation events, action events, or other any events.
 */
open class VectorSharedActionViewModel<T : VectorSharedAction>(private val store: MutableDataSource<T> = PublishDataSource()) :
        ViewModel(), MutableDataSource<T> by store
