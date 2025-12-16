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

@Composable
fun QrScanScreen(
    viewModel: QrScanViewModel,
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
    onDeviceRegistered: (appId: String) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(QrScanIntent.StartScanning)
    }

    LaunchedEffect(state) {
        val deviceRegisteredState = state as? QrScanState.DeviceRegistered
        deviceRegisteredState?.let {
            onDeviceRegistered(it.appId)
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
                    val errorState = state
                    Text(
                        text = errorState.message,
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
