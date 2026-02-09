/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.home

import com.fvslnlqb.chat.core.platform.VectorSharedActionViewModel
import org.matrix.android.sdk.api.session.Session
import javax.inject.Inject

class HomeSharedActionViewModel @Inject constructor(val session: Session) : VectorSharedActionViewModel<HomeActivitySharedAction>()
