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
import com.smartglass.project.ui.component.ButtonStyle
import com.smartglass.project.ui.theme.*

/**
 * 중복 로그인 확인 팝업
 * features_spec.md: 중복 로그인 (코드 1018) 처리
 * ui_design_spec.md: 6. 팝업 (Popup/Dialog) 참고
 */
@Composable
fun DuplicateLoginPopup(
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onCancel) {
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
                    text = "중복 로그인",
                    style = AppTypography.B16px,
                    color = Gray950
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // 설명
                Text(
                    text = "다른 기기에서 로그인되어 있습니다.\n계속 진행하시겠습니까?",
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
                    // 취소 버튼 (Outlined)
                    CommonButton(
                        text = "취소",
                        onClick = onCancel,
                        modifier = Modifier.weight(1f),
                        style = ButtonStyle.Outlined,
                        borderColor = Primary
                    )
                    
                    // 확인 버튼 (Contained)
                    CommonButton(
                        text = "확인",
                        onClick = onConfirm,
                        modifier = Modifier.weight(1f),
                        style = ButtonStyle.Contained
                    )
                }
            }
        }
    }
}
