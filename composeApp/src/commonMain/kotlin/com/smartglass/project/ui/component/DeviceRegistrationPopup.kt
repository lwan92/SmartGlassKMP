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
import androidx.compose.ui.window.Dialog
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
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                    )
                    .padding(24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // 제목
                Text(
                    text = "사용자 기기 등록 안내",
                    style = AppTypography.B16px,
                    color = Gray950
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // 설명
                Text(
                    text = "기기 등록이 완료되지 않았습니다.\n관리자로부터 받은 QR코드를 스캔해 주세요.",
                    style = AppTypography.M14px,
                    color = Gray950,
                    lineHeight = AppTypography.M14px.lineHeight
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // 버튼 영역
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // 취소 버튼
                    CommonButton(
                        text = "취소",
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        backgroundColor = Color.Transparent,
                        textColor = Primary,
                        borderColor = Primary
                    )
                    
                    // QR 코드 스캔 버튼
                    CommonButton(
                        text = "QR 코드 스캔",
                        onClick = onQrScanClick,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
