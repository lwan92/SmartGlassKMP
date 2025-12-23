 # SmartGlass Mobile - API 연동 규격서

> 이 문서는 KMP 프로젝트 개발을 위한 서버 연동 규격서입니다.  
> Ktor Client와 Kotlinx.Serialization을 사용하여 바로 구현할 수 있도록 작성되었습니다.  
> Glass 코드를 제외한 공통 코드와 Mobile 코드에서 사용하는 API만 포함합니다.

---

## 1. 기본 설정

### 1.1 Base URL
```
기본 도메인: https://xr-service.digicaps.com:443
```

**런타임 Base URL 변경 지원**
- 디바이스 등록 시 QR 코드에서 URL 수신
- `BaseUrlInterceptor`를 통해 동적으로 변경 가능
- 모든 API 요청에 동일한 Base URL 적용

### 1.2 공통 헤더
```
Content-Type: application/json
Authorization: Bearer {accessToken}
```

**Authorization 헤더 규칙**
- 로그인/토큰 갱신 API 제외한 모든 API에 필수
- 토큰이 없는 경우 헤더 미포함 (401 에러 발생)
- 401 에러 시 자동 토큰 갱신 후 재시도

### 1.3 공통 응답 구조
모든 API 응답은 다음 구조를 따릅니다:

```json
{
  "success": boolean,
  "code": string,
  "message": string?,
  "status": number?,
  "data": object?
}
```

**에러 응답 예시**
```json
{
  "success": false,
  "code": "1003",
  "message": "토큰이 만료되었습니다.",
  "status": 401
}
```

### 1.4 주요 에러 코드
- `1003`: Access Token 만료
- `1004`: Refresh Token 만료
- `1018`: 중복 로그인 감지
- `400`: 잘못된 요청
- `401`: 인증 실패
- `403`: 권한 없음
- `404`: 리소스 없음
- `500`: 서버 에러

---

## 2. 인증 API (AccountService)

### 2.1 로그인

**Endpoint**
```
POST /api/auth/login
```

**Request Headers**
```
Content-Type: application/json
```

**Request Body**
```json
{
  "loginId": string,
  "password": string,
  "deviceType": string,        // "MOBILE" 또는 "MOBILE_NEO"
  "platform": string,          // "android"
  "allowDuplicateLogin": boolean,
  "appId": string
}
```

| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| loginId | String | false | 로그인 ID |
| password | String | false | 비밀번호 |
| deviceType | String | false | 디바이스 타입 (MOBILE, MOBILE_NEO) |
| platform | String | false | 플랫폼 (android) |
| allowDuplicateLogin | Boolean | false | 중복 로그인 허용 여부 |
| appId | String | false | 앱 ID (디바이스 등록 시 발급) |

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "errorKey": string?,
  "message": string?,
  "data": {
    "token": {
      "grantType": string?,
      "accessToken": string?,
      "refreshToken": string?,
      "accessTokenExpiresIn": number?,
      "refreshTokenExpiresIn": number?
    },
    "user": {
      "userNo": number?,
      "userId": string?,
      "loginId": string?,
      "deptName": string?,
      "ognzName": string?,
      "positionName": string?,
      "userName": string?,
      "role": {
        "roleGroupId": number?,
        "roleGroupName": string?
      }
    },
    "isPasswordReset": boolean?
  }
}
```

| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| success | Boolean | false | 성공 여부 |
| code | String | false | 응답 코드 |
| errorKey | String | true | 에러 키 |
| message | String | true | 메시지 |
| data.token.grantType | String | true | 토큰 타입 (보통 "Bearer") |
| data.token.accessToken | String | true | 액세스 토큰 |
| data.token.refreshToken | String | true | 리프레시 토큰 |
| data.token.accessTokenExpiresIn | Long | true | 액세스 토큰 만료 시간 (초) |
| data.token.refreshTokenExpiresIn | Long | true | 리프레시 토큰 만료 시간 (초) |
| data.user.userNo | Int | true | 사용자 번호 |
| data.user.userId | String | true | 사용자 ID |
| data.user.loginId | String | true | 로그인 ID |
| data.user.deptName | String | true | 부서명 |
| data.user.ognzName | String | true | 조직명 |
| data.user.positionName | String | true | 직책명 |
| data.user.userName | String | true | 사용자 이름 |
| data.user.role.roleGroupId | Int | true | 권한 그룹 ID (1: 관리자) |
| data.user.role.roleGroupName | String | true | 권한 그룹 이름 |
| data.isPasswordReset | Boolean | true | 비밀번호 재설정 필요 여부 |

**에러 응답 (중복 로그인)**
```json
{
  "success": false,
  "code": "1018",
  "message": "다른 기기에서 로그인 중입니다."
}
```

---

### 2.2 QR 로그인

**Endpoint**
```
POST /api/auth/qr-login
```

**Request Headers**
```
Content-Type: application/json
```

**Request Body**
```json
{
  "loginId": string,
  "uuid": string,
  "deviceType": string,        // "MOBILE" 또는 "MOBILE_NEO"
  "allowDuplicateLogin": boolean,
  "appId": string
}
```

| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| loginId | String | false | 로그인 ID (QR에서 추출) |
| uuid | String | false | 디바이스 UUID (QR에서 추출) |
| deviceType | String | false | 디바이스 타입 |
| allowDuplicateLogin | Boolean | false | 중복 로그인 허용 여부 |
| appId | String | false | 앱 ID |

**Response Body**
- 로그인 API와 동일한 구조

---

### 2.3 로그아웃

**Endpoint**
```
POST /api/auth/logout
```

**Request Headers**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Request Body**
- 없음

**Response Body**
```json
{
  "success": boolean,
  "code": string
}
```

---

### 2.4 토큰 갱신

**Endpoint**
```
POST /api/auth/token/refresh
```

**Request Headers**
```
Content-Type: application/json
```

**Request Body**
```json
{
  "refreshToken": string,
  "bypassRefreshExpiry": boolean  // true (기본값)
}
```

| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| refreshToken | String | false | 리프레시 토큰 |
| bypassRefreshExpiry | Boolean | false | 만료 우회 (true) |

**Response Body**
- 로그인 API와 동일한 구조

---

### 2.5 아이디 찾기

**Endpoint**
```
POST /api/auth/find-id
```

**Request Headers**
```
Content-Type: application/json
```

**Request Body**
```json
{
  "userName": string,
  "phoneNo": string
}
```

| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| userName | String | false | 사용자 이름 |
| phoneNo | String | false | 전화번호 |

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "loginId": string?,
    "userName": string?,
    "createDate": string?
  }
}
```

---

### 2.6 비밀번호 찾기

**Endpoint**
```
POST /api/auth/find-password
```

**Request Headers**
```
Content-Type: application/json
```

**Request Body**
```json
{
  "loginId": string,
  "userName": string,
  "phoneNo": string
}
```

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "message": string?
  }
}
```

---

### 2.7 비밀번호 변경

**Endpoint**
```
PUT /api/users/password
```

**Request Headers**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Request Body**
```json
{
  "currentPassword": string,
  "newPassword": string,
  "confirmPassword": string
}
```

**Response Body**
```json
{
  "success": boolean,
  "code": string
}
```

---

### 2.8 디바이스 등록

**Endpoint**
```
POST /api/device/regist/device
```

**Request Headers**
```
Content-Type: application/json
```

**Request Body**
```json
{
  "uuid": string,
  "deviceId": string,
  "deviceType": string,        // "MOBILE" 또는 "GLASS"
  "activeStatus": boolean
}
```

| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| uuid | String | false | 디바이스 UUID (QR에서 추출) |
| deviceId | String | false | 디바이스 ID (Android ID) |
| deviceType | String | false | 디바이스 타입 |
| activeStatus | Boolean | false | 활성 상태 (true) |

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "device": {
      "deviceId": string?,
      "deviceName": string?,
      "modelName": string?,
      "serialNumber": string?,
      "osVersion": string?,
      "osType": string?,
      "isActive": boolean?
    }
  }
}
```

---

### 2.9 앱 등록

**Endpoint**
```
POST /api/device/regist/app
```

**Request Headers**
```
Content-Type: application/json
```

**Request Body**
```json
{
  "deviceId": string,
  "appId": string,
  "appType": string,           // "MOBILE" 또는 "GLASS"
  "appVersion": string         // 예: "1.0.0"
}
```

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "appInfo": {
      "deviceId": string?,
      "appId": string?,
      "appType": string?,
      "appVersion": string?
    }
  }
}
```

---

### 2.10 내 정보 조회

**Endpoint**
```
GET /api/users/me
```

**Request Headers**
```
Authorization: Bearer {accessToken}
```

**Request Body**
- 없음

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "userNo": number?,
    "loginId": string?,
    "userName": string?,
    "ognz": {
      "ognzId": string?,
      "ognzName": string?
    },
    "dept": {
      "deptId": string?,
      "deptName": string?
    },
    "position": {
      "positionId": string?,
      "positionName": string?
    },
    "role": {
      "roleGroupId": number?,
      "roleGroupName": string?
    },
    "phoneNo": string?,
    "telNo": string?,
    "email": string?,
    "status": string?,
    "lastLoginDt": string?,
    "createDt": string?,
    "updateDt": string?,
    "lockYn": string?
  }
}
```

---

## 3. 작업카드 API (WorkCardService)

### 3.1 작업카드 생성

**Endpoint**
```
POST /api/work
```

**Request Headers**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Request Body**
```json
{
  "workName": string,
  "workExplanation": string,
  "workClass": string,         // "INIT", "PROF", "TEAM"
  "shareList": [
    {
      "workerId": string,
      "workerName": string,
      "workerDeptName": string
    }
  ]
}
```

| 필드명 | 타입 | Nullable | 설명 |
|--------|------|----------|------|
| workName | String | false | 작업명 (최대 30자) |
| workExplanation | String | false | 작업 설명 (최대 50자) |
| workClass | String | false | 작업 유형 (INIT, PROF, TEAM) |
| shareList | Array | false | 참여자 목록 (최대 10명) |
| shareList[].workerId | String | false | 작업자 ID (loginId) |
| shareList[].workerName | String | false | 작업자 이름 |
| shareList[].workerDeptName | String | false | 작업자 부서명 |

**Response Body**
```json
{
  "success": true,
  "code": string,
  "data": {
    "workId": string
  }
}
```

**에러 응답**
```json
{
  "success": false,
  "code": string,
  "message": string,
  "status": number
}
```

---

### 3.2 작업카드 목록 조회

**Endpoint**
```
GET /api/work
```

**Request Headers**
```
Authorization: Bearer {accessToken}
```

**Query Parameters**

| 파라미터명 | 타입 | Nullable | 설명 |
|------------|------|----------|------|
| active | Boolean | true | 활성 여부 |
| workClass | String | true | 작업 유형 (INIT, PROF, TEAM) |
| status | String | true | 상태 (쉼표로 구분된 숫자, 예: "1,2,3") |
| dateType | String | true | 날짜 타입 |
| fromDate | String | true | 시작 날짜 (yyyy-MM-dd) |
| toDate | String | true | 종료 날짜 (yyyy-MM-dd) |
| searchType | String | true | 검색 타입 |
| searchText | String | true | 검색어 |
| isAll | Boolean | false | 전체 조회 여부 (기본값: false) |
| page | Int | false | 페이지 번호 (기본값: 0) |
| size | Int | false | 페이지 크기 (기본값: 10) |
| sort | String | false | 정렬 (기본값: "createdDateTime,desc") |

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "page": {
      "size": number,
      "number": number,
      "totalElements": number,
      "totalPages": number
    },
    "statusCount": [
      {
        "status": string,
        "statusName": string,
        "count": number
      }
    ],
    "workList": [
      {
        "workId": string,
        "workName": string,
        "workExplanation": string?,
        "workerId": string,
        "workerName": string,
        "workerDeptName": string?,
        "workClass": string,
        "workClassName": string?,
        "dueDate": string?,
        "completeDate": string?,
        "status": number,
        "statusName": string,
        "forceStopCause": string?,
        "shareWorker": [
          {
            "workerId": string,
            "workerName": string,
            "workerDeptName": string,
            "createBy": string?,
            "createDateTime": string?
          }
        ],
        "workStartTime": string?,
        "workStopTime": string?,
        "workEndTime": string?,
        "endWorkerId": string?,
        "endWorkerName": string?,
        "endWorkerDeptName": string?,
        "lastWorkName": string?,
        "latestWorkName": string?,
        "deleteFlag": string?,
        "createBy": string,
        "createDateTime": string,
        "updateBy": string?,
        "updateDateTime": string?
      }
    ]
  }
}
```

---

### 3.3 작업 상태 변경

**Endpoint**
```
PUT /api/work/{workId}/change-status
```

**Request Headers**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Path Parameters**
- `workId`: 작업 ID

**Request Body**
```json
{
  "status": string,            // "START", "WORK", "PAUSE", "END"
  "forceStopCause": string?    // 강제 종료 사유 (선택)
}
```

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "workId": string?
  }
}
```

---

### 3.4 승강기 정보 등록

**Endpoint**
```
POST /api/work/{workId}/elevator
```

**Request Headers**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Path Parameters**
- `workId`: 작업 ID

**Request Body**
```json
{
  "elevatorId": string,
  "elevatorInfoUrl": string
}
```

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "workId": string?
  }
}
```

---

## 4. 작업 기록 API (WorkRecordApiService)

### 4.1 작업 기록 상세 조회

**Endpoint**
```
GET /api/work/{workId}
```

**Request Headers**
```
Authorization: Bearer {accessToken}
```

**Path Parameters**
- `workId`: 작업 ID

**Query Parameters**
- `withLog`: Boolean (기본값: false) - 로그 포함 여부

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "workId": string,
    "workName": string,
    "workExplanation": string?,
    "workerId": string,
    "workerName": string,
    "workClass": string,
    "status": number,
    "shareWorker": [
      {
        "workerId": string,
        "workerName": string,
        "workerDeptName": string
      }
    ],
    "createDateTime": string,
    // ... 기타 필드
  }
}
```

---

### 4.2 작업카드 수정 (IF-401-011)

**Endpoint**
```
PUT /api/work/{workId}
```

**Request Headers**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Path Parameters**
- `workId`: 작업 ID

**Request Body**
```json
{
  "workName": string,
  "workExplanation": string,
  "workClass": string
}
```

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "workId": string?
  }
}
```

---

### 4.3 작업 공유자 추가 (IF-401-006)

**Endpoint**
```
POST /api/work/{workId}/share
```

**Request Headers**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Path Parameters**
- `workId`: 작업 ID

**Request Body**
```json
{
  "shareList": [
    {
      "workerId": string,
      "workerName": string,
      "workerDeptName": string
    }
  ]
}
```

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "workId": string?
  }
}
```

---

### 4.4 작업 공유자 삭제 (IF-401-007)

**Endpoint**
```
DELETE /api/work/{workId}/share
```

**Request Headers**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Path Parameters**
- `workId`: 작업 ID

**Request Body**
```json
{
  "workerIds": [string]
}
```

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "workId": string?
  }
}
```

---

### 4.5 체크리스트 목록 조회

**Endpoint**
```
GET /api/work/{workId}/checklist
```

**Request Headers**
```
Authorization: Bearer {accessToken}
```

**Path Parameters**
- `workId`: 작업 ID

**Query Parameters**
- `workClass`: String - 작업 유형

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "checklistList": [
      {
        "checklistId": string,
        "checklistName": string,
        "workClass": string,
        "status": string
      }
    ]
  }
}
```

---

### 4.6 체크리스트 시작

**Endpoint**
```
POST /api/work/{workId}/checklist/{checklistId}/start
```

**Request Headers**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Path Parameters**
- `workId`: 작업 ID
- `checklistId`: 체크리스트 ID

**Request Body**
```json
{
  "workerId": string
}
```

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "checklistId": string?
  }
}
```

---

### 4.7 체크리스트 질문 목록 조회

**Endpoint**
```
GET /api/work/{workId}/checklist/{checklistId}
```

**Request Headers**
```
Authorization: Bearer {accessToken}
```

**Path Parameters**
- `workId`: 작업 ID
- `checklistId`: 체크리스트 ID

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "questions": [
      {
        "questionId": string,
        "questionText": string,
        "questionType": string,
        "required": boolean,
        "options": [string]?
      }
    ]
  }
}
```

---

### 4.8 체크리스트 설문 응답 저장 (IF-401-018)

**Endpoint**
```
POST /api/work/{workId}/checklist/{checkListId}/result
```

**Request Headers**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Path Parameters**
- `workId`: 작업 ID
- `checkListId`: 체크리스트 ID

**Request Body**
```json
{
  "questionId": string,
  "answer": string,
  "fileIds": [string]?
}
```

**Response Body**
```json
{
  "success": boolean,
  "code": string
}
```

---

## 5. 미디어 파일 API (MediaFileService)

### 5.1 파일 목록 조회

**Endpoint**
```
GET /api/file/file_list
```

**Request Headers**
```
Authorization: Bearer {accessToken}
```

**Query Parameters**

| 파라미터명 | 타입 | Nullable | 설명 |
|------------|------|----------|------|
| workId | String | true | 작업 ID |
| mediaFilter | Array<String> | true | 미디어 타입 필터 (PHOTO, VIDEO, AUDIO) |
| checklistId | String | true | 체크리스트 ID |
| questionId | String | true | 질문 ID |
| size | Int | true | 페이지 크기 |

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "files": [
      {
        "fileId": string,
        "userId": string,
        "fileOriginName": string,
        "fileFormat": string?,
        "mediaType": string?,
        "uploadDate": string,
        "groupId": string,
        "groupType": string,       // "WORK"
        "fileStatus": string,       // "0": REQUEST, "1": COMPLETE
        "fileSize": number,
        "directoryPath": string,
        "fileViewPath": string?,
        "updateDate": string,
        "thumbnail": string?
      }
    ]
  }
}
```

**fileStatus 값**
- `"0"` 또는 `0`: REQUEST (업로드 대기)
- `"1"` 또는 `1`: COMPLETE (업로드 완료)

---

### 5.2 페이징 파일 목록 조회

**Endpoint**
```
GET /api/file/file_list
```

**Request Headers**
```
Authorization: Bearer {accessToken}
```

**Query Parameters**

| 파라미터명 | 타입 | Nullable | 설명 |
|------------|------|----------|------|
| workId | String | true | 작업 ID |
| mediaFilter | Array<String> | true | 미디어 타입 필터 |
| sort | Array<String> | true | 정렬 (예: ["uploadDate,desc"]) |
| size | Int | true | 페이지 크기 |
| page | Int | true | 페이지 번호 |

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "files": [
      {
        "fileId": string,
        "userId": string,
        "fileOriginName": string,
        "fileFormat": string?,
        "uploadDate": string,
        "groupId": string,
        "groupType": string,
        "fileStatus": string,
        "fileSize": number,
        "directoryPath": string,
        "fileViewPath": string,
        "mediaType": string?,
        "updateDate": string,
        "metadata": string?,
        "thumbnail": string?,
        "workName": string?,
        "workClass": string?,
        "workerName": string?,
        "departmentName": string?
      }
    ],
    "page": {
      "size": number,
      "number": number,
      "totalElements": number,
      "totalPages": number
    }
  }
}
```

---

### 5.3 단일 파일 업로드

**Endpoint**
```
POST /api/file/upload/single
```

**Request Headers**
```
Authorization: Bearer {accessToken}
Content-Type: multipart/form-data
```

**Request Body (Multipart)**
- `file`: File (MultipartBody.Part) - 업로드할 파일
- `base64Data`: String? - Base64 인코딩 데이터 (선택)
- `fileName`: String? - 파일명 (선택)
- `groupId`: String - 그룹 ID (workId)
- `groupType`: String - 그룹 타입 ("WORK")
- `fileId`: String? - 파일 ID (재업로드 시)
- `metadata`: String? - 메타데이터 (JSON 문자열, 선택)

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "success": boolean,
    "message": string,
    "fileName": string?,
    "directoryPath": string?,
    "fileSize": number,
    "fileId": string?,
    "uploadedChunks": number?,
    "missingChunks": [number]?
  }
}
```

---

### 5.4 파일명 변경

**Endpoint**
```
PUT /api/file/edit_file_name
```

**Request Headers**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Request Body**
```json
{
  "files": [
    {
      "fileId": string,
      "fileName": string
    }
  ]
}
```

**Response Body**
- 페이징 파일 목록 조회와 동일한 구조

---

### 5.5 파일 삭제

**Endpoint**
```
DELETE /api/file/delete_file
```

**Request Headers**
```
Authorization: Bearer {accessToken}
```

**Query Parameters**
- `fileIds`: Array<String> - 삭제할 파일 ID 목록 (쉼표로 구분)

**Response Body**
```json
{
  "success": boolean,
  "code": string
}
```

---

### 5.6 ZIP 파일 다운로드

**Endpoint**
```
POST /api/file/download/zip
```

**Request Headers**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Request Body**
```json
{
  "fileIds": [string]
}
```

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "zipFilePath": string
  }
}
```

---

## 6. Chunk 업로드 API (MediaFileChunkService)

### 6.1 Chunk 업로드 진행 상황 확인

**Endpoint**
```
POST /api/file/upload/chunk/progress
```

**Request Headers**
```
Authorization: Bearer {accessToken}
Content-Type: multipart/form-data
```

**Request Body (Multipart)**
- `fileName`: String - 파일명
- `fileId`: String? - 파일 ID (재업로드 시)
- `groupId`: String - 그룹 ID (workId)
- `groupType`: String - 그룹 타입 ("WORK")
- `totalChunks`: String - 총 Chunk 수
- `totalFileSize`: String - 전체 파일 크기 (바이트)
- `thumbnail`: File? - 썸네일 파일 (동영상인 경우, 선택)
- `checklistId`: String - 체크리스트 ID
- `questionId`: String - 질문 ID

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "success": boolean,
    "message": string,
    "fileName": string?,
    "directoryPath": string?,
    "fileSize": number,
    "fileId": string?,
    "uploadedChunks": number?,
    "missingChunks": [number]?
  }
}
```

**missingChunks 규칙**
- 업로드되지 않은 Chunk 인덱스 목록
- 예: `[0, 1, 2, 3]` → 첫 4개 Chunk가 누락됨
- 빈 배열 `[]` → 모든 Chunk 업로드 완료

---

### 6.2 Chunk 파일 업로드

**Endpoint**
```
POST /api/file/upload/chunk
```

**Request Headers**
```
Authorization: Bearer {accessToken}
Content-Type: multipart/form-data
```

**Request Body (Multipart)**
- `file`: File (MultipartBody.Part) - Chunk 파일 (512KB)
- `fileId`: String - 파일 ID
- `chunkIndex`: String - Chunk 인덱스 (0부터 시작)
- `totalChunks`: String - 총 Chunk 수
- `fileName`: String - 원본 파일명
- `groupId`: String - 그룹 ID (workId)
- `groupType`: String - 그룹 타입 ("WORK")
- `totalTileSize`: String - 전체 파일 크기 (오타: totalFileSize)
- `checklistId`: String - 체크리스트 ID
- `questionId`: String - 질문 ID

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "success": boolean,
    "message": string
  }
}
```

**Chunk 업로드 규칙**
- Chunk 크기: 512KB (고정)
- 총 Chunk 수: `ceil(파일크기 / 512KB)`
- Chunk 인덱스: 0부터 시작
- 모든 Chunk 업로드 완료 시 서버에서 자동으로 파일 통합

---

## 7. 회의 API (MeetingService)

### 7.1 통화 요청

**Endpoint**
```
GET /api/chat/rooms
```

**Request Headers**
```
Authorization: Bearer {accessToken}
```

**Request Body**
- 없음

**Response Body**
```json
{
  "success": string?,
  "code": string?,
  "data": {
    "domain": string?,
    "roomName": string?,
    "displayName": string?
  }
}
```

---

### 7.2 사용자 검색 (채팅)

**Endpoint**
```
GET /api/users
```

**Request Headers**
```
Authorization: Bearer {accessToken}
```

**Query Parameters**

| 파라미터명 | 타입 | Nullable | 설명 |
|------------|------|----------|------|
| searchType | String | false | 검색 타입 |
| searchText | String | false | 검색어 |
| page | String | false | 페이지 번호 |
| size | String | false | 페이지 크기 |
| sort | String | false | 정렬 |
| isAll | Boolean | false | 전체 조회 여부 |

**Response Body**
```json
{
  "success": string?,
  "code": string?,
  "data": {
    "userList": [
      {
        "seq": number,
        "userNo": number?,
        "loginId": string?,
        "userName": string?,
        "ognzName": string?,
        "deptName": string?,
        "positionName": string?,
        "roleGroupName": string?,
        "phoneNo": string?,
        "email": string?,
        "status": string?,
        "createDt": string?,
        "updateDt": string?
      }
    ],
    "page": {
      "size": number,
      "number": number,
      "totalElements": number,
      "totalPages": number
    }
  }
}
```

---

### 7.3 작업자 검색

**Endpoint**
```
GET /api/users/search
```

**Request Headers**
```
Authorization: Bearer {accessToken}
```

**Query Parameters**
- `searchType`: String? - 검색 타입
- `searchText`: String? - 검색어
- `sort`: Array<String>? - 정렬

**Response Body**
```json
{
  "success": string?,
  "code": string?,
  "data": {
    "userList": [
      {
        "seq": number?,
        "userNo": number?,
        "loginId": string?,
        "userName": string?,
        "ognzName": string?,
        "deptName": string?,
        "positionName": string?,
        "roleGroupName": string?,
        "phoneNo": string?,
        "email": string?,
        "isFavorite": boolean?
      }
    ],
    "page": {
      "size": number,
      "number": number,
      "totalElements": number,
      "totalPages": number
    }
  }
}
```

---

## 8. 푸시 알림 API (RequestPushService)

### 8.1 푸시 전송

**Endpoint**
```
POST /api/push/send
```

**Request Headers**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Request Body**
```json
{
  "receiverId": string,
  "messageType": string,
  "title": string,
  "contents": string,
  "actionType": string?,
  "actionData": {
    "domain": string?,
    "roomName": string?,
    "receiverId": string?,
    "senderName": string?,
    "mainScreenNickName": string?,
    "deviceType": string?,
    "isMultiCall": boolean?,
    "workId": string?,
    "workName": string?,
    "status": string?,
    "workClass": string?,
    "isInvite": boolean?
  }
}
```

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "senderId": string?,
    "messageId": string?
  }
}
```

---

### 8.2 사용자 상태 목록 조회 (폴링)

**Endpoint**
```
GET /api/push/polling/status
```

**Request Headers**
```
Authorization: Bearer {accessToken}
```

**Query Parameters**

| 파라미터명 | 타입 | Nullable | 설명 |
|------------|------|----------|------|
| systemType | String | false | 시스템 타입 |
| department | String | false | 부서 |
| timeout | Int | false | 타임아웃 (초) |

**Response Body**
```json
{
  "success": string?,
  "code": string?,
  "data": {
    "userList": [
      {
        "loginId": string?,
        "systemType": string?,
        "department": string?,
        "status": string?,        // "0": 로그아웃, "1": 로그인, "2": 통화중
        "deviceType": [string?],
        "deviceDetail": [
          {
            "deviceType": string?,
            "status": number?
          }
        ]
      }
    ]
  }
}
```

---

### 8.3 화상회의 참여 (WebSocket)

**Endpoint**
```
POST /chat
```

**Request Headers**
- 없음 (WebSocket 연결)

**Query Parameters**

| 파라미터명 | 타입 | Nullable | 설명 |
|------------|------|----------|------|
| token | String | true | 액세스 토큰 |
| room_id | String | true | 방 ID |
| room_user_id | String | true | 방 사용자 ID |
| room_nm | String | true | 방 이름 |

**Response**
- WebSocket 연결 문자열 반환 (Jitsi Meet URL)

---

### 8.4 진행 중인 회의 목록 조회

**Endpoint**
```
GET /api/push/chatrooms/polling/active
```

**Request Headers**
```
Authorization: Bearer {accessToken}
```

**Query Parameters**

| 파라미터명 | 타입 | Nullable | 설명 |
|------------|------|----------|------|
| userId | String | false | 사용자 ID |
| timeout | Int | false | 타임아웃 (초) |
| sortOrder | String | false | 정렬 순서 |

**Response Body**
```json
{
  "success": string?,
  "code": string?,
  "data": [
    {
      "roomNm": string?,
      "status": number?,
      "callDirection": string?,
      "workId": string?,
      "workNm": string?,
      "workClass": string?,
      "lastJoinDt": string?,
      "createdBy": {
        "userId": string?,
        "userName": string?,
        "department": string?
      },
      "createdDt": string?,
      "participants": [
        {
          "userId": string?,
          "roomUserId": string?,
          "userName": string?,
          "deviceType": string?,
          "department": string?
        }
      ]
    }
  ]
}
```

---

### 8.5 푸시 알림 목록 조회

**Endpoint**
```
GET /api/push/receive-msgs
```

**Request Headers**
```
Authorization: Bearer {accessToken}
```

**Query Parameters**
- `page`: Int? - 페이지 번호
- `size`: Int? - 페이지 크기
- `messageType`: String? - 메시지 타입
- `readYn`: String? - 읽음 여부 (Y/N)

**Response Body**
```json
{
  "success": boolean,
  "code": string?,
  "data": {
    "unReadMsgCount": number?,
    "messageList": [
      {
        "messageId": string?,
        "sendTime": string?,
        "senderId": string?,
        "result": string?,
        "readTime": string?,
        "messageType": string?,
        "title": string?,
        "contents": string?,
        "actionType": string?,
        "actionData": {
          "workId": string?
        }
      }
    ]
  }
}
```

---

### 8.6 푸시 알림 읽음 처리

**Endpoint**
```
PUT /api/push/receive-msgs
```

**Request Headers**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Request Body**
```json
{
  "isAll": boolean,
  "messageId": string?        // isAll이 false일 때 필수
}
```

**Response Body**
```json
{
  "success": boolean,
  "code": string?,
  "data": {
    "readCount": number?
  }
}
```

---

## 9. 작업 상태 API (WorkStatusService)

### 9.1 작업 상태 설정

**Endpoint**
```
POST /api/work/status/set
```

**Request Headers**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Request Body**
```json
{
  "workId": string,
  "status": string,           // "START", "WORK", "PAUSE", "END"
  "workName": string?,
  "workExplanation": string?
}
```

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "workId": string?
  }
}
```

---

### 9.2 작업 로그 기록

**Endpoint**
```
POST /api/work/record
```

**Request Headers**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Request Body**
```json
{
  "workId": string,
  "recordType": string,
  "menuPath": string,
  "menuName": string,
  "recordData": object?
}
```

**Response Body**
- 없음 (200 OK)

---

### 9.3 작업 목록 조회

**Endpoint**
```
GET /api/work/list
```

**Request Headers**
```
Authorization: Bearer {accessToken}
```

**Query Parameters**

| 파라미터명 | 타입 | Nullable | 설명 |
|------------|------|----------|------|
| work_date_start | String | true | 시작 날짜 |
| work_date_end | String | true | 종료 날짜 |
| order | String | true | 정렬 (asc/desc) |
| active | Boolean | false | 활성 여부 |

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "workList": [
      {
        "workId": string,
        "workName": string,
        "workerId": string,
        "workerName": string,
        "status": string,              // "START", "WORK", "PAUSE", "END"
        "workStartTime": string,       // yyyy-MM-dd HH:mm:ss.SSS
        "workEndTime": string?         // yyyy-MM-dd HH:mm:ss.SSS
      }
    ]
  }
}
```

---

### 9.4 작업 일괄 조회

**Endpoint**
```
POST /api/work/bulk
```

**Request Headers**
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**Request Body**
```json
{
  "workIds": [string]
}
```

**Response Body**
- 작업 목록 조회와 동일한 구조

---

## 10. 워크플로우 API (WorkflowService)

### 10.1 작업 유형 코드 조회

**Endpoint**
```
GET /api/workflow/code/{codeType}
```

**Request Headers**
```
Authorization: Bearer {accessToken}
```

**Path Parameters**
- `codeType`: 코드 타입 (예: "WORK_CLASS")

**Response Body**
```json
{
  "success": boolean,
  "code": string,
  "data": {
    "codeList": [
      {
        "code": string,
        "codeName": string,
        "description": string?
      }
    ]
  }
}
```

---

## 11. 데이터 매핑 규칙 (Mapping Rules)

### 11.1 로그인 응답 매핑

**서버 응답 → Domain Model**
```kotlin
LoginResponse → LoginItem
- data.token.accessToken → accessToken
- data.token.refreshToken → refreshToken
- data.user.userNo → userNo
- data.user.loginId → loginId
- data.user.userName → userName
- data.user.deptName → deptName
- data.user.role.roleGroupId → roleGroupId
- data.isPasswordReset → isPasswordReset
```

---

### 11.2 작업카드 목록 매핑

**서버 응답 → Domain Model**
```kotlin
WorkCardListResponse.Success → WorkCardListItem
- data.page → PageInfo (그대로 매핑)
- data.statusCount → StatusCount 리스트 (그대로 매핑)
- data.workList → WorkItem 리스트
  - shareWorker → ShareWorker 리스트 (그대로 매핑)
```

**상태 값 변환**
- `status`: Int → 그대로 사용
- `statusName`: String → 그대로 사용

---

### 11.3 미디어 파일 목록 매핑

**서버 응답 → Domain Model**
```kotlin
MediaFileResponse.CommonFileListResult → MediaFileItem.CommonFileListResult
- data.files → File 리스트
  - fileStatus: String → MediaFile.FileStatus enum 변환
    - "0" → FileStatus.REQUEST
    - "1" → FileStatus.COMPLETE
  - fileViewPath: String? → String (null이면 빈 문자열)
  - thumbnail: String? → String (null이면 빈 문자열)
```

**fileStatus 변환 규칙**
```kotlin
fun fromInt(value: Int): FileStatus {
    return when (value) {
        0 -> FileStatus.REQUEST
        1 -> FileStatus.COMPLETE
        else -> FileStatus.REQUEST
    }
}
```

---

### 11.4 작업 상태 매핑

**서버 응답 → Domain Model**
```kotlin
WorkStatusResponse.TaskList → WorkStatusItem.TaskList
- data.workList → TaskItem 리스트
  - status: String → TaskStatus enum 변환
    - "START" → TaskStatus.START
    - "WORK" → TaskStatus.WORK
    - "PAUSE" → TaskStatus.PAUSE
    - "END" → TaskStatus.END
```

---

### 11.5 작업카드 응답 매핑

**서버 응답 → Domain Model**
```kotlin
WorkCardResponse → WorkCardItem
- Success → Success (data.workId 그대로)
- Failure → Failure (message, status 그대로)
- Unknown → Unknown
```

---

## 12. Ktor Client 구현 가이드

### 12.1 기본 설정

```kotlin
val httpClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = false
        })
    }
    install(HttpRequestRetry) {
        retryOnServerErrors(maxRetries = 3)
        exponentialDelay()
    }
    install(HttpTimeout) {
        requestTimeoutMillis = 30000
        connectTimeoutMillis = 10000
    }
    defaultRequest {
        header("Content-Type", "application/json")
    }
}
```

### 12.2 인증 인터셉터

```kotlin
class AuthInterceptor(private val tokenProvider: () -> String?) {
    fun create(): HttpRequestInterceptor {
        return HttpRequestInterceptor { request ->
            tokenProvider()?.let { token ->
                request.headers.append("Authorization", "Bearer $token")
            }
        }
    }
}
```

### 12.3 Base URL 설정

```kotlin
fun createHttpClient(baseUrl: String): HttpClient {
    return httpClient.config {
        defaultRequest {
            url(baseUrl)
        }
    }
}
```

### 12.4 데이터 클래스 예시

```kotlin
@Serializable
data class LoginRequest(
    val loginId: String,
    val password: String,
    val deviceType: String,
    val platform: String = "android",
    val allowDuplicateLogin: Boolean,
    val appId: String
)

@Serializable
data class LoginResponse(
    val success: Boolean,
    val code: String,
    val errorKey: String? = null,
    val message: String? = null,
    val data: UserData? = null
)

@Serializable
data class UserData(
    val token: Token? = null,
    val user: User? = null,
    @SerialName("isPasswordReset")
    val isPasswordReset: Boolean? = null
)

@Serializable
data class Token(
    val grantType: String? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val accessTokenExpiresIn: Long? = null,
    val refreshTokenExpiresIn: Long? = null
)
```

### 12.5 API 호출 예시

```kotlin
suspend fun login(request: LoginRequest): LoginResponse {
    return httpClient.post("/api/auth/login") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body()
}
```

---

## 13. 주의사항

### 13.1 파일 업로드
- Chunk 업로드는 `multipart/form-data` 사용
- Chunk 크기: 512KB 고정
- 동시 업로드 제한: 최대 3개

### 13.2 토큰 갱신
- 401 에러 + 코드 1003/1004 시 자동 갱신
- 갱신 실패 시 로그아웃 처리
- 갱신 중 중복 요청 방지 필요

### 13.3 Base URL 변경
- 런타임에 Base URL 변경 가능
- 모든 API에 동일한 Base URL 적용
- WebSocket URL도 함께 변경 (https → wss)

### 13.4 에러 처리
- 모든 API는 공통 응답 구조 사용
- `success: false` 시 `message` 확인
- 특정 에러 코드에 따른 분기 처리 필요

---

## 14. 문서 버전 정보

- **작성일**: 2025-01-22
- **버전**: 1.0.0
- **기반 코드**: SmartGlass Mobile (phone 플레이버)
- **분석 범위**: app/src/main, app/src/phone, data 레이어

---

**문서 끝**