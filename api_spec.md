# SmartGlass Mobile API 연동 규격서

## 목차
1. [Base URL 및 인증](#base-url-및-인증)
2. [Endpoint Definition](#endpoint-definition)
3. [Data Models](#data-models)
4. [Mapping Rules](#mapping-rules)

---

## Base URL 및 인증

### Base URLs
- **Login/Account**: `https://xr-service.digicaps.com:443/`
- **Meeting**: `https://xr-service.digicaps.com:443/`
- **Push**: `wss://xr-service.digicaps.com:443` (WebSocket)
- **Request Push**: `https://xr-service.digicaps.com:443/`
- **Work Status**: `https://xr-service.digicaps.com:443/`
- **Media File**: `https://xr-service.digicaps.com:443/`
- **Chunk Upload**: `https://xr-service.digicaps.com:443/`
- **Work Record**: `https://xr-service.digicaps.com:443/`

### 인증 헤더
- **Authorization**: `Bearer {accessToken}`
- **Content-Type**: `application/json` (일반 요청), `multipart/form-data` (파일 업로드)

### 토큰 갱신
- **자동 갱신**: 400/401 응답 시 `refreshToken`으로 자동 갱신 시도
- **갱신 실패 시**: 로그아웃 처리 및 네트워크 에러 브로드캐스트

---

## Endpoint Definition

### 1. 인증 및 계정 관리 (AccountService)

#### 1.1 로그인
- **Method**: `POST`
- **Path**: `/api/auth/login`
- **Headers**: 
  - `Content-Type: application/json`
- **Request Body**: `LoginData`
- **Response**: `LoginResponse`

#### 1.2 QR 코드 로그인
- **Method**: `POST`
- **Path**: `/api/auth/qr-login`
- **Headers**: 
  - `Content-Type: application/json`
- **Request Body**: `LoginQrData`
- **Response**: `LoginResponse`

#### 1.3 로그아웃
- **Method**: `POST`
- **Path**: `/api/auth/logout`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
  - `Content-Type: application/json`
- **Request Body**: 없음
- **Response**: `LogoutResponse`

#### 1.4 토큰 갱신
- **Method**: `POST`
- **Path**: `/api/auth/token/refresh`
- **Headers**: 
  - `Content-Type: application/json`
- **Request Body**: `RefreshData`
- **Response**: `LoginResponse`

#### 1.5 아이디 찾기
- **Method**: `POST`
- **Path**: `/api/auth/find-id`
- **Headers**: 
  - `Content-Type: application/json`
- **Request Body**: `FindIdData`
- **Response**: `FindIdResponse`

#### 1.6 비밀번호 찾기
- **Method**: `POST`
- **Path**: `/api/auth/find-password`
- **Headers**: 
  - `Content-Type: application/json`
- **Request Body**: `FindPasswordData`
- **Response**: `FindPasswordResponse`

#### 1.7 비밀번호 변경
- **Method**: `PUT`
- **Path**: `/api/users/password`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
  - `Content-Type: application/json`
- **Request Body**: `ChangePasswordData`
- **Response**: `ChangePasswordResponse`

#### 1.8 디바이스 등록
- **Method**: `POST`
- **Path**: `/api/device/regist/device`
- **Headers**: 
  - `Content-Type: application/json`
- **Request Body**: `RegisterDeviceData`
- **Response**: `RegisterDeviceResponse`

#### 1.9 앱 등록
- **Method**: `POST`
- **Path**: `/api/device/regist/app`
- **Headers**: 
  - `Content-Type: application/json`
- **Request Body**: `RegisterAppData`
- **Response**: `RegisterAppResponse`

#### 1.10 내 정보 조회
- **Method**: `GET`
- **Path**: `/api/users/me`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
- **Request Body**: 없음
- **Response**: `MyInfoResponse`

---

### 2. Push 및 통화 (RequestPushService)

#### 2.1 Push 전송
- **Method**: `POST`
- **Path**: `/api/push/send`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
  - `Content-Type: application/json`
- **Request Body**: `PushCallRequest`
- **Response**: `RequestCallResponse`

#### 2.2 사용자 상태 목록 조회 (Polling)
- **Method**: `GET`
- **Path**: `/api/push/polling/status`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
- **Query Parameters**:
  - `systemType`: String (필수)
  - `department`: String (필수)
  - `timeout`: Int (필수)
- **Response**: `UserStatusListResponse`

#### 2.3 채팅 입장 (WebSocket)
- **Method**: `POST`
- **Path**: `/chat`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
- **Query Parameters**:
  - `token`: String? (선택)
  - `room_id`: String? (선택)
  - `room_user_id`: String? (선택)
  - `room_nm`: String? (선택)
- **Response**: String (WebSocket URL)

#### 2.4 진행 중인 통화 목록 조회 (Polling)
- **Method**: `GET`
- **Path**: `/api/push/chatrooms/polling/active`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
- **Query Parameters**:
  - `userId`: String (필수)
  - `timeout`: Int (필수)
  - `sortOrder`: String (필수)
- **Response**: `OnGoingListResponse`

#### 2.5 Push 메시지 목록 조회
- **Method**: `GET`
- **Path**: `/api/push/receive-msgs`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
- **Query Parameters**: Map<String, String> (동적 파라미터)
- **Response**: `PushListResponse`

#### 2.6 Push 메시지 읽음 처리
- **Method**: `PUT`
- **Path**: `/api/push/receive-msgs`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
  - `Content-Type: application/json`
- **Request Body**: `PushReadRequest`
- **Response**: `PushReadResponse`

---

### 3. 회의 및 사용자 (MeetingService)

#### 3.1 통화 룸 정보 조회
- **Method**: `GET`
- **Path**: `/api/chat/rooms`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
- **Request Body**: 없음
- **Response**: `CallResponse`

#### 3.2 채팅 사용자 목록 조회
- **Method**: `GET`
- **Path**: `/api/users`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
- **Query Parameters**:
  - `searchType`: String (필수)
  - `searchText`: String (필수)
  - `page`: String (필수)
  - `size`: String (필수)
  - `sort`: String (필수)
  - `isAll`: Boolean (필수)
- **Response**: `ChatUserListResponse`

#### 3.3 작업자 검색
- **Method**: `GET`
- **Path**: `/api/users/search`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
- **Query Parameters**: Map<String, String> (동적 파라미터)
- **Query Parameters**:
  - `sort`: List<String>? (선택)
- **Response**: `WorkerResponse`

---

### 4. 작업 상태 (WorkStatusService)

#### 4.1 작업 상태 설정
- **Method**: `POST`
- **Path**: `/api/work/status/set`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
  - `Content-Type: application/json`
- **Request Body**: `WorkStatus.SetData`
- **Response**: `WorkStatusResponse`

#### 4.2 작업 기록
- **Method**: `POST`
- **Path**: `/api/work/record`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
  - `Content-Type: application/json`
- **Request Body**: `WorkStatus.RecordData`
- **Response**: 없음 (200 OK)

#### 4.3 작업 목록 조회
- **Method**: `GET`
- **Path**: `/api/work/list`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
- **Query Parameters**:
  - `work_date_start`: String? (선택)
  - `work_date_end`: String? (선택)
  - `order`: String? (선택, 기본값: "asc")
  - `active`: Boolean (필수)
- **Response**: `WorkStatusResponse`

#### 4.4 작업 일괄 데이터 요청
- **Method**: `POST`
- **Path**: `/api/work/bulk`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
  - `Content-Type: application/json`
- **Request Body**: `WorkStatus.BulkData`
- **Response**: `WorkStatusResponse`

---

### 5. 작업 카드 (WorkCardService)

#### 5.1 작업 카드 생성
- **Method**: `POST`
- **Path**: `/api/work`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
  - `Content-Type: application/json`
- **Request Body**: `WorkCardRequest`
- **Response**: `WorkCardResponse`

#### 5.2 작업 카드 상태 변경
- **Method**: `PUT`
- **Path**: `/api/work/{workId}/change-status`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
  - `Content-Type: application/json`
- **Path Parameters**:
  - `workId`: String (필수)
- **Request Body**: `WorkCardStatusChangeRequest`
- **Response**: `WorkCardStatusChangeResponse`

#### 5.3 작업 카드 목록 조회
- **Method**: `GET`
- **Path**: `/api/work`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
- **Query Parameters**: Map<String, String> (동적 파라미터)
- **Response**: `WorkCardListResponse`

#### 5.4 승강기 정보 등록
- **Method**: `POST`
- **Path**: `/api/work/{workId}/elevator`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
  - `Content-Type: application/json`
- **Path Parameters**:
  - `workId`: String (필수)
- **Request Body**: `ElevatorInfoRequest`
- **Response**: `ElevatorInfoResponse`

---

### 6. 미디어 파일 (MediaFileService)

#### 6.1 파일 목록 조회
- **Method**: `GET`
- **Path**: `/api/file/file_list`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
- **Query Parameters**:
  - `workId`: String? (선택)
  - `mediaFilter`: List<String>? (선택)
  - `checklistId`: String? (선택)
  - `questionId`: String? (선택)
  - `size`: Int? (선택)
- **Response**: `MediaFileResponse`

#### 6.2 페이지네이션 파일 목록 조회
- **Method**: `GET`
- **Path**: `/api/file/file_list`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
- **Query Parameters**:
  - `workId`: String? (선택)
  - `mediaFilter`: Array<String>? (선택)
  - `sort`: Array<String>? (선택)
  - `size`: Int? (선택)
  - `page`: Int? (선택)
- **Response**: `MediaFileResponse`

#### 6.3 단일 파일 업로드
- **Method**: `POST`
- **Path**: `/api/file/upload/single`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
  - `Content-Type: multipart/form-data`
- **Request Body**:
  - `file`: MultipartBody.Part? (선택)
  - `base64Data`: RequestBody? (선택)
  - `fileName`: RequestBody? (선택)
  - `groupId`: RequestBody (필수)
  - `groupType`: RequestBody (필수)
  - `fileId`: RequestBody? (선택)
  - `metadata`: RequestBody? (선택)
- **Response**: `MediaFileResponse`

#### 6.4 파일 이름 변경
- **Method**: `PUT`
- **Path**: `/api/file/edit_file_name`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
  - `Content-Type: application/json`
- **Request Body**: `MediaFile.EditFileNameData`
- **Response**: `MediaFileResponse`

#### 6.5 파일 삭제
- **Method**: `DELETE`
- **Path**: `/api/file/delete_file`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
- **Query Parameters**:
  - `fileIds`: List<String> (필수)
- **Response**: `MediaFileResponse`

#### 6.6 ZIP 파일 다운로드
- **Method**: `POST`
- **Path**: `/api/file/download/zip`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
  - `Content-Type: application/json`
- **Request Body**: `MediaFile.DownloadZipData`
- **Response**: `MediaFileResponse`

---

### 7. 청크 업로드 (MediaFileChunkService)

#### 7.1 청크 파일 업로드
- **Method**: `POST`
- **Path**: `/api/file/upload/chunk`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
  - `Content-Type: multipart/form-data`
- **Request Body**:
  - `file`: MultipartBody.Part (필수)
  - `fileId`: RequestBody (필수)
  - `chunkIndex`: RequestBody (필수)
  - `totalChunks`: RequestBody (필수)
  - `fileName`: RequestBody (필수)
  - `groupId`: RequestBody (필수)
  - `groupType`: RequestBody (필수)
  - `totalTileSize`: RequestBody (필수, 실제로는 totalFileSize)
  - `checklistId`: RequestBody (필수)
  - `questionId`: RequestBody (필수)
- **Response**: `MediaFileResponse`

#### 7.2 청크 업로드 진행률 확인
- **Method**: `POST`
- **Path**: `/api/file/upload/chunk/progress`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
  - `Content-Type: multipart/form-data`
- **Request Body**:
  - `fileName`: RequestBody (필수)
  - `fileId`: RequestBody? (선택)
  - `groupId`: RequestBody (필수)
  - `groupType`: RequestBody (필수)
  - `totalChunks`: RequestBody (필수)
  - `totalFileSize`: RequestBody (필수)
  - `thumbnail`: MultipartBody.Part? (선택)
  - `checklistId`: RequestBody (필수)
  - `questionId`: RequestBody (필수)
- **Response**: `MediaFileResponse`

---

### 8. 워크플로우 (WorkflowService)

#### 8.1 워크플로우 코드 조회
- **Method**: `GET`
- **Path**: `/api/workflow/code/{codeType}`
- **Headers**: 
  - `Authorization: Bearer {accessToken}`
- **Path Parameters**:
  - `codeType`: String (필수)
- **Response**: `WorkTypeResponseDto`

---

## Data Models

### 1. 인증 및 계정 관리

#### LoginData (Request)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| loginId | String | No | 로그인 ID |
| password | String | No | 비밀번호 |
| deviceType | String | No | 디바이스 타입 (GLASS, MOBILE_NEO) |
| platform | String | No | 플랫폼 (기본값: "android") |
| allowDuplicateLogin | Boolean | No | 중복 로그인 허용 여부 |
| appId | String | No | 앱 ID |

#### LoginQrData (Request)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| loginId | String | Yes | 로그인 ID |
| uuid | String | Yes | 디바이스 UUID |
| deviceType | String | Yes | 디바이스 타입 |
| platform | String | Yes | 플랫폼 (기본값: "android") |
| allowDuplicateLogin | Boolean | Yes | 중복 로그인 허용 여부 |
| appId | String | Yes | 앱 ID |

#### RefreshData (Request)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| refreshToken | String | No | 리프레시 토큰 |
| bypassRefreshExpiry | Boolean | No | 리프레시 만료 우회 (기본값: true) |

#### LoginResponse (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | No | 응답 코드 |
| errorKey | String | Yes | 에러 키 |
| message | String | Yes | 메시지 |
| data | UserData | Yes | 사용자 데이터 |

#### UserData (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| token | Token | Yes | 토큰 정보 |
| user | User | Yes | 사용자 정보 |
| isPasswordReset | Boolean | Yes | 비밀번호 재설정 필요 여부 |

#### Token (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| grantType | String | Yes | 토큰 타입 |
| accessToken | String | Yes | 액세스 토큰 |
| refreshToken | String | Yes | 리프레시 토큰 |
| accessTokenExpiresIn | Long | Yes | 액세스 토큰 만료 시간 (초) |
| refreshTokenExpiresIn | Long | Yes | 리프레시 토큰 만료 시간 (초) |

#### User (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| userNo | Int | Yes | 사용자 번호 |
| userId | String | Yes | 사용자 ID |
| loginId | String | Yes | 로그인 ID |
| deptName | String | Yes | 부서명 |
| ognzName | String | Yes | 조직명 |
| positionName | String | Yes | 직책명 |
| userName | String | Yes | 사용자 이름 |
| role | Role | Yes | 역할 정보 |

#### Role (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| roleGroupId | Int | Yes | 역할 그룹 ID |
| roleGroupName | String | Yes | 역할 그룹 이름 |

#### RegisterDeviceData (Request)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| uuid | String | Yes | 디바이스 UUID |
| deviceId | String | Yes | 디바이스 ID |
| deviceType | String | Yes | 디바이스 타입 (GLASS, MOBILE) |
| activeStatus | Boolean | No | 활성 상태 |

#### RegisterDeviceResponse (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | No | 응답 코드 |
| data | DeviceData | Yes | 디바이스 데이터 |

#### DeviceData (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| device | Device | Yes | 디바이스 정보 |

#### Device (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| deviceId | String | Yes | 디바이스 ID |
| deviceName | String | Yes | 디바이스 이름 |
| modelName | String | Yes | 모델명 |
| serialNumber | String | Yes | 시리얼 번호 |
| osVersion | String | Yes | OS 버전 |
| osType | String | Yes | OS 타입 |
| isActive | Boolean | Yes | 활성 상태 |

#### RegisterAppData (Request)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| deviceId | String | Yes | 디바이스 ID |
| appId | String | Yes | 앱 ID |
| appType | String | Yes | 앱 타입 (GLASS, MOBILE) |
| appVersion | String | Yes | 앱 버전 |

#### RegisterAppResponse (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | No | 응답 코드 |
| data | AppData | Yes | 앱 데이터 |

#### AppData (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| appInfo | AppInfo | Yes | 앱 정보 |

#### AppInfo (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| deviceId | String | Yes | 디바이스 ID |
| appId | String | Yes | 앱 ID |
| appType | String | Yes | 앱 타입 |
| appVersion | String | Yes | 앱 버전 |

#### LogoutResponse (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | No | 응답 코드 |

#### FindIdData (Request)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| (구조 확인 필요) | - | - | - |

#### FindIdResponse (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | No | 응답 코드 |
| data | FindIdData | Yes | 아이디 찾기 데이터 |

#### FindIdData (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| loginId | String | Yes | 로그인 ID |
| userName | String | Yes | 사용자 이름 |
| createDate | String | Yes | 생성일 |

#### FindPasswordData (Request)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| (구조 확인 필요) | - | - | - |

#### FindPasswordResponse (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | No | 응답 코드 |
| data | FindPasswordData | Yes | 비밀번호 찾기 데이터 |

#### FindPasswordData (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| message | String | Yes | 메시지 |

#### ChangePasswordData (Request)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| (구조 확인 필요) | - | - | - |

#### ChangePasswordResponse (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | No | 응답 코드 |

#### MyInfoResponse (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | No | 응답 코드 |
| data | MyInfoData | Yes | 내 정보 데이터 |

#### MyInfoData (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| userNo | Int | Yes | 사용자 번호 |
| loginId | String | Yes | 로그인 ID |
| userName | String | Yes | 사용자 이름 |
| ognz | OgnzInfo | Yes | 조직 정보 |
| dept | DeptInfo | Yes | 부서 정보 |
| position | PositionInfo | Yes | 직책 정보 |
| role | RoleInfo | Yes | 역할 정보 |
| phoneNo | String | Yes | 전화번호 |
| telNo | String | Yes | 회사 전화번호 |
| email | String | Yes | 이메일 |
| status | String | Yes | 상태 |
| lastLoginDt | String | Yes | 마지막 로그인 일시 |
| createDt | String | Yes | 생성일시 |
| updateDt | String | Yes | 수정일시 |
| lockYn | String | Yes | 잠금 여부 |

#### OgnzInfo (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| ognzId | Int | Yes | 조직 ID |
| ognzName | String | Yes | 조직 이름 |

#### DeptInfo (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| deptId | Int | Yes | 부서 ID |
| deptName | String | Yes | 부서 이름 |

#### PositionInfo (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| positionId | Int | Yes | 직책 ID |
| positionName | String | Yes | 직책 이름 |

#### RoleInfo (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| roleGroupId | Int | Yes | 역할 그룹 ID |
| roleGroupName | String | Yes | 역할 그룹 이름 |

---

### 2. Push 및 통화

#### PushCallRequest (Request)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| userId | String | No | 요청자 로그인 ID |
| receiveId | List<String> | No | 수신자들 로그인 ID 목록 (최대 10명) |
| reserveDate | String | Yes | 예약 발송 시간 |
| deviceType | List<String> | No | 전송할 디바이스 타입 (예: ["ALL"]) |
| systemType | String | No | 전송할 시스템 (기본값: "SMART_GLASS") |
| department | String | Yes | 소속 그룹 |
| messageType | String | No | Push 메시지 유형 (CALL, CONFIRM, REJECT, CANCEL 등) |
| message | RequestPushMessageData | Yes | 메시지 데이터 |
| priority | Int | No | 우선순위 (기본값: 1) |

#### RequestPushMessageData (Request)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| title | String | Yes | 제목 |
| contents | String | Yes | 내용 |
| actionType | String | Yes | 액션 타입 |
| actionData | RequestPushActionData | Yes | 액션 데이터 |

#### RequestPushActionData (Request)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| domain | String | Yes | 도메인 |
| roomName | String | Yes | 룸 이름 |
| receiverId | List<String> | Yes | 수신자 ID 목록 |
| mainScreenNickName | String | Yes | 메인 화면 닉네임 |
| deviceType | String | Yes | 디바이스 타입 |
| isMultiCall | String | Yes | 다중 통화 여부 ("Y", "N") |
| senderName | String | Yes | 발신자 이름 |
| workId | String | Yes | 작업 ID |
| workName | String | Yes | 작업 이름 |
| status | Int | Yes | 상태 |
| workClass | String | Yes | 작업 클래스 |
| isInvite | Boolean | Yes | 초대 여부 (기본값: false) |

#### RequestCallResponse (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | Yes | 응답 코드 |
| data | RequestPushRoomData | Yes | Push 룸 데이터 |

#### RequestPushRoomData (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| messageId | String | Yes | 메시지 ID |
| registTime | String | Yes | 등록 시간 |
| receiveList | List<RequestCallReceiveData> | Yes | 수신자 목록 |

#### RequestCallReceiveData (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| receiveId | String | Yes | 수신자 ID |
| deviceType | String | Yes | 디바이스 타입 |
| sendStatus | Int | Yes | 전송 상태 (1: 성공, 3: 대기, 기타: 실패) |

#### UserStatusListResponse (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | String | Yes | 성공 여부 |
| code | String | Yes | 응답 코드 |
| data | UserStateItem | Yes | 사용자 상태 데이터 |

#### UserStateItem (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| userList | ArrayList<UserState> | Yes | 사용자 상태 목록 |

#### UserState (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| loginId | String | Yes | 로그인 ID |
| systemType | String | Yes | 사용자 시스템 |
| department | String | Yes | 부서 |
| status | String | Yes | 사용자 상태 (0: 로그아웃, 1: 로그인, 2: 통화중) |
| deviceType | Array<String?> | No | 로그인된 디바이스 타입 배열 |
| deviceDetail | Array<DeviceDetailItem> | Yes | 디바이스 상세 정보 |

#### DeviceDetailItem (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| deviceType | String | Yes | 디바이스 타입 |
| status | Int | Yes | 상태 |

#### OnGoingListResponse (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | String | Yes | 성공 여부 |
| code | String | Yes | 응답 코드 |
| data | ArrayList<OnGoingRoomItem> | Yes | 진행 중인 룸 목록 |

#### OnGoingRoomItem (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| roomNm | String | Yes | 룸 이름 |
| status | Int | Yes | 상태 |
| callDirection | String | Yes | 통화 방향 |
| workId | String | Yes | 작업 ID |
| workNm | String | Yes | 작업 이름 |
| workClass | String | Yes | 작업 클래스 |
| lastJoinDt | String | Yes | 마지막 참가 일시 |
| createdBy | CreatedByItem | Yes | 생성자 정보 |
| createdDt | String | Yes | 생성 일시 |
| participants | ArrayList<ParticipantItem> | Yes | 참가자 목록 |

#### CreatedByItem (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| userId | String | Yes | 사용자 ID |
| userName | String | Yes | 사용자 이름 |
| department | String | Yes | 부서 |

#### ParticipantItem (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| userId | String | Yes | 사용자 ID |
| roomUserId | String | Yes | 룸 사용자 ID |
| userName | String | Yes | 사용자 이름 |
| deviceType | String | Yes | 디바이스 타입 |
| department | String | Yes | 부서 |

#### PushListResponse (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | Yes | 응답 코드 |
| data | RequestPushListData | Yes | Push 목록 데이터 |

#### RequestPushListData (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| unReadMsgCount | Long | Yes | 읽지 않은 메시지 수 |
| messageList | List<RequestPushListItemData> | Yes | 메시지 목록 |

#### RequestPushListItemData (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| messageId | String | Yes | 메시지 ID |
| sendTime | String | Yes | 전송 시간 |
| senderId | String | Yes | 발신자 ID |
| result | String | Yes | 결과 |
| readTime | String | Yes | 읽은 시간 |
| messageType | String | Yes | 메시지 타입 |
| title | String | Yes | 제목 |
| contents | String | Yes | 내용 |
| actionType | String | Yes | 액션 타입 |
| actionData | ActionDataBlock | Yes | 액션 데이터 |

#### ActionDataBlock (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| domain | String | Yes | 도메인 |
| roomName | String | Yes | 룸 이름 |
| receiverId | List<String> | Yes | 수신자 ID 목록 |
| senderName | String | Yes | 발신자 이름 |
| mainScreenNickName | String | Yes | 메인 화면 닉네임 |
| deviceType | String | Yes | 디바이스 타입 |
| isMultiCall | String | Yes | 다중 통화 여부 |
| workId | String | Yes | 작업 ID |
| workName | String | Yes | 작업 이름 |
| status | Int | Yes | 상태 |
| workClass | String | Yes | 작업 클래스 |
| isInvite | Boolean | Yes | 초대 여부 |

#### PushReadRequest (Request)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| (구조 확인 필요) | - | - | - |

#### PushReadResponse (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | Yes | 응답 코드 |
| data | Any | Yes | 데이터 (구조 미정) |

---

### 3. 회의 및 사용자

#### CallResponse (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | String | Yes | 성공 여부 |
| code | String | Yes | 응답 코드 |
| data | CallRoomData | Yes | 통화 룸 데이터 |

#### CallRoomData (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| domain | String | Yes | 도메인 |
| roomName | String | Yes | 룸 이름 |
| displayName | String | Yes | 표시 이름 |

#### ChatUserListResponse (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | String | Yes | 성공 여부 |
| code | String | Yes | 응답 코드 |
| data | ChatUserItem | Yes | 채팅 사용자 데이터 |

#### ChatUserItem (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| userList | ArrayList<ChatUserContentItem> | Yes | 사용자 목록 |
| page | ChatListPage | Yes | 페이지 정보 |

#### ChatUserContentItem (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| seq | Int | No | 순번 (기본값: 0) |
| userNo | Int | Yes | 사용자 고유 ID |
| loginId | String | Yes | 로그인 ID |
| userName | String | Yes | 사용자 이름 |
| ognzName | String | Yes | 회사 이름 |
| deptName | String | Yes | 부서명 |
| positionName | String | Yes | 직책 |
| roleGroupName | String | Yes | 권한 그룹 |
| phoneNo | String | Yes | 전화번호 |
| email | String | Yes | 이메일 |
| status | String | Yes | 상태 |
| createDt | String | Yes | 생성일시 |
| updateDt | String | Yes | 수정일시 |

#### ChatListPage (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| size | Int | No | 요청당 반환된 건수 (기본값: 0) |
| number | Int | No | 현재 페이지 번호 (기본값: 0) |
| totalElements | Int | No | 전체 데이터 건수 (기본값: 0) |
| totalPages | Int | No | 전체 페이지 수 (기본값: 0) |

#### WorkerResponse (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | String | Yes | 성공 여부 |
| code | String | Yes | 응답 코드 |
| data | WorkerList | Yes | 작업자 목록 |

#### WorkerList (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| userList | ArrayList<WorkerItem> | Yes | 사용자 목록 |
| page | ChatListPage | Yes | 페이지 정보 |

#### WorkerItem (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| seq | Int | Yes | 순번 |
| userNo | Int | Yes | 사용자 고유 ID |
| loginId | String | Yes | 로그인 ID |
| userName | String | Yes | 사용자 이름 |
| ognzName | String | Yes | 회사 이름 |
| deptName | String | Yes | 부서명 |
| positionName | String | Yes | 직책 |
| roleGroupName | String | Yes | 권한 그룹 |
| phoneNo | String | Yes | 전화번호 |
| email | String | Yes | 이메일 |
| isFavorite | Boolean | Yes | 즐겨찾기 여부 |

---

### 4. 작업 상태

#### WorkStatus.SetData (Request)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| (구조 확인 필요) | - | - | - |

#### WorkStatus.RecordData (Request)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| (구조 확인 필요) | - | - | - |

#### WorkStatus.BulkData (Request)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| (구조 확인 필요) | - | - | - |

#### WorkStatusResponse (Response)
- Sealed Class로 구현됨
- **Success**: 작업 상태 설정 성공
- **Failure**: 작업 상태 설정 실패
- **TaskList**: 작업 목록 조회 성공
- **Unknown**: 알 수 없는 응답

#### WorkStatusResponse.Success (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | No | 응답 코드 |
| data | Data | Yes | 작업 데이터 |

#### WorkStatusResponse.Success.Data (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| workId | String | Yes | 작업 ID |

#### WorkStatusResponse.Failure (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | No | 응답 코드 |
| message | String | No | 에러 메시지 |
| status | Int | No | HTTP 상태 코드 |

#### WorkStatusResponse.TaskList (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | No | 응답 코드 |
| data | TaskListData | No | 작업 목록 데이터 |

#### WorkStatusResponse.TaskList.TaskListData (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| workList | List<TaskItem> | No | 작업 목록 |

#### WorkStatusResponse.TaskList.TaskItem (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| workId | String | No | 작업 ID |
| workName | String | No | 작업 이름 |
| workerId | String | No | 작업자 ID |
| workerName | String | No | 작업자 이름 |
| status | TaskStatus | No | 작업 상태 (enum) |
| workStartTime | String | No | 작업 시작 시간 (yyyy-MM-dd HH:mm:ss.SSS) |
| workEndTime | String | Yes | 작업 종료 시간 (yyyy-MM-dd HH:mm:ss.SSS) |

---

### 5. 작업 카드

#### WorkCardRequest (Request)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| workName | String | No | 작업 이름 |
| workExplanation | String | No | 작업 설명 |
| workClass | String | No | 작업 클래스 |
| shareList | List<ShareWorker> | No | 공유 작업자 목록 |

#### WorkCardRequest.ShareWorker (Request)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| workerId | String | No | 작업자 ID |
| workerName | String | No | 작업자 이름 |
| workerDeptName | String | No | 작업자 부서명 |

#### WorkCardResponse (Response)
- Sealed Class로 구현됨
- **Success**: 작업 카드 생성/수정 성공
- **Failure**: 작업 카드 생성/수정 실패
- **Unknown**: 알 수 없는 응답

#### WorkCardResponse.Success (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | No | 응답 코드 |
| data | Data | Yes | 작업 카드 데이터 |

#### WorkCardResponse.Success.Data (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| workId | String | Yes | 작업 ID |

#### WorkCardResponse.Failure (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | No | 응답 코드 |
| message | String | No | 에러 메시지 |
| status | Int | No | HTTP 상태 코드 |

#### WorkCardStatusChangeRequest (Request)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| status | String | No | 상태 |
| forceStopCause | String | Yes | 강제 중지 사유 |

#### WorkCardStatusChangeResponse (Response)
- Sealed Class로 구현됨
- **Success**: 상태 변경 성공
- **Failure**: 상태 변경 실패
- **Unknown**: 알 수 없는 응답

#### WorkCardListResponse (Response)
- Sealed Class로 구현됨
- **Success**: 작업 카드 목록 조회 성공
- **Failure**: 작업 카드 목록 조회 실패
- **Unknown**: 알 수 없는 응답

#### WorkCardListResponse.Success (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | No | 응답 코드 |
| data | Data | No | 작업 카드 목록 데이터 |

#### WorkCardListResponse.Success.Data (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| page | PageInfo | Yes | 페이지 정보 |
| statusCount | List<StatusCount> | Yes | 상태별 카운트 |
| workList | List<WorkItem> | No | 작업 목록 |

#### WorkCardListResponse.Success.PageInfo (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| size | Int | No | 페이지 크기 |
| number | Int | No | 현재 페이지 번호 |
| totalElements | Int | No | 전체 요소 수 |
| totalPages | Int | No | 전체 페이지 수 |

#### WorkCardListResponse.Success.StatusCount (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| status | String | No | 상태 |
| statusName | String | No | 상태 이름 |
| count | Int | No | 카운트 |

#### WorkCardListResponse.Success.WorkItem (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| workId | String | No | 작업 ID |
| workName | String | No | 작업 이름 |
| workExplanation | String | Yes | 작업 설명 |
| workerId | String | No | 작업자 ID |
| workerName | String | No | 작업자 이름 |
| workerDeptName | String | Yes | 작업자 부서명 |
| workClass | String | No | 작업 클래스 |
| workClassName | String | Yes | 작업 클래스 이름 |
| dueDate | String | Yes | 마감일 |
| completeDate | String | Yes | 완료일 |
| status | Int | No | 상태 |
| statusName | String | No | 상태 이름 |
| forceStopCause | String | Yes | 강제 중지 사유 |
| shareWorker | List<ShareWorker> | Yes | 공유 작업자 목록 |
| workStartTime | String | Yes | 작업 시작 시간 |
| workStopTime | String | Yes | 작업 중지 시간 |
| workEndTime | String | Yes | 작업 종료 시간 |
| endWorkerId | String | Yes | 종료 작업자 ID |
| endWorkerName | String | Yes | 종료 작업자 이름 |
| endWorkerDeptName | String | Yes | 종료 작업자 부서명 |
| lastWorkName | String | Yes | 마지막 작업 이름 |
| latestWorkName | String | Yes | 최신 작업 이름 |
| deleteFlag | String | Yes | 삭제 플래그 |
| createBy | String | No | 생성자 |
| createDateTime | String | No | 생성 일시 |
| updateBy | String | Yes | 수정자 |
| updateDateTime | String | Yes | 수정 일시 |

#### WorkCardListResponse.Success.ShareWorker (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| workerId | String | No | 작업자 ID |
| workerName | String | No | 작업자 이름 |
| workerDeptName | String | No | 작업자 부서명 |
| createBy | String | Yes | 생성자 |
| createDateTime | String | Yes | 생성 일시 |

#### WorkCardListRequest (Query Parameters)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| active | Boolean | Yes | 활성 여부 |
| workClass | String | Yes | 작업 클래스 |
| status | List<Int> | Yes | 상태 목록 |
| dateType | String | Yes | 날짜 타입 |
| fromDate | String | Yes | 시작 날짜 |
| toDate | String | Yes | 종료 날짜 |
| searchType | String | Yes | 검색 타입 |
| searchText | String | Yes | 검색 텍스트 |
| isAll | Boolean | No | 전체 조회 여부 (기본값: false) |
| page | Int | No | 페이지 번호 (기본값: 0) |
| size | Int | No | 페이지 크기 (기본값: 10) |
| sort | List<String> | No | 정렬 (기본값: ["createdDateTime,desc"]) |

---

### 6. 미디어 파일

#### MediaFile.EditFileNameData (Request)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| files | List<FileData> | No | 파일 목록 |

#### MediaFile.EditFileNameData.FileData (Request)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| fileId | String | No | 파일 ID |
| fileName | String | No | 파일 이름 |

#### MediaFile.DownloadZipData (Request)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| (구조 확인 필요) | - | - | - |

#### MediaFileResponse (Response)
- Sealed Class로 구현됨
- **Failure**: 실패 응답
- **CommonFileListResult**: 일반 파일 목록 결과
- **FileUpload**: 파일 업로드 결과
- **PagedFileListResult**: 페이지네이션 파일 목록 결과
- **ZipFilePathResult**: ZIP 파일 경로 결과
- **Unknown**: 알 수 없는 응답

#### MediaFileResponse.Failure (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | No | 응답 코드 |
| message | String | No | 에러 메시지 |
| status | Int | No | HTTP 상태 코드 |

#### MediaFileResponse.CommonFileListResult (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | No | 응답 코드 |
| data | Files | No | 파일 목록 데이터 |

#### MediaFileResponse.CommonFileListResult.Files (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| files | List<File> | No | 파일 목록 |

#### MediaFileResponse.CommonFileListResult.File (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| fileId | String | No | 파일 ID |
| userId | String | No | 사용자 ID |
| fileOriginName | String | No | 원본 파일 이름 |
| fileFormat | String | Yes | 파일 형식 |
| mediaType | String | Yes | 미디어 타입 |
| uploadDate | String | No | 업로드 일시 |
| groupId | String | No | 그룹 ID |
| groupType | MediaFile.GroupType | No | 그룹 타입 (enum) |
| fileStatus | String | No | 파일 상태 ("0", "1" 등 문자열) |
| fileSize | Long | No | 파일 크기 |
| directoryPath | String | No | 디렉토리 경로 |
| fileViewPath | String | Yes | 파일 뷰 경로 |
| updateDate | String | No | 수정 일시 |
| thumbnail | String | Yes | 썸네일 경로 |

#### MediaFileResponse.FileUpload (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | No | 응답 코드 |
| data | Result | No | 업로드 결과 |

#### MediaFileResponse.FileUpload.Result (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| message | String | No | 메시지 |
| fileName | String | Yes | 파일 이름 |
| directoryPath | String | Yes | 디렉토리 경로 |
| fileSize | Long | No | 파일 크기 |
| fileId | String | Yes | 파일 ID |
| uploadedChunks | Int | Yes | 업로드된 청크 수 |
| missingChunks | List<Int> | Yes | 누락된 청크 인덱스 목록 |

#### MediaFileResponse.PagedFileListResult (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | No | 응답 코드 |
| data | PagedData | No | 페이지네이션 데이터 |

#### MediaFileResponse.PagedFileListResult.PagedData (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| files | List<PagedFile> | No | 파일 목록 |
| page | PageInfo | Yes | 페이지 정보 |

#### MediaFileResponse.PagedFileListResult.PageInfo (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| size | Int | No | 페이지 크기 |
| number | Int | No | 현재 페이지 번호 |
| totalElements | Int | No | 전체 요소 수 |
| totalPages | Int | No | 전체 페이지 수 |

#### MediaFileResponse.PagedFileListResult.PagedFile (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| fileId | String | No | 파일 ID |
| userId | String | No | 사용자 ID |
| fileOriginName | String | No | 원본 파일 이름 |
| fileFormat | String | Yes | 파일 형식 |
| uploadDate | String | No | 업로드 일시 |
| groupId | String | No | 그룹 ID |
| groupType | MediaFile.GroupType | No | 그룹 타입 (enum) |
| fileStatus | MediaFile.FileStatus | No | 파일 상태 (enum) |
| fileSize | Long | No | 파일 크기 |
| directoryPath | String | No | 디렉토리 경로 |
| fileViewPath | String | No | 파일 뷰 경로 |
| mediaType | String | Yes | 미디어 타입 |
| updateDate | String | No | 수정 일시 |
| metadata | String | Yes | 메타데이터 |
| thumbnail | String | Yes | 썸네일 경로 |
| workName | String | Yes | 작업 이름 |
| workClass | String | Yes | 작업 클래스 |
| workerName | String | Yes | 작업자 이름 |
| departmentName | String | Yes | 부서 이름 |

#### MediaFileResponse.ZipFilePathResult (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | No | 응답 코드 |
| data | ZipData | No | ZIP 데이터 |

#### MediaFileResponse.ZipFilePathResult.ZipData (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| zipFilePath | String | No | ZIP 파일 경로 |

---

### 7. 워크플로우

#### WorkTypeResponseDto (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | No | 성공 여부 |
| code | String | No | 응답 코드 |
| data | WorkTypeData | Yes | 워크플로우 코드 데이터 |

#### WorkTypeData (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| list | List<WorkTypeItem> | Yes | 워크플로우 코드 목록 |

#### WorkTypeItem (Response)
| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| codeId | String | Yes | 코드 ID |
| codeName | String | Yes | 코드 이름 |
| codeExplanation | String | Yes | 코드 설명 |

---

## Mapping Rules

### 1. 인증 및 계정 관리 매핑

#### LoginResponse → LoginItem
- `success`, `code`, `errorKey`, `message` 직접 매핑
- `data.token.*` → `grantType`, `accessToken`, `refreshToken`, `accessTokenExpiresIn`, `refreshTokenExpiresIn`
- `data.user.*` → `userNo`, `userId`, `loginId`, `deptName`, `ognzName`, `positionName`, `userName`
- `data.user.role.*` → `roleGroupId`, `roleGroupName`
- `data.isPasswordReset` → `isPasswordReset`

#### RegisterDeviceResponse → RegisterDeviceItem
- `success`, `code` 직접 매핑
- `data.device.*` → `deviceId`, `deviceName`, `modelName`, `serialNumber`, `osVersion`, `osType`, `isActive`

#### RegisterAppResponse → RegisterAppItem
- `success`, `code` 직접 매핑
- `data.appInfo.*` → `deviceId`, `appId`, `appType`, `appVersion`

#### MyInfoResponse → MyInfoItem
- `success`, `code` 직접 매핑
- `data.*` → `InformData`로 매핑
- `data.ognz.*` → `OgnzInformData`로 매핑
- `data.dept.*` → `DeptInformData`로 매핑
- `data.position.*` → `PositionInformData`로 매핑
- `data.role.*` → `RoleInformData`로 매핑

---

### 2. Push 및 통화 매핑

#### RequestCallResponse → PushCallResult
- `success`, `code` 직접 매핑
- `data.messageId` → `MessageData.messageId` (null이면 빈 문자열)
- `data.registTime` → `MessageData.registTime` (null이면 빈 문자열)
- `data.receiveList` → `MessageData.receiveList` (각 항목을 `ReceiveData`로 매핑)
- `receive.receiveId` → `ReceiveData.receiveId` (null이면 빈 문자열)
- `receive.deviceType` → `ReceiveData.deviceType` (null이면 빈 문자열)
- `receive.sendStatus` → `ReceiveData.sendStatus` (null이면 0)

#### UserStatusListResponse → UserStatusInfo
- `success`, `code` 직접 매핑
- `data.userList` → `UserStatusListItem.userList` (각 항목을 `UserStatusItem`으로 매핑)

#### UserState → UserStatusItem
- `loginId`, `systemType`, `department` 직접 매핑
- `status` (String) → `UserLoginStatus` enum으로 변환 (0: STATUS_LOG_OUT, 1: STATUS_LOG_IN, 2: STATUS_CALLING)
- `deviceType` (Array<String?>) → `UserDeviceStatus` enum 배열로 변환
- `deviceDetail` → `UserDeviceDetailItem` 배열로 변환 (각 항목의 `deviceType`과 `status`를 enum으로 변환)

#### OnGoingListResponse → OnGoingInfo
- `success`, `code` 직접 매핑
- `data` (ArrayList<OnGoingRoomItem>) → `data` (ArrayList<OnGoingListItem>)로 매핑

#### OnGoingRoomItem → OnGoingListItem
- 모든 필드 직접 매핑
- `createdBy` → `CreatedByInfo`로 매핑
- `participants` → `ActiveParticipant` 배열로 매핑

---

### 3. 회의 및 사용자 매핑

#### CallResponse → CallInfo
- `success`, `code` 직접 매핑
- `data.domain` → `RoomData.domain` (null이면 빈 문자열)
- `data.roomName` → `RoomData.roomName` (null이면 빈 문자열)
- `data.displayName` → `RoomData.displayName` (null이면 빈 문자열)
- `RoomData.senderId`는 빈 문자열로 설정

#### ChatUserListResponse → ChatUserListInfo
- `success`, `code` 직접 매핑
- `data` → `ChatUserData`로 매핑

#### ChatUserItem → ChatUserData
- `userList` → `ChatUser` 배열로 매핑 (null이면 빈 리스트)
- `page` → `ChatListPage`로 매핑 (null이면 기본값 0)

#### ChatUserContentItem → ChatUser
- 모든 필드 직접 매핑
- `seq` (Int) → `seq` (String)으로 변환
- `userNo` (Int?) → `userNo` (String?)으로 변환

---

### 4. 작업 상태 매핑

#### WorkStatusResponse → WorkStatusItem
- Sealed Class 타입에 따라 분기 처리
- **Success**: `WorkStatusResponse.Success` → `WorkStatusItem.Success`
  - `data.workId` → `Data.workId` (null이면 빈 문자열)
- **Failure**: `WorkStatusResponse.Failure` → `WorkStatusItem.Failure`
- **TaskList**: `WorkStatusResponse.TaskList` → `WorkStatusItem.TaskList`
  - `data.workList` → 각 `TaskItem`을 매핑
  - `status` (TaskStatus enum) → `TaskStatus` enum으로 매핑 (entries.first로 매칭)

---

### 5. 작업 카드 매핑

#### WorkCardData → WorkCardRequest
- `workName`, `workExplanation`, `workClass` 직접 매핑
- `shareList` → 각 `ShareWorker`를 `WorkCardRequest.ShareWorker`로 매핑

#### WorkCardResponse → WorkCardItem
- Sealed Class 타입에 따라 분기 처리
- **Success**: `WorkCardResponse.Success` → `WorkCardItem.Success`
  - `data.workId` → `Data.workId`
- **Failure**: `WorkCardResponse.Failure` → `WorkCardItem.Failure`
- **Unknown**: `WorkCardResponse.Unknown` → `WorkCardItem.Unknown`

---

### 6. 미디어 파일 매핑

#### MediaFileResponse → MediaFileItem
- Sealed Class 타입에 따라 분기 처리
- **Failure**: 직접 매핑
- **CommonFileListResult**: `MediaFileResponse.CommonFileListResult` → `MediaFileItem.CommonFileListResult`
  - `data.files` → 각 `File`을 매핑
  - `fileStatus` (String) → `MediaFile.FileStatus` enum으로 변환 (`toIntOrNull()` 사용, 실패 시 0)
  - `fileViewPath` (String?) → `fileViewPath` (String) (null이면 빈 문자열)
  - `thumbnail` (String?) → `thumbnail` (String) (null이면 빈 문자열)
- **FileUpload**: `MediaFileResponse.FileUpload` → `MediaFileItem.FileUpload`
  - `data.*` → `Result`로 매핑
- **PagedFileListResult**: `MediaFileResponse.PagedFileListResult` → `MediaFileItem.PagedFileListResult`
  - `data.files` → 각 `PagedFile`을 매핑
  - `data.page` → `PageInfo`로 매핑 (null이면 기본값 0)
- **ZipFilePathResult**: `MediaFileResponse.ZipFilePathResult` → `MediaFileItem.ZipFilePathResult`
  - `data.zipFilePath` 직접 매핑

---

### 7. Push 메시지 매핑

#### PushResponse → PushMessage
- `pushType` (messageType) 직접 매핑
- `messageBlock.title` → `title`
- `messageBlock.contents` → `message`
- `messageBlock.actionType` → `actionType`
- `messageBlock.actionDataBlock` → `callResponse` (`toDomainModel()` 호출)
- `senderId`, `messageId`, `registTime` 직접 매핑

#### ActionDataBlock → CallPushResponse
- `senderName`이 비어있으면 `message` 사용
- 모든 필드 직접 매핑

---

## Ktor Client 설정 예시

```kotlin
// HttpClient 설정
val httpClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = false
        })
    }
    install(HttpTimeout) {
        connectTimeoutMillis = 10000
        requestTimeoutMillis = 30000
    }
    install(Logging) {
        level = LogLevel.INFO
    }
    defaultRequest {
        header("Content-Type", "application/json")
    }
    engine {
        https {
            trustManager = object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            }
        }
    }
}

// 인증 인터셉터 추가
expect class AuthInterceptor {
    fun intercept(request: HttpRequestBuilder): HttpRequestBuilder
}
```

---

## Kotlinx.Serialization 데이터 클래스 예시

```kotlin
@Serializable
data class LoginRequest(
    @SerialName("loginId") val loginId: String,
    @SerialName("password") val password: String,
    @SerialName("deviceType") val deviceType: String,
    @SerialName("platform") val platform: String = "android",
    @SerialName("allowDuplicateLogin") val allowDuplicateLogin: Boolean,
    @SerialName("appId") val appId: String
)

@Serializable
data class LoginResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("code") val code: String,
    @SerialName("errorKey") val errorKey: String? = null,
    @SerialName("message") val message: String? = null,
    @SerialName("data") val data: UserData? = null
)

@Serializable
data class UserData(
    @SerialName("token") val token: Token? = null,
    @SerialName("user") val user: User? = null,
    @SerialName("isPasswordReset") val isPasswordReset: Boolean? = null
)

@Serializable
data class Token(
    @SerialName("grantType") val grantType: String? = null,
    @SerialName("accessToken") val accessToken: String? = null,
    @SerialName("refreshToken") val refreshToken: String? = null,
    @SerialName("accessTokenExpiresIn") val accessTokenExpiresIn: Long? = null,
    @SerialName("refreshTokenExpiresIn") val refreshTokenExpiresIn: Long? = null
)
```

---

## 참고사항

1. **인증 토큰 자동 갱신**: 400/401 응답 시 `refreshToken`으로 자동 갱신 시도
2. **에러 코드 처리**: 
   - `1003`, `1004`: 토큰 갱신 실패 시 로그아웃 처리
   - `1018`: 중복 로그인
   - `3004`: 통화 가능한 사용자 없음
3. **파일 상태 변환**: `fileStatus`는 문자열로 받지만 enum으로 변환 필요
4. **날짜 형식**: 작업 시간은 `yyyy-MM-dd HH:mm:ss.SSS` 형식
5. **페이지네이션**: 기본값 `page=0`, `size=10`
6. **다중 통화**: 최대 10명까지 지원
7. **청크 업로드**: 파일을 청크 단위로 분할하여 업로드, 진행률 확인 API로 재시도 처리
