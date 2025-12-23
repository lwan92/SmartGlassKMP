package com.smartglass.project.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * ui_design_spec.md 1.1: Colors
 * 모든 색상은 명세서의 Hex 값을 정확히 따릅니다.
 */

// ========== Primary Colors ==========
val Primary = Color(0xFF368DED) // primary
val PrimaryPressed = Color(0xFF2A6CB8) // primary_pressed
val PrimaryDisabled = Color(0xFFF2F2F2) // primary_disabled
val PrimaryDisabledText = Color(0xFFBDBDBD) // primary_disabled_text

// ========== Secondary Colors ==========
val Secondary = Color(0xFF539DF3) // secondary
val Secondary80 = Color(0xCC539DF3) // secondary_80
val SecondaryPressed = Color(0xFFBCD8F0) // secondary_pressed
val SecondaryDisabled = Color(0xFFF5F8FB) // secondary_disabled
val SecondaryDisabledText = Color(0xFFA3B1BF) // secondary_disabled_text

// ========== Tertiary Colors ==========
val Tertiary = Color(0xFF263238) // tertiary
val Tertiary60 = Color(0x99263238) // tertiary_60
val Tertiary75 = Color(0xBF263238) // tertiary_75

// ========== Destructive Colors ==========
val Destructive = Color(0xFFFDECEA) // destructive
val DestructivePressed = Color(0xFFF8D5D3) // destructive_pressed
val DestructiveDisabled = Color(0xFFF9F3F3) // destructive_disabled
val DestructiveText = Color(0xFFFF3B30) // destructive_text
val DestructiveDisabledText = Color(0xFFD6C5C5) // destructive_disabled_text
val Err = Color(0xFFD53F25) // err
val Err60 = Color(0x99D53F25) // err_60
val Err80 = Color(0xCCD53F25) // err_80
val Err12 = Color(0x1FD53F25) // err_12

// ========== Background Colors ==========
val Background = Color(0xFFECEFF8) // background
val BackgroundS = Color(0xFF020202) // background_s
val SectionBackground = Color(0xFFF6F7FB) // section_background
val StatusBarBg = Color(0xFFFFFFFF) // status_bar_bg

// ========== Text Colors ==========
val MainText = Color(0xFF333333) // main_text
val DisabledText = Color(0xFF7A8086) // disabled_text
val EditTextHintColor = Color(0xFFE4E4E4) // edit_text_hint_color
val AutoLoginColor = Color(0xFF6B7684) // auto_login_color
val FindAccountColor = Color(0xFF868A93) // find_account_color
val HighEmphasis = Color(0xFF170E2B) // 기존 HighEmphasis 유지

// ========== Gray Scale ==========
val Gray0 = Color(0xFFFFFFFF) // gray_0 (white)
val Gray50 = Color(0xFFFAFAFB) // gray_50
val Gray100 = Color(0xFFF1F1F3) // gray_100
val Gray200 = Color(0xFFE7EAEF) // gray_200
val Gray300 = Color(0xFFE4E4E4) // gray_300
val Gray400 = Color(0xFFCECECE) // gray_400
val Gray500 = Color(0xFFA6ABB3) // gray_500
val Gray600 = Color(0xFF919191) // gray_600
val Gray700 = Color(0xFF868A93) // gray_700
val Gray800 = Color(0xFF666666) // gray_800
val Gray900 = Color(0xFF4E5968) // gray_900
val Gray950 = Color(0xFF18191B) // gray_950

// ========== White Opacity Variants ==========
val White = Color(0xFFFFFFFF) // white
val WhiteS = Color(0xFFFFFFFF) // white_s
val White97 = Color(0xF7FFFFFF) // white_97
val White90 = Color(0xE6FFFFFF) // white_90
val White80 = Color(0xCCFFFFFF) // white_80
val White60 = Color(0x99FFFFFF) // white_60
val White50 = Color(0x80FFFFFF) // white_50
val White38 = Color(0x61FFFFFF) // white_38
val White24 = Color(0x3DFFFFFF) // white_24
val White20 = Color(0x33FFFFFF) // white_20
val White12 = Color(0x1FFFFFFF) // white_12
val White06 = Color(0x0FFFFFFF) // white_06
val White05 = Color(0x0DFFFFFF) // white_05

// ========== Black Opacity Variants ==========
val Black = Color(0xFF000000) // black
val Black84 = Color(0xD6000000) // black_84
val Black64 = Color(0xA3000000) // black_64
val Black60 = Color(0x99000000) // black_60
val Black50 = Color(0x80000000) // black_50 (오버레이)
val Black44 = Color(0x70000000) // black_44
val Black40 = Color(0x66000000) // black_40
val Black36 = Color(0x5C000000) // black_36 (Ripple 효과)
val Black28 = Color(0x47000000) // black_28
val Black24 = Color(0x3D000000) // black_24
val Black15 = Color(0x26000000) // black_15
val Black12 = Color(0x1F000000) // black_12
val Black10 = Color(0x1A000000) // black_10
val Black5 = Color(0x0D000000) // black_5
val Black4 = Color(0x0A000000) // black_4

// ========== Status Colors ==========
val StatusForceStop = Color(0xFFFF3B30) // status_force_stop
val StatusInProgress = Color(0xFF4B9DFF) // status_in_progress
val StatusPaused = Color(0xFFFA7564) // status_paused
val StatusCompleted = Color(0xFF00C399) // status_completed
val StatusPending = Color(0xFF82868A) // status_pending
val Success = Color(0xFF23A16C) // success
val Online = Color(0xFF219653) // online

// ========== Label Background Colors ==========
val LabelBgPending = Color(0xFFFA517B) // label_bg_pending
val LabelBgInProgress = Color(0xFF4EC4FF) // label_bg_in_progress
val LabelBgCompleted = Color(0xFFE7EAEF) // label_bg_completed
val LabelBgForceStop = Color(0xFFB0B4BA) // label_bg_force_stop
val LabelWorkType = Color(0xFF4E5968) // label_work_type
val LabelWorkTypeBg = Color(0xFF4E5968) // label_work_type_bg

// ========== Menu Colors ==========
val BlueMenu = Color(0xFF4B9DFF) // blue_menu
val OrangeMenu = Color(0xFFFA7564) // orange_menu
val PurpleMenu = Color(0xFF855BDE) // purple_menu

// ========== Card Colors ==========
val BlueCard = Color(0xFFE9F2FF) // blue_card
val BlueBtn = Color(0x33368DED) // blue_btn (투명도 20%)
val PinkCard = Color(0xFFFEEFEF) // pink_card
val PinkBtn = Color(0x33FF9771) // pink_btn (투명도 20%)

// ========== Other Colors ==========
val Dark = Color(0xFF2B2B2B) // dark
val Disabled = Color(0xFFD7D7D7) // disabled
val Transparent = Color(0x00000000) // transparent
val IconBg = Color(0xFFFFEA9F) // icon_bg
val ToastBg = Color(0xFF82868A) // toast_bg
val Nav = Color(0xFF0B1B38) // nav
val CommonShadow = Color(0xFF696969) // common_shadow
val Green = Color(0xFF16BF7E) // green
val Red10 = Color(0x1AFF3B30) // red_10
val Red30 = Color(0x4DFF3B30) // red_30
