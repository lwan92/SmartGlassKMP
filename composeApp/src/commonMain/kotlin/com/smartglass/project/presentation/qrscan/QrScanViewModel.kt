package com.smartglass.project.presentation.qrscan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * QR 스캔 화면 ViewModel
 * features_spec.md의 QR 스캔 로직을 기반으로 구현
 */
class QrScanViewModel() : ViewModel() {
    private val _state = MutableStateFlow<QrScanState>(QrScanState.Idle)
    val state: StateFlow<QrScanState> = _state.asStateFlow()

    fun handleIntent(intent: QrScanIntent) {
        when (intent) {
            is QrScanIntent.StartScanning -> startScanning()
            is QrScanIntent.QrCodeScanned -> onQrCodeScanned(intent.qrData)
            is QrScanIntent.RegisterDevice -> registerDevice()
            is QrScanIntent.Close -> close()
        }
    }

    /**
     * 스캔 시작
     */
    private fun startScanning() {
        _state.value = QrScanState.Scanning
    }

    /**
     * QR 코드 스캔 완료 처리
     * @param qrData 스캔된 QR 코드 데이터 (appId 포함)
     */
    private fun onQrCodeScanned(qrData: String) {
        _state.value = QrScanState.Scanned(qrData)
        // 자동으로 디바이스 등록 시도
        registerDevice()
    }

    /**
     * 디바이스 등록
     * api_spec.md의 디바이스 등록 API를 호출
     */
    private fun registerDevice() {
        viewModelScope.launch {
            val currentState = _state.value
            if (currentState is QrScanState.Scanned) {
                _state.value = QrScanState.RegisteringDevice
                
                // TODO: 디바이스 등록 API 호출
                // api_spec.md의 1.8 디바이스 등록 API 사용
                // RegisterDeviceRequest:
                //   - deviceId: 디바이스 고유 ID
                //   - deviceType: "MOBILE"
                //   - platform: "ios" 또는 "android"
                //   - appId: QR 코드에서 획득한 appId
                
                try {
                    // 임시로 성공 처리
                    // 실제 구현 시 Repository를 통해 API 호출
                    _state.value = QrScanState.DeviceRegistered(currentState.qrData)
                } catch (e: Exception) {
                    _state.value = QrScanState.Error("디바이스 등록에 실패했습니다: ${e.message}")
                }
            }
        }
    }

    /**
     * 화면 닫기
     */
    private fun close() {
        _state.value = QrScanState.Idle
    }
}
