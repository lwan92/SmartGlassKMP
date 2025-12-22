package com.smartglass.project.presentation.qrscan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartglass.project.data.local.PreferencesManager
import com.smartglass.project.domain.usecase.RegisterDeviceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class QrScanViewModel(
    private val registerDeviceUseCase: RegisterDeviceUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    private val _state = MutableStateFlow<QrScanState>(QrScanState.Idle)
    val state: StateFlow<QrScanState> = _state.asStateFlow()

    fun handleIntent(intent: QrScanIntent) {
        when (intent) {
            is QrScanIntent.StartScanning -> startScanning()
            is QrScanIntent.QrCodeScanned -> onQrCodeScanned(intent.qrData)
            is QrScanIntent.ScanError -> onScanError(intent.error)
            is QrScanIntent.RegisterDevice -> registerDevice()
            is QrScanIntent.Close -> closeScanner()
        }
    }

    private fun startScanning() {
        _state.value = QrScanState.Scanning
    }

    private fun onQrCodeScanned(qrData: String) {
        viewModelScope.launch {
            _state.value = QrScanState.RegisteringDevice
            
            // TODO: 실제 deviceId 가져오기 (플랫폼별로 다름)
            val randomId = Random.Default.nextLong()
            val deviceId = "device-${randomId.toString(16)}"
            
            val result = registerDeviceUseCase(
                qrCodeData = qrData,
                deviceId = deviceId,
                appVersion = "1.0.0"
            )
            
            result.fold(
                onSuccess = { appId ->
                    // 디바이스 등록 성공 → PreferencesManager에 저장
                    preferencesManager.setDeviceRegistered(true)
                    
                    _state.value = QrScanState.DeviceRegistered(appId = appId)
                },
                onFailure = { error ->
                    _state.value = QrScanState.Error(
                        error.message ?: "디바이스 등록에 실패했습니다"
                    )
                }
            )
        }
    }
    
    private fun onScanError(error: String) {
        _state.value = QrScanState.Error(error)
    }

    private fun registerDevice() {
        _state.value = QrScanState.RegisteringDevice
        
        // TODO: 실제 디바이스 등록 로직 구현
        viewModelScope.launch {
            kotlinx.coroutines.delay(1000)
            _state.value = QrScanState.DeviceRegistered("test-app-id")
        }
    }

    private fun closeScanner() {
        _state.value = QrScanState.Idle
    }
}
