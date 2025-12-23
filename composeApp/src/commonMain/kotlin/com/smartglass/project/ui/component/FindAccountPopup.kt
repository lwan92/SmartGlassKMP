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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.smartglass.project.ui.component.CommonButton
import com.smartglass.project.ui.component.ButtonStyle
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
                // 제목 (Figma 디자인)
                Text(
                    text = "아이디 찾기/비밀번호 찾기 안내",
                    style = AppTypography.B16px,
                    color = Gray950
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // 설명 (Figma 디자인)
                Text(
                    text = "사용자 계정(아이디/비밀번호) 관련 사항은 관리자에게 문의하여 확인하시기 바랍니다.",
                    style = AppTypography.M14px,
                    color = Gray950,
                    lineHeight = 18.sp
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // 닫기 버튼 (Figma 디자인: Outlined 스타일)
                CommonButton(
                    text = "닫기",
                    onClick = onConfirm,
                    modifier = Modifier.fillMaxWidth(),
                    style = ButtonStyle.Outlined,
                    borderColor = Primary,
                    textColor = Primary
                )
            }
        }
    }
}
