package com.smartglass.project.platform.qr

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.*
import platform.CoreGraphics.CGRect
import platform.Foundation.NSError
import platform.UIKit.UIView
import platform.darwin.NSObject
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_async
import platform.QuartzCore.CALayer

/**
 * iOS QR 스캐너 구현
 * AVFoundation의 AVCaptureSession 사용
 */
@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun QrScanner(
    modifier: Modifier,
    onQrCodeScanned: (String) -> Unit,
    onError: (String) -> Unit
) {
    var hasScanned by remember { mutableStateOf(false) }
    
    DisposableEffect(Unit) {
        hasScanned = false
        onDispose { }
    }
    
    UIKitView(
        factory = {
            val view = UIView()
            val captureSession = AVCaptureSession()
            
            // 카메라 디바이스 설정
            val videoDevice = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeVideo)
            
            if (videoDevice == null) {
                dispatch_async(dispatch_get_main_queue()) {
                    onError("카메라를 찾을 수 없습니다")
                }
                return@UIKitView view
            }
            
            // 카메라 입력 설정
            var error: NSError? = null
            val videoInput = AVCaptureDeviceInput.deviceInputWithDevice(
                device = videoDevice,
                error = null
            ) as? AVCaptureDeviceInput
            
            if (videoInput == null) {
                dispatch_async(dispatch_get_main_queue()) {
                    onError("카메라 접근 실패")
                }
                return@UIKitView view
            }
            
            if (captureSession.canAddInput(videoInput as AVCaptureInput)) {
                captureSession.addInput(videoInput as AVCaptureInput)
            } else {
                dispatch_async(dispatch_get_main_queue()) {
                    onError("카메라 입력 추가 실패")
                }
                return@UIKitView view
            }
            
            // 메타데이터 출력 설정
            val metadataOutput = AVCaptureMetadataOutput()
            
            if (captureSession.canAddOutput(metadataOutput as AVCaptureOutput)) {
                captureSession.addOutput(metadataOutput as AVCaptureOutput)
                
                // QR 코드 타입 설정
                metadataOutput.setMetadataObjectTypes(
                    listOf(AVMetadataObjectTypeQRCode)
                )
                
                // 메타데이터 델리게이트 설정
                val delegate = object : NSObject(), AVCaptureMetadataOutputObjectsDelegateProtocol {
                    override fun captureOutput(
                        output: AVCaptureOutput,
                        didOutputMetadataObjects: List<*>,
                        fromConnection: AVCaptureConnection
                    ) {
                        if (hasScanned) return
                        
                        val metadataObjects = didOutputMetadataObjects
                        if (metadataObjects.isNotEmpty()) {
                            val metadataObj = metadataObjects.first() as? AVMetadataMachineReadableCodeObject
                            metadataObj?.stringValue?.let { qrCode ->
                                hasScanned = true
                                dispatch_async(dispatch_get_main_queue()) {
                                    onQrCodeScanned(qrCode)
                                }
                            }
                        }
                    }
                }
                
                metadataOutput.setMetadataObjectsDelegate(
                    delegate,
                    dispatch_get_main_queue()
                )
            } else {
                dispatch_async(dispatch_get_main_queue()) {
                    onError("메타데이터 출력 추가 실패")
                }
                return@UIKitView view
            }
            
            // 프리뷰 레이어 설정
            val previewLayer = AVCaptureVideoPreviewLayer(session = captureSession)
            previewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
            previewLayer.frame = view.bounds
            view.layer.addSublayer(previewLayer)
            
            // 캡처 세션 시작
            dispatch_async(dispatch_get_main_queue()) {
                captureSession.startRunning()
            }
            
            view
        },
        modifier = modifier,
        update = { view ->
            // 뷰 업데이트 시 프리뷰 레이어 크기 조정
            view.layer.sublayers?.firstOrNull()?.let { layer ->
                (layer as? AVCaptureVideoPreviewLayer)?.frame = view.bounds
            }
        },
        onRelease = { view ->
            // 리소스 정리
            view.layer.sublayers?.forEach { sublayer ->
                (sublayer as? CALayer)?.removeFromSuperlayer()
            }
        }
    )
}
