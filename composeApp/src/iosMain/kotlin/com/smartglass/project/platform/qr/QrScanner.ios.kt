package com.smartglass.project.platform.qr

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.AVFoundation.*
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSLog
import platform.UIKit.UIView
import platform.darwin.NSObject
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_async
import platform.QuartzCore.CALayer
import platform.UIKit.UIScreen

/**
 * iOS QR 스캐너 구현
 * AVFoundation의 AVCaptureSession 사용
 * 렌더링 및 디버깅 강화 버전
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
        NSLog("🎥 QrScanner: Composable 시작")
        hasScanned = false
        
        onDispose {
            NSLog("🎥 QrScanner: Composable 종료, 세션 정리")
            captureSession?.stopRunning()
        }
    }
    
    UIKitView(
        factory = {
            NSLog("🎥 QrScanner: UIKitView factory 시작")
            
            val view = UIView()
            view.backgroundColor = platform.UIKit.UIColor.redColor // 디버그용 빨간색
            view.clipsToBounds = true
            
            NSLog("🎥 QrScanner: UIView 생성 완료")
            
            // 카메라 디바이스 확인
            val videoDevice = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeVideo)
            
            if (videoDevice == null) {
                NSLog("❌ QrScanner: 카메라 디바이스를 찾을 수 없음")
                dispatch_async(dispatch_get_main_queue()) {
                    onError("카메라를 찾을 수 없습니다")
                }
                return@UIKitView view
            }
            
            NSLog("✅ QrScanner: 카메라 디바이스 찾음")
            
            // 캡처 세션 생성
            val session = AVCaptureSession()
            session.sessionPreset = AVCaptureSessionPresetHigh
            captureSession = session
            
            NSLog("🎥 QrScanner: AVCaptureSession 생성 완료")
            
            // 카메라 입력 설정
            val videoInput = try {
                AVCaptureDeviceInput.deviceInputWithDevice(
                    device = videoDevice,
                    error = null
                ) as? AVCaptureDeviceInput
            } catch (e: Exception) {
                NSLog("❌ QrScanner: 카메라 입력 생성 예외 - ${e.message}")
                dispatch_async(dispatch_get_main_queue()) {
                    onError("카메라 접근 실패: ${e.message}")
                }
                return@UIKitView view
            }
            
            if (videoInput == null) {
                NSLog("❌ QrScanner: 카메라 입력이 null")
                dispatch_async(dispatch_get_main_queue()) {
                    onError("카메라 입력 생성 실패")
                }
                return@UIKitView view
            }
            
            NSLog("✅ QrScanner: 카메라 입력 생성 완료")
            
            // 세션 설정
            session.beginConfiguration()
            
            if (session.canAddInput(videoInput as AVCaptureInput)) {
                session.addInput(videoInput as AVCaptureInput)
                NSLog("✅ QrScanner: 카메라 입력 추가 성공")
            } else {
                session.commitConfiguration()
                NSLog("❌ QrScanner: 카메라 입력 추가 실패")
                dispatch_async(dispatch_get_main_queue()) {
                    onError("카메라 입력 추가 실패")
                }
                return@UIKitView view
            }
            
            // 메타데이터 출력 설정
            val metadataOutput = AVCaptureMetadataOutput()
            
            if (session.canAddOutput(metadataOutput as AVCaptureOutput)) {
                session.addOutput(metadataOutput as AVCaptureOutput)
                NSLog("✅ QrScanner: 메타데이터 출력 추가 성공")
            } else {
                session.commitConfiguration()
                NSLog("❌ QrScanner: 메타데이터 출력 추가 실패")
                dispatch_async(dispatch_get_main_queue()) {
                    onError("메타데이터 출력 추가 실패")
                }
                return@UIKitView view
            }
            
            session.commitConfiguration()
            
            // QR 코드 타입 설정
            metadataOutput.setMetadataObjectTypes(
                listOf(AVMetadataObjectTypeQRCode)
            )
            NSLog("✅ QrScanner: QR 코드 타입 설정 완료")
            
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
                        NSLog("📱 QrScanner: QR 코드 감지됨!")
                        val metadataObj = metadataObjects.first() as? AVMetadataMachineReadableCodeObject
                        metadataObj?.stringValue?.let { qrCode ->
                            NSLog("✅ QrScanner: QR 코드 스캔 성공 - $qrCode")
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
            
            // 프리뷰 레이어 생성 및 설정 (메인 스레드)
            dispatch_async(dispatch_get_main_queue()) {
                val screenBounds = UIScreen.mainScreen.bounds
                
                screenBounds.useContents {
                    val screenWidth = this.size.width
                    val screenHeight = this.size.height
                    
                    NSLog("🎥 QrScanner: 화면 크기 - width: $screenWidth, height: $screenHeight")
                    
                    val previewLayer = AVCaptureVideoPreviewLayer(session = session)
                    previewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
                    
                    // 명시적으로 프레임 설정
                    previewLayer.frame = CGRectMake(
                        0.0,
                        0.0,
                        screenWidth,
                        screenHeight
                    )
                    
                    view.layer.insertSublayer(previewLayer, 0u)
                    
                    NSLog("✅ QrScanner: 프리뷰 레이어 추가 완료")
                }
                
                // 세션 시작
                session.startRunning()
                
                if (session.running) {
                    NSLog("✅✅✅ QrScanner: 카메라 세션 실행 중! ✅✅✅")
                    
                    // 배경색을 투명으로 변경 (프리뷰가 보이도록)
                    view.backgroundColor = platform.UIKit.UIColor.clearColor
                } else {
                    NSLog("❌ QrScanner: 카메라 세션 시작 실패")
                    onError("카메라 세션 시작 실패")
                }
            }
            
            view
        },
        modifier = modifier,
        update = { view ->
            // 뷰 크기 변경 시 프리뷰 레이어도 업데이트
            view.layer.sublayers?.firstOrNull()?.let { layer ->
                (layer as? AVCaptureVideoPreviewLayer)?.let { previewLayer ->
                    previewLayer.frame = view.bounds
                    NSLog("🔄 QrScanner: 프리뷰 레이어 크기 업데이트")
                }
            }
        },
        onRelease = { view ->
            NSLog("🧹 QrScanner: onRelease - 리소스 정리")
            captureSession?.stopRunning()
            view.layer.sublayers?.forEach { sublayer ->
                (sublayer as? CALayer)?.removeFromSuperlayer()
            }
        }
    )
}
