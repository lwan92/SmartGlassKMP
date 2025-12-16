package com.smartglass.project.presentation.qrscan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QrScanViewModel : ViewModel() {
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
            
            // TODO: 실제 디바이스 등록 API 호출
            // val result = registerDeviceUseCase(qrData)
            
            // 임시로 성공 처리
            kotlinx.coroutines.delay(1000)
            _state.value = QrScanState.DeviceRegistered(appId = qrData)
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
