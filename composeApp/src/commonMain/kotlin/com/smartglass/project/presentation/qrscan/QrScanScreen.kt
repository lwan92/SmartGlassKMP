package com.smartglass.project.presentation.qrscan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
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

/**
 * QR 스캔 화면
 * TODO: 플랫폼별 QR 스캔 구현 필요
 * - Android: CameraX + MLKit
 * - iOS: AVFoundation
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
                // TODO: 실제 QR 스캔 UI 구현
                // 임시 UI: QR 스캔 placeholder
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // 카메라 프리뷰 placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .background(Color.DarkGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "📷",
                                fontSize = 64.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "QR 코드 스캔 화면",
                                color = Color.White,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "카메라 프리뷰 영역",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 14.sp
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    Text(
                        text = "QR 코드를 스캔해주세요",
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "관리자로부터 받은 QR 코드를\n프레임 안에 맞춰주세요",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // 테스트용 버튼
                    Button(
                        onClick = {
                            // 테스트용 QR 데이터
                            viewModel.handleIntent(QrScanIntent.QrCodeScanned("test-app-id-12345"))
                        }
                    ) {
                        Text("테스트용 QR 스캔 시뮬레이션")
                    }
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
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(onClick = {
                        viewModel.handleIntent(QrScanIntent.StartScanning)
                    }) {
                        Text("다시 시도")
                    }
                }
            }
            
            else -> {}
        }
    }
}
