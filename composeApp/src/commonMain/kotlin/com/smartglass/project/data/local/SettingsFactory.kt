package com.smartglass.project.data.local

import com.russhwolf.settings.Settings

/**
 * 플랫폼별 Settings 인스턴스를 생성하는 factory
 */
expect fun createSettings(): Settings
