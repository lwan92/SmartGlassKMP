package com.smartglass.project.platform.qr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Android QR 스캐너 구현
 * TODO: CameraX + MLKit 구현 필요
 */
@Composable
actual fun QrScanner(
    modifier: Modifier,
    onQrCodeScanned: (String) -> Unit,
    onError: (String) -> Unit
) {
    // Android는 아직 placeholder
    Box(
        modifier = modifier
            .fillMaxSize()
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
                text = "Android QR 스캐너",
                color = Color.White,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "TODO: CameraX 구현",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {
                onQrCodeScanned("test-android-qr-code")
            }) {
                Text("테스트 QR 스캔")
            }
        }
    }
}
