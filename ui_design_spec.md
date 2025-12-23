# SmartGlass Mobile - UI 디자인 시스템 명세서

> 이 문서는 KMP 프로젝트 개발을 위한 디자인 시스템 명세서입니다.  
> Compose Multiplatform으로 동일한 UI를 구현할 수 있도록 모든 디자인 토큰과 컴포넌트 스펙을 정리했습니다.  
> Glass 코드를 제외한 공통 코드와 Mobile 코드를 기반으로 작성되었습니다.

---

## 1. Design Tokens

### 1.1 Colors

#### 1.1.1 Primary Colors

| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| primary | `#368DED` | 주요 액션 버튼, 링크 |
| primary_pressed | `#2A6CB8` | Primary 버튼 눌림 상태 |
| primary_disabled | `#F2F2F2` | Primary 버튼 비활성화 배경 |
| primary_disabled_text | `#BDBDBD` | Primary 버튼 비활성화 텍스트 |

#### 1.1.2 Secondary Colors

| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| secondary | `#539DF3` | 보조 액션 |
| secondary_80 | `#CC539DF3` | Secondary 80% 투명도 |
| secondary_pressed | `#BCD8F0` | Secondary 버튼 눌림 상태 |
| secondary_disabled | `#F5F8FB` | Secondary 버튼 비활성화 배경 |
| secondary_disabled_text | `#A3B1BF` | Secondary 버튼 비활성화 텍스트 |

#### 1.1.3 Tertiary Colors

| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| tertiary | `#263238` | 3차 액션 |
| tertiary_60 | `#99263238` | Tertiary 60% 투명도 |
| tertiary_75 | `#BF263238` | Tertiary 75% 투명도 |

#### 1.1.4 Destructive Colors

| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| destructive | `#FDECEA` | Destructive 버튼 배경 |
| destructive_pressed | `#F8D5D3` | Destructive 버튼 눌림 상태 |
| destructive_disabled | `#F9F3F3` | Destructive 버튼 비활성화 배경 |
| destructive_text | `#FF3B30` | Destructive 텍스트, 에러 메시지 |
| destructive_disabled_text | `#D6C5C5` | Destructive 버튼 비활성화 텍스트 |
| err | `#D53F25` | 에러 상태 |
| err_60 | `#99D53F25` | 에러 60% 투명도 |
| err_80 | `#CCD53F25` | 에러 80% 투명도 |
| err_12 | `#1FD53F25` | 에러 12% 투명도 |

#### 1.1.5 Background Colors

| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| background | `#ECEFF8` | 앱 전체 배경색 |
| background_s | `#020202` | 보조 배경색 |
| section_background | `#F6F7FB` | 섹션 배경색 |
| status_bar_bg | `#FFFFFF` | 상태바 배경색 |

#### 1.1.6 Text Colors

| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| main_text | `#333333` | 기본 텍스트 |
| disabled_text | `#7A8086` | 비활성화 텍스트 |
| edit_text_hint_color | `#E4E4E4` | 입력 필드 힌트 색상 |
| auto_login_color | `#6B7684` | 자동 로그인 텍스트 |
| find_account_color | `#868A93` | 계정 찾기 텍스트 |

#### 1.1.7 Gray Scale

| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| gray_950 | `#18191B` | 가장 진한 회색 (제목, 중요 텍스트) |
| gray_900 | `#4E5968` | 진한 회색 (부제목) |
| gray_800 | `#666666` | 중간 진한 회색 |
| gray_700 | `#868A93` | 중간 회색 (보조 텍스트) |
| gray_600 | `#919191` | 중간 밝은 회색 |
| gray_500 | `#A6ABB3` | 밝은 회색 |
| gray_400 | `#CECECE` | 매우 밝은 회색 |
| gray_300 | `#E4E4E4` | 매우 밝은 회색 (구분선) |
| gray_200 | `#E7EAEF` | 거의 흰색 회색 |
| gray_100 | `#F1F1F3` | 거의 흰색 회색 |
| gray_50 | `#FAFAFB` | 거의 흰색 회색 |
| gray_0 | `#FFFFFF` | 흰색 |

#### 1.1.8 White Opacity Variants

| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| white | `#FFFFFF` | 기본 흰색 |
| white_s | `#FFFFFF` | 보조 흰색 |
| white_97 | `#F7FFFFFF` | 흰색 97% |
| white_90 | `#E6FFFFFF` | 흰색 90% |
| white_80 | `#CCFFFFFF` | 흰색 80% |
| white_60 | `#99FFFFFF` | 흰색 60% |
| white_50 | `#80FFFFFF` | 흰색 50% |
| white_38 | `#61FFFFFF` | 흰색 38% |
| white_24 | `#3DFFFFFF` | 흰색 24% |
| white_20 | `#33FFFFFF` | 흰색 20% |
| white_12 | `#1FFFFFFF` | 흰색 12% |
| white_06 | `#0FFFFFFF` | 흰색 6% |
| white_05 | `#0DFFFFFF` | 흰색 5% |

#### 1.1.9 Black Opacity Variants

| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| black | `#000000` | 기본 검정색 |
| black_84 | `#D6000000` | 검정색 84% |
| black_64 | `#A3000000` | 검정색 64% |
| black_60 | `#99000000` | 검정색 60% |
| black_50 | `#80000000` | 검정색 50% (오버레이) |
| black_44 | `#70000000` | 검정색 44% |
| black_40 | `#66000000` | 검정색 40% |
| black_36 | `#5C000000` | 검정색 36% (Ripple 효과) |
| black_28 | `#47000000` | 검정색 28% |
| black_24 | `#3D000000` | 검정색 24% |
| black_15 | `#26000000` | 검정색 15% |
| black_12 | `#1F000000` | 검정색 12% |
| black_10 | `#1A000000` | 검정색 10% |
| black_5 | `#0D000000` | 검정색 5% |
| black_4 | `#0A000000` | 검정색 4% |

#### 1.1.10 Status Colors

| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| status_force_stop | `#FF3B30` | 작업 중단 상태 |
| status_in_progress | `#4B9DFF` | 작업 진행 중 상태 |
| status_paused | `#FA7564` | 작업 일시정지 상태 |
| status_completed | `#00C399` | 작업 완료 상태 |
| status_pending | `#82868A` | 대기 중 상태 |
| success | `#23A16C` | 성공 상태 |
| online | `#219653` | 온라인 상태 |

#### 1.1.11 Label Background Colors

| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| label_bg_pending | `#FA517B` | 진행전 라벨 배경 |
| label_bg_in_progress | `#4EC4FF` | 작업중 라벨 배경 |
| label_bg_completed | `#E7EAEF` | 종료 라벨 배경 |
| label_bg_force_stop | `#B0B4BA` | 작업중단 라벨 배경 |
| label_work_type | `#4E5968` | 작업 유형 라벨 텍스트 |
| label_work_type_bg | `#4E5968` | 작업 유형 라벨 배경 |

#### 1.1.12 Menu Colors

| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| blue_menu | `#4B9DFF` | 파란색 메뉴 |
| orange_menu | `#FA7564` | 주황색 메뉴 |
| purple_menu | `#855BDE` | 보라색 메뉴 |

#### 1.1.13 Card Colors

| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| blue_card | `#E9F2FF` | 파란색 카드 배경 |
| blue_btn | `#33368DED` | 파란색 버튼 (투명도 20%) |
| pink_card | `#FEEFEF` | 분홍색 카드 배경 |
| pink_btn | `#33FF9771` | 분홍색 버튼 (투명도 20%) |

#### 1.1.14 Other Colors

| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| dark | `#2B2B2B` | 다크 배경 |
| disabled | `#D7D7D7` | 비활성화 배경 |
| transparent | `#00000000` | 투명 |
| icon_bg | `#FFEA9F` | 아이콘 배경 |
| toast_bg | `#82868A` | 토스트 배경 |
| nav | `#0B1B38` | 네비게이션 배경 |
| common_shadow | `#696969` | 공통 그림자 색상 |
| green | `#16BF7E` | 초록색 |
| red_10 | `#1AFF3B30` | 빨간색 10% 투명도 |
| red_30 | `#4DFF3B30` | 빨간색 30% 투명도 |

---

### 1.2 Typography

#### 1.2.1 Font Families

**Roboto A1 (Glass용)**
- `roboto_black.ttf` - Weight: 900
- `roboto_extrabold.ttf` - Weight: 800
- `roboto_bold.ttf` - Weight: 700
- `roboto_semibold.ttf` - Weight: 600
- `roboto_medium.ttf` - Weight: 500

**Pretendard (Mobile용)**
- `pretendard_extrabold.ttf` - Weight: 800
- `pretendard_bold.ttf` - Weight: 700
- `pretendard_semibold.ttf` - Weight: 600
- `pretendard_medium.ttf` - Weight: 500
- `pretendard_regular.ttf` - Weight: 400

#### 1.2.2 Typography Scale (Glass)

| 스타일명 | Font Size | Font Weight | Font Family | Line Height | 용도 |
|----------|-----------|-------------|-------------|-------------|------|
| Typography.H1 | 40sp | 900 (Black) | roboto_black | - | 대제목 |
| Typography.H2 | 36sp | 800 (ExtraBold) | roboto_extrabold | - | 제목 |
| Typography.H3 | 32sp | 700 (Bold) | roboto_bold | - | 부제목 |
| Typography.H4 | 28sp | 600 (SemiBold) | roboto_semibold | - | 섹션 제목 |
| Typography.H5 | 24sp | 600 (SemiBold) | roboto_semibold | - | 카드 제목 |
| Typography.subtext | 16sp | 500 (Medium) | roboto_semibold | - | 보조 텍스트 |
| Typography.subtext.s | 16sp | 500 (Medium) | roboto_medium | - | 보조 텍스트 (소) |
| Typography.subtext.m | 16sp | 700 (Bold) | roboto_medium | - | 보조 텍스트 (중) |

#### 1.2.3 Typography Scale (Mobile - Pretendard)

**ExtraBold (EB)**

| 스타일명 | Font Size | Font Weight | Line Height | 용도 |
|----------|-----------|-------------|-------------|------|
| Typography.phone.EB_32px | 32sp | 800 | - | 대제목 |
| Typography.phone.EB_28px | 28sp | 800 | - | 제목 |
| Typography.phone.EB_24px | 24sp | 800 | - | 부제목 |
| Typography.phone.EB_20px | 20sp | 800 | - | 섹션 제목 |
| Typography.phone.EB_18px | 18sp | 800 | - | 카드 제목 |
| Typography.phone.EB_16px | 16sp | 700 | - | 본문 강조 |
| Typography.phone.EB_14px | 14sp | 700 | - | 보조 텍스트 강조 |
| Typography.phone.EB_12px | 12sp | 700 | - | 작은 텍스트 강조 |
| Typography.phone.EB_10px | 10sp | 700 | - | 최소 텍스트 강조 |

**Bold (B)**

| 스타일명 | Font Size | Font Weight | Line Height | 용도 |
|----------|-----------|-------------|-------------|------|
| Typography.phone.B_24px | 24sp | 700 | - | 부제목 |
| Typography.phone.B_22px | 22sp | 700 | - | 섹션 제목 |
| Typography.phone.B_20px | 20sp | 700 | - | 카드 제목 |
| Typography.phone.B_18px | 18sp | 700 | - | 본문 강조 |
| Typography.phone.B_16px | 16sp | 700 | - | 버튼 텍스트, 본문 |
| Typography.phone.B_14px | 14sp | 700 | - | 보조 텍스트 |
| Typography.phone.B_12px | 12sp | 700 | - | 작은 텍스트 |
| Typography.phone.B_10px | 10sp | 700 | - | 최소 텍스트 |

**SemiBold (SB)**

| 스타일명 | Font Size | Font Weight | Line Height | 용도 |
|----------|-----------|-------------|-------------|------|
| Typography.phone.SB_28px | 28sp | 700 | - | 제목 |
| Typography.phone.SB_20px | 20sp | 700 | - | 섹션 제목 |
| Typography.phone.SB_18px | 18sp | 700 | - | 카드 제목 |
| Typography.phone.SB_16px | 16sp | 700 | - | 본문 |
| Typography.phone.SB_14px | 14sp | 700 | - | 보조 텍스트 |
| Typography.phone.SB_12px | 12sp | 700 | - | 작은 텍스트 |
| Typography.phone.SB_600_11px | 11sp | 600 | - | 작은 텍스트 (600) |
| Typography.phone.SB_10px | 10sp | 700 | - | 최소 텍스트 |

**Medium (M)**

| 스타일명 | Font Size | Font Weight | Line Height | 용도 |
|----------|-----------|-------------|-------------|------|
| Typography.phone.M_44px | 44sp | 700 | - | 대형 숫자 |
| Typography.phone.M_18px | 18sp | 700 | - | 본문 |
| Typography.phone.M_16px | 16sp | 700 | - | 본문 |
| Typography.phone.M_14px | 14sp | 700 | - | 입력 필드, 본문 |
| Typography.phone.M_12px | 12sp | 700 | - | 보조 텍스트 |
| Typography.phone.M_11px | 11sp | 700 | - | 작은 텍스트 |
| Typography.phone.M_10px | 10sp | 700 | - | 최소 텍스트 |

**Regular (R)**

| 스타일명 | Font Size | Font Weight | Line Height | 용도 |
|----------|-----------|-------------|-------------|------|
| Typography.phone.R_18px | 18sp | 400 | - | 본문 |
| Typography.phone.R_16px | 16sp | 400 | - | 본문 |
| Typography.phone.R_14px | 14sp | 400 | - | 본문, 힌트 |
| Typography.phone.R_12px | 12sp | 400 | - | 보조 텍스트 |
| Typography.phone.R_11px | 11sp | 400 | - | 작은 텍스트 |
| Typography.phone.R_10px | 10sp | 400 | - | 최소 텍스트 |

#### 1.2.4 Line Spacing

- 기본: `lineSpacingExtra = 2dp` (일부 TextView에서 사용)
- 대부분의 텍스트는 기본 Line Height 사용

---

### 1.3 Shape (Corner Radius)

| 컴포넌트 | Corner Radius | 용도 |
|----------|---------------|------|
| 버튼 (Primary Contained/Outlined) | 4dp | 모든 버튼 |
| 입력 필드 | 4dp | TextInputLayout, EditText |
| 카드 (작업카드) | 8dp | 작업카드 아이템 |
| 팝업 (하단) | 8dp (상단만) | 하단 팝업 상단 모서리 |
| 라벨 (상태/유형) | 4dp | 상태 라벨, 작업 유형 라벨 |
| 홈 메뉴 탭 | 12dp | 홈 화면 탭 컨테이너 |
| Ripple 효과 | 8dp | 버튼 Ripple 마스크 |

---

### 1.4 Elevation & Shadow

| 컴포넌트 | Elevation | Shadow Color | 용도 |
|----------|-----------|--------------|------|
| 버튼 (기본) | 5dp | - | 기본 버튼 |
| 작업카드 아이템 | 6dp | `#696969` | 작업카드 리스트 아이템 |
| 홈 메뉴 탭 버튼 | 6dp | `#696969` | 홈 화면 탭 버튼 |
| 새 작업 버튼 (플로팅) | 4dp | `#FFFFFF` | 홈 화면 플로팅 버튼 |

---

## 2. Layout Structure

### 2.1 공통 Spacing System

#### 2.1.1 Padding & Margin 값

| 크기 | 값 | 용도 |
|------|-----|------|
| xs | 4dp | 매우 작은 간격 (라벨 간격) |
| sm | 8dp | 작은 간격 (텍스트 간격, 아이콘 간격) |
| md | 12dp | 중간 간격 (섹션 간격) |
| lg | 16dp | 큰 간격 (화면 좌우 여백, 카드 내부 패딩) |
| xl | 20dp | 매우 큰 간격 (입력 필드 간격) |
| xxl | 24dp | 초대형 간격 (섹션 상단 여백) |
| xxxl | 28dp | 최대 간격 (화면 상단 여백) |
| xxxxl | 36dp | 최대 간격 (버튼 상단 여백) |
| xxxxxl | 48dp | 최대 간격 (로고 하단 여백) |

#### 2.1.2 화면 여백 (Screen Padding)

| 화면 | 좌우 여백 | 상단 여백 | 하단 여백 |
|------|-----------|-----------|-----------|
| Login | 16dp | 108dp (로고) | 28dp |
| Home | 16dp | 0dp | 0dp |
| WorkCardNew | 16dp | 28dp | 16dp |
| Notice | 16dp | 28dp | 0dp |
| WorkRecord | 16dp | - | - |

---

### 2.2 주요 화면 구조

#### 2.2.1 Login 화면

```
┌─────────────────────────────────┐
│        (상단 여백: 108dp)        │
│                                 │
│    [회사 로고] (180dp × 40dp)   │
│                                 │
│        (여백: 48dp)             │
│                                 │
│  [아이디 입력] (높이: 48dp)      │
│    좌우 여백: 16dp              │
│    내부 좌측 패딩: 16dp          │
│    내부 우측 패딩: 52dp          │
│                                 │
│        (여백: 20dp)             │
│                                 │
│  [비밀번호 입력] (높이: 48dp)    │
│    좌우 여백: 16dp              │
│    내부 좌측 패딩: 16dp          │
│    내부 우측 패딩: 52dp          │
│                                 │
│        (여백: 12dp)             │
│                                 │
│  [자동 로그인 체크박스]          │
│    크기: 16dp × 16dp            │
│                                 │
│        (여백: 36dp)             │
│                                 │
│  [로그인 버튼] (높이: 48dp)      │
│    좌우 여백: 16dp              │
│                                 │
│        (여백: 16dp)             │
│                                 │
│  [계정 찾기]                    │
│                                 │
│        (하단 여백: 28dp)         │
│                                 │
│    [하단 로고] (108dp × 40dp)   │
└─────────────────────────────────┘
```

**구성 요소**
- 회사 로고: 상단 108dp, 중앙 정렬
- 입력 필드: 높이 48dp, 좌우 여백 16dp, 내부 좌측 패딩 16dp, 우측 패딩 52dp (아이콘 공간)
- 로그인 버튼: 높이 48dp, 좌우 여백 16dp, 상단 여백 36dp
- 하단 로고: 하단 28dp, 중앙 정렬

---

#### 2.2.2 Home 화면

```
┌─────────────────────────────────┐
│  [사용자 정보 영역]              │
│    높이: 최소 68dp               │
│    배경: gray_0 (#FFFFFF)       │
│    상단 여백: 28dp               │
│    좌측 여백: 16dp               │
│    우측 여백: 16dp               │
│                                 │
│    ┌─────────────────────────┐  │
│    │ 이름 | 부서명 →         │  │
│    │ 알림 아이콘 | 설정 아이콘 │  │
│    └─────────────────────────┘  │
│                                 │
│  [탭 메뉴 영역]                  │
│    높이: 75dp                   │
│    좌우 패딩: 16dp              │
│    내부 컨테이너 높이: 43dp      │
│    내부 컨테이너 상하 여백: 16dp │
│                                 │
│    ┌─────────────────────────┐  │
│    │ [나의 작업 (35dp)]      │  │
│    │ [대기 중 통화 (35dp)]   │  │
│    └─────────────────────────┘  │
│    좌우 여백: 4dp               │
│                                 │
│  [작업 목록 영역]                │
│    상단 여백: 12dp              │
│    좌우 패딩: 16dp              │
│                                 │
│    ┌─────────────────────────┐  │
│    │ [전체보기] (우측 정렬)   │  │
│    │ 우측 여백: 16dp          │  │
│    └─────────────────────────┘  │
│                                 │
│    [RecyclerView]               │
│    좌우 패딩: 16dp              │
│                                 │
│  [플로팅 버튼]                   │
│    우측 여백: 16dp              │
│    하단 여백: 42dp              │
│    내부 패딩:                   │
│      상하: 10dp                 │
│      좌우: 16dp                 │
└─────────────────────────────────┘
```

**구성 요소**
- 사용자 정보 영역: 최소 높이 68dp, 상단 여백 28dp
- 탭 메뉴: 높이 75dp, 내부 컨테이너 높이 43dp, 탭 버튼 높이 35dp
- 작업 목록: 상단 여백 12dp, RecyclerView 좌우 패딩 16dp
- 플로팅 버튼: 우측 16dp, 하단 42dp

---

#### 2.2.3 WorkCardNew 화면

```
┌─────────────────────────────────┐
│  [RecyclerView]                 │
│    상단 패딩: 28dp               │
│    좌우 패딩: 16dp               │
│    하단 패딩: 0dp                │
│    하단 여백: 16dp               │
│                                 │
│  [확인 버튼]                     │
│    높이: 48dp                   │
│    좌우 여백: 16dp              │
│    하단 여백: 16dp              │
└─────────────────────────────────┘
```

---

#### 2.2.4 작업카드 아이템 구조

```
┌─────────────────────────────────┐
│  [카드 컨테이너]                  │
│    좌우 패딩: 16dp               │
│    상단 패딩: 16dp               │
│    하단 패딩: 12dp               │
│    배경: gray_0 (#FFFFFF)        │
│    Corner Radius: 8dp            │
│    Elevation: 6dp                │
│                                 │
│  [라벨 영역]                     │
│    ┌─────────┬─────────┐        │
│    │ 작업유형│ 상태    │        │
│    └─────────┴─────────┘        │
│    라벨 간격: 8dp                │
│    라벨 패딩: 좌우 8dp, 상하 4dp │
│                                 │
│  [작업 생성자] (우측 정렬)       │
│    상단 여백: 0dp                │
│    우측 여백: 8dp                │
│                                 │
│  [작업명]                        │
│    상단 여백: 8dp                │
│    우측 여백: 4dp (화살표 공간)   │
│                                 │
│  [구분선]                        │
│    높이: 0.5dp                   │
│    상단 여백: 12dp               │
│    색상: gray_300 (#E4E4E4)     │
│                                 │
│  [마지막 작업]                   │
│    상단 여백: 8dp                │
│    레이블-값 간격: 12dp          │
│    값-시간 간격: 4dp             │
│                                 │
│  [작업 참여자]                   │
│    상단 여백: 4dp                │
│    레이블-값 간격: 12dp          │
│    값-추가인원 간격: 4dp          │
│    추가인원-아이콘 간격: 4dp      │
└─────────────────────────────────┘
```

---

#### 2.2.5 Notice 화면

```
┌─────────────────────────────────┐
│  [상단 영역]                     │
│    좌측 여백: 16dp               │
│    우측 여백: 16dp               │
│    상단 여백: 28dp               │
│                                 │
│    [읽지 않은 알림] (좌측)        │
│    [날짜 범위] (우측)             │
│    상단 여백: 29.5dp             │
│                                 │
│  [RecyclerView]                 │
│    좌우 여백: 16dp               │
│    상단 여백: 13.5dp             │
└─────────────────────────────────┘
```

---

#### 2.2.6 팝업 구조

```
┌─────────────────────────────────┐
│  [오버레이]                      │
│    배경: black_50 (#80000000)   │
│                                 │
│  [팝업 컨테이너] (하단 정렬)      │
│    배경: gray_0 (#FFFFFF)       │
│    Corner Radius: 8dp (상단만)   │
│    좌우 패딩: 16dp               │
│    상단 패딩: 24dp               │
│    하단 패딩: 24dp               │
│                                 │
│    [제목]                        │
│                                 │
│    [설명]                        │
│    상단 여백: 24dp               │
│    Line Spacing: 2dp            │
│                                 │
│    [정보 텍스트] (선택)          │
│    상단 여백: 24dp               │
│    Line Spacing: 2dp            │
│                                 │
│    [버튼 영역]                   │
│    상단 여백: 24dp               │
│    버튼 간격: 6dp                │
└─────────────────────────────────┘
```

---

## 3. Component Catalog

### 3.1 Button Components

#### 3.1.1 GlassButton (Primary)

**사양**
- 높이: 48dp (Large), 44dp (Medium), 38dp (Small)
- Corner Radius: 4dp (배경), 8dp (Ripple 마스크)
- Elevation: 5dp
- Ripple Color: `black_36` (#5C000000)

**Primary Contained**
- 배경색:
  - Normal: `white` (#FFFFFF)
  - Focused: `white_50` (#80FFFFFF)
  - Disabled: `disabled` (#D7D7D7)
- 텍스트 색상:
  - Normal: `main_text` (#333333)
  - Focused: `main_text` (#333333)
  - Disabled: `disabled_text` (#7A8086)
- 텍스트 스타일:
  - Large: Typography.phone.B_16px
  - Medium: Typography.phone.B_14px
  - Small: Typography.phone.B_12px

**Primary Outlined**
- 배경색:
  - Normal: `transparent` (#00000000)
  - Focused: `white_12` (#1FFFFFFF)
  - Disabled: `disabled` (#D7D7D7)
- 테두리:
  - Normal: `white` (#FFFFFF), 1dp
  - Focused: `white` (#FFFFFF), 1dp
  - Disabled: `disabled` (#D7D7D7), 1dp
- 텍스트 색상:
  - Normal: `white` (#FFFFFF)
  - Focused: `main_text` (#333333)
  - Disabled: `disabled_text` (#7A8086)

**사이즈별 너비 (선택적)**
- Small: 112dp
- Medium: 180dp
- Large: match_parent (기본)

---

#### 3.1.2 Error Button

**Error Contained**
- 배경색:
  - Normal: `destructive` (#FDECEA)
  - Pressed: `destructive_pressed` (#F8D5D3)
  - Disabled: `destructive_disabled` (#F9F3F3)
- 텍스트 색상:
  - Normal: `white` (#FFFFFF)
  - Disabled: `destructive_disabled_text` (#D6C5C5)
- 높이: 40dp (기본)

**Error Outlined**
- 배경색:
  - Normal: `transparent`
  - Disabled: `destructive_disabled`
- 테두리: `destructive_text` (#FF3B30), 1dp
- 텍스트 색상:
  - Normal: `main_text` (#333333)
  - Disabled: `destructive_disabled_text`

---

#### 3.1.3 홈 메뉴 탭 버튼

**사양**
- 높이: 35dp
- 배경: `s_home_menu_btn` (selector)
- Elevation: 6dp
- Shadow Color: `common_shadow` (#696969)
- 좌우 여백: 4dp
- 텍스트 색상: `s_gray700_gray950` (selector)
- 텍스트 스타일: Typography.phone.B_16px

**상태별 색상**
- Selected: `gray_950` (#18191B)
- Unselected: `gray_700` (#868A93)

---

#### 3.1.4 플로팅 버튼 (새 작업 만들기)

**사양**
- 배경: `b_home_new_work_btn`
- Elevation: 4dp
- Shadow Color: `white` (#FFFFFF)
- 내부 패딩:
  - 상하: 10dp
  - 좌우: 16dp
- 우측 여백: 16dp
- 하단 여백: 42dp
- 아이콘 크기: 20dp × 20dp
- 아이콘-텍스트 간격: 8dp
- 텍스트-화살표 간격: 4dp
- 텍스트 스타일: Typography.phone.SB_14px
- 텍스트 색상: `white` (#FFFFFF)

---

### 3.2 Input Components

#### 3.2.1 TextInputLayout (공통 입력 필드)

**사양**
- 높이: 48dp
- Corner Radius: 4dp
- 배경: `input_background` (selector)
- 내부 패딩:
  - 좌우: 8dp (일부는 12dp)
  - 상하: 9dp (일부는 12dp)

**상태별 배경**

**Normal**
- 배경색: `white` (#FFFFFF)
- 테두리: `#D1D1D1`, 1dp
- Corner Radius: 4dp

**Focused**
- 배경색: `white` (#FFFFFF)
- 테두리: `#2196F3` (Blue), 2dp
- Corner Radius: 4dp

**Error**
- 배경색: `white` (#FFFFFF)
- 테두리: `#F44336` (Red), 2dp
- Corner Radius: 4dp

**텍스트 스타일**
- 입력 텍스트: Typography.phone.M_14px
- 텍스트 색상: `selector_input_text` (selector)
- 힌트 색상: `gray_600` (#919191)
- 힌트 스타일: Typography.phone.R_14px

---

#### 3.2.2 Login 입력 필드

**사양**
- 높이: 48dp
- 좌우 여백: 16dp
- 내부 좌측 패딩: 16dp
- 내부 우측 패딩: 52dp (아이콘 공간)
- 배경: `input_background`
- 텍스트 색상: `selector_input_text`
- 힌트 색상: `edit_text_hint_color` (#E4E4E4)

**옵션 버튼**
- 삭제 버튼: 20dp × 20dp, 우측 여백 16dp
- 눈 아이콘: 20dp × 20dp, 우측 여백 16dp

---

#### 3.2.3 작업카드 입력 필드

**레이블 영역**
- 레이블 스타일: Typography.phone.B_14px
- 레이블 색상: `selector_input_title_text` (selector)
- 필수 표시 (*): Typography.phone.B_14px, 색상 `selector_required_text`
- 레이블-필수 표시 간격: 4dp
- 글자 수 표시: Typography.phone.M_11px, 색상 `gray_700` (#868A93), 우측 정렬

**입력 영역**
- 상단 여백: 8dp
- 배경: `bg_input_selector`
- 내부 패딩: 좌우 8dp, 상하 9dp
- 텍스트 스타일: Typography.phone.M_14px
- 텍스트 색상: `selector_input_text`
- 힌트 색상: `gray_600` (#919191)

---

#### 3.2.4 작업카드 정보 필드 (읽기 전용)

**사양**
- 레이블 너비: 80dp
- 레이블 스타일: Typography.phone.M_14px
- 레이블 색상: `gray_900` (#4E5968)
- 값-레이블 간격: 8dp
- 값 스타일: Typography.phone.M_14px
- 값 색상: `gray_950` (#18191B)

---

### 3.3 Card Components

#### 3.3.1 작업카드 아이템

**사양**
- 배경색: `gray_0` (#FFFFFF)
- Corner Radius: 8dp
- Elevation: 6dp
- Shadow Color: `common_shadow` (#696969)
- 패딩:
  - 좌우: 16dp
  - 상단: 16dp
  - 하단: 12dp

**라벨 (상태/유형)**
- 높이: wrap_content
- 패딩: 좌우 8dp, 상하 4dp
- Corner Radius: 4dp
- 텍스트 스타일: Typography.phone.SB_12px
- 텍스트 색상: `gray_0` (#FFFFFF)
- 라벨 간격: 8dp

**상태별 배경색**
- 진행전: `label_bg_pending` (#FA517B)
- 작업중: `label_bg_in_progress` (#4EC4FF)
- 종료: `label_bg_completed` (#E7EAEF)
- 작업중단: `label_bg_force_stop` (#B0B4BA)

**작업 유형 라벨**
- 배경색: `label_work_type_bg` (#4E5968)
- 텍스트 색상: `gray_0` (#FFFFFF)

**작업명**
- 스타일: Typography.phone.B_16px
- 색상: `gray_950` (#18191B)
- 상단 여백: 8dp
- 최대 1줄, ellipsize: end

**구분선**
- 높이: 0.5dp
- 색상: `gray_300` (#E4E4E4)
- 상단 여백: 12dp

**작업 생성자**
- 레이블 스타일: Typography.phone.B_12px
- 레이블 색상: `gray_700` (#868A93)
- 레이블-값 간격: 6dp
- 값 스타일: Typography.phone.M_12px
- 값 색상: `gray_950` (#18191B)

**마지막 작업**
- 레이블 스타일: Typography.phone.SB_12px
- 레이블 색상: `gray_900` (#4E5968)
- 레이블-값 간격: 12dp
- 값 스타일: Typography.phone.M_14px
- 값 색상: `gray_950` (#18191B)
- 값-시간 간격: 4dp
- 시간 스타일: Typography.phone.M_12px
- 시간 색상: `gray_600` (#919191)

**작업 참여자**
- 레이블 스타일: Typography.phone.SB_12px
- 레이블 색상: `gray_900` (#4E5968)
- 레이블-값 간격: 12dp
- 값 스타일: Typography.phone.M_14px
- 값 색상: `gray_950` (#18191B)
- 값-추가인원 간격: 4dp
- 추가인원 스타일: Typography.phone.M_12px
- 추가인원 색상: `gray_950` (#18191B)
- 추가인원-아이콘 간격: 4dp
- 아이콘 크기: 24dp × 24dp

---

#### 3.3.2 공지사항 아이템

**사양**
- 아이콘 크기: 24dp × 24dp
- 아이콘-메시지 간격: 12dp
- 메시지 컨테이너 패딩: 12dp
- 배경: `bg_notice_item`

**제목**
- 스타일: Typography.phone.B_16px (추정)
- 색상: `gray_950` (#18191B)

**내용**
- 스타일: Typography.phone.R_12px
- 색상: `gray_900` (#4E5968)
- 상단 여백: 8dp
- 우측 여백: 8dp (화살표 공간)

**시간**
- 스타일: Typography.phone.R_11px
- 색상: `gray_900` (#4E5968)
- 상단 여백: 8dp

---

#### 3.3.3 체크리스트 아이템

**사양**
- 높이: 68dp
- 배경: `b_work_record_menu`
- 클릭 가능, 포커스 가능

**체크 아이콘**
- 크기: 16dp × 16dp
- 좌측 여백: 16dp

**체크리스트 제목**
- 스타일: Typography.phone.M_14px
- 색상: `gray_950` (#18191B)
- 상단 여백: 12dp
- 좌측 여백: 12dp (체크 아이콘 기준)
- 최대 1줄, ellipsize: end

**체크리스트 설명**
- 스타일: Typography.phone.R_12px
- 색상: `gray_700` (#868A93)
- 상단 여백: 8dp
- 좌측 여백: 12dp
- 최대 1줄, ellipsize: end

---

### 3.4 Label Components

#### 3.4.1 상태 라벨

**사양**
- 높이: wrap_content
- 패딩: 좌우 8dp, 상하 4dp
- Corner Radius: 4dp
- 텍스트 스타일: Typography.phone.SB_12px
- 텍스트 색상: `gray_0` (#FFFFFF)

**상태별 배경색**
- 진행전: `#FA517B`
- 작업중: `#4EC4FF`
- 종료: `#E7EAEF` (텍스트 색상: `gray_950`)
- 작업중단: `#B0B4BA`

---

#### 3.4.2 작업 유형 라벨

**사양**
- 높이: wrap_content
- 패딩: 좌우 8dp, 상하 4dp
- Corner Radius: 4dp
- 배경색: `label_work_type_bg` (#4E5968)
- 텍스트 스타일: Typography.phone.SB_12px
- 텍스트 색상: `gray_0` (#FFFFFF)

---

### 3.5 Popup Components

#### 3.5.1 하단 팝업

**사양**
- 오버레이 배경: `black_50` (#80000000)
- 컨테이너 배경: `gray_0` (#FFFFFF)
- Corner Radius: 8dp (상단만)
- 패딩:
  - 좌우: 16dp
  - 상단: 24dp
  - 하단: 24dp

**제목**
- 스타일: Typography.phone.B_16px
- 색상: `gray_950` (#18191B)

**설명**
- 스타일: Typography.phone.M_14px
- 색상: `gray_950` (#18191B)
- 상단 여백: 24dp
- Line Spacing: 2dp

**정보 텍스트**
- 스타일: Typography.phone.M_12px
- 색상: `gray_900` (#4E5968)
- 상단 여백: 24dp
- Line Spacing: 2dp

**버튼 영역**
- 상단 여백: 24dp
- 버튼 간격: 6dp
- 버튼 높이: 48dp (Large), 44dp (Medium)

---

### 3.6 Icon Components

#### 3.6.1 아이콘 크기

| 용도 | 크기 | 설명 |
|------|------|------|
| 작은 아이콘 | 12dp × 12dp | 화살표, 작은 버튼 |
| 작은 아이콘 | 16dp × 16dp | 체크 아이콘, 작은 액션 |
| 중간 아이콘 | 20dp × 20dp | 버튼 내부 아이콘, 삭제 버튼 |
| 중간 아이콘 | 24dp × 24dp | 알림, 설정, 참여자 아이콘 |
| 큰 아이콘 | 32dp × 32dp | 스크롤 아이콘 |
| 큰 아이콘 | 36dp × 36dp | 로그인 화면 버튼 아이콘 |
| 초대형 아이콘 | 64dp × 64dp | 컨트롤 아이콘 |

---

### 3.7 Divider Components

#### 3.7.1 구분선

**사양**
- 높이: 0.5dp
- 색상: `gray_300` (#E4E4E4)
- 좌우 여백: 0dp (기본)

**사용 예시**
- 작업카드 아이템 내부 구분선
- 리스트 아이템 간 구분선

---

### 3.8 Toast Components

#### 3.8.1 토스트

**사양**
- 배경색: `toast_bg` (#82868A)
- Corner Radius: 4dp (추정)
- 패딩: 좌우 16dp, 상하 12dp (추정)
- 텍스트 색상: `white` (#FFFFFF)
- 텍스트 스타일: Typography.phone.B_14px (추정)

---

## 4. Compose Multiplatform 구현 가이드

### 4.1 Color 정의 예시

```kotlin
object AppColors {
    // Primary
    val Primary = Color(0xFF368DED)
    val PrimaryPressed = Color(0xFF2A6CB8)
    val PrimaryDisabled = Color(0xFFF2F2F2)
    val PrimaryDisabledText = Color(0xFFBDBDBD)
    
    // Background
    val Background = Color(0xFFECEFF8)
    val SectionBackground = Color(0xFFF6F7FB)
    val White = Color(0xFFFFFFFF)
    
    // Text
    val MainText = Color(0xFF333333)
    val DisabledText = Color(0xFF7A8086)
    val Gray950 = Color(0xFF18191B)
    val Gray900 = Color(0xFF4E5968)
    val Gray700 = Color(0xFF868A93)
    val Gray600 = Color(0xFF919191)
    val Gray400 = Color(0xFFCECECE)
    val Gray300 = Color(0xFFE4E4E4)
    
    // Status
    val StatusInProgress = Color(0xFF4B9DFF)
    val StatusCompleted = Color(0xFF00C399)
    val StatusPending = Color(0xFF82868A)
    
    // Label
    val LabelBgPending = Color(0xFFFA517B)
    val LabelBgInProgress = Color(0xFF4EC4FF)
    val LabelBgCompleted = Color(0xFFE7EAEF)
    val LabelBgForceStop = Color(0xFFB0B4BA)
    
    // Destructive
    val DestructiveText = Color(0xFFFF3B30)
    val Destructive = Color(0xFFFDECEA)
    
    // Overlay
    val Black50 = Color(0x80000000)
    val Black36 = Color(0x5C000000)
}
```

---

### 4.2 Typography 정의 예시

```kotlin
object AppTypography {
    // Pretendard ExtraBold
    val EB32 = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight(800),
        fontFamily = FontFamily(Font(R.font.pretendard_extrabold))
    )
    
    val EB28 = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight(800),
        fontFamily = FontFamily(Font(R.font.pretendard_extrabold))
    )
    
    val EB24 = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight(800),
        fontFamily = FontFamily(Font(R.font.pretendard_extrabold))
    )
    
    val EB20 = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight(800),
        fontFamily = FontFamily(Font(R.font.pretendard_extrabold))
    )
    
    val EB18 = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight(800),
        fontFamily = FontFamily(Font(R.font.pretendard_extrabold))
    )
    
    val EB16 = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_extrabold))
    )
    
    val EB14 = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_extrabold))
    )
    
    val EB12 = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_extrabold))
    )
    
    val EB10 = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_extrabold))
    )
    
    // Pretendard Bold
    val B24 = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_bold))
    )
    
    val B22 = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_bold))
    )
    
    val B20 = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_bold))
    )
    
    val B18 = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_bold))
    )
    
    val B16 = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_bold))
    )
    
    val B14 = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_bold))
    )
    
    val B12 = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_bold))
    )
    
    val B10 = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_bold))
    )
    
    // Pretendard SemiBold
    val SB28 = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_semibold))
    )
    
    val SB20 = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_semibold))
    )
    
    val SB18 = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_semibold))
    )
    
    val SB16 = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_semibold))
    )
    
    val SB14 = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_semibold))
    )
    
    val SB12 = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_semibold))
    )
    
    val SB600_11 = TextStyle(
        fontSize = 11.sp,
        fontWeight = FontWeight(600),
        fontFamily = FontFamily(Font(R.font.pretendard_semibold))
    )
    
    val SB10 = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_semibold))
    )
    
    // Pretendard Medium
    val M44 = TextStyle(
        fontSize = 44.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_medium))
    )
    
    val M18 = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_medium))
    )
    
    val M16 = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_medium))
    )
    
    val M14 = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_medium))
    )
    
    val M12 = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_medium))
    )
    
    val M11 = TextStyle(
        fontSize = 11.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_medium))
    )
    
    val M10 = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.pretendard_medium))
    )
    
    // Pretendard Regular
    val R18 = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight(400),
        fontFamily = FontFamily(Font(R.font.pretendard_regular))
    )
    
    val R16 = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight(400),
        fontFamily = FontFamily(Font(R.font.pretendard_regular))
    )
    
    val R14 = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight(400),
        fontFamily = FontFamily(Font(R.font.pretendard_regular))
    )
    
    val R12 = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight(400),
        fontFamily = FontFamily(Font(R.font.pretendard_regular))
    )
    
    val R11 = TextStyle(
        fontSize = 11.sp,
        fontWeight = FontWeight(400),
        fontFamily = FontFamily(Font(R.font.pretendard_regular))
    )
    
    val R10 = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight(400),
        fontFamily = FontFamily(Font(R.font.pretendard_regular))
    )
}
```

---

### 4.3 Spacing 정의 예시

```kotlin
object AppSpacing {
    val XS = 4.dp    // 매우 작은 간격
    val SM = 8.dp    // 작은 간격
    val MD = 12.dp   // 중간 간격
    val LG = 16.dp   // 큰 간격
    val XL = 20.dp   // 매우 큰 간격
    val XXL = 24.dp  // 초대형 간격
    val XXXL = 28.dp // 최대 간격
    val XXXXL = 36.dp
    val XXXXXL = 48.dp
}
```

---

### 4.4 Shape 정의 예시

```kotlin
object AppShapes {
    val Button = RoundedCornerShape(4.dp)
    val Card = RoundedCornerShape(8.dp)
    val Label = RoundedCornerShape(4.dp)
    val Input = RoundedCornerShape(4.dp)
    val PopupTop = RoundedCornerShape(
        topStart = 8.dp,
        topEnd = 8.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )
    val HomeMenuTab = RoundedCornerShape(12.dp)
}
```

---

### 4.5 Button 컴포넌트 예시

```kotlin
@Composable
fun PrimaryContainedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: ButtonSize = ButtonSize.Large
) {
    val height = when (size) {
        ButtonSize.Large -> 48.dp
        ButtonSize.Medium -> 44.dp
        ButtonSize.Small -> 38.dp
    }
    
    val textStyle = when (size) {
        ButtonSize.Large -> AppTypography.B16
        ButtonSize.Medium -> AppTypography.B14
        ButtonSize.Small -> AppTypography.B12
    }
    
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(height)
            .shadow(
                elevation = 5.dp,
                shape = AppShapes.Button
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) AppColors.White else AppColors.PrimaryDisabled,
            contentColor = if (enabled) AppColors.MainText else AppColors.DisabledText
        ),
        shape = AppShapes.Button
    ) {
        Text(
            text = text,
            style = textStyle
        )
    }
}
```

---

### 4.6 Input 컴포넌트 예시

```kotlin
@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    enabled: Boolean = true,
    isError: Boolean = false
) {
    val borderColor = when {
        isError -> AppColors.DestructiveText
        else -> Color(0xFFD1D1D1)
    }
    
    val borderWidth = if (isError) 2.dp else 1.dp
    
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .height(48.dp)
            .border(
                width = borderWidth,
                color = borderColor,
                shape = AppShapes.Input
            ),
        enabled = enabled,
        textStyle = AppTypography.M14.copy(color = AppColors.Gray950),
        placeholder = {
            Text(
                text = hint,
                style = AppTypography.R14.copy(color = AppColors.Gray600)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = AppColors.White,
            unfocusedContainerColor = AppColors.White,
            disabledContainerColor = AppColors.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = AppShapes.Input
    )
}
```

---

### 4.7 Card 컴포넌트 예시

```kotlin
@Composable
fun WorkCardItem(
    workName: String,
    status: WorkStatus,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppSpacing.LG)
            .shadow(
                elevation = 6.dp,
                shape = AppShapes.Card,
                spotColor = AppColors.CommonShadow
            ),
        shape = AppShapes.Card,
        colors = CardDefaults.cardColors(
            containerColor = AppColors.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppSpacing.LG,
                    vertical = AppSpacing.MD
                )
        ) {
            // 라벨 영역
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppSpacing.SM)
            ) {
                StatusLabel(status = status)
            }
            
            Spacer(modifier = Modifier.height(AppSpacing.SM))
            
            // 작업명
            Text(
                text = workName,
                style = AppTypography.B16,
                color = AppColors.Gray950,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(AppSpacing.MD))
            
            // 구분선
            Divider(
                color = AppColors.Gray300,
                thickness = 0.5.dp
            )
            
            // 기타 정보...
        }
    }
}
```

---

### 4.8 Label 컴포넌트 예시

```kotlin
@Composable
fun StatusLabel(
    status: WorkStatus,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor) = when (status) {
        WorkStatus.Pending -> AppColors.LabelBgPending to AppColors.White
        WorkStatus.InProgress -> AppColors.LabelBgInProgress to AppColors.White
        WorkStatus.Completed -> AppColors.LabelBgCompleted to AppColors.Gray950
        WorkStatus.ForceStop -> AppColors.LabelBgForceStop to AppColors.White
    }
    
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = AppShapes.Label
            )
            .padding(
                horizontal = AppSpacing.SM,
                vertical = AppSpacing.XS
            )
    ) {
        Text(
            text = status.displayName,
            style = AppTypography.SB12,
            color = textColor
        )
    }
}
```

---

## 5. 화면별 상세 레이아웃 스펙

### 5.1 Login 화면

**전체 구조**
- 배경색: `white` (#FFFFFF)
- ConstraintLayout 사용

**회사 로고**
- 크기: 180dp × 40dp
- 상단 여백: 108dp
- 중앙 정렬

**아이디 입력 필드**
- 높이: 48dp
- 좌우 여백: 16dp
- 상단 여백: 48dp (로고 기준)
- 내부 좌측 패딩: 16dp
- 내부 우측 패딩: 52dp
- 배경: `input_background`
- 텍스트 스타일: Typography.phone.SB_14px
- 힌트 색상: `edit_text_hint_color` (#E4E4E4)

**비밀번호 입력 필드**
- 높이: 48dp
- 좌우 여백: 16dp
- 상단 여백: 20dp (아이디 입력 필드 기준)
- 내부 좌측 패딩: 16dp
- 내부 우측 패딩: 52dp

**자동 로그인 체크박스**
- 크기: 16dp × 16dp
- 상단 여백: 12dp (비밀번호 입력 필드 기준)
- 좌측 여백: 16dp (입력 필드와 동일)
- 텍스트 스타일: Typography.phone.B_12px
- 텍스트 색상: `auto_login_color` (#6B7684)
- 텍스트-체크박스 간격: 8dp

**로그인 버튼**
- 높이: 48dp
- 좌우 여백: 16dp
- 상단 여백: 36dp
- 배경: Primary Contained
- 텍스트 스타일: Typography.phone.B_16px

**계정 찾기**
- 상단 여백: 16dp
- 텍스트 스타일: Typography.phone.B_14px
- 텍스트 색상: `find_account_color` (#868A93)
- 중앙 정렬

**하단 로고**
- 크기: 108dp × 40dp
- 하단 여백: 28dp
- 중앙 정렬

---

### 5.2 Home 화면

**전체 구조**
- 배경색: `section_background` (#F6F7FB)
- ConstraintLayout 사용

**사용자 정보 영역**
- 배경색: `gray_0` (#FFFFFF)
- 최소 높이: 68dp
- 상단 여백: 0dp
- 좌측 여백: 16dp
- 우측 여백: 16dp
- 내부 상단 여백: 28dp

**사용자 이름**
- 스타일: Typography.phone.B_18px
- 색상: `gray_950` (#18191B)
- 최대 1줄, ellipsize: end

**구분자 (|)**
- 스타일: Typography.phone.M_18px
- 색상: `gray_600` (#919191)
- 좌측 여백: 6dp

**부서명**
- 스타일: Typography.phone.B_14px
- 색상: `gray_600` (#919191)
- 좌측 여백: 6dp
- 최대 길이: 10자
- 최대 1줄

**화살표 아이콘**
- 크기: 12dp × 24dp
- 좌측 여백: 8dp

**알림 아이콘**
- 크기: 24dp × 24dp
- 우측 여백: 8dp
- 상단 여백: 28dp

**설정 아이콘**
- 크기: 24dp × 24dp
- 우측 여백: 16dp
- 상단 여백: 28dp

**탭 메뉴 영역**
- 높이: 75dp
- 좌우 패딩: 16dp
- 내부 컨테이너 높이: 43dp
- 내부 컨테이너 배경: `b_home_menu_corner` (Corner Radius: 12dp)
- 내부 컨테이너 상하 여백: 16dp

**탭 버튼**
- 높이: 35dp
- 좌우 여백: 4dp
- 배경: `s_home_menu_btn` (selector)
- Elevation: 6dp
- Shadow Color: `common_shadow` (#696969)

**탭 텍스트**
- 스타일: Typography.phone.B_16px
- 색상: `s_gray700_gray950` (selector)
  - Selected: `gray_950` (#18191B)
  - Unselected: `gray_700` (#868A93)

**카운트 텍스트**
- 스타일: Typography.phone.B_14px
- 색상: `gray_500` (#A6ABB3)
- 좌측 여백: 8dp

**작업 목록 영역**
- 상단 여백: 0dp
- RecyclerView 좌우 패딩: 16dp

**전체보기 버튼**
- 우측 여백: 16dp
- 아이콘 크기: 24dp × 0dp
- 아이콘-텍스트 간격: 4dp
- 텍스트 스타일: Typography.phone.SB_14px
- 텍스트 색상: `gray_950` (#18191B)
- 텍스트 좌측 여백: 28dp

**플로팅 버튼 (새 작업 만들기)**
- 배경: `b_home_new_work_btn`
- Elevation: 4dp
- Shadow Color: `white` (#FFFFFF)
- 우측 여백: 16dp
- 하단 여백: 42dp
- 내부 패딩:
  - 상하: 10dp
  - 좌우: 16dp
- 아이콘 크기: 20dp × 20dp
- 아이콘-텍스트 간격: 8dp
- 텍스트 스타일: Typography.phone.SB_14px
- 텍스트 색상: `white` (#FFFFFF)
- 텍스트-화살표 간격: 4dp
- 화살표 크기: 12dp × 24dp

---

### 5.3 WorkCardNew 화면

**전체 구조**
- 배경색: `white` (#FFFFFF)
- ConstraintLayout 사용

**RecyclerView**
- 상단 패딩: 28dp
- 좌우 패딩: 16dp
- 하단 패딩: 0dp
- 하단 여백: 16dp

**확인 버튼**
- 높이: 48dp
- 좌우 여백: 16dp
- 하단 여백: 16dp
- 배경: Primary Contained
- 텍스트: "확인"

---

### 5.4 작업카드 입력 필드 아이템

**전체 구조**
- 상하 패딩: 8dp

**레이블 영역**
- 레이블 스타일: Typography.phone.B_14px
- 레이블 색상: `selector_input_title_text` (selector)
- 필수 표시 (*): Typography.phone.B_14px, 색상 `selector_required_text`
- 레이블-필수 표시 간격: 4dp
- 글자 수 표시: Typography.phone.M_11px, 색상 `gray_700` (#868A93), 우측 정렬

**입력 영역**
- 상단 여백: 8dp
- 배경: `bg_input_selector`
- 내부 패딩: 좌우 8dp, 상하 9dp
- 텍스트 스타일: Typography.phone.M_14px
- 텍스트 색상: `selector_input_text` (selector)
- 힌트 색상: `gray_600` (#919191)

---

### 5.5 작업카드 정보 필드 아이템

**전체 구조**
- 상하 패딩: 8dp

**레이블**
- 너비: 80dp
- 스타일: Typography.phone.M_14px
- 색상: `gray_900` (#4E5968)

**값**
- 좌측 여백: 8dp
- 스타일: Typography.phone.M_14px
- 색상: `gray_950` (#18191B)

---

### 5.6 작업카드 참여자 필드 아이템

**전체 구조**
- 상하 패딩: 8dp

**레이블**
- 스타일: Typography.phone.B_14px
- 색상: `selector_input_title_text` (selector)

**필수 표시**
- 스타일: Typography.phone.B_14px
- 색상: `destructive_text` (#FF3B30)
- 좌측 여백: 4dp

**검색 입력 영역**
- 상단 여백: 8dp
- 배경: `bg_input_selector`
- 내부 패딩: 좌우 12dp, 상하 12dp
- 텍스트 스타일: Typography.phone.M_14px
- 텍스트 색상: `gray_900` (#4E5968)
- 힌트 색상: `gray_600` (#919191)

**참여자 리스트**
- 상단 여백: 8dp

---

### 5.7 Notice 화면

**전체 구조**
- 배경색: `gray_0` (#FFFFFF)
- ConstraintLayout 사용

**상단 영역**
- 좌측 여백: 16dp
- 우측 여백: 16dp
- 상단 여백: 28dp

**읽지 않은 알림 텍스트**
- 스타일: Typography.phone.SB_14px
- 색상: `gray_600` (#919191)
- 좌측 정렬

**날짜 범위 텍스트**
- 스타일: Typography.phone.SB_12px
- 색상: `gray_950` (#18191B)
- 우측 정렬
- 상단 여백: 29.5dp

**RecyclerView**
- 좌우 여백: 16dp
- 상단 여백: 13.5dp

---

### 5.8 공지사항 아이템

**전체 구조**
- ConstraintLayout 사용

**아이콘**
- 크기: 24dp × 24dp
- 좌측 정렬

**메시지 컨테이너**
- 좌측 여백: 12dp (아이콘 기준)
- 배경: `bg_notice_item`
- 내부 패딩: 12dp

**제목**
- 스타일: Typography.phone.B_16px (추정)
- 색상: `gray_950` (#18191B)

**내용**
- 스타일: Typography.phone.R_12px
- 색상: `gray_900` (#4E5968)
- 상단 여백: 8dp
- 우측 여백: 8dp (화살표 공간)

**화살표 아이콘**
- 크기: 16dp × 16dp
- 우측 정렬

**시간**
- 스타일: Typography.phone.R_11px
- 색상: `gray_900` (#4E5968)
- 상단 여백: 8dp

---

### 5.9 체크리스트 아이템

**전체 구조**
- 높이: 68dp
- 배경: `b_work_record_menu`
- 클릭 가능, 포커스 가능

**체크 아이콘**
- 크기: 16dp × 16dp
- 좌측 여백: 16dp
- 중앙 정렬 (수직)

**체크리스트 제목**
- 스타일: Typography.phone.M_14px
- 색상: `gray_950` (#18191B)
- 상단 여백: 12dp
- 좌측 여백: 12dp (체크 아이콘 기준)
- 최대 1줄, ellipsize: end

**체크리스트 설명**
- 스타일: Typography.phone.R_12px
- 색상: `gray_700` (#868A93)
- 상단 여백: 8dp
- 좌측 여백: 12dp
- 최대 1줄, ellipsize: end

---

### 5.10 팝업

**오버레이**
- 배경색: `black_50` (#80000000)
- 전체 화면

**팝업 컨테이너**
- 배경색: `gray_0` (#FFFFFF)
- Corner Radius: 8dp (상단만)
- 하단 정렬
- 좌우 패딩: 16dp
- 상단 패딩: 24dp
- 하단 패딩: 24dp

**제목**
- 스타일: Typography.phone.B_16px
- 색상: `gray_950` (#18191B)

**설명**
- 스타일: Typography.phone.M_14px
- 색상: `gray_950` (#18191B)
- 상단 여백: 24dp
- Line Spacing: 2dp

**정보 텍스트**
- 스타일: Typography.phone.M_12px
- 색상: `gray_900` (#4E5968)
- 상단 여백: 24dp
- Line Spacing: 2dp

**버튼 영역**
- 상단 여백: 24dp
- 버튼 간격: 6dp
- 좌측 버튼: Primary Outlined, Medium
- 우측 버튼: Primary Contained, Medium

---

## 6. 상태별 색상 (Selector)

### 6.1 텍스트 색상 Selector

**selector_input_text**
- Normal: `gray_950` (#18191B)
- Disabled: `gray_600` (#919191)

**selector_input_title_text**
- Normal: `gray_950` (#18191B)
- Disabled: `gray_600` (#919191)

**selector_required_text**
- Normal: `destructive_text` (#FF3B30)
- Disabled: `gray_600` (#919191)

**s_gray700_gray950**
- Selected: `gray_950` (#18191B)
- Unselected: `gray_700` (#868A93)

---

### 6.2 배경 색상 Selector

**s_home_menu_btn**
- Selected: `gray_0` (#FFFFFF)
- Unselected: `transparent` (#00000000)

---

## 7. 아이콘 리소스

### 7.1 주요 아이콘 크기

| 아이콘 | 크기 | 용도 |
|--------|------|------|
| 화살표 (좌/우) | 12dp × 24dp | 네비게이션 |
| 화살표 (상/하) | 20dp × 20dp | 작업카드 내부 |
| 삭제 | 20dp × 20dp | 입력 필드 삭제 버튼 |
| 눈 (비밀번호 표시) | 20dp × 20dp | 비밀번호 표시/숨김 |
| 알림 | 24dp × 24dp | 알림 아이콘 |
| 설정 | 24dp × 24dp | 설정 아이콘 |
| 참여자 | 24dp × 24dp | 참여자 아이콘 |
| 체크 | 16dp × 16dp | 체크리스트 체크 아이콘 |
| 플로팅 아이콘 | 20dp × 20dp | 새 작업 만들기 버튼 |

---

## 8. Compose 구현 체크리스트

### 8.1 필수 구현 사항

- [ ] Color 객체 정의 (모든 Hex 값)
- [ ] Typography 스타일 정의 (모든 폰트 크기/무게)
- [ ] Spacing 상수 정의 (4dp ~ 48dp)
- [ ] Shape 정의 (Corner Radius)
- [ ] Button 컴포넌트 (Primary, Error, Outlined)
- [ ] Input 컴포넌트 (TextField, TextInputLayout)
- [ ] Card 컴포넌트 (작업카드, 공지사항)
- [ ] Label 컴포넌트 (상태, 작업 유형)
- [ ] Popup 컴포넌트 (하단 팝업)
- [ ] Divider 컴포넌트
- [ ] Toast 컴포넌트

### 8.2 상태 관리

- [ ] Button 상태 (Normal, Focused, Pressed, Disabled)
- [ ] Input 상태 (Normal, Focused, Error, Disabled)
- [ ] Tab 상태 (Selected, Unselected)
- [ ] Ripple 효과 (버튼 클릭 시)

---

## 9. 문서 버전 정보

- **작성일**: 2025-01-22
- **버전**: 1.0.0
- **기반 코드**: SmartGlass Mobile (phone 플레이버)
- **분석 범위**: app/src/main/res, app/src/phone/res

---

**문서 끝**

