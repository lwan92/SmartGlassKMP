package com.smartglass.project.data.local

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

/**
 * PreferencesManager 구현체
 * multiplatform-settings 사용
 */
class PreferencesManagerImpl(
    private val settings: Settings
) : PreferencesManager {
    
    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USERNAME = "username"
        private const val KEY_AUTO_LOGIN = "auto_login"
        private const val KEY_APP_ID = "app_id"
        private const val KEY_DEVICE_REGISTERED = "device_registered"
    }
    
    override fun saveAccessToken(token: String) {
        settings[KEY_ACCESS_TOKEN] = token
    }
    
    override fun getAccessToken(): String? {
        return settings.getStringOrNull(KEY_ACCESS_TOKEN)
    }
    
    override fun saveRefreshToken(token: String) {
        settings[KEY_REFRESH_TOKEN] = token
    }
    
    override fun getRefreshToken(): String? {
        return settings.getStringOrNull(KEY_REFRESH_TOKEN)
    }
    
    override fun clearTokens() {
        settings.remove(KEY_ACCESS_TOKEN)
        settings.remove(KEY_REFRESH_TOKEN)
    }
    
    override fun saveUserId(userId: String) {
        settings[KEY_USER_ID] = userId
    }
    
    override fun getUserId(): String? {
        return settings.getStringOrNull(KEY_USER_ID)
    }
    
    override fun saveUsername(username: String) {
        settings[KEY_USERNAME] = username
    }
    
    override fun getUsername(): String? {
        return settings.getStringOrNull(KEY_USERNAME)
    }
    
    override fun setAutoLogin(enabled: Boolean) {
        settings[KEY_AUTO_LOGIN] = enabled
    }
    
    override fun isAutoLoginEnabled(): Boolean {
        return settings.getBoolean(KEY_AUTO_LOGIN, false)
    }
    
    override fun saveAppId(appId: String) {
        settings[KEY_APP_ID] = appId
    }
    
    override fun getAppId(): String? {
        return settings.getStringOrNull(KEY_APP_ID)
    }
    
    override fun setDeviceRegistered(registered: Boolean) {
        settings[KEY_DEVICE_REGISTERED] = registered
    }
    
    override fun isDeviceRegistered(): Boolean {
        return settings.getBoolean(KEY_DEVICE_REGISTERED, false)
    }
    
    override fun clearAll() {
        settings.clear()
    }
}
