package com.smartglass.project.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.smartglass.project.ui.theme.AppTypography
import com.smartglass.project.ui.theme.*

/**
 * ui_design_spec.md 3.1.1: GlassButton (Primary)
 * 
 * 사양:
 * - 높이: 48dp (Large), 44dp (Medium), 38dp (Small)
 * - Corner Radius: 4dp (배경), 8dp (Ripple 마스크)
 * - Elevation: 5dp
 * - Ripple Color: black_36 (#5C000000)
 */
enum class ButtonSize {
    Large,   // 48dp
    Medium,  // 44dp
    Small    // 38dp
}

enum class ButtonStyle {
    Contained,  // Primary Contained
    Outlined    // Primary Outlined
}

@Composable
fun CommonButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: ButtonSize = ButtonSize.Large,
    style: ButtonStyle = ButtonStyle.Contained,
    backgroundColor: Color? = null,
    textColor: Color? = null,
    borderColor: Color? = null,
    leadingIcon: @Composable (() -> Unit)? = null  // 버튼 텍스트 앞에 표시할 아이콘
) {
    val height = when (size) {
        ButtonSize.Large -> 48.dp
        ButtonSize.Medium -> 44.dp
        ButtonSize.Small -> 38.dp
    }
    
    val textStyle = when (size) {
        ButtonSize.Large -> AppTypography.B16px
        ButtonSize.Medium -> AppTypography.B14px
        ButtonSize.Small -> AppTypography.B12px
    }
    
    val shape = RoundedCornerShape(4.dp)
    val rippleShape = RoundedCornerShape(8.dp) // Ripple 마스크
    
    // 스타일별 색상 결정
    val (finalBackgroundColor, finalTextColor, finalBorderColor) = when (style) {
        ButtonStyle.Contained -> {
            when {
                !enabled -> Triple(
                    PrimaryDisabled,  // ui_design_spec.md: #F2F2F2
                    PrimaryDisabledText,  // ui_design_spec.md: #BDBDBD
                    null
                )
                backgroundColor != null -> Triple(
                    backgroundColor,
                    textColor ?: Color.White,
                    null
                )
                else -> Triple(
                    Primary,
                    Color.White,
                    null
                )
            }
        }
        ButtonStyle.Outlined -> {
            when {
                !enabled -> Triple(
                    PrimaryDisabled,
                    PrimaryDisabledText,
                    PrimaryDisabled
                )
                else -> Triple(
                    Transparent,
                    textColor ?: Color.White,
                    borderColor ?: Color.White
                )
            }
        }
    }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .shadow(
                elevation = if (enabled && style == ButtonStyle.Contained) 5.dp else 0.dp,
                shape = shape,
                clip = false
            )
            .clip(shape)
            .background(finalBackgroundColor)
            .then(
                if (finalBorderColor != null) {
                    Modifier.border(1.dp, finalBorderColor, shape)
                } else {
                    Modifier
                }
            )
            .clickable(
                enabled = enabled,
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() }
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            // Leading Icon
            leadingIcon?.let {
                it()
                Spacer(modifier = Modifier.width(4.dp))
            }
            
            // Text
            Text(
                text = text,
                style = textStyle,
                color = finalTextColor
            )
        }
    }
}
