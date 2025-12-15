# SmartGlass Mobile UI 디자인 시스템 명세서 (Phone)

## 목차
1. [Design Tokens](#design-tokens)
2. [Layout Structure](#layout-structure)
3. [Component Catalog](#component-catalog)

---

## Design Tokens

### 1. Colors

#### 1.1 Primary Colors
| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| primary | `#368DED` | 주요 액션 버튼 배경색 |
| primary_pressed | `#2A6CB8` | 주요 액션 버튼 눌림 상태 |
| primary_disabled | `#F2F2F2` | 비활성화된 버튼 배경 |
| primary_disabled_text | `#BDBDBD` | 비활성화된 버튼 텍스트 |

#### 1.2 Secondary Colors
| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| secondary | `#D7E8F8` | 보조 액션 배경색 |
| secondary_pressed | `#BCD8F0` | 보조 액션 눌림 상태 |
| secondary_disabled | `#F5F8FB` | 보조 액션 비활성화 배경 |
| secondary_disabled_text | `#A3B1BF` | 보조 액션 비활성화 텍스트 |

#### 1.3 Tertiary Colors
| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| tertiary | `#18A1C5` | 3차 액션 색상 |

#### 1.4 Destructive Colors
| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| destructive | `#FDECEA` | 삭제/위험 액션 배경 |
| destructive_pressed | `#F8D5D3` | 삭제/위험 액션 눌림 상태 |
| destructive_disabled | `#F9F3F3` | 삭제/위험 액션 비활성화 배경 |
| destructive_text | `#FF3B30` | 삭제/위험 액션 텍스트 |
| destructive_disabled_text | `#D6C5C5` | 삭제/위험 액션 비활성화 텍스트 |

#### 1.5 Background Colors
| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| background | `#ECEFF8` | 화면 배경색 |
| section_background | `#F6F7FB` | 섹션 배경색 |
| gray_0 | `#FFFFFF` | 흰색 (카드 배경 등) |
| gray_50 | `#FAFAFB` | 매우 밝은 회색 |
| gray_100 | `#F1F1F3` | 밝은 회색 |
| gray_200 | `#E7EAEF` | 밝은 회색 (태그 배경) |
| gray_300 | `#E4E4E4` | 회색 (구분선, 힌트 텍스트) |
| gray_400 | `#CECECE` | 중간 회색 |
| gray_500 | `#A6ABB3` | 중간 회색 (보조 텍스트) |
| gray_600 | `#919191` | 어두운 회색 |
| gray_700 | `#868A93` | 어두운 회색 (레이블 텍스트) |
| gray_800 | `#666666` | 매우 어두운 회색 |
| gray_900 | `#4E5968` | 매우 어두운 회색 (작업 타입 레이블) |
| gray_950 | `#18191B` | 거의 검은색 (주요 텍스트) |

#### 1.6 Status Colors
| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| status_force_stop | `#FF3B30` | 강제 중지 상태 |
| status_in_progress | `#4B9DFF` | 진행 중 상태 |
| status_paused | `#FA7564` | 일시정지 상태 |
| status_completed | `#00C399` | 완료 상태 |
| status_pending | `#82868A` | 대기 상태 |
| online | `#219653` | 온라인 상태 |

#### 1.7 Work Card Status Label Colors
| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| label_bg_pending | `#FA517B` | 진행전 배경 |
| label_bg_in_progress | `#4EC4FF` | 작업중 배경 |
| label_bg_completed | `#E7EAEF` | 종료 배경 |
| label_bg_force_stop | `#B0B4BA` | 작업중단 배경 |
| label_work_type | `#4E5968` | 작업 타입 텍스트 (초동조사 등) |
| label_work_type_bg | `#4E5968` | 작업 타입 배경 |

#### 1.8 Menu Colors
| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| blue_menu | `#4B9DFF` | 메뉴 블루 |
| orange_menu | `#FA7564` | 메뉴 오렌지 |
| purple_menu | `#855BDE` | 메뉴 퍼플 |

#### 1.9 Text Colors
| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| main_text | `#333333` | 주요 텍스트 |
| disabled_text | `#7A8086` | 비활성화 텍스트 |
| edit_text_hint_color | `#E4E4E4` | 입력창 힌트 색상 |
| auto_login_color | `#6B7684` | 자동 로그인 텍스트 |
| find_account_color | `#868A93` | 계정 찾기 텍스트 |

#### 1.10 White Opacity Variants
| 색상명 | Hex 값 | Alpha | 용도 |
|--------|--------|-------|------|
| white | `#FFFFFF` | 100% | 기본 흰색 |
| white_97 | `#F7FFFFFF` | 97% | - |
| white_90 | `#E6FFFFFF` | 90% | - |
| white_80 | `#CCFFFFFF` | 80% | - |
| white_60 | `#99FFFFFF` | 60% | - |
| white_50 | `#80FFFFFF` | 50% | - |
| white_38 | `#61FFFFFF` | 38% | - |
| white_24 | `#3DFFFFFF` | 24% | - |
| white_20 | `#33FFFFFF` | 20% | 대기 화면 텍스트 |
| white_12 | `#1FFFFFFF` | 12% | - |
| white_06 | `#0FFFFFFF` | 6% | - |
| white_05 | `#0DFFFFFF` | 5% | - |

#### 1.11 Black Opacity Variants
| 색상명 | Hex 값 | Alpha | 용도 |
|--------|--------|-------|------|
| black | `#000000` | 100% | 기본 검은색 |
| black_84 | `#D6000000` | 84% | - |
| black_64 | `#A3000000` | 64% | - |
| black_60 | `#99000000` | 60% | - |
| black_50 | `#80000000` | 50% | 팝업 오버레이 |
| black_44 | `#70000000` | 44% | - |
| black_40 | `#66000000` | 40% | - |
| black_36 | `#5C000000` | 36% | Ripple 효과 |
| black_28 | `#47000000` | 28% | - |
| black_24 | `#3D000000` | 24% | 회의 메뉴바 배경 |
| black_15 | `#26000000` | 15% | - |
| black_12 | `#1F000000` | 12% | - |
| black_10 | `#1A000000` | 10% | - |
| black_5 | `#0D000000` | 5% | 대기 화면 배경 |
| black_4 | `#0A000000` | 4% | - |

#### 1.12 Other Colors
| 색상명 | Hex 값 | 용도 |
|--------|--------|------|
| transparent | `#00000000` | 투명 |
| dark | `#2B2B2B` | 어두운 배경 |
| disabled | `#D7D7D7` | 비활성화 배경 |
| success | `#23A16C` | 성공 상태 |
| icon_bg | `#FFEA9F` | 아이콘 배경 |
| red | `#FF3B30` | 빨간색 (destructive_text와 동일) |
| toast_bg | `#82868A` | 토스트 배경 |
| nav | `#0B1B38` | 네비게이션 배경 |
| icon_plus_color | `#FFFFFF` | 플러스 아이콘 색상 |
| black_disable | `#CCD3D3D3` | 비활성화 검은색 |
| pink_label | `#FA517B` | 핑크 레이블 |
| blue_label | `#4EC4FF` | 블루 레이블 |
| blue_card | `#E9F2FF` | 블루 카드 배경 |
| blue_btn | `#33FF9771` | 블루 버튼 (투명도 포함) |
| pink_card | `#FEEFEF` | 핑크 카드 배경 |
| pink_btn | `#33FF9771` | 핑크 버튼 (투명도 포함) |
| red_10 | `#1AFF3B30` | 빨간색 10% 투명도 |
| red_30 | `#4DFF3B30` | 빨간색 30% 투명도 |
| green | `#16BF7E` | 초록색 |
| status_bar_bg | `#FFFFFF` | 상태바 배경 |

---

### 2. Typography

#### 2.1 Font Families
- **Pretendard**: 주요 폰트 패밀리
  - `pretendard_regular` (400)
  - `pretendard_medium` (500)
  - `pretendard_semibold` (600)
  - `pretendard_bold` (700)
  - `pretendard_extrabold` (800)

#### 2.2 Typography Scale (Phone)

##### ExtraBold (EB) - 800 Weight
| 스타일명 | Font Size | Weight | Line Height | 용도 |
|----------|-----------|--------|-------------|------|
| Typography.phone.EB_32px | 32sp | 800 | Auto | 대기 화면 수신자 이름 |
| Typography.phone.EB_28px | 28sp | 800 | Auto | - |
| Typography.phone.EB_24px | 24sp | 800 | Auto | - |
| Typography.phone.EB_20px | 20sp | 800 | Auto | - |
| Typography.phone.EB_18px | 18sp | 800 | Auto | - |
| Typography.phone.EB_16px | 16sp | 700 | Auto | - |
| Typography.phone.EB_14px | 14sp | 700 | Auto | - |
| Typography.phone.EB_12px | 12sp | 700 | Auto | - |
| Typography.phone.EB_10px | 10sp | 700 | Auto | - |

##### Bold (B) - 700 Weight
| 스타일명 | Font Size | Weight | Line Height | 용도 |
|----------|-----------|--------|-------------|------|
| Typography.phone.B_24px | 24sp | 700 | Auto | - |
| Typography.phone.B_22px | 22sp | 700 | Auto | - |
| Typography.phone.B_20px | 20sp | 700 | Auto | - |
| Typography.phone.B_18px | 18sp | 700 | Auto | 홈 화면 사용자 이름 |
| Typography.phone.B_16px | 16sp | 700 | Auto | 버튼 텍스트 (Large), 작업 카드 제목 |
| Typography.phone.B_14px | 14sp | 700 | Auto | 버튼 텍스트 (Medium), 팝업 제목 |
| Typography.phone.B_12px | 12sp | 700 | Auto | 버튼 텍스트 (Small), 레이블 |
| Typography.phone.B_10px | 10sp | 700 | Auto | - |

##### SemiBold (SB) - 600-700 Weight
| 스타일명 | Font Size | Weight | Line Height | 용도 |
|----------|-----------|--------|-------------|------|
| Typography.phone.SB_28px | 28sp | 700 | Auto | - |
| Typography.phone.SB_20px | 20sp | 700 | Auto | 대기 화면 제목 |
| Typography.phone.SB_18px | 18sp | 700 | Auto | - |
| Typography.phone.SB_16px | 16sp | 700 | Auto | 홈 메뉴 버튼 |
| Typography.phone.SB_14px | 14sp | 700 | Auto | 새 작업 버튼, 채팅 제목 |
| Typography.phone.SB_12px | 12sp | 700 | Auto | 레이블, 상태 텍스트 |
| Typography.phone.SB_600_11px | 11sp | 600 | Auto | - |
| Typography.phone.SB_10px | 10sp | 700 | Auto | - |

##### Medium (M) - 500 Weight
| 스타일명 | Font Size | Weight | Line Height | 용도 |
|----------|-----------|--------|-------------|------|
| Typography.phone.M_44px | 44sp | 700 | Auto | - |
| Typography.phone.M_18px | 18sp | 700 | Auto | 로그인 구분선 |
| Typography.phone.M_16px | 16sp | 700 | Auto | 대기 화면 상태 텍스트 |
| Typography.phone.M_14px | 14sp | 700 | Auto | 입력창 텍스트, 작업 카드 내용 |
| Typography.phone.M_12px | 12sp | 700 | Auto | 작업 카드 생성자, 시간 |
| Typography.phone.M_11px | 11sp | 700 | Auto | 입력창 글자 수 카운터 |
| Typography.phone.M_10px | 10sp | 700 | Auto | - |

##### Regular (R) - 400 Weight
| 스타일명 | Font Size | Weight | Line Height | 용도 |
|----------|-----------|--------|-------------|------|
| Typography.phone.R_18px | 18sp | 400 | Auto | - |
| Typography.phone.R_16px | 16sp | 400 | Auto | - |
| Typography.phone.R_14px | 14sp | 400 | Auto | 채팅 메시지, 팝업 설명 |
| Typography.phone.R_12px | 12sp | 400 | Auto | 통화 카드 마지막 통화 레이블 |
| Typography.phone.R_11px | 11sp | 400 | Auto | - |
| Typography.phone.R_10px | 10sp | 400 | Auto | - |

#### 2.3 Text Color Mapping
- **주요 텍스트**: `gray_950` (#18191B)
- **보조 텍스트**: `gray_700` (#868A93), `gray_600` (#919191), `gray_500` (#A6ABB3)
- **힌트 텍스트**: `gray_300` (#E4E4E4), `gray_600` (#919191)
- **비활성화 텍스트**: `disabled_text` (#7A8086), `primary_disabled_text` (#BDBDBD)
- **흰색 텍스트**: `white` (#FFFFFF), `white_20` (#33FFFFFF)

---

### 3. Shape (Corner Radius)

| 컴포넌트/용도 | Corner Radius | Shape Type |
|---------------|---------------|------------|
| 버튼 (일반) | 8dp | Rectangle |
| 버튼 (원형 플로팅) | 50dp | Rectangle |
| 카드 | 8dp | Rectangle |
| 입력창 | 8dp | Rectangle |
| 팝업 (상단) | 8dp (topLeft, topRight) | Rectangle |
| 홈 메뉴 컨테이너 | 12dp | Rectangle |
| 태그/레이블 | 8dp | Rectangle |
| 참여자 태그 | 8dp | Rectangle |
| 회의 메뉴 아이콘 | - | Circle/Oval |

---

### 4. Spacing & Sizing

#### 4.1 Standard Spacing Scale
| 크기 | 값 | 용도 |
|------|-----|------|
| xs | 4dp | 매우 작은 간격 |
| sm | 8dp | 작은 간격 (레이블 간격, 아이콘-텍스트) |
| md | 12dp | 중간 간격 (섹션 간격) |
| lg | 16dp | 큰 간격 (화면 여백, 카드 내부 패딩) |
| xl | 20dp | 매우 큰 간격 (섹션 간 큰 간격) |
| xxl | 24dp | 매우 큰 간격 (팝업 내부 여백) |
| xxxl | 28dp | 매우 큰 간격 (상단 여백) |
| xxxxl | 36dp | 매우 큰 간격 (버튼-입력창 간격) |
| xxxxxl | 48dp | 매우 큰 간격 (로고-입력창 간격) |

#### 4.2 Icon Sizes
| 용도 | 크기 |
|------|------|
| 작은 아이콘 | 12dp, 16dp, 20dp |
| 중간 아이콘 | 24dp, 32dp |
| 큰 아이콘 | 36dp, 40dp, 48dp, 64dp |
| 회의 메뉴 아이콘 | 64dp (control_icon_size) |
| 회의 메뉴 아이콘 높이 | 77dp (conference_icon_height) |

#### 4.3 Component Heights
| 컴포넌트 | 높이 |
|----------|------|
| 버튼 (Large) | 48dp |
| 버튼 (Medium) | 44dp |
| 버튼 (Small) | 38dp |
| 입력창 | 48dp |
| 새 비밀번호 입력창 | 50dp |
| 홈 사용자 정보 영역 | 68dp (minHeight) |
| 홈 메뉴 버튼 컨테이너 | 75dp |
| 홈 메뉴 버튼 | 43dp (높이), 35dp (내부 버튼) |
| 작업 카드 헤더 | 74dp |
| 상태바 | 40dp |
| 회의 메뉴바 (접힘) | 93dp |
| 회의 메뉴바 (펼침) | 251dp |
| 대기 화면 제목 | 40dp |
| 팝업 내부 패딩 | 24dp (상하좌우) |

---

## Layout Structure

### 1. 로그인 화면 (f_login.xml)

#### 구조
```
ConstraintLayout (match_parent)
├── ImageView (회사 로고)
│   ├── width: 180dp
│   ├── height: 40dp
│   └── marginTop: 108dp
├── EditText (아이디 입력)
│   ├── width: match_parent
│   ├── height: 48dp
│   ├── marginHorizontal: 16dp
│   ├── paddingLeft: 16dp
│   ├── paddingRight: 52dp
│   └── marginTop: 48dp (로고 아래)
├── Button (삭제 버튼)
│   ├── width: 20dp
│   ├── height: 20dp
│   └── marginEnd: 16dp
├── EditText (비밀번호 입력)
│   ├── width: match_parent
│   ├── height: 48dp
│   ├── marginHorizontal: 16dp
│   ├── paddingLeft: 16dp
│   ├── paddingRight: 52dp
│   └── marginTop: 20dp (아이디 입력 아래)
├── Button (비밀번호 표시/숨김)
│   ├── width: 20dp
│   ├── height: 20dp
│   └── marginEnd: 16dp
├── Button (자동 로그인 체크박스)
│   ├── width: 16dp
│   ├── height: 16dp
│   └── marginTop: 12dp
├── TextView (자동 로그인 텍스트)
│   └── marginStart: 8dp (체크박스 옆)
├── GlassButton (로그인 버튼)
│   ├── width: match_parent
│   ├── height: 48dp
│   ├── marginHorizontal: 16dp
│   └── marginTop: 36dp
├── Button (계정 찾기)
│   └── marginTop: 16dp
└── ImageView (하단 로고)
    ├── width: 108dp
    ├── height: 40dp
    └── marginBottom: 28dp
```

#### Spacing 값
- 화면 상단 여백: 108dp (로고)
- 로고-입력창 간격: 48dp
- 입력창 간격: 20dp
- 입력창-자동로그인 간격: 12dp
- 자동로그인-로그인버튼 간격: 36dp
- 로그인버튼-계정찾기 간격: 16dp
- 화면 좌우 여백: 16dp
- 하단 로고 여백: 28dp

---

### 2. 홈 화면 (f_home.xml)

#### 구조
```
ConstraintLayout (match_parent, background: section_background)
├── ConstraintLayout (사용자 정보 영역)
│   ├── width: match_parent
│   ├── height: wrap_content
│   ├── minHeight: 68dp
│   ├── background: gray_0
│   ├── paddingStart: 16dp
│   ├── paddingEnd: 16dp
│   └── paddingTop: 28dp
│   ├── ConstraintLayout (로그인 정보)
│   │   ├── marginStart: 16dp
│   │   ├── marginTop: 28dp
│   │   └── marginEnd: 8dp
│   │   ├── TextView (이름)
│   │   │   └── style: Typography.phone.B_18px
│   │   ├── TextView (구분선 "|")
│   │   │   ├── marginStart: 6dp
│   │   │   └── textColor: gray_600
│   │   └── TextView (부서명)
│   │       ├── marginStart: 6dp
│   │       ├── maxLength: 10
│   │       └── textColor: gray_600
│   ├── ImageView (알림 아이콘)
│   │   ├── width: 24dp
│   │   ├── height: 24dp
│   │   └── marginEnd: 8dp
│   └── View (설정 아이콘)
│       ├── width: 24dp
│       ├── height: 24dp
│       ├── marginTop: 28dp
│       └── marginEnd: 16dp
├── ConstraintLayout (메뉴 버튼 영역)
│   ├── width: match_parent
│   ├── height: 75dp
│   ├── paddingHorizontal: 16dp
│   └── marginTop: 0dp (사용자 정보 아래)
│   └── ConstraintLayout (메뉴 컨테이너)
│       ├── width: match_parent
│       ├── height: 43dp
│       ├── background: b_home_menu_corner (corner radius: 12dp)
│       ├── ConstraintLayout (나의 작업 버튼)
│       │   ├── width: 0dp (weight: 1)
│       │   ├── height: 35dp
│       │   ├── marginStart: 4dp
│       │   └── elevation: 5dp
│       └── ConstraintLayout (대기 중 통화 버튼)
│           ├── width: 0dp (weight: 1)
│           ├── height: 35dp
│           └── marginEnd: 4dp
├── ConstraintLayout (작업 목록 영역)
│   ├── width: match_parent
│   ├── height: 0dp (match_constraint)
│   └── marginTop: 0dp (메뉴 버튼 아래)
│   ├── ConstraintLayout (작업 상단)
│   │   ├── width: match_parent
│   │   ├── height: wrap_content
│   │   └── minHeight: 24dp
│   │   └── ConstraintLayout (전체 작업 버튼)
│   │       ├── marginEnd: 16dp
│   │       └── minHeight: 24dp
│   └── ConstraintLayout (작업 중간 - RecyclerView 영역)
│       ├── width: match_parent
│       ├── height: 0dp (match_constraint)
│       └── marginTop: 12dp
│       └── RecyclerView
│           ├── paddingHorizontal: 16dp
│           └── paddingTop: 0dp
│   └── ConstraintLayout (새 작업 버튼 - 플로팅)
│       ├── width: wrap_content
│       ├── height: wrap_content
│       ├── paddingVertical: 10dp
│       ├── paddingHorizontal: 16dp
│       ├── marginEnd: 16dp
│       ├── marginBottom: 42dp
│       └── background: b_home_new_work_btn (corner radius: 50dp)
│       ├── View (아이콘)
│       │   ├── width: 20dp
│       │   └── height: 20dp
│       ├── TextView (텍스트)
│       │   ├── marginStart: 8dp
│       │   └── marginEnd: 4dp
│       └── View (화살표)
│           ├── width: 12dp
│           └── height: 24dp
```

#### Spacing 값
- 사용자 정보 영역 상단 패딩: 28dp
- 사용자 정보 영역 좌우 패딩: 16dp
- 이름-구분선 간격: 6dp
- 구분선-부서명 간격: 6dp
- 메뉴 버튼 컨테이너 높이: 75dp
- 메뉴 버튼 내부 높이: 43dp
- 메뉴 버튼 내부 버튼 높이: 35dp
- 메뉴 버튼 간격: 4dp (좌우)
- 작업 상단-작업 목록 간격: 12dp
- 작업 목록 좌우 패딩: 16dp
- 새 작업 버튼 하단 여백: 42dp
- 새 작업 버튼 우측 여백: 16dp

---

### 3. 작업 카드 목록 아이템 (item_work_card_list_item.xml)

#### 구조
```
ConstraintLayout (카드 루트)
├── width: match_parent
├── height: wrap_content
├── background: bg_work_card_item_default (white, corner radius: 8dp)
├── elevation: 6dp
├── paddingHorizontal: 16dp
├── paddingTop: 16dp
└── paddingBottom: 12dp
├── LinearLayout (레이블 컨테이너)
│   ├── width: wrap_content
│   ├── height: wrap_content
│   ├── TextView (작업 타입 레이블)
│   │   ├── style: Typography.phone.SB_12px
│   │   ├── paddingHorizontal: 8dp
│   │   ├── paddingVertical: 4dp
│   │   ├── background: bg_label_work_type (gray_900)
│   │   └── textColor: gray_0
│   └── TextView (상태 레이블)
│       ├── style: Typography.phone.SB_12px
│       ├── marginStart: 8dp
│       ├── paddingHorizontal: 8dp
│       ├── paddingVertical: 4dp
│       └── textColor: gray_0
├── TextView (작업 생성자 레이블)
│   ├── style: Typography.phone.B_12px
│   ├── marginStart: 8dp
│   ├── marginEnd: 6dp
│   └── textColor: gray_700
├── TextView (작업 생성자 이름)
│   ├── style: Typography.phone.M_12px
│   └── textColor: gray_950
├── TextView (작업명)
│   ├── style: Typography.phone.B_16px
│   ├── marginTop: 8dp
│   ├── marginEnd: 4dp
│   └── textColor: gray_950
├── ImageView (화살표 아이콘)
│   ├── width: 20dp
│   ├── height: 20dp
│   └── marginTop: 2.5dp
├── View (구분선)
│   ├── width: match_parent
│   ├── height: 0.5dp
│   ├── marginTop: 12dp
│   └── background: gray_300
├── TextView (마지막 작업 레이블)
│   ├── style: Typography.phone.SB_12px
│   ├── marginTop: 8dp
│   └── textColor: gray_900
├── TextView (마지막 작업 이름)
│   ├── style: Typography.phone.M_14px
│   ├── marginStart: 12dp
│   ├── marginEnd: 4dp
│   └── textColor: gray_950
├── TextView (마지막 작업 시간)
│   ├── style: Typography.phone.M_12px
│   └── textColor: gray_600
└── ConstraintLayout (작업 참여자 영역)
    ├── width: match_parent
    ├── height: wrap_content
    └── marginTop: 4dp
    ├── TextView (작업 참여자 레이블)
    │   ├── style: Typography.phone.SB_12px
    │   └── textColor: gray_900
    ├── TextView (작업 참여자 이름)
    │   ├── style: Typography.phone.M_14px
    │   ├── marginStart: 12dp
    │   └── textColor: gray_950
    ├── TextView (추가 인원)
    │   ├── style: Typography.phone.M_12px
    │   ├── marginStart: 4dp
    │   └── textColor: gray_950
    └── ImageView (참여자 아이콘)
        ├── width: 24dp
        ├── height: 24dp
        └── marginStart: 4dp
```

#### Spacing 값
- 카드 내부 좌우 패딩: 16dp
- 카드 내부 상단 패딩: 16dp
- 카드 내부 하단 패딩: 12dp
- 레이블 간격: 8dp
- 레이블-작업명 간격: 8dp
- 작업명-구분선 간격: 12dp
- 구분선-마지막 작업 간격: 8dp
- 마지막 작업 레이블-이름 간격: 12dp
- 마지막 작업 이름-시간 간격: 4dp
- 마지막 작업-참여자 간격: 4dp
- 참여자 레이블-이름 간격: 12dp
- 참여자 이름-추가인원 간격: 4dp
- 참여자 추가인원-아이콘 간격: 4dp

---

### 4. 팝업 (v_popup.xml)

#### 구조
```
FrameLayout (오버레이)
├── width: match_parent
├── height: match_parent
└── background: black_50 (#80000000)
└── ConstraintLayout (팝업 루트)
    ├── width: match_parent
    ├── height: wrap_content
    ├── layout_gravity: bottom
    ├── background: bg_popup (white, top corners: 8dp)
    ├── paddingLeft: 16dp
    ├── paddingTop: 24dp
    ├── paddingRight: 16dp
    └── paddingBottom: 24dp
    ├── TextView (제목)
    │   ├── style: Typography.phone.B_16px
    │   └── textColor: gray_950
    ├── TextView (설명)
    │   ├── style: Typography.phone.M_14px
    │   ├── marginTop: 24dp
    │   ├── lineSpacingExtra: 2dp
    │   └── textColor: gray_950
    ├── TextView (정보 텍스트 - 선택적)
    │   ├── style: Typography.phone.M_12px
    │   ├── marginTop: 24dp
    │   ├── lineSpacingExtra: 2dp
    │   └── textColor: gray_900
    ├── Barrier (텍스트 끝 지점)
    └── GlassButton (왼쪽 버튼 - 선택적)
        ├── width: 0dp (weight: 1)
        ├── marginTop: 24dp
        ├── marginEnd: 6dp
        └── uiType: primary_outlined
    └── GlassButton (오른쪽 버튼)
        ├── width: 0dp (weight: 1)
        ├── marginTop: 24dp
        ├── marginStart: 6dp
        └── uiType: primary
```

#### Spacing 값
- 팝업 내부 패딩: 24dp (상하좌우)
- 제목-설명 간격: 24dp
- 설명-정보텍스트 간격: 24dp
- 텍스트-버튼 간격: 24dp
- 버튼 간격: 6dp (좌우)

---

### 5. 대기 화면 (f_waiting.xml)

#### 구조
```
ConstraintLayout (전체 화면)
├── width: match_parent
├── height: match_parent
└── background: black_50 (#80000000)
├── TextView (제목)
│   ├── width: match_parent
│   ├── height: 40dp
│   ├── style: Typography.phone.SB_20px
│   ├── background: black_5 (#0D000000)
│   └── textColor: white
├── View (닫기 버튼)
│   ├── width: 24dp
│   ├── height: 24dp
│   └── marginEnd: 20dp
├── TextView (수신자 이름)
│   ├── style: Typography.phone.EB_32px
│   ├── marginTop: 171dp
│   ├── maxWidth: 200dp
│   └── textColor: white_20 (#33FFFFFF)
├── TextView (상태 텍스트)
│   ├── style: Typography.phone.M_16px
│   ├── marginTop: 4dp
│   └── textColor: white
└── LoadingAnimationView (로딩 애니메이션)
    ├── marginTop: 20dp
    └── width/height: wrap_content
```

#### Spacing 값
- 제목 높이: 40dp
- 제목-닫기 버튼 간격: 20dp (우측)
- 제목-수신자 이름 간격: 171dp
- 수신자 이름-상태 텍스트 간격: 4dp
- 상태 텍스트-로딩 간격: 20dp

---

### 6. 화상회의 화면 (f_video_conference.xml)

#### 구조
```
ConstraintLayout (전체 화면)
├── width: match_parent
├── height: match_parent
└── background: background_s (#020202)
├── ConstraintLayout (Jitsi Meet 컨테이너)
│   ├── width: match_parent
│   └── height: match_parent
├── View (화면 음소거 오버레이)
│   ├── width: match_parent
│   ├── height: match_parent
│   └── background: background_s 또는 transparent
├── ConstraintLayout (참여자 확장 패널)
│   ├── width: 48dp
│   ├── height: wrap_content
│   ├── marginTop: 120dp
│   ├── paddingVertical: 12dp
│   └── background: b_conference_participant_list
│   ├── ImageView (화살표 아이콘)
│   │   ├── width: 6dp
│   │   └── height: 12dp
│   └── TextView (텍스트)
│       ├── style: Typography.phone.SB_12px
│       ├── marginTop: 8dp
│       └── textColor: gray_0
├── include (상단 간단 메뉴)
│   ├── width: wrap_content
│   ├── height: wrap_content
│   ├── marginTop: 17dp
│   └── marginStart: 16dp
├── include (채팅 간단 레이아웃)
│   ├── width: match_parent
│   ├── height: wrap_content
│   ├── minHeight: 113dp
│   └── paddingHorizontal: 24dp
│   └── paddingVertical: 16dp
├── include (세로 모드 메뉴바)
│   ├── width: match_parent
│   ├── height: wrap_content
│   └── paddingBottom: 10dp
│   └── background: black_24 (#3D000000)
│   ├── View (상단 라인)
│   │   ├── width: 62dp
│   │   ├── height: 4dp
│   │   └── marginTop: 4dp
│   └── IconTextButton들 (메뉴 아이콘)
│       ├── width: 64dp (control_icon_size)
│       ├── height: 77dp (conference_icon_height)
│       └── marginTop: 10dp (첫 번째 행)
├── include (가로 모드 메뉴바)
│   └── (동일 구조)
└── ConstraintLayout (시크바 컨테이너)
    ├── width: 200dp
    ├── height: 18dp
    └── marginTop: 55dp
```

#### Spacing 값
- 상단 간단 메뉴 상단 여백: 17dp
- 상단 간단 메뉴 좌측 여백: 16dp
- 참여자 패널 상단 여백: 120dp
- 참여자 패널 내부 패딩: 12dp (상하)
- 참여자 패널 아이콘-텍스트 간격: 8dp
- 시크바 상단 여백: 55dp
- 시크바 너비: 200dp
- 시크바 높이: 18dp
- 회의 메뉴바 하단 패딩: 10dp
- 회의 메뉴바 상단 라인 높이: 4dp
- 회의 메뉴바 상단 라인 상단 여백: 4dp
- 회의 메뉴 아이콘 간격: 10dp (상하), spread (좌우)

---

### 7. 작업 카드 생성 화면 (f_work_card_new.xml)

#### 구조
```
ConstraintLayout (전체 화면)
├── width: match_parent
├── height: match_parent
└── background: white
├── RecyclerView (폼 내용)
│   ├── width: match_parent
│   ├── height: 0dp (match_constraint)
│   ├── paddingHorizontal: 16dp
│   ├── paddingTop: 28dp
│   └── paddingBottom: 0dp
└── GlassButton (확인 버튼)
    ├── width: match_parent
    ├── height: 48dp
    ├── margin: 16dp
    ├── sizeType: large
    └── uiType: primary
```

#### Spacing 값
- RecyclerView 상단 패딩: 28dp
- RecyclerView 좌우 패딩: 16dp
- 하단 버튼 여백: 16dp (상하좌우)

---

### 8. 입력 필드 아이템 (item_work_card_input_field.xml)

#### 구조
```
ConstraintLayout (필드 컨테이너)
├── width: match_parent
├── height: wrap_content
└── paddingVertical: 8dp
├── TextView (레이블)
│   ├── style: Typography.phone.B_14px (fontFamily: pretendard_bold)
│   ├── textSize: 14sp
│   └── textColor: selector_input_title_text
├── TextView (필수 표시 "*")
│   ├── marginStart: 4dp
│   ├── textSize: 14sp
│   └── textColor: selector_required_text
├── TextView (글자 수 카운터)
│   ├── style: Typography.phone.M_11px
│   ├── textColor: gray_700
│   └── gravity: end
└── TextInputLayout (입력 컨테이너)
    ├── width: match_parent
    ├── marginTop: 8dp
    ├── paddingVertical: 9dp
    ├── paddingHorizontal: 8dp
    └── background: bg_input_selector
    └── TextInputEditText
        ├── style: Typography.phone.M_14px
        ├── textColor: selector_input_text
        └── textColorHint: gray_600
```

#### Spacing 값
- 필드 컨테이너 상하 패딩: 8dp
- 레이블-필수표시 간격: 4dp
- 레이블-입력창 간격: 8dp
- 입력창 내부 패딩: 9dp (상하), 8dp (좌우)

---

### 9. 정보 필드 아이템 (item_work_card_info_field.xml)

#### 구조
```
ConstraintLayout (필드 컨테이너)
├── width: match_parent
├── height: wrap_content
└── paddingVertical: 8dp
├── TextView (레이블)
│   ├── width: 80dp
│   ├── style: Typography.phone.M_14px
│   └── textColor: gray_900
└── TextView (값)
    ├── width: 0dp (match_constraint)
    ├── marginStart: 8dp
    ├── style: Typography.phone.M_14px
    └── textColor: gray_950
```

#### Spacing 값
- 필드 컨테이너 상하 패딩: 8dp
- 레이블 너비: 80dp (고정)
- 레이블-값 간격: 8dp

---

### 10. 참여자 태그 (item_participant_tag.xml)

#### 구조
```
ConstraintLayout (태그 컨테이너)
├── width: wrap_content
├── height: wrap_content
├── marginEnd: 8dp
├── marginBottom: 8dp
├── paddingHorizontal: 8dp
├── paddingVertical: 6dp
└── background: bg_participant_tag (gray_200, corner radius: 8dp)
├── TextView (참여자 이름)
│   ├── fontFamily: pretendard_medium
│   ├── textSize: 12sp
│   └── textColor: gray_950
└── ImageView (삭제 아이콘)
    ├── width: 16dp
    ├── height: 16dp
    └── marginStart: 4dp
```

#### Spacing 값
- 태그 좌우 패딩: 8dp
- 태그 상하 패딩: 6dp
- 태그 간격: 8dp (우측, 하단)
- 이름-삭제 아이콘 간격: 4dp

---

### 11. 채팅 사용자 목록 화면 (f_chat_user_list.xml)

#### 구조
```
ConstraintLayout (전체 화면)
├── width: match_parent
├── height: match_parent
└── background: section_background (#F6F7FB)
├── RecyclerView (사용자 목록)
│   ├── width: match_parent
│   ├── height: 0dp (match_constraint)
│   └── clipChildren: false
└── GlassButton (화상 회의 시작 버튼)
    ├── width: match_parent
    ├── height: wrap_content
    ├── minHeight: 48dp
    ├── marginHorizontal: 16dp
    ├── marginTop: 8dp
    ├── marginBottom: 16dp
    ├── sizeType: large
    └── uiType: primary
```

#### Spacing 값
- 버튼 좌우 여백: 16dp
- 버튼 상단 여백: 8dp
- 버튼 하단 여백: 16dp

---

## Component Catalog

### 1. 버튼 (GlassButton)

#### 1.1 Primary Contained Button
- **배경**: `b_primary_contained` (primary 색상, corner radius: 8dp)
- **텍스트 색상**: `c_primary_contained` (gray_0, disabled: primary_disabled_text)
- **높이**:
  - Large: 48dp
  - Medium: 44dp
  - Small: 38dp
- **텍스트 스타일**:
  - Large: Typography.phone.B_16px
  - Medium: Typography.phone.B_14px
  - Small: Typography.phone.B_12px
- **상태**:
  - Normal: primary 배경 (#368DED)
  - Disabled: primary_disabled 배경 (#F2F2F2)
- **Ripple 효과**: black_36 (#5C000000)
- **아이콘 지원**: 20dp x 20dp, 아이콘-텍스트 간격: 4dp

#### 1.2 Primary Outlined Button
- **배경**: `b_primary_outlined` (투명, 테두리: primary 색상 1dp, corner radius: 8dp)
- **텍스트 색상**: `c_primary_outlined` (primary 색상, disabled: primary_disabled_text)
- **높이**: Primary Contained과 동일
- **텍스트 스타일**: Primary Contained과 동일
- **상태**:
  - Normal: 투명 배경, primary 테두리
  - Disabled: primary_disabled 배경

#### 1.3 Tertiary Contained Button
- **배경**: `b_tertiary_contained` (tertiary 색상)
- **텍스트 색상**: `c_primary_contained` (gray_0)
- **높이**: Primary Contained과 동일
- **텍스트 스타일**: Primary Contained과 동일

#### 1.4 Tertiary Outlined Button
- **배경**: `b_tertiary_outlined` (투명, 테두리: tertiary 색상)
- **텍스트 색상**: `c_tertiary_outlined` (tertiary 색상)
- **높이**: Primary Contained과 동일
- **텍스트 스타일**: Primary Contained과 동일

---

### 2. 입력창 (TextInputLayout + TextInputEditText)

#### 2.1 일반 입력창
- **컨테이너**: TextInputLayout
  - 높이: wrap_content
  - 배경: `bg_input_selector` (상태별 색상 변경)
  - 패딩: 9dp (상하), 8dp (좌우)
- **입력 필드**: TextInputEditText
  - 스타일: Typography.phone.M_14px
  - 텍스트 색상: selector_input_text
  - 힌트 색상: gray_600 (#919191)
  - 배경: null (컨테이너 배경 사용)

#### 2.2 로그인 입력창
- **높이**: 48dp
- **좌우 여백**: 16dp
- **좌측 패딩**: 16dp
- **우측 패딩**: 52dp (아이콘 버튼 공간)
- **배경**: `input_background` (corner radius: 8dp)
- **텍스트 색상**: gray_950
- **힌트 색상**: edit_text_hint_color (#E4E4E4)

---

### 3. 카드 (Work Card Item)

#### 3.1 기본 작업 카드
- **배경**: `bg_work_card_item_default` (white, corner radius: 8dp)
- **Elevation**: 6dp
- **패딩**: 16dp (좌우), 16dp (상단), 12dp (하단)
- **최소 높이**: wrap_content
- **구분선**: 0.5dp, gray_300 (#E4E4E4)

#### 3.2 통화 카드
- **구조**: 작업 카드와 동일
- **차이점**: 통화 아이콘 (32dp x 32dp) 사용

---

### 4. 레이블 (Label/Chip)

#### 4.1 작업 타입 레이블
- **배경**: `bg_label_work_type` (gray_900, corner radius: 8dp)
- **텍스트**: Typography.phone.SB_12px
- **텍스트 색상**: gray_0 (#FFFFFF)
- **패딩**: 8dp (좌우), 4dp (상하)

#### 4.2 상태 레이블
- **진행전**: `bg_label_pending` (pink_label #FA517B, corner radius: 8dp)
- **작업중**: `bg_label_in_progress` (blue_label #4EC4FF, corner radius: 8dp)
- **종료**: `bg_label_completed` (gray_200 #E7EAEF, corner radius: 8dp)
- **작업중단**: `bg_label_force_stop` (#B0B4BA, corner radius: 8dp)
- **텍스트**: Typography.phone.SB_12px
- **텍스트 색상**: gray_0 (#FFFFFF)
- **패딩**: 8dp (좌우), 4dp (상하)
- **레이블 간격**: 8dp

---

### 5. 태그 (Tag)

#### 5.1 참여자 태그
- **배경**: `bg_participant_tag` (gray_200 #E7EAEF, corner radius: 8dp)
- **패딩**: 8dp (좌우), 6dp (상하)
- **텍스트**: fontFamily: pretendard_medium, textSize: 12sp
- **텍스트 색상**: gray_950
- **삭제 아이콘**: 16dp x 16dp
- **아이콘-텍스트 간격**: 4dp
- **태그 간격**: 8dp (우측, 하단)

---

### 6. 팝업 (Popup/Dialog)

#### 6.1 기본 팝업
- **오버레이**: black_50 (#80000000)
- **팝업 배경**: `bg_popup` (white, top corners: 8dp)
- **패딩**: 24dp (상하좌우)
- **제목**: Typography.phone.B_16px, gray_950
- **설명**: Typography.phone.M_14px, gray_950, lineSpacingExtra: 2dp
- **정보 텍스트**: Typography.phone.M_12px, gray_900, lineSpacingExtra: 2dp
- **텍스트 간격**: 24dp
- **버튼 간격**: 6dp

#### 6.2 두 버튼 팝업
- **왼쪽 버튼**: GlassButton, uiType: primary_outlined, sizeType: medium
- **오른쪽 버튼**: GlassButton, uiType: primary, sizeType: medium
- **버튼 너비**: 0dp (weight: 1, 동일 비율)

---

### 7. 토스트 (Toast)

#### 7.1 기본 토스트
- **배경**: toast_bg (#82868A)
- **패딩**: 8dp (상하), 16dp (좌우)
- **여백**: 4dp (좌우)
- **아이콘**: 16dp x 16dp
- **아이콘-텍스트 간격**: 8dp
- **텍스트**: Typography.phone.M_14px, white
- **제목**: Typography.phone.B_14px, white (선택적)
- **제목-내용 간격**: 4dp

---

### 8. 회의 메뉴 아이콘 (ConferenceMenuIcon)

#### 8.1 아이콘 버튼
- **크기**: 64dp x 77dp (control_icon_size x conference_icon_height)
- **아이콘 크기**: 32dp x 32dp
- **아이콘 간격**: 12dp (상하, 첫 번째 행)
- **텍스트**: 아이콘 아래 표시
- **배경**: 상태별 색상 변경

#### 8.2 상단 간단 메뉴
- **컨테이너**: LinearLayout, 32dp 너비, 164dp 높이
- **아이콘 크기**: 32dp x 32dp
- **아이콘 간격**: 12dp (상하)

---

### 9. 시크바 (SeekBar)

#### 9.1 볼륨/줌 시크바
- **컨테이너**: 200dp 너비, 18dp 높이
- **시크바 높이**: 20dp
- **최대값**: 15
- **트랙 배경**: `bg_seekbar_track`
- **썸**: `bg_seekbar_thumb`
- **아이콘**: 16dp x 16dp (볼륨 아이콘)
- **아이콘-시크바 간격**: 6dp

---

### 10. 체크박스 (Checkbox)

#### 10.1 자동 로그인 체크박스
- **크기**: 16dp x 16dp
- **배경**: `b_check_selector` (상태별)
- **텍스트 간격**: 8dp

---

### 11. 홈 메뉴 버튼

#### 11.1 메뉴 컨테이너
- **배경**: `b_home_menu_corner` (background 색상, corner radius: 12dp)
- **높이**: 43dp
- **패딩**: 16dp (좌우)
- **전체 높이**: 75dp

#### 11.2 메뉴 버튼
- **배경**: `s_home_menu_btn` (선택 시 white, 미선택 시 transparent)
- **높이**: 35dp
- **Elevation**: 5dp
- **텍스트**: Typography.phone.B_16px
- **텍스트 색상**: `s_gray700_gray950` (선택 시 gray_950, 미선택 시 gray_700)
- **버튼 간격**: 4dp (좌우)

---

### 12. 플로팅 버튼 (Floating Button)

#### 12.1 새 작업 버튼
- **배경**: `b_home_new_work_btn` (#2F3857, corner radius: 50dp)
- **패딩**: 10dp (상하), 16dp (좌우)
- **아이콘**: 20dp x 20dp
- **텍스트**: Typography.phone.SB_14px, white
- **아이콘-텍스트 간격**: 8dp
- **텍스트-화살표 간격**: 4dp
- **화살표**: 12dp x 24dp
- **하단 여백**: 42dp
- **우측 여백**: 16dp

---

### 13. 구분선 (Divider)

#### 13.1 기본 구분선
- **높이**: 0.5dp 또는 1dp
- **색상**: gray_300 (#E4E4E4) 또는 gray_400 (#CECECE)
- **여백**: 상황에 따라 다름 (일반적으로 12dp-16dp)

---

### 14. 로딩 애니메이션

#### 14.1 대기 화면 로딩
- **컨테이너**: LoadingAnimationView
- **크기**: wrap_content
- **상단 여백**: 20dp (상태 텍스트 아래)

---

## Compose Multiplatform 구현 가이드

### 1. Color 정의 예시

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
    val Gray0 = Color(0xFFFFFFFF)
    val Gray950 = Color(0xFF18191B)
    
    // Status
    val StatusInProgress = Color(0xFF4B9DFF)
    val StatusCompleted = Color(0xFF00C399)
    
    // Label
    val LabelBgPending = Color(0xFFFA517B)
    val LabelBgInProgress = Color(0xFF4EC4FF)
}
```

### 2. Typography 정의 예시

```kotlin
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = PretendardExtraBold,
        fontSize = 32.sp,
        fontWeight = FontWeight(800),
        color = AppColors.Gray950
    ),
    headlineMedium = TextStyle(
        fontFamily = PretendardBold,
        fontSize = 18.sp,
        fontWeight = FontWeight(700),
        color = AppColors.Gray950
    ),
    bodyLarge = TextStyle(
        fontFamily = PretendardMedium,
        fontSize = 14.sp,
        fontWeight = FontWeight(500),
        color = AppColors.Gray950
    ),
    bodyMedium = TextStyle(
        fontFamily = PretendardRegular,
        fontSize = 14.sp,
        fontWeight = FontWeight(400),
        color = AppColors.Gray950
    ),
    labelSmall = TextStyle(
        fontFamily = PretendardSemiBold,
        fontSize = 12.sp,
        fontWeight = FontWeight(600),
        color = AppColors.Gray950
    )
)
```

### 3. Spacing 정의 예시

```kotlin
object Spacing {
    val xs = 4.dp
    val sm = 8.dp
    val md = 12.dp
    val lg = 16.dp
    val xl = 20.dp
    val xxl = 24dp
    val xxxl = 28.dp
    val xxxxl = 36.dp
    val xxxxxl = 48.dp
}
```

### 4. Shape 정의 예시

```kotlin
object AppShapes {
    val Small = RoundedCornerShape(8.dp)
    val Medium = RoundedCornerShape(12.dp)
    val Large = RoundedCornerShape(50.dp)
    val TopOnly = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
}
```

### 5. Button 컴포넌트 예시

```kotlin
@Composable
fun PrimaryButton(
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
        ButtonSize.Large -> Typography.labelLarge
        ButtonSize.Medium -> Typography.labelMedium
        ButtonSize.Small -> Typography.labelSmall
    }
    
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(height),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) AppColors.Primary else AppColors.PrimaryDisabled,
            contentColor = if (enabled) AppColors.Gray0 else AppColors.PrimaryDisabledText
        ),
        shape = AppShapes.Small
    ) {
        Text(
            text = text,
            style = textStyle
        )
    }
}
```

---

## 참고사항

1. **폰트 스케일 대응**: GlassButton은 폰트 스케일에 따라 자동 크기 조정 (AutoSizeTextTypeUniformWithConfiguration 사용)

2. **Ripple 효과**: 모든 버튼에 black_36 (#5C000000) Ripple 효과 적용

3. **Elevation**: 카드는 6dp, 버튼은 5dp elevation 사용

4. **상태별 색상**: 입력창, 버튼 등은 상태(선택, 포커스, 비활성화)에 따라 색상 변경

5. **텍스트 줄 간격**: 팝업 설명 텍스트는 lineSpacingExtra: 2dp 사용

6. **아이콘 크기**: 용도에 따라 12dp, 16dp, 20dp, 24dp, 32dp, 64dp 등 다양한 크기 사용

7. **반응형 레이아웃**: ConstraintLayout의 weight, chainStyle 등을 활용하여 반응형 구현

8. **텍스트 제한**: 일부 텍스트는 maxLines, ellipsize, maxLength 등으로 제한

9. **투명도 처리**: 색상명에 포함된 숫자는 Alpha 값 (예: white_20 = 20% 투명도)

10. **상태 표시**: 온라인/오프라인/통화중 등은 색상으로 구분 (online: #219653 등)
