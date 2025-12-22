package com.smartglass.project.di

import com.smartglass.project.data.local.PreferencesManager
import com.smartglass.project.data.local.PreferencesManagerImpl
import com.smartglass.project.data.local.createSettings
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
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin DI 모듈 설정
 */

val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }
}

val apiModule = module {
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
    viewModel { LoginViewModel(get(), get()) }
    viewModel { QrScanViewModel(get(), get()) }
    viewModel { PasswordResetViewModel() }
}

val localStorageModule = module {
    single { createSettings() }
    single<PreferencesManager> { PreferencesManagerImpl(get()) }
}

val platformModule = module {
    single { createPermissionManager() }
}

fun initKoin() {
    startKoin {
        modules(
            networkModule,
            apiModule,
            repositoryModule,
            useCaseModule,
            viewModelModule,
            localStorageModule,
            platformModule
        )
    }
}
