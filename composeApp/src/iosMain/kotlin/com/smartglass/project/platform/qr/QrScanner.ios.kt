package com.smartglass.project.platform.qr

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.*
import platform.Foundation.NSError
import platform.UIKit.UIView
import platform.darwin.NSObject
import platform.darwin.dispatch_get_global_queue
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_async
import platform.darwin.DISPATCH_QUEUE_PRIORITY_DEFAULT
import platform.QuartzCore.CALayer

/**
 * iOS QR 스캐너 구현
 * AVFoundation의 AVCaptureSession 사용
 * 디버깅 강화 버전
 */
@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun QrScanner(
    modifier: Modifier,
    onQrCodeScanned: (String) -> Unit,
    onError: (String) -> Unit
) {
    var hasScanned by remember { mutableStateOf(false) }
    var captureSession: AVCaptureSession? by remember { mutableStateOf(null) }
    
    DisposableEffect(Unit) {
        hasScanned = false
        
        onDispose {
            // 세션 정리
            captureSession?.stopRunning()
        }
    }
    
    UIKitView(
        factory = {
            val view = UIView()
            view.backgroundColor = platform.UIKit.UIColor.blackColor
            
            // 카메라 디바이스 설정
            val videoDevice = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeVideo)
            
            if (videoDevice == null) {
                dispatch_async(dispatch_get_main_queue()) {
                    onError("카메라를 찾을 수 없습니다")
                }
                return@UIKitView view
            }
            
            // 캡처 세션 생성
            val session = AVCaptureSession()
            captureSession = session
            
            // 카메라 입력 설정
            val videoInput = try {
                AVCaptureDeviceInput.deviceInputWithDevice(
                    device = videoDevice,
                    error = null
                ) as? AVCaptureDeviceInput
            } catch (e: Exception) {
                dispatch_async(dispatch_get_main_queue()) {
                    onError("카메라 접근 실패: ${e.message}")
                }
                return@UIKitView view
            }
            
            if (videoInput == null) {
                dispatch_async(dispatch_get_main_queue()) {
                    onError("카메라 입력 생성 실패")
                }
                return@UIKitView view
            }
            
            // 세션에 입력 추가
            session.beginConfiguration()
            
            if (session.canAddInput(videoInput as AVCaptureInput)) {
                session.addInput(videoInput as AVCaptureInput)
            } else {
                session.commitConfiguration()
                dispatch_async(dispatch_get_main_queue()) {
                    onError("카메라 입력 추가 실패")
                }
                return@UIKitView view
            }
            
            // 메타데이터 출력 설정
            val metadataOutput = AVCaptureMetadataOutput()
            
            if (session.canAddOutput(metadataOutput as AVCaptureOutput)) {
                session.addOutput(metadataOutput as AVCaptureOutput)
            } else {
                session.commitConfiguration()
                dispatch_async(dispatch_get_main_queue()) {
                    onError("메타데이터 출력 추가 실패")
                }
                return@UIKitView view
            }
            
            session.commitConfiguration()
            
            // QR 코드 타입 설정 (세션 설정 후)
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
            
            // 프리뷰 레이어 설정
            val previewLayer = AVCaptureVideoPreviewLayer(session = session)
            previewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
            previewLayer.frame = view.bounds
            view.layer.addSublayer(previewLayer)
            
            // 백그라운드 스레드에서 캡처 세션 시작
            dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT.toLong(), 0u)) {
                session.startRunning()
                
                // 세션이 제대로 시작되었는지 확인
                if (!session.running) {
                    dispatch_async(dispatch_get_main_queue()) {
                        onError("카메라 세션 시작 실패")
                    }
                }
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
            captureSession?.stopRunning()
            view.layer.sublayers?.forEach { sublayer ->
                (sublayer as? CALayer)?.removeFromSuperlayer()
            }
        }
    )
}
