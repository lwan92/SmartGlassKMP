package com.smartglass.project.presentation.qrscan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * QR 스캔 화면
 * features_spec.md 및 Figma 디자인을 기반으로 구현
 * - 카메라 권한 확인
 * - QR 코드 스캔 UI 표시
 * - 스캔 완료 시 디바이스 등록 API 호출
 */
@Composable
fun QrScanScreen(
    viewModel: QrScanViewModel,
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
    onDeviceRegistered: (appId: String) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    // 스캔 시작
    LaunchedEffect(Unit) {
        viewModel.handleIntent(QrScanIntent.StartScanning)
    }

    // 디바이스 등록 성공 시 콜백 호출
    LaunchedEffect(state) {
        when (state) {
            is QrScanState.DeviceRegistered -> {
                onDeviceRegistered(state.appId)
            }
            else -> {}
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (state) {
                is QrScanState.Scanning -> {
                    Text(
                        text = "QR 코드를 스캔해주세요",
                        color = Color.White,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    // TODO: 실제 QR 코드 스캔 UI 구현
                    // - 카메라 프리뷰
                    // - 스캔 영역 표시
                    // - 스캔 완료 시 QrScanIntent.QrCodeScanned 호출
                }
                is QrScanState.RegisteringDevice -> {
                    Text(
                        text = "디바이스 등록 중...",
                        color = Color.White,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
                is QrScanState.Error -> {
                    Text(
                        text = state.message,
                        color = Color.Red,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
                else -> {}
            }
        }
    }
}
