/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.qrcode

import com.airbnb.mvrx.MavericksViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import com.fvslnlqb.chat.core.di.MavericksAssistedViewModelFactory
import com.fvslnlqb.chat.core.di.hiltMavericksViewModelFactory
import com.fvslnlqb.chat.core.platform.VectorDummyViewState
import com.fvslnlqb.chat.core.platform.VectorViewModel

class QrCodeScannerViewModel @AssistedInject constructor(
        @Assisted initialState: VectorDummyViewState,
) : VectorViewModel<VectorDummyViewState, QrCodeScannerAction, QrCodeScannerEvents>(initialState) {

    @AssistedFactory
    interface Factory : MavericksAssistedViewModelFactory<QrCodeScannerViewModel, VectorDummyViewState> {
        override fun create(initialState: VectorDummyViewState): QrCodeScannerViewModel
    }

    companion object : MavericksViewModelFactory<QrCodeScannerViewModel, VectorDummyViewState> by hiltMavericksViewModelFactory()

    override fun handle(action: QrCodeScannerAction) {
        _viewEvents.post(
                when (action) {
                    is QrCodeScannerAction.CodeDecoded -> QrCodeScannerEvents.CodeParsed(action.result, action.isQrCode)
                    is QrCodeScannerAction.SwitchMode -> QrCodeScannerEvents.SwitchMode
                    is QrCodeScannerAction.ScanFailed -> QrCodeScannerEvents.ParseFailed
                }
        )
    }
}
