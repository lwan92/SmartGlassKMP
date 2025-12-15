package com.smartglass.project.presentation.qrscan

/**
 * QR 스캔 화면의 상태 정의
 * features_spec.md의 QR 스캔 로직을 기반으로 작성
 */
sealed class QrScanState {
    object Idle : QrScanState()
    object Scanning : QrScanState()                    // QR 코드 스캔 중
    data class Scanned(val qrData: String) : QrScanState()  // QR 코드 스캔 완료
    object RegisteringDevice : QrScanState()            // 디바이스 등록 진행 중
    data class DeviceRegistered(val appId: String) : QrScanState()  // 디바이스 등록 성공
    data class Error(val message: String) : QrScanState()  // 에러 발생
}
