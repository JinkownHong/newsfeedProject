<img width="885" alt="image" src="https://github.com/JinkownHong/newsfeedProject/assets/161419351/6a26e1e7-9fc6-47cd-800f-c096dc660dc6"># 🚀 Newsfeed Project: Explore (Exhibition + Project)

이번 프로젝트의 미션은 “뉴스피드 백엔드 개발 프로젝트”입니다.

## 📋 프로젝트 개요

콘서트, 공연, 전시회, 영화 등 문화 예술 정보 및 후기를 공유하는 뉴스피드 백엔드 개발을 목표로 프로젝트를 진행하였으며, 다음과 같은 기능을 구현하였습니다.

## ✨ ERD
![image](https://github.com/JinkownHong/newsfeedProject/assets/161419351/a52271cb-cb67-46ec-afc8-847cc63674b2)


## ✨ Wireframe
![image](https://github.com/JinkownHong/newsfeedProject/assets/161419351/e2e58def-338d-4b57-940d-3d468d9f6bb2)


## ✨ 주요 기능

### 1. POST
* 기본적인 Post 전체 조회, 상세 조회, 제작, 수정, 삭제 기능을 구현
* Post는 제목과 내용 외에도 해시태그, 좋아요 필드를 함께 포함

### 2. COMMENT
* 댓글 작성, 수정, 삭제 API 구성

### 3. HASH TAG
* 뉴스피드는 전시회, 콘서트, 영화, 뮤지컬 등 다양한 장르의 문화 예술을 공유
* HashTag를 통해 유저가 원하는 정보를 찾고 공유할 수 있도록 기능 구현
* 각 Post 별 작성자가 원하는대로 카테고리 분류 가능
* 원하는 해시태그 클릭 시 해당 해시태그를 사용한 모든 게시글 조회 가능

### 4. LIKE
* Post, Comment에 좋아요, 좋아요 취소 기능 구현
* 게시글 조회 시 개별 Post의 좋아요 갯수 표시
* 좋아요 옆 버튼 클릭 시 해당 게시글에 좋아요를 누른 User들을 확인 가능

### 5. LOGIN
* JWT(JSON Web Token) 활용 인증 인가 기능 구현

## ✨ 팀 프로젝트 진행 및 기능 구현 중 어려웠던 부분

### 1. Github 활용
* 문제점: 팀원 대부분 Github를 처음 활용하여 팀 프로젝트를 진행
* 해결 방안:
  - Git Commit 관련 팀 내 Rule 제작 (Gitmoji 활용, Class, Method 명 등을 메시지 내 활용 시 영어 그대로 해당 내용을 기입 등)
  - Slack Github 연동
  - Project 및 Issue 기능 활용

### 2. 코드 작성 관련

#### (1) LIKE
* 좋아요, 좋아요 취소 관련 기능 구현에서 다방면에서 고려 필요
* POST, DELETE API MAPPING을 통해 좋아요 저장 및 취소 기능 구현
* Post Service Layer에서 Post 전체 조회, 상세 조회 시 각 게시글 별 좋아요 개수를 함께 표시하도록 코드 작성
* 쿼리 부분에서 발생한 오류는 List의 Size를 활용하여 해결

#### (2) HASHTAG
* Post 작성 시 여러 개의 HashTag를 처리하는 방법 고민
* String 타입 파라미터 hashTag를 받아 List<String> 타입으로 반환
* 입력된 문자열을 쉼표로 구분하여 각 해시태그를 추출하고 유효성을 검사 후 리스트로 반환
* 기존에 저장된 HashTag와 새롭게 들어오는 HashTag를 분류하여 처리
* “existHashTag” / “createHashTag” 메소드를 분리하여 처리

4. **API 문서 확인**:
    * Swagger를 사용하여 API 문서를 확인할 수 있습니다. 브라우저에서 `http://localhost:8080/swagger-ui.html`로 접속합니다.

## 📜 라이센스

이 프로젝트는 MIT 라이센스를 따릅니다. 자세한 내용은 `LICENSE` 파일을 참조하세요.

## 🔨 빌드 환경

* **Language:** Kotlin
* **IDE:** Intellij
* **SDK:** Eclipse Temurin 22.0.1

