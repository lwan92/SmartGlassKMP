package com.smartglass.project.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.smartglass.project.ui.theme.*

/**
 * ui_design_spec.md 3.2.1: TextInputLayout (공통 입력 필드)
 * ui_design_spec.md 3.2.2: Login 입력 필드
 * 
 * 사양:
 * - 높이: 48dp
 * - Corner Radius: 4dp
 * - 내부 좌측 패딩: 16dp
 * - 내부 우측 패딩: 52dp (아이콘 공간)
 * - 배경: white (#FFFFFF)
 * - 텍스트 스타일: Typography.phone.SB_14px (Login 화면)
 * - 힌트 색상: gray_600 (#919191)
 */
@Composable
fun CommonTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    showPassword: Boolean = false,
    onTogglePasswordVisibility: (() -> Unit)? = null,
    onFocusChange: ((Boolean) -> Unit)? = null,
    showClearButton: Boolean = false,
    isError: Boolean = false
) {
    var isFocused by remember { mutableStateOf(false) }
    
    val shape = RoundedCornerShape(4.dp)
    
    // 상태별 테두리 색상 및 두께
    val borderColor = when {
        isError -> Color(0xFFF44336) // Red
        isFocused -> Primary // #368DED (명세서에는 #2196F3이지만 Primary 사용)
        else -> Color(0xFFD1D1D1) // Normal
    }
    
    val borderWidth = if (isFocused || isError) 2.dp else 1.dp
    
    Box(
        modifier = modifier
            .height(48.dp)
            .background(Color.White, shape)
            .border(
                width = borderWidth,
                color = borderColor,
                shape = shape
            )
            .padding(horizontal = 16.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                    onFocusChange?.invoke(focusState.isFocused)
                },
            textStyle = AppTypography.SB14px.copy(color = MainText),
            cursorBrush = SolidColor(MainText),
            visualTransformation = if (isPassword && !showPassword) PasswordVisualTransformation() else VisualTransformation.None,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = AppTypography.R14px,
                                color = Gray600
                            )
                        }
                        innerTextField()
                    }
                    
                    // 삭제 버튼 (아이디 입력 필드)
                    if (showClearButton && value.isNotEmpty() && !isPassword) {
                        Text(
                            text = "✕",
                            style = AppTypography.M14px,
                            color = Gray600,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable { onValueChange("") }
                        )
                    }
                    
                    // 비밀번호 보기/숨기기 버튼
                    if (isPassword && onTogglePasswordVisibility != null) {
                        Text(
                            text = if (showPassword) "숨기기" else "보기",
                            style = AppTypography.M14px,
                            color = Gray600,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable { onTogglePasswordVisibility() }
                        )
                    }
                }
            }
        )
    }
}
