# SmartGlass Mobile - 기능 명세서

> 이 문서는 KMP 프로젝트 개발을 위한 플랫폼 독립적인 기능 명세서입니다.  
> Glass 코드를 제외한 공통 코드(app/src/main)와 Mobile 코드(app/src/phone)를 기반으로 작성되었습니다.

---

## 1. 프로젝트 개요

### 1.1 프로젝트 정보
- **프로젝트명**: SmartGlass Mobile
- **패키지명**: com.digicap.keji.smartglass
- **최소 SDK**: 30
- **타겟 SDK**: 34
- **빌드 플레이버**: phone, glass (본 명세서는 phone 플레이버 기준)

### 1.2 주요 기능
- **인증 시스템**: 일반 로그인, QR 로그인, 자동 로그인
- **작업 관리**: 작업카드 생성/수정/조회, 작업 진행 상태 관리
- **실시간 통신**: WebSocket 기반 화상회의, 채팅
- **미디어 처리**: 사진/동영상/오디오 촬영 및 Chunk 기반 업로드
- **알림 관리**: FCM 기반 Push 알림
- **설정**: 디바이스 정보, Wi-Fi 설정

---

## 2. 아키텍처 개요

### 2.1 레이어 구조
```
┌─────────────────────────────────────┐
│   Presentation Layer (app)          │
│   - Fragment/ViewModel              │
│   - UI State/Intent/SideEffect      │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│   Domain Layer                       │
│   - UseCase                          │
│   - Repository Interface             │
│   - Domain Models                    │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│   Data Layer                         │
│   - Repository Implementation        │
│   - API Service                      │
│   - Local DataSource                 │
└─────────────────────────────────────┘
```

### 2.2 주요 패턴
- **MVI Pattern**: Intent → UiState → SideEffect
- **Clean Architecture**: Domain-first 접근
- **의존성 주입**: Hilt/Dagger
- **비동기 처리**: Kotlin Coroutines + Flow

### 2.3 상태 관리 전략
- **StateFlow**: UI 상태 관리
- **SharedFlow**: 일회성 이벤트 (SideEffect)
- **MutableStateFlow**: 내부 상태 변경

---

## 3. 화면별 기능 명세

### 3.1 Login (로그인)

#### 3.1.1 Screen Logic

**Input (사용자 행동)**
1. 아이디/비밀번호 입력 및 로그인 버튼 클릭
2. 자동 로그인 체크박스 토글
3. QR 코드 스캔 (디바이스 등록)
4. 계정 찾기 버튼 클릭

**Output/State Change**
1. **일반 로그인 성공**
   - UserInfo 저장 (액세스 토큰, 리프레시 토큰)
   - 로그인 상태 변경: `isLoggedIn = true`
   - 비밀번호 재설정 필요 시 → 비밀번호 변경 화면
   - 정상 로그인 → Home 화면

2. **일반 로그인 실패**
   - 에러 토스트 메시지 표시
   - 중복 로그인 감지 시 → 중복 로그인 팝업 표시

3. **디바이스 등록 QR**
   - 디바이스 등록 QR: 디바이스 정보 저장 후 앱 등록

4. **자동 로그인**
   - 앱 시작 시 저장된 Refresh Token으로 자동 로그인 시도
   - 성공 → Home 화면
   - 실패 → 로그인 화면 유지

**Loading State**
- `isLoading`: true/false (로딩 표시)

#### 3.1.2 Flow Diagram

```
[앱 시작]
   ↓
[디바이스 등록 확인]
   ↓
   NO → [디바이스 등록 팝업] → [QR 스캔] → [디바이스 등록 API] → [성공]
   ↓                                                                    ↓
   YES ───────────────────────────────────────────────────────────────┘
   ↓
[자동 로그인 체크]
   ↓
   YES → [Refresh Token 검증] → [성공] → [Home 화면]
   ↓                             ↓
   NO ←──────────────────────── [실패]
   ↓
[로그인 화면 표시]
   ↓
[일반 로그인]
   ↓
[로그인 API 호출]
   ↓
[중복 로그인?] → YES → [중복 로그인 팝업] → [강제 로그인 허용]
   ↓                                              ↓
   NO ────────────────────────────────────────────┘
   ↓
[비밀번호 재설정 필요?] → YES → [비밀번호 변경 화면]
   ↓
   NO
   ↓
[Home 화면]
```

#### 3.1.3 Business Rules

**BR-LOGIN-001: 로그인 유효성 검사**
- 아이디: 필수 입력, 빈 문자열 불가
- 비밀번호: 필수 입력, 빈 문자열 불가
- 두 필드 모두 입력되어야 로그인 버튼 활성화

**BR-LOGIN-002: 중복 로그인 처리**
- 서버 응답 코드 `1018`: 중복 로그인 감지
- 사용자 선택:
  - 취소: 로그인 중단
  - 확인: `allowDuplicateLogin = true`로 재요청

**BR-LOGIN-003: 자동 로그인**
- 자동 로그인 체크 시 RefreshToken 로컬 저장
- 앱 시작 시 RefreshToken 존재 여부 확인
- RefreshToken으로 액세스 토큰 갱신 시도
- 실패 시 로그인 화면 표시

**BR-LOGIN-004: 디바이스 등록**
- 최초 실행 시 디바이스 등록 필수
- QR 코드에서 UUID, URL, CompanyCode 추출
- 디바이스 타입: `MOBILE` (glass는 `GLASS`)
- OS 타입: `AOS` (Android)
- 등록 완료 후 AppId 발급

**BR-LOGIN-005: 비밀번호 재설정**
- 로그인 응답에서 `isPasswordReset = true` 확인
- 비밀번호 변경 화면으로 이동
- 변경 완료 후 로그아웃 처리

---

### 3.2 Home (홈)

#### 3.2.1 Screen Logic

**Input**
1. 탭 전환: "나의 작업" ↔ "대기 중 통화"
2. 작업 카드 클릭
3. 통화 카드 클릭
4. "전체보기" 버튼 클릭
5. "새 작업 만들기" 버튼 클릭
6. 알림 아이콘 클릭
7. 설정 아이콘 클릭
8. 프로필 영역 클릭

**Output/State Change**
1. **나의 작업 탭**
   - 작업 목록 조회 (alive works)
   - 페이징 처리 (스크롤 최하단 도달 시 추가 로드)
   - 작업 카드 클릭 → WorkRecord 화면

2. **대기 중 통화 탭**
   - 대기 중인 화상회의 목록 조회
   - 통화 카드 클릭 → VideoConference 화면 (즉시 참여)

3. **전체보기**
   - AllWorkCardList 화면으로 이동 (모달)

4. **새 작업 만들기**
   - WorkCardNew 화면으로 이동

**Loading State**
- `WaitingCallListUiState`: Idle, Loading, Success, Error
- `AllWorkCardListUiState`: Idle, Loading, Success, Error

#### 3.2.2 Flow Diagram

```
[Home 화면 진입]
   ↓
[초기 데이터 로드]
   ├─ [나의 작업 목록 조회] (캐시 우선)
   └─ [대기 중 통화 목록 조회]
   ↓
[나의 작업 탭 (기본 선택)]
   ↓
[작업 카드 표시]
   ├─ 진행 중 (status: START, WORK, PAUSE)
   ├─ 완료 (status: END)
   └─ 작업 정보: 작업명, 설명, 참여자, 생성일
   ↓
[사용자 액션]
   ├─ [작업 카드 클릭] → [WorkRecord 화면]
   ├─ [전체보기] → [AllWorkCardList 화면]
   ├─ [새 작업 만들기] → [WorkCardNew 화면]
   └─ [스크롤 끝] → [추가 데이터 로드 (페이징)]
   
[대기 중 통화 탭 전환]
   ↓
[통화 카드 표시]
   ├─ 방 이름
   ├─ 생성자 정보
   ├─ 참여자 수
   └─ 작업 정보
   ↓
[통화 카드 클릭]
   ↓
[VideoConference 화면] (자동 참여)
```

#### 3.2.3 Business Rules

**BR-HOME-001: 작업 목록 캐싱**
- 첫 진입 시 서버에서 데이터 조회
- 이후 진입 시 캐시 데이터 우선 표시
- 백그라운드에서 서버 동기화
- 상태 변경/푸시 수신 후 화면 복귀 시 갱신 플래그 확인

**BR-HOME-002: 페이징 처리**
- 페이지 크기: 10개
- 스크롤 최하단 도달 시 `LoadMore` Intent 발생
- 로딩 중 중복 요청 방지

**BR-HOME-003: 작업 개수 표시**
- 총 개수 99개 이상 시 "99+" 표시
- 실시간 업데이트 (캐시 동기화)

**BR-HOME-004: 대기 중 통화 참여**
- 통화 카드 클릭 시 RoomData 생성
  - domain: 기본 URL
  - roomName: 회의 룸 이름
  - displayName: 현재 사용자 이름
  - senderId: 현재 사용자 로그인 ID
- 작업 정보 포함 (workId, workName, workClass)

**BR-HOME-005: 읽지 않은 알림 표시**
- 알림 아이콘에 배지 표시
- 알림 목록에서 읽음 처리 시 실시간 업데이트

---

### 3.3 WorkCard (작업카드 생성/수정)

#### 3.3.1 Screen Logic

**Input**
1. 작업 유형 선택 (초기화, 전문가, 팀 작업)
2. 작업명 입력 (최대 30자)
3. 작업 설명 입력 (최대 50자)
4. 참여자 검색 및 추가
5. 참여자 삭제
6. 저장 버튼 클릭

**Output/State Change**
1. **작업 유형 선택**
   - INIT (초기화 작업)
   - PROF (전문가 작업)
   - TEAM (팀 작업)

2. **참여자 관리**
   - 검색어 입력 시 debounce (100ms) 후 필터링
   - 검색 결과: 이름/부서명 매칭 (한글 초성 검색 지원)
   - 참여자 추가: 최대 10명 (본인 포함)
   - 생성자는 자동으로 첫 번째 참여자로 추가
   - 참여자 삭제: 최소 1명 유지 (생성자는 항상 포함)

3. **폼 유효성 검사**
   - 작업 유형: 필수 선택
   - 작업명: 필수 입력, 1~30자
   - 작업 설명: 필수 입력, 1~50자
   - 참여자: 최소 1명
   - 모든 조건 만족 시 저장 버튼 활성화

4. **생성 성공**
   - 작업카드 ID 반환
   - WorkRecord 화면으로 이동

5. **수정 모드**
   - 기존 작업카드 데이터 로드
   - 편집 권한 확인:
     - 생성자: 모든 필드 편집 가능
     - 관리자 OR 참여자: 작업 설명, 참여자만 편집 가능
     - 그 외: 편집 불가

**Loading State**
- `WorkCardUiState`: Idle, Loading, Success(type, workCardId), Error

#### 3.3.2 Flow Diagram

**작업카드 생성 플로우**
```
[WorkCardNew 화면 진입]
   ↓
[초기 폼 설정]
   ├─ 작업 유형: 미선택
   ├─ 작업명: 빈 값
   ├─ 작업 설명: 빈 값
   └─ 참여자: [현재 사용자] (자동 추가)
   ↓
[사용자 입력]
   ├─ [작업 유형 선택] → [폼 유효성 재검사]
   ├─ [작업명 입력] → [폼 유효성 재검사]
   ├─ [작업 설명 입력] → [폼 유효성 재검사]
   └─ [참여자 관리]
        ├─ [검색어 입력] → [debounce 100ms] → [필터링] → [검색 결과 표시]
        ├─ [참여자 추가] → [최대 10명 체크] → [추가] → [폼 유효성 재검사]
        └─ [참여자 삭제] → [최소 1명 체크] → [삭제] → [폼 유효성 재검사]
   ↓
[폼 유효성 검사]
   ├─ 모든 필드 입력 완료?
   ├─ 참여자 1명 이상?
   └─ YES → [저장 버튼 활성화]
   ↓
[저장 버튼 클릭]
   ↓
[WorkCardData 생성]
   ├─ workName
   ├─ workExplanation
   ├─ workClass (INIT/PROF/TEAM)
   └─ shareList (참여자 목록)
   ↓
[작업카드 생성 API 호출]
   ↓
[성공] → [WorkRecord 화면] (workId 전달)
[실패] → [에러 토스트 표시]
```

**작업카드 수정 플로우**
```
[WorkCardEdit 화면 진입] (workId 전달)
   ↓
[기존 작업카드 데이터 로드]
   ↓
[권한 체크]
   ├─ 생성자? → [모든 필드 편집 가능]
   ├─ 관리자 OR 참여자? → [작업 설명, 참여자만 편집 가능]
   └─ 그 외 → [모든 필드 편집 불가]
   ↓
[폼 초기화]
   ├─ 작업 유형: 기존 값 (편집 권한에 따라 비활성화)
   ├─ 작업명: 기존 값 (편집 권한에 따라 비활성화)
   ├─ 작업 설명: 기존 값
   └─ 참여자: 기존 참여자 목록
   ↓
[사용자 수정]
   ├─ [편집 가능 필드 수정]
   └─ [변경 사항 감지] → [저장 버튼 활성화]
   ↓
[저장 버튼 클릭]
   ↓
[변경 사항 비교]
   ├─ [작업카드 정보 수정] (IF-401-011)
   ├─ [추가된 참여자 처리] (IF-401-006)
   └─ [삭제된 참여자 처리] (IF-401-007)
   ↓
[모든 API 호출 성공] → [WorkRecord 화면]
[실패] → [에러 토스트 표시]
```

#### 3.3.3 Business Rules

**BR-WORKCARD-001: 작업 유형**
- INIT (초기화 작업): displayName="초기화 작업", description="초기화 작업 설명"
- PROF (전문가 작업): displayName="전문가 작업", description="전문가 작업 설명"
- TEAM (팀 작업): displayName="팀 작업", description="팀 작업 설명"

**BR-WORKCARD-002: 참여자 검색**
- Debounce: 100ms
- 검색 대상: 사용자 이름, 부서명
- 한글 초성 검색 지원 (예: "ㄱㄷㅎ" → "김동현")
- 이미 추가된 참여자는 검색 결과에서 제외

**BR-WORKCARD-003: 참여자 제한**
- 최대 인원: 10명 (생성자 포함)
- 최소 인원: 1명
- 10명 도달 시:
  - 검색 UI 비활성화
  - "최대 인원에 도달했습니다" 메시지 표시

**BR-WORKCARD-004: 편집 권한**
- **생성자** (mainWorkerId == currentLoginId):
  - 작업 유형, 작업명, 작업 설명, 참여자 모두 편집 가능
- **관리자** (roleGroupId == 1):
  - 작업 설명, 참여자만 편집 가능
- **참여자** (shareWorker에 포함):
  - 작업 설명, 참여자만 편집 가능
- **그 외**:
  - 모든 필드 편집 불가 (읽기 전용)

**BR-WORKCARD-005: 수정 API 호출 순서**
1. 작업카드 정보 수정 (IF-401-011)
   - workName, workExplanation, workClass 변경
2. 추가된 참여자 처리 (IF-401-006)
   - 기존 목록과 비교하여 신규 참여자 추가
3. 삭제된 참여자 처리 (IF-401-007)
   - 기존 목록과 비교하여 삭제할 참여자 제거

**BR-WORKCARD-006: 변경 사항 감지**
- 수정 모드에서 저장 버튼 활성화 조건:
  - 폼 유효성 검사 통과 AND 원본 데이터와 차이 있음
- 비교 대상:
  - workType, workName, workDescription
  - 참여자 목록 (loginId 기준, 순서 무관)

---

### 3.4 WorkRecord (작업 기록 관리)

#### 3.4.1 Screen Logic

**Input**
1. 탭 전환: 진행 상태, 체크리스트, 메뉴, 새 작업
2. 진행 상태 변경 (시작, 작업, 일시정지, 종료)
3. 체크리스트 항목 체크/언체크
4. 화상회의 시작
5. 미디어 촬영/녹화

**Output/State Change**
1. **작업 진행 상태**
   - START (시작): 작업 시작
   - WORK (작업 중): 진행 중
   - PAUSE (일시정지): 잠시 중단
   - END (종료): 작업 완료

2. **탭별 화면**
   - 진행 상태: 작업 정보, 상태 변경 버튼
   - 체크리스트: 점검 항목 목록
   - 메뉴: 추가 기능 (갤러리, 설정 등)
   - 새 작업: 새로운 체크리스트 추가

**Container State**
- `WorkRecordContainerState`: currentTab, isLoading, error

#### 3.4.2 Flow Diagram

```
[WorkRecord 화면 진입] (workId 전달)
   ↓
[작업 정보 로드]
   ├─ 작업명
   ├─ 작업 설명
   ├─ 참여자 목록
   ├─ 현재 진행 상태
   └─ 체크리스트 목록
   ↓
[Container 초기화]
   └─ 기본 탭: 진행 상태
   ↓
[탭별 Fragment 표시]
   ├─ [진행 상태 Fragment]
   │    ├─ [시작 버튼] → [상태 변경: START]
   │    ├─ [작업 버튼] → [상태 변경: WORK]
   │    ├─ [일시정지 버튼] → [상태 변경: PAUSE]
   │    └─ [종료 버튼] → [상태 변경: END] → [Home 화면]
   │
   ├─ [체크리스트 Fragment]
   │    ├─ 체크리스트 항목 표시
   │    ├─ [항목 체크/언체크] → [서버 업데이트]
   │    └─ [사진 촬영] → [Camera 화면] (questionId 전달)
   │
   ├─ [메뉴 Fragment]
   │    ├─ [갤러리] → [Gallery 화면]
   │    ├─ [화상회의] → [VideoConference 화면]
   │    └─ [설정] → [Setting 화면]
   │
   └─ [새 작업 Fragment]
        └─ 새로운 체크리스트 추가 UI
```

#### 3.4.3 Business Rules

**BR-WORKRECORD-001: 작업 상태 전이**
```
[작업 생성]
   ↓
[START] ←→ [WORK] ←→ [PAUSE]
   ↓         ↓
  [END] ←───┘
```
- START → WORK: 자동 전이 또는 수동 전환
- WORK ↔ PAUSE: 자유롭게 전환 가능
- WORK/PAUSE → END: 작업 종료
- END 이후 상태 변경 불가

**BR-WORKRECORD-002: 체크리스트 연동**
- 각 체크리스트 항목에 questionId 존재
- 사진 촬영 시 questionId를 Camera 화면에 전달
- 촬영된 미디어는 해당 questionId와 연결되어 업로드

**BR-WORKRECORD-003: 작업 로그 기록**
- 모든 상태 변경 시 로그 기록
- 로그 타입: MOVE_MENU, WORK_START, WORK_END, PHOTO_SAVE 등
- 타임스탬프, 메뉴 경로, 작업명 포함

---

### 3.5 Conference (화상회의)

#### 3.5.1 Screen Logic

**Input**
1. 마이크 ON/OFF
2. 비디오 ON/OFF
3. 화면 공유 ON/OFF
4. 플래시 ON/OFF
5. 녹화 시작/종료
6. 볼륨 조절
7. 화질 변경
8. 줌 조절
9. 채팅 메시지 전송/수신
10. 참여자 목록 보기
11. 참여자 Pin (메인 화면 고정)

**Output/State Change**
1. **UI 상태**
   - `VideoConferenceUIState`:
     - isMuted: 마이크 음소거 여부
     - isVideoOn: 비디오 ON/OFF
     - isScreenOn: 화면 공유 여부
     - isLightOn: 플래시 ON/OFF
     - isRecording: 녹화 중 여부
     - isOtherUserRecording: 다른 사용자 녹화 중 여부
     - currentVolume: 볼륨 (0~10)
     - currentQuality: 화질 (LOW, STANDARD, HIGH, ULTRA)
     - currentZoom: 줌 레벨 (0~7)
     - chatViewMode: 채팅 표시 모드 (NONE, SIMPLE, EXPANDABLE)
     - isMenuBarVisible: 메뉴바 표시 여부

2. **WebSocket 연결 상태**
   - Disconnected: 연결 끊김 (자동 재연결 시도)
   - Connecting: 연결 시도 중
   - Connected: 연결됨

3. **참여자 정보**
   - RoomsInfo: 방 정보 및 참여자 목록
   - 참여자 추가/삭제 이벤트 실시간 반영

**Loading State**
- `joinTimeout`: 회의 참여 타임아웃 (10초)

#### 3.5.2 Flow Diagram

**화상회의 참여 플로우**
```
[VideoConference 화면 진입] (RoomData 전달)
   ↓
[WebSocket 연결 시작]
   ├─ room_id, room_user_id, displayName, workId
   └─ 10초 타임아웃 설정
   ↓
[연결 상태 관찰]
   ├─ [Connected] → [회의 참여 성공]
   │    ├─ 타임아웃 취소
   │    ├─ JitsiMeet SDK 초기화
   │    └─ 참여자 정보 동기화
   │
   ├─ [Disconnected] → [자동 재연결 시도]
   │    ├─ 이전 상태 확인
   │    ├─ saveRoomData 사용하여 재연결 요청
   │    └─ needStateCheck = false (상태 체크 스킵)
   │
   └─ [ErrorReceived] → [에러 처리]
        ├─ 토큰 만료 (1003, 1004) → [토큰 갱신 시도]
        │    ├─ [TokenRefreshCoordinator 사용]
        │    ├─ [성공] → [재연결]
        │    └─ [실패] → [로그아웃 처리]
        │
        └─ 기타 에러 → [로그아웃 처리]
   ↓
[회의 진행 중]
   ├─ [UI 컨트롤]
   │    ├─ 마이크/비디오/화면공유 토글
   │    ├─ 볼륨/화질/줌 조절
   │    └─ 녹화 시작/종료
   │
   ├─ [채팅]
   │    ├─ 메시지 수신 → [ChatViewMode 업데이트]
   │    │    ├─ NONE → SIMPLE (토스트 형태)
   │    │    ├─ SIMPLE → 메시지 갱신
   │    │    └─ EXPANDABLE → 채팅창에 추가
   │    └─ 메시지 전송 → [WebSocket으로 브로드캐스트]
   │
   └─ [참여자 관리]
        ├─ PARTICIPANT_JOINED → [참여자 추가]
        ├─ PARTICIPANT_LEFT → [참여자 제거]
        └─ [참여자 Pin] → [메인 화면 고정]
   ↓
[회의 종료]
   ├─ WebSocket 연결 해제
   ├─ JitsiMeet 정리
   └─ [이전 화면으로 복귀]
```

**토큰 갱신 플로우**
```
[WebSocket 에러 수신] (status: 400/401, code: 1003/1004)
   ↓
[중복 갱신 방지 체크]
   ├─ isRefreshingToken == true → [대기]
   └─ isRefreshingToken == false → [갱신 시작]
   ↓
[TokenRefreshCoordinator.awaitFreshAccessToken()]
   ↓
[갱신 결과]
   ├─ [성공] → [WebSocket 재연결] (needStateCheck = false)
   └─ [실패] → [로그아웃 처리]
   ↓
[isRefreshingToken = false]
```

#### 3.5.3 Business Rules

**BR-CONFERENCE-001: 화질 설정**
- LOW: 180p
- STANDARD: 360p (기본값)
- HIGH: 720p
- ULTRA: 2160p
- 순환 변경: LOW → STANDARD → HIGH → ULTRA → LOW

**BR-CONFERENCE-002: 줌 레벨**
- 0: x1.0 (기본)
- 1: x1.5
- 2: x2.0
- 3: x2.5
- 4: x3.0
- 5: x3.5
- 6: x4.0
- 7: x4.5

**BR-CONFERENCE-003: 채팅 표시 모드**
- NONE: 채팅 숨김
- SIMPLE: 상단 토스트 형태 (최근 메시지만 표시)
- EXPANDABLE: 전체 채팅창 표시

**BR-CONFERENCE-004: WebSocket 재연결**
- Disconnected 이벤트 수신 시 자동 재연결
- 재연결 조건:
  - saveRoomData 존재 (이전 회의 정보)
  - curParticipantId 존재 (내 참여자 ID)
- 재연결 시 needStateCheck = false (연결 상태 체크 스킵)

**BR-CONFERENCE-005: 토큰 갱신**
- 중복 갱신 방지: isRefreshingToken 플래그 사용
- TokenRefreshCoordinator를 통한 동기화된 갱신
- 갱신 성공 → WebSocket 재연결
- 갱신 실패 → 로그아웃 처리

**BR-CONFERENCE-006: 참여자 정보 파싱**
- RoomsInfo 구조:
  - rooms: 방 목록
    - isMainRoom: 메인 룸 여부
    - id: 방 ID
    - jid: Jitsi ID
    - participants: 참여자 목록
      - role: 역할 (moderator, participant)
      - id: 참여자 ID
      - jid: Jitsi ID
      - displayName: 표시 이름
      - avatarUrl: 프로필 이미지 URL

**BR-CONFERENCE-007: 녹화 제어**
- 본인 녹화: `isRecording`
- 타인 녹화: `isOtherUserRecording`
- 녹화 중 표시: 빨간 점 UI

---

### 3.6 Camera (카메라/미디어 녹화)

#### 3.6.1 Screen Logic

**Input**
1. 모드 전환: 사진, 동영상, 연속촬영
2. 렌즈 전환: 전면/후면
3. 조명 ON/OFF
4. 줌 조절
5. 촬영/녹화 버튼 클릭

**Output/State Change**
1. **카메라 상태**
   - `PhoneCameraState`:
     - mode: PHOTO, VIDEO, BURST
     - lensType: FRONT, BACK
     - lightState: isOn
     - zoomState: minLevel, maxLevel, currentLevel

2. **촬영/녹화 상태**
   - `PhotoCaptureState`: Idle, Capturing, Captured, Error
   - `VideoRecordingState`: Idle, Recording, InProgress, Stopping, Recorded, Error
   - `AudioRecordingState`: Idle, Recording, Stopping, Recorded, Error

3. **업로드 상태**
   - `UploadState`: Idle, Progress(%), Success, Error, Cancel

**Loading State**
- 각 미디어 타입별 상태 독립 관리

#### 3.6.2 Flow Diagram

**사진 촬영 플로우**
```
[Camera 화면 진입] (workId, checklistId, questionId 전달)
   ↓
[카메라 초기화]
   ├─ 권한 체크 (CAMERA)
   ├─ ProcessCameraProvider 초기화
   ├─ ImageCapture UseCase 설정
   └─ VideoCapture UseCase 설정
   ↓
[사진 모드 선택] (기본)
   ↓
[촬영 버튼 클릭]
   ↓
[사진 촬영]
   ├─ 촬영 사운드 재생
   ├─ ImageCapture.takePicture()
   ├─ 파일 저장 (P_yyyyMMdd_HHmmss.jpg)
   └─ _capturedMedias에 추가
   ↓
[업로드 시작]
   ↓
[checkProgress API 호출]
   ├─ 파일 메타정보 전송
   ├─ fileId 발급 (신규) 또는 조회 (재업로드)
   ├─ uploadedChunks, missingChunks 수신
   └─ 캐시에 파일 정보 저장
   ↓
[Chunk 업로드]
   ├─ WorkManager 사용
   ├─ ChunkUploadWorker 실행
   ├─ missingChunks만 업로드
   └─ 진행률 업데이트 (UploadState.Progress)
   ↓
[업로드 완료]
   ├─ UploadState.Success
   └─ _uploadStates에서 제거
```

**동영상 녹화 플로우**
```
[동영상 모드 선택]
   ↓
[녹화 버튼 클릭] (시작)
   ↓
[녹화 시작]
   ├─ 녹화 시작 사운드 재생
   ├─ VideoCapture.startRecording()
   ├─ 파일 생성 (V_yyyyMMdd_HHmmss.mp4)
   └─ VideoRecordingState.InProgress
   ↓
[녹화 버튼 클릭] (종료)
   ↓
[녹화 중지]
   ├─ 녹화 종료 사운드 재생
   ├─ VideoCapture.stopRecording()
   ├─ VideoRecordingState.Recorded
   └─ 썸네일 추출 (첫 프레임)
   ↓
[checkProgress API 호출]
   ├─ 썸네일 포함하여 전송
   └─ fileId 발급
   ↓
[Chunk 업로드]
   ├─ 동영상 파일 업로드
   └─ 진행률 업데이트
   ↓
[업로드 완료]
   ├─ 썸네일 파일 삭제
   └─ VideoRecordingState.Idle
```

**연속촬영 플로우**
```
[연속촬영 모드 선택]
   ↓
[촬영 버튼 클릭]
   ↓
[3장 연속 촬영]
   ├─ 연사 사운드 재생 (3회, 100ms 간격)
   ├─ 1번째 촬영 (사운드 없음)
   ├─ 100ms 대기
   ├─ 2번째 촬영 (사운드 없음)
   ├─ 100ms 대기
   └─ 3번째 촬영 (사운드 없음)
   ↓
[각 파일 개별 업로드]
   ├─ 파일1 checkProgress + 업로드
   ├─ 파일2 checkProgress + 업로드
   └─ 파일3 checkProgress + 업로드
```

**로그인 상태 변경 시 처리**
```
[로그인 상태 관찰]
   ↓
[상태 변경 감지]
   ├─ [로그인] (null → true, false → true)
   │    └─ [Pending 파일 체크]
   │         ├─ 로컬 미디어 파일 검색
   │         ├─ 서버 파일 목록 조회
   │         ├─ 상태가 REQUEST인 파일 필터링
   │         └─ 자동 업로드 재시작
   │
   └─ [로그아웃] (true → false)
        └─ [모든 업로드 즉시 중단]
             ├─ 캐시 초기화
             └─ WorkManager 작업 취소
```

#### 3.6.3 Business Rules

**BR-CAMERA-001: 파일 명명 규칙**
- 사진: `P_yyyyMMdd_HHmmss.jpg`
- 동영상: `V_yyyyMMdd_HHmmss.mp4`
- 오디오: `A_yyyyMMdd_HHmmss.m4a`
- 썸네일: `{동영상파일명}_thumb.jpg`

**BR-CAMERA-002: 카메라 모드**
- PHOTO: 사진 촬영
- VIDEO: 동영상 녹화
- BURST: 연속 촬영 (3장)

**BR-CAMERA-003: Chunk 업로드**
- Chunk 크기: 512KB (고정)
- 총 Chunk 수: `ceil(파일크기 / 512KB)`
- checkProgress API로 업로드 진행 상황 확인:
  - uploadedChunks: 이미 업로드된 청크 목록
  - missingChunks: 업로드 필요한 청크 목록
- missingChunks만 선택적으로 업로드 (재업로드 지원)

**BR-CAMERA-004: 업로드 재시도**
- 로그인 상태 변경 시 Pending 파일 자동 업로드
- 파일 상태가 REQUEST인 경우만 재시도
- 서버에서 fileId 조회하여 이어서 업로드

**BR-CAMERA-005: 동영상 썸네일**
- 첫 번째 프레임 추출
- 크기: 512x512
- 포맷: JPEG (90% 품질)
- 중앙 크롭 적용
- checkProgress API에 Base64 인코딩하여 전송
- 업로드 완료 후 썸네일 파일 삭제

**BR-CAMERA-006: 로그아웃 시 처리**
- 진행 중인 모든 업로드 즉시 중단
- WorkManager의 모든 ChunkUploadWorker 취소
- 캐시 데이터 초기화
- 녹화 중인 경우 즉시 중지 (파일 삭제)

**BR-CAMERA-007: 권한 요구사항**
- CAMERA: 카메라 사용
- RECORD_AUDIO: 동영상/오디오 녹음
- READ_MEDIA_IMAGES (Android 13+): 이미지 읽기
- READ_MEDIA_VIDEO (Android 13+): 동영상 읽기
- READ_EXTERNAL_STORAGE (Android 12 이하): 미디어 읽기
- WRITE_EXTERNAL_STORAGE (Android 12 이하): 미디어 쓰기

---

### 3.7 Gallery (미디어 뷰어)

#### 3.7.1 Screen Logic

**Input**
1. 탭 전환: 사진, 동영상, 오디오
2. 미디어 선택
3. 뷰 타입 전환: 리스트, 그리드
4. 미디어 삭제
5. 미디어 다운로드 (동영상)

**Output/State Change**
1. **갤러리 상태**
   - `GalleryItemView`:
     - imageItem: LIST or GRID
     - videoItem: LIST or GRID
     - audioItem: LIST

2. **탭별 미디어 목록**
   - 사진: 작업별 그룹화, 날짜순 정렬
   - 동영상: 작업별 그룹화, 썸네일 표시
   - 오디오: 작업별 그룹화, 재생 컨트롤

3. **미디어 상세**
   - 사진: ImageSlider 화면 (좌우 스와이프)
   - 동영상: VideoPlayer 화면 (재생/일시정지/탐색)
   - 오디오: AudioPlayer 화면 (재생/일시정지/파형 표시)

**Loading State**
- 미디어 목록 로딩
- 썸네일 로딩 (동영상)

#### 3.7.2 Flow Diagram

```
[Gallery 화면 진입] (workId 전달)
   ↓
[작업 정보 로드]
   ├─ workId로 workName 조회
   └─ 미디어 파일 목록 조회
   ↓
[탭별 미디어 필터링]
   ├─ [사진 탭]
   │    ├─ 확장자: jpg, jpeg, png
   │    ├─ 작업별 그룹화 (WorkHeaderData)
   │    ├─ 날짜별 헤더 표시
   │    └─ 뷰 타입: 리스트 또는 그리드
   │
   ├─ [동영상 탭]
   │    ├─ 확장자: mp4
   │    ├─ 썸네일 표시 (MediaMetadataRetriever)
   │    ├─ 재생 시간 표시
   │    └─ 뷰 타입: 리스트 또는 그리드
   │
   └─ [오디오 탭]
        ├─ 확장자: m4a
        ├─ 녹음 시간 표시
        └─ 뷰 타입: 리스트
   ↓
[미디어 선택]
   ├─ [사진 클릭] → [ImageSlider 화면]
   │    ├─ ViewPager2로 좌우 스와이프
   │    ├─ 확대/축소 가능
   │    └─ 삭제 버튼
   │
   ├─ [동영상 클릭] → [VideoPlayer 화면]
   │    ├─ 서버에서 ZIP 다운로드 (Chunk 통합)
   │    ├─ 로컬 압축 해제
   │    ├─ ExoPlayer로 재생
   │    ├─ 재생 컨트롤 (재생/일시정지/탐색)
   │    └─ 삭제 버튼
   │
   └─ [오디오 클릭] → [AudioPlayer 화면]
        ├─ 서버에서 ZIP 다운로드
        ├─ 로컬 압축 해제
        ├─ MediaPlayer로 재생
        ├─ 파형 시각화 (LineBarVisualizer)
        └─ 삭제 버튼
   ↓
[뷰 타입 변경]
   ├─ 리스트 → 그리드
   └─ 그리드 → 리스트
```

**동영상 다운로드 및 재생 플로우**
```
[VideoPlayer 화면 진입] (fileId, fileName 전달)
   ↓
[다운로드 상태 확인]
   ├─ 로컬 파일 존재? → [즉시 재생]
   └─ 미존재 → [서버 다운로드]
   ↓
[서버 다운로드]
   ├─ API: GET /api/file/{fileId}/download/zip
   ├─ 응답: ZIP 파일 (모든 Chunk 통합)
   ├─ 저장 경로: zipFiles/{fileName}.zip
   └─ 진행률 표시
   ↓
[압축 해제]
   ├─ ZipInputStream 사용
   ├─ 원본 파일명으로 저장
   └─ ZIP 파일 삭제
   ↓
[ExoPlayer 초기화]
   ├─ MediaItem 생성
   ├─ PlayerView 연결
   └─ 자동 재생 시작
   ↓
[재생 컨트롤]
   ├─ 재생/일시정지
   ├─ SeekBar로 탐색
   ├─ 전체화면 전환
   └─ 재생 완료 시 종료
   ↓
[화면 종료]
   └─ ExoPlayer 해제
```

#### 3.7.3 Business Rules

**BR-GALLERY-001: 미디어 그룹화**
- 작업별로 그룹화 (WorkHeaderData)
- 그룹 헤더: "작업명 (미디어 개수)"
- 날짜별 서브 헤더 (사진만)

**BR-GALLERY-002: 썸네일 생성**
- 동영상 첫 프레임 추출
- MediaMetadataRetriever 사용
- 크기: 512x512
- 중앙 크롭 적용
- 메모리 최적화: BitmapPool 사용

**BR-GALLERY-003: 다운로드 캐싱**
- 한 번 다운로드한 파일은 로컬에 유지
- 저장 경로: `zipFiles/`
- 갤러리 화면 종료 시 일괄 삭제 (`clearDownloadZipFiles`)

**BR-GALLERY-004: 삭제 처리**
- 서버 API 호출: DELETE /api/file/{fileId}
- 로컬 파일 삭제
- 갤러리 목록 갱신
- 삭제된 fileId를 ViewModel에 전달하여 목록 동기화

**BR-GALLERY-005: 뷰 타입 저장**
- 사용자가 선택한 뷰 타입을 ViewModel에 저장
- 화면 전환 시에도 선택 상태 유지
- 미디어 타입별로 독립적인 뷰 타입 관리

---

### 3.8 Notice (공지사항)

#### 3.8.1 Screen Logic

**Input**
1. 공지 목록 조회
2. 공지 클릭 (읽음 처리)
3. 공지 상세 보기

**Output/State Change**
1. **공지 목록**
   - 날짜별 그룹화
   - 헤더: "오늘" 또는 "yyyy년 MM월 dd일"
   - 읽지 않은 공지: 강조 표시

2. **읽지 않은 개수**
   - 실시간 업데이트
   - Home 화면 알림 아이콘에 배지 표시

3. **공지 읽음 처리**
   - 공지 클릭 시 자동으로 읽음 처리
   - API 호출: IF-301-005 (읽음 처리)
   - 읽지 않은 개수 감소

**Loading State**
- 공지 목록 로딩

#### 3.8.2 Flow Diagram

```
[Notice 화면 진입]
   ↓
[공지 목록 조회]
   ├─ API: GET /api/push/list
   ├─ 모든 공지 목록 수신
   └─ 읽지 않은 공지 필터링 (readTime == null)
   ↓
[읽지 않은 공지 캐싱]
   ├─ _unReadNoticeItems (Set<String>)
   └─ ViewModel 생성 시 한 번만 캐싱
   ↓
[공지 목록 표시]
   ├─ 날짜별 그룹화
   ├─ 최신순 정렬
   ├─ 헤더 표시
   │    ├─ 오늘: "오늘"
   │    └─ 과거: "yyyy년 MM월 dd일"
   └─ 공지 아이템 표시
        ├─ 제목
        ├─ 내용 (미리보기)
        ├─ 전송 시간
        └─ 읽음 여부 표시
   ↓
[공지 클릭]
   ↓
[읽음 여부 확인]
   ├─ 이미 읽음 → [상세 화면]
   └─ 읽지 않음 → [읽음 처리]
        ├─ _unReadNoticeItems에서 제거
        ├─ API 호출 (IF-301-005)
        └─ [상세 화면]
   ↓
[NoticeDetail 화면]
   ├─ 제목
   ├─ 내용 (전체)
   ├─ 전송 시간
   └─ 작업 정보 (있는 경우)
        └─ 작업으로 이동 버튼
```

#### 3.8.3 Business Rules

**BR-NOTICE-001: 읽지 않은 공지 캐싱**
- ViewModel 생성 시 한 번만 캐싱
- 이후 화면 복귀 시에는 캐시 사용
- `reInitializeCache()` 호출 시 재캐싱

**BR-NOTICE-002: 읽음 처리**
- 공지 클릭 시 읽지 않은 공지만 API 호출
- 이미 읽은 공지는 API 호출 생략
- 캐시에서 즉시 제거하여 UI 반영

**BR-NOTICE-003: 날짜 그룹화**
- 전송 시간 기준 날짜별 그룹화
- 오늘 날짜: "오늘"
- 과거 날짜: "yyyy년 MM월 dd일" (한글 형식)

**BR-NOTICE-004: 공지 타입**
- messageType에 따라 다른 처리
- 작업 관련 공지: workId 포함
- 일반 공지: workId 없음

---

### 3.9 Setting (설정)

#### 3.9.1 Screen Logic

**Input**
1. 디바이스 정보 조회
2. Wi-Fi 설정
3. 터치패드 설정 (Glass 전용 - 제외)
4. 방향 설정 (Glass 전용 - 제외)

**Output/State Change**
1. **디바이스 정보**
   - 디바이스 타입 (MOBILE/GLASS)
   - OS 버전
   - 앱 버전
   - 디바이스 ID
   - UUID

2. **Wi-Fi 정보**
   - 연결된 SSID
   - 신호 강도
   - IP 주소

**Loading State**
- 설정 정보 로딩

#### 3.9.2 Business Rules

**BR-SETTING-001: 디바이스 정보**
- 디바이스 타입:
  - MOBILE: 스마트폰
  - GLASS: 스마트 글래스 (제외)
- OS 타입: AOS (Android)
- 버전 정보: BuildConfig.VERSION_NAME

**BR-SETTING-002: Wi-Fi 설정**
- Wi-Fi 연결 상태 확인
- SSID, 신호 강도, IP 주소 표시
- Wi-Fi 켜기/끄기 (Android 10 이상 제한)

---

## 4. 핵심 비즈니스 규칙

### 4.1 인증 및 권한 관리

#### 4.1.1 토큰 관리
```
Access Token:
- 유효 기간: 서버 설정
- 저장 위치: DataStore (암호화)
- 갱신: RefreshToken 사용

Refresh Token:
- 유효 기간: 장기 (일반적으로 14~30일)
- 저장 위치: DataStore (암호화)
- 사용: Access Token 만료 시 자동 갱신
```

#### 4.1.2 토큰 갱신 로직
```kotlin
// TokenRefreshCoordinator 사용
suspend fun awaitFreshAccessToken(): Result<String> {
    // 1. 이미 갱신 중인지 확인 (중복 방지)
    if (isRefreshing()) {
        // 진행 중인 갱신 완료 대기
        return waitForCompletion()
    }
    
    // 2. 갱신 시작
    markAsRefreshing()
    
    // 3. RefreshToken으로 API 호출
    val result = try {
        val refreshToken = getRefreshToken()
        val response = authApi.refreshToken(refreshToken)
        
        // 4. 새로운 토큰 저장
        saveAccessToken(response.accessToken)
        saveRefreshToken(response.refreshToken)
        
        Result.success(response.accessToken)
    } catch (e: Exception) {
        Result.failure(e)
    }
    
    // 5. 갱신 완료 표시
    markAsCompleted(result)
    
    return result
}
```

**갱신 실패 처리**
- 401/400 에러 + 특정 코드 (1003, 1004) → 로그아웃
- 네트워크 에러 → 재시도 (최대 3회)
- 기타 에러 → 로그아웃

#### 4.1.3 작업카드 권한 체크
```kotlin
fun checkEditPermission(
    currentLoginId: String,
    mainWorkerId: String,
    roleGroupId: Int,
    participants: List<ShareWorkerEntity>
): EditPermission {
    val isCreator = currentLoginId == mainWorkerId
    val isAdmin = roleGroupId == 1
    val isParticipant = participants.any { it.workerId == currentLoginId }
    
    return when {
        isCreator -> EditPermission.FULL // 모든 필드 편집 가능
        isAdmin || isParticipant -> EditPermission.LIMITED // 설명, 참여자만
        else -> EditPermission.NONE // 읽기 전용
    }
}
```

### 4.2 파일 업로드 (Chunk 기반)

#### 4.2.1 Chunk 업로드 흐름
```
1. checkProgress API 호출
   - 입력: 파일 메타정보 (파일명, 크기, 총 Chunk 수, 그룹 ID)
   - 출력: fileId, uploadedChunks, missingChunks
   
2. missingChunks만 업로드
   - Chunk 크기: 512KB
   - 동시 업로드: 최대 3개
   - WorkManager 사용 (백그라운드 지속)
   
3. 각 Chunk 업로드
   - API: POST /api/file/chunk
   - Body: fileId, chunkIndex, chunkData (Base64)
   
4. 모든 Chunk 업로드 완료
   - 서버가 자동으로 파일 통합
   - 상태: REQUEST → COMPLETE
```

#### 4.2.2 재업로드 지원
```
시나리오: 앱 종료 또는 네트워크 끊김 후 재시작

1. 로그인 시 Pending 파일 검사
   - 로컬에 남아있는 미디어 파일 검색
   - 서버에서 파일 목록 조회
   
2. 상태 매칭
   - fileOriginName으로 매칭
   - 서버 상태가 REQUEST인 파일 필터링
   
3. 이어서 업로드
   - 기존 fileId 사용
   - checkProgress로 uploadedChunks 확인
   - missingChunks만 재업로드
```

#### 4.2.3 업로드 상태 관리
```kotlin
sealed class UploadState {
    object Idle : UploadState()
    data class Progress(val progress: Int) : UploadState() // 0~100
    data class Success(val fileUpload: MediaFileItem.FileUpload) : UploadState()
    data class Error(val message: String) : UploadState()
    object Cancel : UploadState()
}

// 파일 경로를 키로 사용
val uploadStates: StateFlow<Map<String, UploadState>>
```

### 4.3 WebSocket 재연결

#### 4.3.1 연결 상태
```kotlin
sealed class Connection {
    object Disconnected : Connection()
    object Connecting : Connection()
    object Connected : Connection()
}
```

#### 4.3.2 재연결 로직
```
[Disconnected 이벤트 수신]
   ↓
[이전 상태 확인]
   ├─ 이미 Disconnected → 무시 (중복 방지)
   └─ 다른 상태 → 재연결 시도
   ↓
[saveRoomData 확인]
   ├─ 존재 → 재연결 요청
   │    ├─ room_id
   │    ├─ room_user_id (curParticipantId)
   │    ├─ displayName
   │    └─ workId
   │
   └─ 미존재 → 로그 기록 및 종료
   ↓
[connectWebSocket(needStateCheck = false)]
   └─ 상태 체크 스킵하여 즉시 연결
```

#### 4.3.3 토큰 만료 처리
```
[ErrorReceived 이벤트 수신]
   ↓
[에러 코드 확인]
   ├─ status: 400 또는 401
   └─ code: "1003" 또는 "1004"
   ↓
[토큰 만료 확인]
   ├─ YES → [토큰 갱신 시도]
   │    ├─ TokenRefreshCoordinator 사용
   │    ├─ [성공] → [WebSocket 재연결]
   │    └─ [실패] → [로그아웃]
   │
   └─ NO → [로그아웃]
```

### 4.4 참여자 관리

#### 4.4.1 참여자 검색 알고리즘
```kotlin
fun filter(query: String): List<UserItem> {
    return allWorkers.filter { worker ->
        // 1. 이름 일치
        worker.userName.contains(query, ignoreCase = true) ||
        
        // 2. 부서명 일치
        worker.deptName.contains(query, ignoreCase = true) ||
        
        // 3. 한글 초성 검색 (예: "ㄱㄷㅎ" → "김동현")
        matchesKoreanInitials(worker.userName, query)
    }
}

fun matchesKoreanInitials(name: String, query: String): Boolean {
    val initials = name.map { char ->
        if (char in '가'..'힣') {
            val unicode = char.code - 0xAC00
            val initial = unicode / (21 * 28)
            INITIALS[initial]
        } else {
            char
        }
    }.joinToString("")
    
    return initials.contains(query, ignoreCase = true)
}
```

#### 4.4.2 참여자 제한
```
최대 인원: 10명 (생성자 포함)
최소 인원: 1명

검증 로직:
- 추가 시: currentCount < 10
- 삭제 시: currentCount > 1
- UI 피드백: 최대 인원 도달 시 검색 비활성화
```

---

## 5. 상태 관리 전략

### 5.1 MVI 패턴

#### 5.1.1 Intent (사용자 의도)
```kotlin
sealed interface AllWorkCardListIntent {
    data class LoadInitialData(val aliveWorks: Boolean) : AllWorkCardListIntent
    data class LoadInitialDataIfNeeded(val aliveWorks: Boolean) : AllWorkCardListIntent
    data class LoadMore(val aliveWorks: Boolean) : AllWorkCardListIntent
    object Refresh : AllWorkCardListIntent
}
```

#### 5.1.2 UiState (UI 상태)
```kotlin
sealed interface AllWorkCardListUiState {
    object Idle : AllWorkCardListUiState
    object Loading : AllWorkCardListUiState
    
    data class Success(
        val groupedWorkCards: Map<String, List<WorkRecordUiModel>>,
        val totalElements: Int,
        val currentPage: Int,
        val hasMore: Boolean
    ) : AllWorkCardListUiState
    
    data class Error(val message: String) : AllWorkCardListUiState
}
```

#### 5.1.3 SideEffect (일회성 이벤트)
```kotlin
sealed interface AllWorkCardListSideEffect {
    data class ShowToast(val message: String) : AllWorkCardListSideEffect
    data class NavigateToDetail(val workId: String) : AllWorkCardListSideEffect
}
```

### 5.2 StateFlow 패턴

#### 5.2.1 상태 노출
```kotlin
private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
val uiState: StateFlow<UiState> = _uiState.asStateFlow()
```

#### 5.2.2 상태 업데이트
```kotlin
// 전체 교체
_uiState.value = UiState.Loading

// 부분 업데이트 (data class copy)
_uiState.update { 
    it.copy(isLoading = false, data = newData)
}
```

#### 5.2.3 상태 구독 (Fragment)
```kotlin
viewLifecycleOwner.lifecycleScope.launch {
    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.uiState.collectLatest { state ->
            when (state) {
                is UiState.Idle -> { }
                is UiState.Loading -> showLoading()
                is UiState.Success -> updateUI(state.data)
                is UiState.Error -> showError(state.message)
            }
        }
    }
}
```

### 5.3 공통 상태 패턴

#### 5.3.1 Loading/Success/Error 패턴
```kotlin
sealed class DataState<out T> {
    object Idle : DataState<Nothing>()
    object Loading : DataState<Nothing>()
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val message: String) : DataState<Nothing>()
}
```

#### 5.3.2 API Result 패턴
```kotlin
sealed class ApiResult<out T> {
    data class Success<T>(val value: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
    data class Exception(val exception: Throwable) : ApiResult<Nothing>()
    object Empty : ApiResult<Nothing>()
}
```

---

## 6. API 통신 규칙

### 6.1 공통 헤더
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

### 6.2 에러 응답 구조
```json
{
  "success": false,
  "code": "1003",
  "message": "토큰이 만료되었습니다.",
  "data": null
}
```

### 6.3 주요 에러 코드
- `1003`: Access Token 만료
- `1004`: Refresh Token 만료
- `1018`: 중복 로그인
- `400`: 잘못된 요청
- `401`: 인증 실패
- `403`: 권한 없음
- `404`: 리소스 없음
- `500`: 서버 에러

### 6.4 네트워크 재시도 정책
```
조건:
- 네트워크 에러 (ConnectException, SocketTimeoutException)
- 5xx 서버 에러

재시도:
- 최대 3회
- 지수 백오프 (1초, 2초, 4초)
- 401 에러는 재시도 없음 (토큰 갱신 또는 로그아웃)
```

### 6.5 요청 제한 (Throttling)
```
동일 API 연속 호출 방지:
- 최소 간격: 300ms
- ThrottlingInterceptor 사용
- 빠른 연속 호출 시 마지막 요청만 처리
```

---

## 7. 부록

### 7.1 상태 전이도

#### 7.1.1 로그인 상태
```
[로그아웃] ←→ [로그인]
    ↑            ↓
    └────────────┘
   (로그아웃 API 호출
    또는 토큰 만료)
```

#### 7.1.2 작업 상태
```
[생성]
  ↓
[START] → [WORK] ← → [PAUSE]
           ↓
         [END]
```

#### 7.1.3 업로드 상태
```
[Idle] → [Progress] → [Success]
                   ↘
                    [Error]
                   ↗
              [Cancel]
```

### 7.2 주요 UseCase 목록

#### 7.2.1 인증
- `RequestAccountUseCase`: 로그인, 로그아웃, 토큰 갱신
- `FindIdUseCase`: 아이디 찾기
- `FindPasswordUseCase`: 비밀번호 찾기
- `ChangePasswordUseCase`: 비밀번호 변경

#### 7.2.2 작업카드
- `WorkCardUseCase`: 작업카드 생성
- `UpdateWorkCardUseCase`: 작업카드 수정
- `GetWorkCardListUseCase`: 작업카드 목록 조회
- `GetWorkRecordUseCase`: 작업 기록 조회

#### 7.2.3 미디어
- `CapturePhotoUseCase`: 사진 촬영
- `StartVideoCaptureUseCase`: 동영상 녹화 시작
- `StopVideoCaptureUseCase`: 동영상 녹화 종료
- `StartAudioCaptureUseCase`: 오디오 녹음 시작
- `StopAudioCaptureUseCase`: 오디오 녹음 종료
- `GetFileListUseCase`: 파일 목록 조회
- `EditFileNameUseCase`: 파일 이름 변경
- `ExtractVideoThumbnailUseCase`: 동영상 썸네일 추출

#### 7.2.4 통신
- `RequestEnterChatUseCase`: 화상회의 참여 (WebSocket)
- `RequestPushListUseCase`: 푸시 알림 목록 조회
- `RequestPushReadUseCase`: 푸시 알림 읽음 처리

---

## 8. KMP 마이그레이션 고려사항

### 8.1 플랫폼 독립적 로직
- ViewModel 로직
- UseCase
- Repository Interface
- Domain Models
- Business Rules

### 8.2 플랫폼 종속 로직
- Camera/Media 처리 (expect/actual)
- 파일 시스템 접근 (expect/actual)
- 네트워크 통신 구현 (Ktor 사용 권장)
- 로컬 저장소 (DataStore, Room)

### 8.3 권장 아키텍처
```
┌──────────────────────────────────┐
│  UI Layer (Platform Specific)    │
│  - Compose Multiplatform         │
│  - ViewModels (shared)           │
└──────────────────────────────────┘
            ↓
┌──────────────────────────────────┐
│  Domain Layer (Common)            │
│  - UseCase                        │
│  - Repository Interface           │
│  - Domain Models                  │
└──────────────────────────────────┘
            ↓
┌──────────────────────────────────┐
│  Data Layer (Common + Actual)     │
│  - Repository Implementation      │
│  - API Service (Ktor)             │
│  - Local DataSource (expect)      │
└──────────────────────────────────┘
```

---

## 9. 문서 버전 정보

- **작성일**: 2025-01-22
- **버전**: 1.0.0
- **기반 코드**: SmartGlass Mobile (phone 플레이버)
- **분석 범위**: app/src/main, app/src/phone, domain, data

---

**문서 끝**