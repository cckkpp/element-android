/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.widgets

import com.fvslnlqb.chat.core.platform.VectorViewModelAction

sealed class WidgetAction : VectorViewModelAction {
    data class OnWebViewStartedToLoad(val url: String) : WidgetAction()
    data class OnWebViewLoadingError(val url: String, val isHttpError: Boolean, val errorCode: Int, val errorDescription: String) : WidgetAction()
    data class OnWebViewLoadingSuccess(val url: String) : WidgetAction()
    object LoadFormattedUrl : WidgetAction()
    object DeleteWidget : WidgetAction()
    object RevokeWidget : WidgetAction()
    object OnTermsReviewed : WidgetAction()
    object CloseWidget : WidgetAction()
}
