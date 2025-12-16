package com.smartglass.project.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.smartglass.project.ui.theme.*

/**
 * 계정 찾기 안내 팝업
 * Figma: https://www.figma.com/design/diRXHJDeWdqsBzI1qcA8I4/SmartGlass-Design?node-id=8685-133732
 * ui_design_spec.md: 6. 팝업 (Popup/Dialog) 참고
 */
@Composable
fun FindAccountPopup(
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onConfirm) {
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
                    text = "아이디 / 비밀번호 찾기",
                    style = AppTypography.B16px,
                    color = Gray950
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // 설명
                Text(
                    text = "아이디 / 비밀번호를 잊으셨나요?\n관리자에게 문의해 주세요.",
                    style = AppTypography.M14px,
                    color = Gray950,
                    lineHeight = AppTypography.M14px.lineHeight
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // 확인 버튼
                CommonButton(
                    text = "확인",
                    onClick = onConfirm,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
