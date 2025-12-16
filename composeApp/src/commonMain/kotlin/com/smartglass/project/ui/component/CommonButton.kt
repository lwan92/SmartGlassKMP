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
import androidx.compose.ui.unit.dp
import com.smartglass.project.ui.theme.AppTypography
import com.smartglass.project.ui.theme.Primary
import com.smartglass.project.ui.theme.PrimaryDisabled
import com.smartglass.project.ui.theme.PrimaryDisabledText

@Composable
fun CommonButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = Primary,
    textColor: Color = Color.White,
    borderColor: Color? = null
) {
    val shape = RoundedCornerShape(8.dp)
    val finalBackgroundColor = if (enabled) backgroundColor else PrimaryDisabled
    val finalTextColor = if (enabled) textColor else PrimaryDisabledText
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(shape)
            .background(finalBackgroundColor)
            .then(
                if (borderColor != null) {
                    Modifier.border(1.dp, borderColor, shape)
                } else {
                    Modifier
                }
            )
            .clickable(enabled = enabled, onClick = onClick)
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = AppTypography.B16px,
            color = finalTextColor
        )
    }
}
