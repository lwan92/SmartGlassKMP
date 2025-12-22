package com.smartglass.project.di

import com.smartglass.project.data.local.PreferencesManager
import com.smartglass.project.data.local.PreferencesManagerImpl
import com.smartglass.project.data.local.createSettings
import com.smartglass.project.data.network.HttpClientFactory
import com.smartglass.project.data.remote.api.AuthApi
import com.smartglass.project.data.repository.AuthRepositoryImpl
import com.smartglass.project.domain.repository.AuthRepository
import com.smartglass.project.domain.usecase.LoginUseCase
import com.smartglass.project.domain.usecase.RegisterDeviceUseCase
import com.smartglass.project.platform.permissions.createPermissionManager
import com.smartglass.project.presentation.intro.IntroViewModel
import com.smartglass.project.presentation.login.LoginViewModel
import com.smartglass.project.presentation.passwordreset.PasswordResetViewModel
import com.smartglass.project.presentation.qrscan.QrScanViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val localStorageModule = module {
    single { createSettings() }
    single<PreferencesManager> { PreferencesManagerImpl(get()) }
}

val platformModule = module {
    single { createPermissionManager() }
}

val networkModule = module {
    single { HttpClientFactory.create() }
    single { AuthApi(get()) }
}

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}

val useCaseModule = module {
    factory { LoginUseCase(get()) }
    factory { RegisterDeviceUseCase(get()) }
}

val viewModelModule = module {
    viewModel { IntroViewModel(get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { QrScanViewModel(get()) }
    viewModel { PasswordResetViewModel() }
}

fun initKoin() {
    startKoin {
        modules(
            localStorageModule,
            platformModule,
            networkModule,
            repositoryModule,
            useCaseModule,
            viewModelModule
        )
    }
}
