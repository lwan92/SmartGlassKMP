package com.smartglass.project.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.smartglass.project.ui.component.CommonButton
import com.smartglass.project.ui.component.ButtonStyle
import com.smartglass.project.ui.theme.*

/**
 * 디바이스 등록 안내 팝업
 * Figma: https://www.figma.com/design/diRXHJDeWdqsBzI1qcA8I4/SmartGlass-Design?node-id=7769-9215
 * ui_design_spec.md: 6. 팝업 (Popup/Dialog) 참고
 */
@Composable
fun DeviceRegistrationPopup(
    onQrScanClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Black50),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        color = Gray0,
                        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // 제목
                Text(
                    text = "사용자 기기 등록 안내",
                    style = AppTypography.B16px,
                    color = Gray950
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // 환영 메시지 및 설명 (Figma 디자인)
                Column(
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    Text(
                        text = "환영합니다 !",
                        style = AppTypography.M14px,
                        color = Gray950,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "NeoInspection 모바일앱을 사용하려면 기기 등록이 필요합니다. (*앱 설치 후 최초 1회만 진행)",
                        style = AppTypography.M14px,
                        color = Gray950,
                        lineHeight = 18.sp
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // 안내 텍스트 (번호가 매겨진 리스트)
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "아래 안내에 따라 진행해주시기 바랍니다.",
                        style = AppTypography.M12px,
                        color = Gray900,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "1. 먼저 NeoInspection 포털 접속 후 사용자 계정 로그인",
                        style = AppTypography.M12px,
                        color = Gray900,
                        lineHeight = 18.sp
                    )
                    Text(
                        text = "2. 포털 홈 화면의 'QR코드' 아이콘을 클릭",
                        style = AppTypography.M12px,
                        color = Gray900,
                        lineHeight = 18.sp
                    )
                    Text(
                        text = "3. 아래 'QR코드 스캔' 버튼 선택 후 포털의 QR코드 스캔",
                        style = AppTypography.M12px,
                        color = Gray900,
                        lineHeight = 18.sp
                    )
                    Text(
                        text = "4. 스캔 정상처리 시 사용자 기기 등록 자동 완료",
                        style = AppTypography.M12px,
                        color = Gray900,
                        lineHeight = 18.sp
                    )
                    Text(
                        text = "5. 기기 등록 후 모바일앱에서 사용자 계정으로 로그인",
                        style = AppTypography.M12px,
                        color = Gray900,
                        lineHeight = 18.sp
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // QR 코드 스캔 버튼 (Figma 디자인: 버튼 하나만, QR 아이콘 포함)
                CommonButton(
                    text = "QR코드 스캔",
                    onClick = onQrScanClick,
                    modifier = Modifier.fillMaxWidth(),
                    style = ButtonStyle.Contained
                )
            }
        }
    }
}
