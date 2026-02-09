/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.signout.soft.epoxy

import android.os.Build
import android.text.Editable
import android.widget.Button
import androidx.autofill.HintConstants
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.fvslnlqb.chat.R
import com.fvslnlqb.chat.core.epoxy.ClickListener
import com.fvslnlqb.chat.core.epoxy.TextListener
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyHolder
import com.fvslnlqb.chat.core.epoxy.VectorEpoxyModel
import com.fvslnlqb.chat.core.epoxy.addTextChangedListenerOnce
import com.fvslnlqb.chat.core.epoxy.onClick
import com.fvslnlqb.chat.core.epoxy.setValueOnce
import com.fvslnlqb.chat.core.platform.SimpleTextWatcher
import com.fvslnlqb.chat.core.resources.StringProvider

@EpoxyModelClass
abstract class LoginPasswordFormItem : VectorEpoxyModel<LoginPasswordFormItem.Holder>(R.layout.item_login_password_form) {

    @EpoxyAttribute var passwordValue: String = ""
    @EpoxyAttribute var submitEnabled: Boolean = false
    @EpoxyAttribute var errorText: String? = null
    @EpoxyAttribute lateinit var stringProvider: StringProvider
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash) var forgetPasswordClickListener: ClickListener? = null
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash) var submitClickListener: ClickListener? = null
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash) var onPasswordEdited: TextListener? = null

    private val textChangeListener = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable) {
            onPasswordEdited?.invoke(s.toString())
        }
    }

    override fun bind(holder: Holder) {
        super.bind(holder)

        setupAutoFill(holder)
        holder.passwordFieldTil.error = errorText
        holder.forgetPassword.onClick(forgetPasswordClickListener)
        holder.submit.isEnabled = submitEnabled
        holder.submit.onClick(submitClickListener)
        holder.setValueOnce(holder.passwordField, passwordValue)
        holder.passwordField.addTextChangedListenerOnce(textChangeListener)
    }

    override fun unbind(holder: Holder) {
        super.unbind(holder)
        holder.passwordField.removeTextChangedListener(textChangeListener)
    }

    private fun setupAutoFill(holder: Holder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.passwordField.setAutofillHints(HintConstants.AUTOFILL_HINT_PASSWORD)
        }
    }

    class Holder : VectorEpoxyHolder() {
        val passwordField by bind<TextInputEditText>(R.id.itemLoginPasswordFormPasswordField)
        val passwordFieldTil by bind<TextInputLayout>(R.id.itemLoginPasswordFormPasswordFieldTil)
        val forgetPassword by bind<Button>(R.id.itemLoginPasswordFormForgetPasswordButton)
        val submit by bind<Button>(R.id.itemLoginPasswordFormSubmit)
    }
}
