/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.onboarding.ftueauth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import com.fvslnlqb.chat.core.extensions.associateContentStateWith
import com.fvslnlqb.chat.core.extensions.autofillPhoneNumber
import com.fvslnlqb.chat.core.extensions.content
import com.fvslnlqb.chat.core.extensions.editText
import com.fvslnlqb.chat.core.extensions.setOnImeDoneListener
import com.fvslnlqb.chat.core.extensions.toReducedUrl
import com.fvslnlqb.chat.databinding.FragmentFtuePhoneInputBinding
import com.fvslnlqb.chat.features.onboarding.OnboardingAction
import com.fvslnlqb.chat.features.onboarding.OnboardingViewState
import com.fvslnlqb.chat.features.onboarding.RegisterAction
import im.vector.lib.strings.CommonStrings
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.matrix.android.sdk.api.auth.registration.RegisterThreePid
import reactivecircus.flowbinding.android.widget.textChanges
import javax.inject.Inject

@AndroidEntryPoint
class FtueAuthPhoneEntryFragment :
        AbstractFtueAuthFragment<FragmentFtuePhoneInputBinding>() {

    @Inject lateinit var phoneNumberParser: PhoneNumberParser

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFtuePhoneInputBinding {
        return FragmentFtuePhoneInputBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        views.phoneEntryInput.associateContentStateWith(button = views.phoneEntrySubmit)
        views.phoneEntryInput.setOnImeDoneListener { updatePhoneNumber() }
        views.phoneEntrySubmit.debouncedClicks { updatePhoneNumber() }

        views.phoneEntryInput.editText().textChanges()
                .onEach {
                    views.phoneEntryInput.error = null
                    views.phoneEntrySubmit.isEnabled = it.isNotBlank()
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

        views.phoneEntryInput.autofillPhoneNumber()
    }

    private fun updatePhoneNumber() {
        val number = views.phoneEntryInput.content()

        when (val result = phoneNumberParser.parseInternationalNumber(number)) {
            PhoneNumberParser.Result.ErrorInvalidNumber -> views.phoneEntryInput.error = getString(CommonStrings.login_msisdn_error_other)
            PhoneNumberParser.Result.ErrorMissingInternationalCode ->
                views.phoneEntryInput.error = getString(CommonStrings.login_msisdn_error_not_international)
            is PhoneNumberParser.Result.Success -> {
                val (countryCode, phoneNumber) = result
                viewModel.handle(OnboardingAction.PostRegisterAction(RegisterAction.AddThreePid(RegisterThreePid.Msisdn(phoneNumber, countryCode))))
            }
        }
    }

    override fun updateWithState(state: OnboardingViewState) {
        views.phoneEntryHeaderSubtitle.text = getString(CommonStrings.ftue_auth_phone_subtitle, state.selectedHomeserver.userFacingUrl.toReducedUrl())
    }

    override fun onError(throwable: Throwable) {
        views.phoneEntryInput.error = errorFormatter.toHumanReadable(throwable)
    }

    override fun resetViewModel() {
        viewModel.handle(OnboardingAction.ResetAuthenticationAttempt)
    }
}
