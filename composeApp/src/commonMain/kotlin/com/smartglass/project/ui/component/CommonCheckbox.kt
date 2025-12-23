package com.smartglass.project.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartglass.project.ui.theme.*

/**
 * ui_design_spec.md: 자동 로그인 체크박스
 * 
 * 사양:
 * - 크기: 16dp × 16dp
 * - 텍스트 색상: auto_login_color (#6B7684)
 * - 텍스트 스타일: Typography.phone.B_12px
 * - 텍스트-체크박스 간격: 8dp
 */
@Composable
fun CommonCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.clickable { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(
                    if (checked) Primary else Color.White,
                    RoundedCornerShape(4.dp)
                )
                .border(
                    width = 1.dp,
                    color = if (checked) Primary else Gray400,
                    shape = RoundedCornerShape(4.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (checked) {
                Text(
                    text = "✓",
                    color = Color.White,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        Text(
            text = label,
            style = AppTypography.B12px,
            color = AutoLoginColor
        )
    }
}
