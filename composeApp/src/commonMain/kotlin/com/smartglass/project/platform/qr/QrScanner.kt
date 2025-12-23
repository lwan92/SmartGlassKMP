package com.smartglass.project.platform.qr

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * 플랫폼별 QR 스캐너 인터페이스
 * iOS: AVFoundation 기반
 * Android: CameraX + MLKit 기반 (미구현)
 */
@Composable
expect fun QrScanner(
    modifier: Modifier = Modifier,
    onQrCodeScanned: (String) -> Unit,
    onError: (String) -> Unit = {}
)
