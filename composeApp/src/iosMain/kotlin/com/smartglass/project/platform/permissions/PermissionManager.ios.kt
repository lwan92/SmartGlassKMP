package com.smartglass.project.platform.permissions

import platform.AVFoundation.AVAuthorizationStatus
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusDenied
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVAuthorizationStatusRestricted
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class IosPermissionManager : PermissionManager {
    
    override suspend fun checkPermissions(): Boolean {
        // iOS에서는 카메라 권한 확인
        return checkCameraPermission()
    }
    
    override suspend fun requestPermissions(): Boolean {
        // iOS에서는 카메라 권한 요청
        return requestCameraPermission()
    }
    
    override suspend fun checkCameraPermission(): Boolean {
        val status = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)
        return status == AVAuthorizationStatusAuthorized
    }
    
    override suspend fun requestCameraPermission(): Boolean = suspendCoroutine { continuation ->
        val currentStatus = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)
        
        when (currentStatus) {
            AVAuthorizationStatusAuthorized -> {
                continuation.resume(true)
            }
            AVAuthorizationStatusNotDetermined -> {
                AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { granted ->
                    continuation.resume(granted)
                }
            }
            AVAuthorizationStatusDenied, AVAuthorizationStatusRestricted -> {
                continuation.resume(false)
            }
            else -> {
                continuation.resume(false)
            }
        }
    }
}

actual fun createPermissionManager(): PermissionManager {
    return IosPermissionManager()
}
