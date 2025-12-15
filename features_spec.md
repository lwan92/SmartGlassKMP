# SmartGlass Mobile 기능 명세서

## 목차
1. [Screen Logic](#screen-logic)
2. [Core Business Rules](#core-business-rules)
3. [State Management](#state-management)

---

## Screen Logic

### 1. Intro 화면 (IntroFragment)

#### 사용자 행동 및 앱 반응

**Input: 앱 시작**
- Output: 스플래시 이미지 표시 (브랜딩 이미지 또는 기본 이미지)
- State Change: Intro 화면 표시

**Input: 2초 대기 후**
- Output: 권한 확인 시작
- State Change: 권한 확인 상태로 전환

**Input: 오버레이 권한 미승인**
- Output: 오버레이 권한 요청 팝업 표시
- State Change: 권한 요청 대기 상태

**Input: 오버레이 권한 팝업에서 "설정" 버튼 클릭**
- Output: 시스템 설정 화면으로 이동
- State Change: 설정 화면으로 전환

**Input: 오버레이 권한 팝업에서 "취소" 버튼 클릭**
- Output: 권한 요청 계속 진행
- State Change: 권한 확인 상태로 복귀

**Input: 필수 권한 확인 완료**
- Output: 로그인 상태 확인
- State Change: 로그인 확인 상태로 전환

**Input: 자동 로그인 가능 (토큰 존재 + 디바이스 등록됨)**
- Output: 자동 로그인 시도
- State Change: 로그인 진행 중 상태

**Input: 자동 로그인 성공**
- Output: 홈 화면으로 이동
- State Change: 로그인 완료 → 홈 화면

**Input: 자동 로그인 실패 또는 자동 로그인 불가**
- Output: 로그인 화면으로 이동
- State Change: 로그인 화면으로 전환

**Input: 권한 거부**
- Output: 권한 안내 토스트 표시, 로그인 화면으로 이동
- State Change: 로그인 화면으로 전환

---

### 2. 로그인 화면 (LoginFragment)

#### 사용자 행동 및 앱 반응

**Input: 화면 진입**
- Output: 로고 이미지 표시 (브랜딩 이미지 또는 기본 이미지)
- State Change: 로그인 화면 표시

**Input: 디바이스 미등록 상태에서 입력 필드 포커스**
- Output: 디바이스 등록 팝업 표시
- State Change: 디바이스 등록 대기 상태

**Input: 디바이스 등록 팝업에서 "QR 코드 스캔" 버튼 클릭**
- Output: 카메라 권한 확인 → QR 코드 스캔 화면으로 이동
- State Change: QR 스캔 화면으로 전환

**Input: QR 코드 스캔 완료 (디바이스 등록용)**
- Output: 디바이스 등록 API 호출, 로딩 표시
- State Change: 디바이스 등록 진행 중 상태

**Input: 디바이스 등록 성공**
- Output: 성공 토스트 표시, 입력 필드 활성화, 브랜딩 이미지 다운로드
- State Change: 로그인 입력 가능 상태

**Input: 디바이스 등록 실패**
- Output: 실패 토스트 표시, 디바이스 등록 팝업 재표시
- State Change: 디바이스 등록 대기 상태

**Input: 아이디/비밀번호 입력**
- Output: 입력 필드 업데이트, 로그인 버튼 활성화/비활성화
- State Change: 입력 상태 업데이트

**Input: 비밀번호 표시/숨김 버튼 클릭**
- Output: 비밀번호 입력 타입 변경 (텍스트/비밀번호)
- State Change: 비밀번호 표시 상태 토글

**Input: 자동 로그인 체크박스 클릭**
- Output: 자동 로그인 체크 상태 토글
- State Change: 자동 로그인 설정 상태 업데이트

**Input: 로그인 버튼 클릭 또는 비밀번호 입력 후 엔터**
- Output: 로그인 API 호출, 로딩 표시
- State Change: 로그인 진행 중 상태

**Input: 로그인 성공**
- Output: 로그인 상태 저장, 비밀번호 재설정 필요 시 비밀번호 변경 화면으로 이동, 그 외 홈 화면으로 이동
- State Change: 로그인 완료 → 홈 화면 또는 비밀번호 변경 화면

**Input: 로그인 실패 (일반)**
- Output: 에러 토스트 표시
- State Change: 로그인 실패 상태

**Input: 로그인 실패 (중복 로그인 - 코드 1018)**
- Output: 중복 로그인 팝업 표시
- State Change: 중복 로그인 확인 대기 상태

**Input: 중복 로그인 팝업에서 "확인" 버튼 클릭**
- Output: 중복 로그인 허용 옵션으로 재로그인 시도
- State Change: 로그인 진행 중 상태

**Input: 중복 로그인 팝업에서 "취소" 버튼 클릭**
- Output: 팝업 닫기
- State Change: 로그인 화면 상태로 복귀

**Input: QR 코드 로그인 버튼 클릭**
- Output: 카메라 권한 확인 → QR 코드 스캔 화면으로 이동
- State Change: QR 스캔 화면으로 전환

**Input: QR 코드 스캔 완료 (로그인용)**
- Output: QR 로그인 API 호출
- State Change: 로그인 진행 중 상태

**Input: QR 로그인 성공**
- Output: 자동 로그인 설정, 홈 화면으로 이동
- State Change: 로그인 완료 → 홈 화면

**Input: 계정 찾기 버튼 클릭**
- Output: 계정 찾기 안내 팝업 표시
- State Change: 계정 찾기 안내 상태

---

### 3. 홈 화면 (HomeFragment)

#### 사용자 행동 및 앱 반응

**Input: 화면 진입**
- Output: 대기 중인 통화 목록 로드 요청
- State Change: 홈 화면 표시, 대기 통화 목록 로딩 상태

**Input: 대기 통화 목록 로드 성공**
- Output: 대기 통화 목록 표시
- State Change: 대기 통화 목록 성공 상태

**Input: 대기 통화 목록 로드 실패**
- Output: 에러 메시지 표시 또는 빈 목록 표시
- State Change: 대기 통화 목록 에러 상태

**Input: 통화 시작 버튼 클릭**
- Output: 통화 사용자 선택 화면으로 이동
- State Change: 통화 사용자 선택 화면으로 전환

**Input: 작업 목록 버튼 클릭**
- Output: 작업 목록 화면으로 이동
- State Change: 작업 목록 화면으로 전환

**Input: 설정 버튼 클릭**
- Output: 설정 화면으로 이동
- State Change: 설정 화면으로 전환

**Input: 알림 버튼 클릭**
- Output: 알림 화면으로 이동
- State Change: 알림 화면으로 전환

**Input: 로그아웃 버튼 클릭**
- Output: 로그아웃 확인 팝업 표시
- State Change: 로그아웃 확인 대기 상태

**Input: 로그아웃 확인 팝업에서 "확인" 버튼 클릭**
- Output: 로그아웃 API 호출, 작업 일시정지, 사용자 정보 삭제, 로그인 화면으로 이동
- State Change: 로그아웃 완료 → 로그인 화면

**Input: 앱 종료 버튼 클릭**
- Output: 앱 종료 확인 팝업 표시
- State Change: 앱 종료 확인 대기 상태

**Input: 앱 종료 확인 팝업에서 "종료" 버튼 클릭**
- Output: 모든 미디어 녹화 중지, 업로드 취소, 앱 종료
- State Change: 앱 종료

**Input: 새 작업 카드 표시 이벤트**
- Output: 새 작업 카드 UI 표시
- State Change: 새 작업 카드 표시 상태

**Input: Pending Call 존재 (앱 종료 상태에서 통화 수신)**
- Output: HomeFragment로 이동 후 Pending Call 처리, 통화 수락 팝업 표시 또는 통화 화면으로 이동
- State Change: 통화 수신 처리 상태

---

### 4. 작업 목록 화면 (WorkListFragment)

#### 사용자 행동 및 앱 반응

**Input: 화면 진입**
- Output: 작업 목록 로드 요청
- State Change: 작업 목록 로딩 상태

**Input: 작업 목록 로드 성공**
- Output: 작업 목록 표시, 포커스 위치 복원
- State Change: 작업 목록 성공 상태

**Input: 작업 목록 로드 실패**
- Output: 에러 메시지 표시
- State Change: 작업 목록 에러 상태

**Input: 작업 항목 선택**
- Output: 선택한 작업으로 작업 기록 화면으로 이동
- State Change: 작업 기록 화면으로 전환

**Input: 새 작업 시작 버튼 클릭**
- Output: 작업 시작 API 호출, 작업 기록 화면으로 이동
- State Change: 작업 시작 → 작업 기록 화면

**Input: 작업 완료 이벤트 수신**
- Output: 완료된 작업을 목록에서 제거, 포커스 위치 조정
- State Change: 작업 목록 업데이트 상태

**Input: 스크롤 (다음/이전)**
- Output: 다음/이전 항목으로 포커스 이동, 필요시 스크롤
- State Change: 포커스 위치 업데이트

---

### 5. 작업 기록 컨테이너 화면 (WorkRecordContainerFragment)

#### 사용자 행동 및 앱 반응

**Input: 화면 진입**
- Output: 기본 탭 선택 (카드/체크리스트/진행상황)
- State Change: 작업 기록 컨테이너 화면 표시

**Input: 탭 전환 (카드/체크리스트/진행상황)**
- Output: 선택한 탭의 화면 표시
- State Change: 탭 상태 업데이트

**Input: 작업 ID 로드**
- Output: 해당 작업의 데이터 로드 요청
- State Change: 작업 데이터 로딩 상태

---

### 6. 작업 기록 카드 화면 (WorkRecordCardFragment)

#### 사용자 행동 및 앱 반응

**Input: 화면 진입**
- Output: 작업 카드 목록 로드 요청
- State Change: 작업 카드 목록 로딩 상태

**Input: 작업 카드 목록 로드 성공**
- Output: 작업 카드 목록 표시
- State Change: 작업 카드 목록 성공 상태

**Input: 작업 카드 선택**
- Output: 선택한 작업 카드 정보 저장, 작업 시작 또는 재개
- State Change: 작업 시작 상태

**Input: 작업 카드 목록 로드 실패**
- Output: 에러 메시지 표시
- State Change: 작업 카드 목록 에러 상태

---

### 7. 화상회의 화면 (VideoConferenceFragment)

#### 사용자 행동 및 앱 반응

**Input: 화면 진입**
- Output: Jitsi Meet 초기화, WebSocket 연결, 룸 참가 요청
- State Change: 화상회의 초기화 상태

**Input: 룸 참가 성공**
- Output: 화상회의 화면 표시, 참가자 목록 업데이트
- State Change: 화상회의 진행 중 상태

**Input: 룸 참가 실패 (10초 타임아웃)**
- Output: 타임아웃 에러 표시, 이전 화면으로 이동
- State Change: 화상회의 실패 상태

**Input: 마이크 토글 버튼 클릭**
- Output: 마이크 음소거/해제, UI 상태 업데이트
- State Change: 마이크 상태 토글

**Input: 비디오 토글 버튼 클릭**
- Output: 비디오 켜기/끄기, UI 상태 업데이트
- State Change: 비디오 상태 토글

**Input: 화면 공유 버튼 클릭**
- Output: 화면 공유 시작/중지, UI 상태 업데이트
- State Change: 화면 공유 상태 토글

**Input: 플래시 토글 버튼 클릭**
- Output: 플래시 켜기/끄기, UI 상태 업데이트
- State Change: 플래시 상태 토글

**Input: 녹화 시작 버튼 클릭**
- Output: 녹화 시작, UI 상태 업데이트
- State Change: 녹화 진행 중 상태

**Input: 녹화 중지 버튼 클릭**
- Output: 녹화 중지, 녹화 파일 저장
- State Change: 녹화 완료 상태

**Input: 참가자 추가 이벤트 수신**
- Output: 참가자 목록 업데이트, 썸네일 추가
- State Change: 참가자 목록 업데이트 상태

**Input: 참가자 제거 이벤트 수신**
- Output: 참가자 목록 업데이트, 썸네일 제거
- State Change: 참가자 목록 업데이트 상태

**Input: 채팅 메시지 수신**
- Output: 채팅 메시지 표시 (Simple 모드 또는 Expandable 모드)
- State Change: 채팅 메시지 업데이트 상태

**Input: 채팅 입력 후 전송**
- Output: 채팅 메시지 전송, WebSocket으로 메시지 발송
- State Change: 채팅 메시지 전송 상태

**Input: 볼륨 조절 (시크바)**
- Output: 볼륨 시크바 표시/숨김, 볼륨 레벨 업데이트
- State Change: 볼륨 조절 상태

**Input: 화질 변경**
- Output: 화질 레벨 변경 (LOW → STANDARD → HIGH → ULTRA → LOW 순환)
- State Change: 화질 상태 업데이트

**Input: 줌 조절 (시크바)**
- Output: 줌 시크바 표시/숨김, 줌 레벨 업데이트
- State Change: 줌 조절 상태

**Input: 메뉴 바 표시/숨김**
- Output: 메뉴 바 토글
- State Change: 메뉴 바 표시 상태 토글

**Input: 통화 종료 버튼 클릭**
- Output: 통화 종료, WebSocket 연결 해제, 이전 화면으로 이동
- State Change: 통화 종료 → 이전 화면

**Input: 통화 취소 Push 수신**
- Output: 통화 취소 메시지 표시, 통화 사용자 목록 화면으로 이동
- State Change: 통화 취소 → 사용자 목록 화면

**Input: 통화 거절 Push 수신**
- Output: 거절 메시지 표시, 대기 중이면 거절한 사용자 목록에서 제거, 모든 사용자가 거절 시 이전 화면으로 이동
- State Change: 통화 거절 처리 상태

**Input: 통화 수락 Push 수신**
- Output: 통화 수락 확인, 화상회의 화면으로 이동
- State Change: 통화 수락 → 화상회의 화면

---

### 8. 통화 대기 화면 (WaitingFragment)

#### 사용자 행동 및 앱 반응

**Input: 화면 진입**
- Output: 대기 화면 표시, 통화 요청 전송, 60초 타임아웃 시작
- State Change: 통화 대기 상태

**Input: 통화 요청 전송 성공**
- Output: 수신자 목록 표시, 통화 요청 토스트 표시
- State Change: 통화 요청 전송 완료 상태

**Input: 통화 요청 전송 실패**
- Output: 에러 메시지 표시, 통화 사용자 목록 화면으로 이동
- State Change: 통화 요청 실패 상태

**Input: 수신자 중 일부 수락**
- Output: 수락한 사용자와 화상회의 화면으로 이동
- State Change: 통화 수락 → 화상회의 화면

**Input: 모든 수신자 거절**
- Output: 모든 사용자 거절 메시지 표시, 이전 화면으로 이동
- State Change: 통화 거절 완료 → 이전 화면

**Input: 60초 타임아웃**
- Output: 타임아웃 메시지 표시, 통화 사용자 목록 화면으로 이동
- State Change: 타임아웃 → 사용자 목록 화면

**Input: 뒤로가기**
- Output: 통화 취소 Push 전송, 통화 사용자 목록 화면으로 이동
- State Change: 통화 취소 → 사용자 목록 화면

---

### 9. 미디어 녹화 화면 (MediaRecordFragment)

#### 사용자 행동 및 앱 반응

**Input: 화면 진입**
- Output: 카메라 초기화, 미리보기 표시
- State Change: 미디어 녹화 화면 표시, 카메라 초기화 상태

**Input: 카메라 초기화 성공**
- Output: 카메라 미리보기 표시
- State Change: 카메라 준비 완료 상태

**Input: 카메라 초기화 실패**
- Output: 에러 메시지 표시
- State Change: 카메라 초기화 실패 상태

**Input: 사진 촬영 버튼 클릭**
- Output: 사진 촬영, 파일 저장, 업로드 시작
- State Change: 사진 촬영 중 → 촬영 완료 → 업로드 진행 중

**Input: 연속 촬영 버튼 클릭**
- Output: 3장 연속 촬영, 파일 저장, 업로드 시작
- State Change: 연속 촬영 중 → 촬영 완료 → 업로드 진행 중

**Input: 비디오 녹화 시작 버튼 클릭**
- Output: 비디오 녹화 시작, 녹화 중 UI 표시
- State Change: 비디오 녹화 진행 중 상태

**Input: 비디오 녹화 중지 버튼 클릭**
- Output: 비디오 녹화 중지, 파일 저장, 썸네일 생성, 업로드 시작
- State Change: 비디오 녹화 중지 → 저장 중 → 업로드 진행 중

**Input: 오디오 녹음 시작 버튼 클릭**
- Output: 오디오 녹음 시작, 녹음 중 UI 표시
- State Change: 오디오 녹음 진행 중 상태

**Input: 오디오 녹음 중지 버튼 클릭**
- Output: 오디오 녹음 중지, 파일 저장, 업로드 시작
- State Change: 오디오 녹음 중지 → 저장 중 → 업로드 진행 중

**Input: 플래시 토글 버튼 클릭**
- Output: 플래시 켜기/끄기
- State Change: 플래시 상태 토글

**Input: 파일 업로드 진행률 업데이트**
- Output: 업로드 진행률 표시
- State Change: 업로드 진행 상태 업데이트

**Input: 파일 업로드 완료**
- Output: 업로드 완료 상태 표시
- State Change: 업로드 완료 상태

**Input: 파일 업로드 실패**
- Output: 업로드 실패 메시지 표시, 재시도 가능
- State Change: 업로드 실패 상태

**Input: 파일 목록 새로고침**
- Output: 서버에서 파일 목록 재요청, 로컬 파일과 동기화
- State Change: 파일 목록 로딩 상태

**Input: 파일 이름 변경**
- Output: 파일 이름 변경 API 호출, 로컬 파일 이름 변경
- State Change: 파일 이름 변경 진행 중 → 완료

**Input: 파일 삭제**
- Output: 파일 삭제 API 호출, 로컬 파일 삭제
- State Change: 파일 삭제 진행 중 → 완료

**Input: 로그인 상태 변경 (로그인)**
- Output: 대기 중인 파일 업로드 재시도
- State Change: 대기 파일 업로드 재시도 상태

**Input: 작업 ID 변경**
- Output: 해당 작업의 파일 목록 로드
- State Change: 파일 목록 로딩 상태

**Input: 작업 ID 제거 (작업 종료)**
- Output: 업로드 큐 취소, 파일 목록 초기화
- State Change: 파일 목록 초기화 상태

---

### 10. 설정 화면 (SettingFragment)

#### 사용자 행동 및 앱 반응

**Input: 화면 진입**
- Output: 설정 메뉴 표시
- State Change: 설정 화면 표시

**Input: 디바이스 정보 메뉴 클릭**
- Output: 디바이스 정보 화면으로 이동
- State Change: 디바이스 정보 화면으로 전환

**Input: 터치패드 설정 메뉴 클릭**
- Output: 터치패드 설정 화면으로 이동
- State Change: 터치패드 설정 화면으로 전환

**Input: 방향 설정 메뉴 클릭**
- Output: 방향 설정 화면으로 이동
- State Change: 방향 설정 화면으로 전환

---

### 11. 통화 수신 팝업 (Call Popup)

#### 사용자 행동 및 앱 반응

**Input: 통화 수신 Push 수신**
- Output: 통화 수신 팝업 표시 (발신자 이름 표시)
- State Change: 통화 수신 대기 상태

**Input: 통화 수신 팝업에서 "수락" 버튼 클릭**
- Output: 통화 수락 Push 전송, 통화 수락 로그 기록, 화상회의 화면으로 이동
- State Change: 통화 수락 → 화상회의 화면

**Input: 통화 수신 팝업에서 "거절" 버튼 클릭**
- Output: 통화 거절 Push 전송, 통화 거절 로그 기록, 팝업 닫기
- State Change: 통화 거절 완료

**Input: 이미 통화 팝업이 표시된 상태에서 새로운 통화 수신**
- Output: 새로운 통화 자동 거절, 거절 Push 전송
- State Change: 통화 자동 거절 상태

**Input: 앱 종료 상태에서 통화 수신**
- Output: Pending Call 저장, 앱 시작 시 처리
- State Change: Pending Call 저장 상태

---

## Core Business Rules

### 1. 인증 및 로그인 규칙

#### 1.1 자동 로그인 규칙
- **조건**: Refresh Token이 존재하고, 디바이스가 등록되어 있으며, 자동 로그인 설정이 활성화된 경우
- **동작**: 앱 시작 시 자동으로 Refresh Token을 사용하여 로그인 시도
- **성공 시**: 홈 화면으로 이동
- **실패 시**: 로그인 화면으로 이동

#### 1.2 중복 로그인 처리 규칙
- **조건**: 로그인 API 응답 코드가 "1018"인 경우
- **동작**: 중복 로그인 팝업 표시
- **사용자 선택 "확인"**: `allowDuplicateLogin=true` 옵션으로 재로그인 시도
- **사용자 선택 "취소"**: 로그인 취소, 로그인 화면 유지

#### 1.3 QR 코드 로그인 규칙
- **QR 코드 데이터 형식**: JSON 형식, `loginId`, `uuid` 필수
- **자동 로그인**: QR 코드 로그인 성공 시 항상 자동 로그인 설정 (`autoLogin=true`)
- **중복 로그인**: 일반 로그인과 동일하게 처리

#### 1.4 비밀번호 재설정 규칙
- **조건**: 로그인 성공 후 `isPasswordReset=true`인 경우
- **동작**: 비밀번호 변경 화면으로 강제 이동
- **비밀번호 변경 완료**: 로그아웃 처리 후 로그인 화면으로 이동

#### 1.5 로그아웃 규칙
- **작업 일시정지**: 로그아웃 시 현재 진행 중인 작업이 있으면 일시정지 처리
- **사용자 정보 삭제**: `clearUserInfo=true`인 경우 사용자 정보 완전 삭제
- **Push 서비스 중지**: 로그아웃 시 Push 서비스 중지
- **BLE 서비스 중지**: 로그아웃 시 BLE 서비스 중지

---

### 2. 디바이스 등록 규칙

#### 2.1 디바이스 등록 필수 조건
- **QR 코드 스캔**: 디바이스 등록을 위해 QR 코드 스캔 필수
- **QR 코드 데이터 형식**: JSON 형식, `uuid` 필수
- **디바이스 타입**: GLASS 또는 MOBILE 구분

#### 2.2 디바이스 등록 프로세스
1. QR 코드 스캔 → 디바이스 등록 API 호출
2. 등록 성공 → 앱 등록 API 호출
3. 등록 성공 → 브랜딩 이미지 다운로드 (로고, 스플래시)
4. 등록 실패 → 디바이스 등록 팝업 재표시

#### 2.3 디바이스 미등록 시 제한사항
- 로그인 입력 필드 비활성화
- 입력 필드 포커스 시 디바이스 등록 팝업 표시

---

### 3. 통화 (Call) 규칙

#### 3.1 통화 요청 규칙
- **최대 수신자 수**: 10명 제한
- **수신자 필터링**: `sendStatus == 1 || sendStatus == 3`인 사용자만 통화 가능
- **통화 불가 시**: "현재는 통화 가능한 사용자가 없습니다" 메시지 표시

#### 3.2 통화 수신 규칙
- **중복 통화 수신**: 이미 통화 팝업이 표시된 상태에서 새로운 통화 수신 시 자동 거절
- **앱 종료 상태 수신**: Pending Call로 저장, 앱 시작 시 HomeFragment에서 처리
- **디바이스 타입 불일치**: 동일 계정의 다른 디바이스 타입에서 보낸 수신은 무시

#### 3.3 통화 응답 규칙
- **수락 (CONFIRM)**: 통화 수락 Push 전송, 화상회의 화면으로 이동, 통화 수락 로그 기록
- **거절 (REJECT)**: 통화 거절 Push 전송, 통화 거절 로그 기록
- **대기 중 거절**: 대기 목록에서 거절한 사용자 제거, 모든 사용자 거절 시 이전 화면으로 이동

#### 3.4 통화 취소 규칙
- **발신자 취소**: 통화 취소 Push 전송, 통화 사용자 목록 화면으로 이동
- **수신자 취소 수신**: 대기 화면에서 통화 사용자 목록 화면으로 이동

#### 3.5 통화 타임아웃 규칙
- **대기 타임아웃**: 60초 후 자동으로 통화 사용자 목록 화면으로 이동
- **참가 타임아웃**: 10초 내 룸 참가 실패 시 타임아웃 에러 표시

#### 3.6 통화 상태 전환 규칙
```
UserList → Waiting → VideoConference
     ↑         ↓            ↓
     └─────────┴────────────┘
```

---

### 4. 작업 (Work) 규칙

#### 4.1 작업 시작 규칙
- **작업 카드 선택 필수**: 작업 카드가 선택되지 않은 상태에서는 작업 시작 불가
- **작업 ID 생성**: 작업 시작 시 서버에서 작업 ID 발급
- **작업 상태 저장**: 로컬에 작업 상태 저장 (작업 ID, 작업 이름, 작업 클래스 등)

#### 4.2 작업 일시정지 규칙
- **로그아웃 시**: 현재 진행 중인 작업 자동 일시정지
- **작업 상태**: `WORK_NONE` 또는 `WORK_END`가 아닌 경우 일시정지 처리

#### 4.3 작업 재개 규칙
- **작업 목록에서 선택**: 기존 작업 ID로 작업 재개
- **작업 상태 복원**: 저장된 작업 상태 복원

#### 4.4 작업 완료 규칙
- **작업 완료 이벤트**: 서버에서 작업 완료 이벤트 수신 시 작업 목록에서 제거
- **포커스 위치 조정**: 완료된 작업 제거 후 포커스 위치 자동 조정

---

### 5. 미디어 파일 규칙

#### 5.1 파일 명명 규칙
- **사진**: `P_` 접두사 + 타임스탬프
- **비디오**: `V_` 접두사 + 타임스탬프
- **오디오**: `A_` 접두사 + 타임스탬프
- **저장 경로**: `{rootMediaPath}/Task_{workId}/`

#### 5.2 파일 업로드 규칙
- **청크 업로드**: 파일을 청크 단위로 분할하여 업로드
- **진행률 확인**: 서버에서 업로드 진행률 확인 (`checkProgress` API)
- **재시도 로직**: 실패한 청크만 재업로드
- **업로드 큐 관리**: WorkManager를 사용한 백그라운드 업로드

#### 5.3 파일 동기화 규칙
- **로그인 시**: 로컬 파일과 서버 파일 목록 비교, 업로드 필요한 파일 자동 업로드
- **파일 상태**: `REQUEST` 상태인 파일만 업로드, 그 외 파일은 삭제
- **동기화 메시지**: "이전에 저장된 작업 내역을 서버와 동기화 중입니다" 토스트 표시

#### 5.4 파일 삭제 규칙
- **업로드 완료 후**: 업로드 성공한 파일은 로컬에서 삭제 (주석 처리됨, 현재는 유지)
- **업로드 불필요**: 서버에 이미 존재하는 파일은 로컬에서 삭제

#### 5.5 파일 이름 변경 규칙
- **서버 업데이트**: 파일 이름 변경 API 호출
- **로컬 업데이트**: 로컬 파일 이름도 함께 변경
- **캐시 업데이트**: 파일 캐시에 변경된 이름 반영

---

### 6. 권한 관리 규칙

#### 6.1 필수 권한 목록
- **카메라**: 사진 촬영, 비디오 녹화, QR 코드 스캔
- **마이크**: 오디오 녹음, 화상회의
- **전화 상태**: 전화 수신 감지
- **통화 로그 읽기**: 통화 로그 기록
- **통화 응답**: 통화 수신 시 자동 응답
- **포그라운드 서비스**: Push 서비스, BLE 서비스
- **블루투스**: BLE 통신
- **위치**: 블루투스 스캔
- **WiFi**: 네트워크 상태 확인
- **저장소**: 미디어 파일 저장 (Android 12 이하)
- **미디어 읽기**: 미디어 파일 읽기 (Android 13 이상)
- **알림**: Push 알림 표시 (Android 13 이상)

#### 6.2 오버레이 권한 규칙
- **필수 여부**: 필수 권한
- **미승인 시**: 오버레이 권한 요청 팝업 표시
- **설정 이동**: 사용자가 설정 화면으로 이동하여 권한 부여

#### 6.3 권한 거부 처리 규칙
- **일부 권한 거부**: 권한 안내 토스트 표시, 로그인 화면으로 이동
- **영구 거부**: "설정의 앱 알림 정보에서 권한을 허용해 주세요" 메시지 표시

---

### 7. Push 메시지 규칙

#### 7.1 Push 타입별 처리 규칙
- **CALL**: 통화 수신 팝업 표시
- **CANCEL**: 통화 취소 처리, 팝업 닫기
- **REJECT**: 통화 거절 처리, 대기 목록에서 제거
- **CONFIRM**: 통화 수락 처리, 화상회의 화면으로 이동
- **ALERT (LOGOUT)**: 로그아웃 처리
- **NOTICE/SYSTEM_INFO/WORK_ALERT/EVENT**: 포그라운드 시 토스트 표시, 백그라운드 시 대기
- **LOGOUT**: 로그아웃 처리

#### 7.2 Push 서비스 연결 규칙
- **로그인 시**: Push 서비스 시작 (Foreground Service)
- **로그아웃 시**: Push 서비스 중지
- **WiFi 연결 시**: Push 서비스 재연결 시도
- **토큰 갱신 실패**: 로그아웃 처리

#### 7.3 Pending Call 규칙
- **저장 조건**: 앱 종료 상태에서 통화 수신
- **저장 위치**: ViewModel 및 SharedPreferences
- **유효 시간**: 5분 이내의 Pending Call만 유효
- **처리 시점**: HomeFragment로 이동한 후 처리

---

### 8. 네트워크 오류 처리 규칙

#### 8.1 인증 오류 처리
- **상태 코드 400/401**: 인증 오류로 간주
- **에러 코드 1003/1004/1018**: 로그아웃 처리
- **토큰 갱신 실패**: 로그아웃 처리

#### 8.2 네트워크 오류 처리
- **일반 오류**: 에러 메시지 표시, 재시도 가능
- **연결 실패**: "현재 연결이 원활하지 않습니다" 메시지 표시

---

### 9. 작업 로그 기록 규칙

#### 9.1 로그 기록 타입
- **통화 수신**: `CALL_RECEIVE`
- **통화 수락**: `CALL_CONFIRM`
- **통화 거절**: `CALL_REJECT`
- **통화 시작**: `CALL_PHONE_START`
- **메뉴 이동**: `MOVE_MENU`
- **작업 시작**: `WORK_START`
- **작업 재개**: `WORK_CONTINUE`
- **작업 일시정지**: `WORK_PAUSE`
- **작업 종료**: `WORK_END`

#### 9.2 로그 기록 시점
- **통화 관련**: 통화 수신/수락/거절 시 즉시 기록
- **작업 관련**: 작업 상태 변경 시 기록
- **메뉴 이동**: 화면 전환 시 기록 (일부 제외)

---

### 10. 화상회의 규칙

#### 10.1 룸 참가 규칙
- **도메인 기본값**: 도메인이 비어있으면 `https://meet-poc.digicaps.com` 사용
- **참가 타임아웃**: 10초 내 참가 실패 시 타임아웃 처리
- **WebSocket 연결**: 룸 참가 전 WebSocket 연결 필수

#### 10.2 참가자 관리 규칙
- **참가자 추가**: `onNotifyRoomsInfo` 이벤트로 참가자 목록 업데이트
- **참가자 제거**: `onParticipantLeft` 이벤트로 참가자 목록에서 제거
- **참가자 수 표시**: 현재 룸의 참가자 수 표시

#### 10.3 화질 변경 규칙
- **화질 순환**: LOW → STANDARD → HIGH → ULTRA → LOW
- **화질 레벨**: 180p, 360p, 720p, 2160p

#### 10.4 줌 조절 규칙
- **줌 레벨**: 0 (x1.0) ~ 7 (x4.5)
- **줌 단계**: 0.5배씩 증가

#### 10.5 녹화 규칙
- **로컬 녹화**: Jitsi Meet의 로컬 녹화 기능 사용
- **다른 사용자 녹화 감지**: 다른 사용자가 녹화 중임을 감지하여 표시

---

## State Management

### 1. Intro 화면 상태

```kotlin
sealed class IntroState {
    object Idle : IntroState()
    object CheckingPermissions : IntroState()
    object RequestingPermissions : IntroState()
    object CheckingLogin : IntroState()
    object AutoLoginInProgress : IntroState()
    object NavigateToLogin : IntroState()
    object NavigateToHome : IntroState()
}
```

---

### 2. 로그인 화면 상태

```kotlin
sealed class LoginState {
    object Idle : LoginState()
    object DeviceRegistrationRequired : LoginState()
    object DeviceRegistrationInProgress : LoginState()
    object LoginInProgress : LoginState()
    object QrLoginInProgress : LoginState()
    data class LoginSuccess(val requiresPasswordReset: Boolean) : LoginState()
    data class LoginFailure(val errorCode: String?, val message: String?) : LoginState()
    object DuplicateLogin : LoginState()
    object NavigateToHome : LoginState()
    object NavigateToPasswordReset : LoginState()
}
```

---

### 3. 홈 화면 상태

```kotlin
sealed class WaitingCallListUiState {
    object Idle : WaitingCallListUiState()
    object Loading : WaitingCallListUiState()
    data class Success(val list: List<OnGoingItem>) : WaitingCallListUiState()
    data class Error(val message: String) : WaitingCallListUiState()
}
```

---

### 4. 작업 목록 화면 상태

```kotlin
sealed class WorkHistoryState {
    object Idle : WorkHistoryState()
    object Loading : WorkHistoryState()
    data class Success(val data: WorkStatusItem.TaskList) : WorkHistoryState()
    data class Error(val message: String?) : WorkHistoryState()
}
```

---

### 5. 작업 기록 컨테이너 상태

```kotlin
data class WorkRecordContainerState(
    val currentTab: WorkRecordTab = WorkRecordTab.CARD,
    val isLoading: Boolean = false,
    val error: String? = null
)
```

---

### 6. 화상회의 상태

```kotlin
sealed class ConferenceState {
    object None : ConferenceState()
    object UserList : ConferenceState()
    data class Waiting(
        val senderName: String,
        val receiveUser: MutableList<ContactInfo>
    ) : ConferenceState()
    data class VideoConference(val roomData: RoomData) : ConferenceState()
}
```

```kotlin
data class VideoConferenceUIState(
    val isMuted: Boolean = false,
    val isVideoOn: Boolean = true,
    val isScreenOn: Boolean = false,
    val isLightOn: Boolean = false,
    val isRecording: Boolean = false,
    val isOtherUserRecording: Boolean = false,
    val isCalling: Boolean = false,
    val currentVolume: Int = 7,
    val currentQuality: VIDEO_QUALITY = VIDEO_QUALITY.STANDARD,
    val currentZoom: Int = 0,
    val isExpandable: Boolean = false,
    val isMenuBarVisible: Boolean = false,
    val isParticipantVisible: Boolean = true,
    val isExpandableParticipantVisible: Boolean = false,
    val isUsePointer: Boolean = false,
    val isSeekBarVisible: Boolean = false,
    val currentSeekBarType: SeekBarType = SeekBarType.NONE,
    val chatViewMode: ChatViewMode = ChatViewMode.NONE
)
```

```kotlin
sealed class NavigationEvent {
    object ToUserList : NavigationEvent()
    data class ToWaiting(val clearBackStack: Boolean = false) : NavigationEvent()
    data class ToVideo(
        val clearBackStack: Boolean = false,
        val removeWaitingFromStack: Boolean = false
    ) : NavigationEvent()
    object Back : NavigationEvent()
}
```

---

### 7. 미디어 녹화 상태

```kotlin
sealed class PhotoCaptureState {
    object Idle : PhotoCaptureState()
    object Capturing : PhotoCaptureState()
    data class Captured(val result: MediaFileInfo) : PhotoCaptureState()
    data class Error(val message: String) : PhotoCaptureState()
}
```

```kotlin
sealed class VideoRecordingState {
    object Idle : VideoRecordingState()
    object Recording : VideoRecordingState()
    data class InProgress(val fileName: String) : VideoRecordingState()
    object Stopping : VideoRecordingState()
    data class Recorded(val result: MediaFileInfo) : VideoRecordingState()
    data class Error(val message: String) : VideoRecordingState()
    object Cancel : VideoRecordingState()
}
```

```kotlin
sealed class AudioRecordingState {
    object Idle : AudioRecordingState()
    object Recording : AudioRecordingState()
    object Stopping : AudioRecordingState()
    data class Recorded(val result: MediaFileInfo) : AudioRecordingState()
    data class Error(val message: String) : AudioRecordingState()
    object Cancel : AudioRecordingState()
}
```

```kotlin
sealed class UploadState {
    object Idle : UploadState()
    data class Progress(val progress: Int) : UploadState()
    data class Success(val fileUpload: MediaFileItem.FileUpload) : UploadState()
    data class Error(val message: String) : UploadState()
    object Cancel : UploadState()
}
```

```kotlin
data class UploadQueueState(
    val totalCount: Int = 0,
    val pendingCount: Int = 0,
    val runningCount: Int = 0,
    val succeededCount: Int = 0,
    val failedCount: Int = 0
) {
    val isInProgress: Boolean
        get() = pendingCount > 0 || runningCount > 0
}
```

```kotlin
sealed class CameraProviderInitState {
    object NotInitialized : CameraProviderInitState()
    object Initializing : CameraProviderInitState()
    object Initialized : CameraProviderInitState()
    data class Failed(val error: String) : CameraProviderInitState()
}
```

---

### 8. 파일 목록 상태

```kotlin
sealed class ApiResult<out T> {
    object Empty : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
    data class Success<T>(val value: T) : ApiResult<T>()
    data class Error(val message: String?, val code: String? = null) : ApiResult<Nothing>()
    data class Exception(val throwable: Throwable) : ApiResult<Nothing>()
}
```

---

### 9. 통화 수신 팝업 상태

```kotlin
data class CallPopupState(
    val isVisible: Boolean = false,
    val popupType: CommandConst.MenuType? = null,
    val message: String = "",
    val senderName: String = ""
)
```

---

### 10. 작업 상태

```kotlin
enum class WorkRecordState {
    WORK_NONE,
    WORK_START,
    WORK_PAUSE,
    WORK_CONTINUE,
    WORK_END
}
```

---

### 11. 로그인 상태

```kotlin
val isLoggedIn: StateFlow<Boolean>
```

---

### 12. 네트워크 상태

```kotlin
sealed class WifiState {
    object Disconnect : WifiState()
    data class Connect(val ssid: String?) : WifiState()
}
```

---

### 13. Push 서비스 상태

```kotlin
data class PushServiceState(
    val isConnected: Boolean = false,
    val isReconnecting: Boolean = false
)
```

---

### 14. 대기 타임아웃 상태

```kotlin
val waitingTimeout: StateFlow<Boolean>
```

---

### 15. Pending Call 상태

```kotlin
val hasPendingCall: StateFlow<Boolean>
```

---

## 참고사항

### 플랫폼 종속성 제거를 위한 고려사항

1. **UI 프레임워크 독립성**: 모든 상태와 비즈니스 로직은 UI 프레임워크(Android View, Compose 등)와 독립적으로 설계되어야 합니다.

2. **플랫폼 API 추상화**: 카메라, 마이크, 파일 시스템 등 플랫폼별 API는 인터페이스로 추상화하여 구현체를 교체 가능하도록 해야 합니다.

3. **네비게이션 추상화**: 화면 전환 로직은 플랫폼별 네비게이션 시스템과 독립적으로 설계되어야 합니다.

4. **상태 관리 일관성**: 모든 화면의 상태는 동일한 패턴(StateFlow, Sealed Class 등)으로 관리하여 일관성을 유지해야 합니다.

5. **비즈니스 로직 분리**: UI 로직과 비즈니스 로직을 명확히 분리하여 테스트 가능성을 높여야 합니다.
