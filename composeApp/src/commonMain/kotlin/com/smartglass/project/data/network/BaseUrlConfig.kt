package com.smartglass.project.data.network

import com.smartglass.project.data.local.PreferencesManager

/**
 * Base URL 설정을 관리하는 클래스
 * api_spec.md 1.1 참조
 */
class BaseUrlConfig(
    private val preferencesManager: PreferencesManager
) {
    companion object {
        private const val DEFAULT_BASE_URL = "https://xr-service.digicaps.com:443"
    }
    
    /**
     * Base URL을 저장합니다.
     * @param url QR 코드에서 받은 URL 또는 기본 URL
     */
    fun setBaseUrl(url: String) {
        preferencesManager.saveBaseUrl(url)
    }
    
    /**
     * 저장된 Base URL을 조회합니다.
     * @return Base URL (없으면 기본값 반환)
     */
    fun getBaseUrl(): String {
        return preferencesManager.getBaseUrl() ?: DEFAULT_BASE_URL
    }
    
    /**
     * Base URL을 초기화합니다 (기본값으로 복원).
     */
    fun resetBaseUrl() {
        preferencesManager.saveBaseUrl(DEFAULT_BASE_URL)
    }
}

