package com.smartglass.project.domain.usecase

import com.smartglass.project.domain.repository.AuthRepository
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.random.Random

/**
 * 디바이스 등록 UseCase
 * features_spec.md: 2.2 디바이스 등록 프로세스
 * 1. QR 코드 스캔 → 디바이스 등록 API 호출
 * 2. 등록 성공 → 앱 등록 API 호출
 * 3. 등록 성공 → 브랜딩 이미지 다운로드 (TODO)
 */
class RegisterDeviceUseCase(
    private val authRepository: AuthRepository
) {
    
    /**
     * QR 코드 데이터 파싱 및 디바이스 등록
     * 
     * QR 코드 데이터 형식 (features_spec.md):
     * - JSON 형식, uuid 필수
     * - 디바이스 타입: GLASS 또는 MOBILE
     * 
     * @param qrCodeData QR 코드 스캔 결과 (JSON 문자열)
     * @param deviceId 디바이스 고유 ID (UUID 또는 기기 ID)
     * @param appVersion 앱 버전
     * @return Result<String> 성공 시 appId 반환
     */
    suspend operator fun invoke(
        qrCodeData: String,
        deviceId: String,
        appVersion: String = "1.0.0"
    ): Result<String> {
        return try {
            println("🟢 RegisterDeviceUseCase: QR 코드 데이터 파싱 시작")
            println("🟢 QR 데이터: $qrCodeData")
            
            // 1. QR 코드 데이터 파싱
            val json = Json { ignoreUnknownKeys = true }
            val qrJson = json.parseToJsonElement(qrCodeData).jsonObject
            
            val uuid = qrJson["uuid"]?.jsonPrimitive?.content 
                ?: return Result.failure(Exception("QR 코드에 uuid가 없습니다"))
            
            println("🟢 파싱 성공 - uuid: $uuid")
            println("🟢 deviceId: $deviceId")
            
            // 2. 디바이스 등록
            val deviceType = "MOBILE" // TODO: 플랫폼에 따라 동적으로 설정
            println("🟢 디바이스 등록 API 호출 시작 (uuid: $uuid, deviceId: $deviceId, deviceType: $deviceType)")
            
            val deviceResult = authRepository.registerDevice(
                uuid = uuid,
                deviceId = deviceId,
                deviceType = deviceType
            )
            
            if (deviceResult.isFailure) {
                val error = deviceResult.exceptionOrNull()
                println("❌ 디바이스 등록 실패: ${error?.message}")
                return Result.failure(
                    error ?: Exception("디바이스 등록 실패")
                )
            }
            
            val registeredDeviceId = deviceResult.getOrThrow()
            println("🟢 디바이스 등록 성공 - registeredDeviceId: $registeredDeviceId")
            
            // 3. 앱 등록
            val appId = generateAppId()
            val appType = "MOBILE"
            println("🟢 앱 등록 API 호출 시작 (deviceId: $registeredDeviceId, appId: $appId)")
            
            val appResult = authRepository.registerApp(
                deviceId = registeredDeviceId,
                appId = appId,
                appType = appType,
                appVersion = appVersion
            )
            
            if (appResult.isFailure) {
                val error = appResult.exceptionOrNull()
                println("❌ 앱 등록 실패: ${error?.message}")
                return Result.failure(
                    error ?: Exception("앱 등록 실패")
                )
            }
            
            val finalAppId = appResult.getOrThrow()
            println("🟢 앱 등록 성공 - appId: $finalAppId")
            
            // 4. 브랜딩 이미지 다운로드 (TODO)
            
            // 5. 성공
            Result.success(finalAppId)
            
        } catch (e: Exception) {
            println("❌ RegisterDeviceUseCase 예외 발생: ${e.message}")
            e.printStackTrace()
            Result.failure(Exception("QR 코드 데이터 파싱 실패: ${e.message}"))
        }
    }
    
    private fun generateAppId(): String {
        // UUID 형식의 앱 ID 생성
        val random = Random.Default.nextLong()
        return "app-${random.toString(16)}"
    }
}
