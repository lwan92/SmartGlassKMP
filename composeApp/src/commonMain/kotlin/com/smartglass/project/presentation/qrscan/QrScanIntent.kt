package com.smartglass.project.presentation.qrscan

/**
 * QR 스캔 화면의 사용자 액션 정의
 * features_spec.md의 QR 스캔 로직을 기반으로 작성
 */
sealed class QrScanIntent {
    object StartScanning : QrScanIntent()              // 스캔 시작
    data class QrCodeScanned(val qrData: String) : QrScanIntent()  // QR 코드 스캔 완료
    data class ScanError(val error: String) : QrScanIntent()       // 스캔 에러
    object RegisterDevice : QrScanIntent()             // 디바이스 등록
    object Close : QrScanIntent()                      // 화면 닫기
}
