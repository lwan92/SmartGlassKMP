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
import platform.darwin.dispatch_queue_t

/**
 * iOS QR 스캐너 구현
 * AVFoundation의 AVCaptureSession 사용
 * QR 코드 스캔 개선 버전 - Delegate 메모리 관리 수정
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
    
    // ✅ Delegate를 remember로 관리하여 GC 방지
    val metadataDelegate = remember {
        object : NSObject(), AVCaptureMetadataOutputObjectsDelegateProtocol {
            override fun captureOutput(
                output: AVCaptureOutput,
                didOutputMetadataObjects: List<*>,
                fromConnection: AVCaptureConnection
            ) {
                NSLog("📡 QrScanner: captureOutput 콜백 호출됨! (메타데이터 개수: ${didOutputMetadataObjects.size})")
                
                if (hasScanned) {
                    NSLog("⚠️ QrScanner: 이미 스캔 완료, 무시")
                    return
                }
                
                val metadataObjects = didOutputMetadataObjects
                if (metadataObjects.isNotEmpty()) {
                    NSLog("📱 QrScanner: ${metadataObjects.size}개의 메타데이터 객체 감지!")
                    
                    metadataObjects.forEach { obj ->
                        NSLog("🔍 QrScanner: 객체 타입 - ${obj}")
                    }
                    
                    val metadataObj = metadataObjects.first() as? AVMetadataMachineReadableCodeObject
                    if (metadataObj != null) {
                        NSLog("✅ QrScanner: AVMetadataMachineReadableCodeObject로 캐스팅 성공")
                        metadataObj.stringValue?.let { qrCode ->
                            NSLog("✅✅✅ QrScanner: QR 코드 스캔 성공! ✅✅✅")
                            NSLog("📱 QR 데이터: $qrCode")
                            hasScanned = true
                            dispatch_async(dispatch_get_main_queue()) {
                                onQrCodeScanned(qrCode)
                            }
                        } ?: NSLog("❌ QrScanner: stringValue가 null")
                    } else {
                        NSLog("❌ QrScanner: AVMetadataMachineReadableCodeObject로 캐스팅 실패")
                    }
                } else {
                    NSLog("ℹ️ QrScanner: 메타데이터 객체가 비어있음")
                }
            }
        }
    }
    
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
            view.backgroundColor = platform.UIKit.UIColor.blackColor
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
            
            // 세션 설정 시작
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
            NSLog("✅ QrScanner: 세션 설정 완료")
            
            // ✅ remember로 관리되는 delegate 사용
            metadataOutput.setMetadataObjectsDelegate(
                metadataDelegate,
                dispatch_get_main_queue()
            )
            NSLog("✅ QrScanner: 델리게이트 설정 완료 (remember로 관리됨)")
            
            // QR 코드 타입 설정 (델리게이트 설정 후)
            val availableTypes = metadataOutput.availableMetadataObjectTypes
            NSLog("📋 QrScanner: 사용 가능한 메타데이터 타입: ${availableTypes.size}개")
            
            if (availableTypes.contains(AVMetadataObjectTypeQRCode)) {
                metadataOutput.setMetadataObjectTypes(listOf(AVMetadataObjectTypeQRCode))
                NSLog("✅ QrScanner: QR 코드 타입 설정 완료")
            } else {
                NSLog("❌ QrScanner: QR 코드 타입을 지원하지 않음")
                dispatch_async(dispatch_get_main_queue()) {
                    onError("QR 코드 스캔을 지원하지 않는 기기입니다")
                }
                return@UIKitView view
            }
            
            view
        },
        modifier = modifier,
        update = { view ->
            NSLog("🎥 QrScanner: UIKitView update 호출")
            
            // 프리뷰 레이어 설정 (메인 스레드에서)
            dispatch_async(dispatch_get_main_queue()) {
                captureSession?.let { session ->
                    // 기존 레이어 제거
                    view.layer.sublayers?.forEach { layer ->
                        if (layer is AVCaptureVideoPreviewLayer) {
                            (layer as CALayer).removeFromSuperlayer()
                        }
                    }
                    
                    // 새 프리뷰 레이어 생성
                    val previewLayer = AVCaptureVideoPreviewLayer(session = session)
                    previewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
                    
                    // 화면 크기 가져오기
                    val screenBounds = UIScreen.mainScreen.bounds
                    screenBounds.useContents {
                        NSLog("🎥 QrScanner: 화면 크기 - width: ${size.width}, height: ${size.height}")
                        previewLayer.frame = CGRectMake(0.0, 0.0, size.width, size.height)
                    }
                    
                    view.layer.insertSublayer(previewLayer, 0u)
                    NSLog("✅ QrScanner: 프리뷰 레이어 추가 완료")
                    
                    // rectOfInterest 설정 (전체 화면)
                    captureSession?.outputs?.forEach { output ->
                        if (output is AVCaptureMetadataOutput) {
                            (output as AVCaptureMetadataOutput).rectOfInterest = CGRectMake(0.0, 0.0, 1.0, 1.0)
                            NSLog("✅ QrScanner: rectOfInterest 설정 완료 (전체 화면)")
                        }
                    }
                    
                    // 카메라 세션 시작
                    if (!session.running) {
                        session.startRunning()
                        view.backgroundColor = platform.UIKit.UIColor.clearColor
                        NSLog("✅✅✅ QrScanner: 카메라 세션 실행 중! ✅✅✅")
                        NSLog("📱 QR 코드를 카메라에 비춰주세요...")
                    }
                }
            }
        },
        onRelease = { view ->
            NSLog("🎥 QrScanner: UIKitView 해제")
        }
    )
}
