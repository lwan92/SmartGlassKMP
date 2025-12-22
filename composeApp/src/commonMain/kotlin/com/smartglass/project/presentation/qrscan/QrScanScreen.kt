package com.smartglass.project.presentation.qrscan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartglass.project.platform.qr.QrScanner
import com.smartglass.project.ui.theme.AppTypography
import com.smartglass.project.ui.theme.Primary
import org.jetbrains.compose.resources.painterResource
import smartglass.composeapp.generated.resources.Res

/**
 * QR 스캔 화면
 * Figma: https://www.figma.com/design/diRXHJDeWdqsBzI1qcA8I4/SmartGlass-Design?node-id=8135-42836
 * iOS: AVFoundation 기반 실제 QR 스캔
 * Android: Placeholder
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
            .background(Color.Black)
    ) {
        // 카메라 프리뷰
        when (val currentState = state) {
            is QrScanState.Idle, is QrScanState.Scanning -> {
                // 실제 QR 스캔 UI
                QrScanner(
                    modifier = Modifier.fillMaxSize(),
                    onQrCodeScanned = { qrCode ->
                        viewModel.handleIntent(QrScanIntent.QrCodeScanned(qrCode))
                    },
                    onError = { error ->
                        viewModel.handleIntent(QrScanIntent.ScanError(error))
                    }
                )
            }
            
            is QrScanState.RegisteringDevice -> {
                // 등록 중 로딩
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
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
            }
            
            is QrScanState.Error -> {
                // 에러 표시
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "⚠️",
                            fontSize = 48.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = currentState.message,
                            color = Color.Red,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                        
                        // 3초 후 다시 스캔 모드로
                        LaunchedEffect(Unit) {
                            kotlinx.coroutines.delay(3000)
                            viewModel.handleIntent(QrScanIntent.StartScanning)
                        }
                    }
                }
            }
            
            else -> {}
        }
        
        // 상단 헤더 (Figma 디자인)
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
        ) {
            // Status Bar 영역
            Spacer(modifier = Modifier.height(24.dp))
            
            // 헤더 바
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color.Black.copy(alpha = 0.05f))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 플래시 버튼 (비활성)
                Box(modifier = Modifier.size(24.dp))
                
                // 타이틀
                Text(
                    text = "QR 스캔",
                    style = AppTypography.SB16px.copy(fontSize = 20.sp),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                
                // 닫기 버튼 (X)
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onClose() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✕",
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
