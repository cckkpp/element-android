/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.pin.lockscreen.di

import android.app.KeyguardManager
import android.content.Context
import androidx.biometric.BiometricManager
import androidx.core.content.getSystemService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import com.fvslnlqb.chat.core.di.MavericksAssistedViewModelFactory
import com.fvslnlqb.chat.core.di.MavericksViewModelKey
import com.fvslnlqb.chat.features.pin.PinCodeStore
import com.fvslnlqb.chat.features.pin.lockscreen.configuration.LockScreenConfiguration
import com.fvslnlqb.chat.features.pin.lockscreen.configuration.LockScreenMode
import com.fvslnlqb.chat.features.pin.lockscreen.crypto.migrations.LegacyPinCodeMigrator
import com.fvslnlqb.chat.features.pin.lockscreen.pincode.EncryptedPinCodeStorage
import com.fvslnlqb.chat.features.pin.lockscreen.ui.LockScreenViewModel
import org.matrix.android.sdk.api.securestorage.SecretStoringUtils
import org.matrix.android.sdk.api.util.BuildVersionSdkIntProvider
import org.matrix.android.sdk.api.util.DefaultBuildVersionSdkIntProvider
import java.security.KeyStore

@Module
@InstallIn(SingletonComponent::class)
object LockScreenModule {

    @Provides
    @PinCodeKeyAlias
    fun providePinCodeKeyAlias(): String = "vector.pin_code"

    @Provides
    @BiometricKeyAlias
    fun provideSystemKeyAlias(): String = "vector.system_v2"

    @Provides
    fun provideKeyStore(): KeyStore = KeyStore.getInstance("AndroidKeyStore").also { it.load(null) }

    @Provides
    fun provideBuildVersionSdkIntProvider(): BuildVersionSdkIntProvider = DefaultBuildVersionSdkIntProvider()

    @Provides
    fun provideLockScreenConfig() = LockScreenConfiguration(
            mode = LockScreenMode.VERIFY,
            pinCodeLength = 4,
            isWeakBiometricsEnabled = false,
            isDeviceCredentialUnlockEnabled = false,
            isStrongBiometricsEnabled = true,
            needsNewCodeValidation = true,
    )

    @Provides
    fun provideBiometricManager(@ApplicationContext context: Context) = BiometricManager.from(context)

    @Provides
    fun provideLegacyPinCodeMigrator(
            @PinCodeKeyAlias pinCodeKeyAlias: String,
            context: Context,
            pinCodeStore: PinCodeStore,
            keyStore: KeyStore,
            buildVersionSdkIntProvider: BuildVersionSdkIntProvider,
    ) = LegacyPinCodeMigrator(
            pinCodeKeyAlias,
            pinCodeStore,
            keyStore,
            SecretStoringUtils(context, keyStore, buildVersionSdkIntProvider),
            buildVersionSdkIntProvider,
    )

    @Provides
    fun provideKeyguardManager(context: Context): KeyguardManager = context.getSystemService()!!
}

@Module
@InstallIn(SingletonComponent::class)
interface LockScreenBindsModule {

    @Binds
    @IntoMap
    @MavericksViewModelKey(LockScreenViewModel::class)
    fun bindLockScreenViewModel(factory: LockScreenViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    fun bindSharedPreferencesStorage(pinCodeStore: PinCodeStore): EncryptedPinCodeStorage
}
