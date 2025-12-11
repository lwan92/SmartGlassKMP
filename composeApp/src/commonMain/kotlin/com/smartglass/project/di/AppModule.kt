package com.smartglass.project.di

import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
    // 여기에 의존성들을 추가합니다
}

fun initKoin() {
    startKoin {
        modules(appModule)
    }
}
