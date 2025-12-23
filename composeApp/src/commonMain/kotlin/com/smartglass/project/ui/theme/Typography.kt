package com.smartglass.project.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * ui_design_spec.md 1.2: Typography
 * Mobile용 Pretendard 폰트를 사용합니다.
 * 폰트 파일이 없을 경우 시스템 기본 폰트를 사용합니다.
 */

// Pretendard Variable 폰트가 없을 경우 시스템 기본 폰트 사용
val pretendardFamily = FontFamily.Default

object AppTypography {
    // ========== ExtraBold (EB) ==========
    val EB32px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.ExtraBold, // 800
        fontSize = 32.sp
    )

    val EB28px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.ExtraBold, // 800
        fontSize = 28.sp
    )

    val EB24px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.ExtraBold, // 800
        fontSize = 24.sp
    )

    val EB20px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.ExtraBold, // 800
        fontSize = 20.sp
    )

    val EB18px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.ExtraBold, // 800
        fontSize = 18.sp
    )

    val EB16px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 16.sp
    )

    val EB14px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 14.sp
    )

    val EB12px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 12.sp
    )

    val EB10px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 10.sp
    )

    // ========== Bold (B) ==========
    val B24px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 24.sp
    )

    val B22px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 22.sp
    )

    val B20px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 20.sp
    )

    val B18px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 18.sp
    )

    val B16px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 16.sp
    )

    val B14px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 14.sp
    )

    val B12px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 12.sp
    )

    val B10px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 10.sp
    )

    // ========== SemiBold (SB) ==========
    val SB28px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700 (명세서에는 700로 표기)
        fontSize = 28.sp
    )

    val SB20px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 20.sp
    )

    val SB18px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 18.sp
    )

    val SB16px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 16.sp
    )

    val SB14px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 14.sp
    )

    val SB12px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 12.sp
    )

    val SB600_11px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.SemiBold, // 600
        fontSize = 11.sp
    )

    val SB10px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 10.sp
    )

    // ========== Medium (M) ==========
    val M44px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 44.sp
    )

    val M18px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 18.sp
    )

    val M16px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 16.sp
    )

    val M14px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Medium, // 500
        fontSize = 14.sp,
        lineHeight = 18.sp
    )

    val M12px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 12.sp
    )

    val M11px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 11.sp
    )

    val M10px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 10.sp
    )

    // ========== Regular (R) ==========
    val R18px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Normal, // 400
        fontSize = 18.sp
    )

    val R16px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Normal, // 400
        fontSize = 16.sp
    )

    val R14px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Normal, // 400
        fontSize = 14.sp,
        lineHeight = 18.sp
    )

    val R12px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Normal, // 400
        fontSize = 12.sp
    )

    val R11px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Normal, // 400
        fontSize = 11.sp
    )

    val R10px = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Normal, // 400
        fontSize = 10.sp
    )
}
