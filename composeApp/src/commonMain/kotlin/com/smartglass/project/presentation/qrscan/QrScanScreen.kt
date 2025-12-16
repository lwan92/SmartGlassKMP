package com.smartglass.project.presentation.qrscan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartglass.project.ui.theme.Primary
import io.github.kalinjul.easyqrscan.QrCodeScanner

/**
 * QR 스캔 화면
 * EasyQRScan 라이브러리 사용
 * features_spec.md: 2. 로그인 화면 - QR 코드 스캔 참고
 */
@Composable
fun QrScanScreen(
    viewModel: QrScanViewModel,
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
    onDeviceRegistered: (appId: String) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    
    // 디바이스 등록 성공 시 콜백
    LaunchedEffect(state) {
        when (val currentState = state) {
            is QrScanState.DeviceRegistered -> {
                onDeviceRegistered(currentState.appId)
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
        when (val currentState = state) {
            is QrScanState.Idle, is QrScanState.Scanning -> {
                // QR 스캔 UI (EasyQRScan)
                QrCodeScanner(
                    modifier = Modifier.fillMaxSize(),
                    onScanned = { qrData ->
                        viewModel.handleIntent(QrScanIntent.QrCodeScanned(qrData))
                    }
                )
                
                // 안내 오버레이
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 48.dp)
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "QR 코드를 스캔해주세요",
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "관리자로부터 받은 QR 코드를\n프레임 안에 맞춰주세요",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
            
            is QrScanState.RegisteringDevice -> {
                // 등록 중 로딩
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = Primary,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "디바이스 등록 중...",
                        color = Color.White,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
            
            is QrScanState.Error -> {
                // 에러 표시
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = currentState.message,
                        color = Color.Red,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(32.dp)
                    )
                    
                    // 3초 후 다시 스캔 모드로
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(3000)
                        viewModel.handleIntent(QrScanIntent.StartScanning)
                    }
                }
            }
            
            else -> {}
        }
    }
}
