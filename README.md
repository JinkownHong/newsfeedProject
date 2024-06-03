# 🚀 Newsfeed Project: Explore (Exhibition + Project)

이번 프로젝트의 미션은 “뉴스피드 백엔드 개발 프로젝트”입니다.

## 📋 프로젝트 개요

콘서트, 공연, 전시회, 영화 등 문화 예술 정보 및 후기를 공유하는 뉴스피드 백엔드 개발을 목표로 프로젝트를 진행하였으며, 다음과 같은 기능을 구현하였습니다.

## ✨ ERD
![image](https://github.com/JinkownHong/newsfeedProject/assets/161419351/a52271cb-cb67-46ec-afc8-847cc63674b2)


## ✨ Wireframe
![image](https://github.com/JinkownHong/newsfeedProject/assets/161419351/e2e58def-338d-4b57-940d-3d468d9f6bb2)


## ✨ 주요 기능

#### 1. POST
* 기본적인 Post 전체 조회, 상세 조회, 제작, 수정, 삭제 기능 구현
* Post는 제목과 내용 외에도 HashTag, Like Column 함께 포함

#### 2. COMMENT
* 댓글 작성, 수정, 삭제 기본 API 구성

#### 3. HASH TAG
* 해당 뉴스피드 전시회, 콘서트, 영화, 뮤지컬 등 다양한 장르의 문화 예술을 공유하는 공간으로, HashTag 를 통해 유저가 원하는 정보를 찾고 공유할 수 있도록 해당 기능 구현
* 각 Post 별 작성자가 원하는대로 카테고리 분류가 가능하여 카테고리의 무한한 확장이 가능하다는 장점
* 유저가 관심있는 HashTag 클릭 시 해당 HashTag 를 사용한 모든 Post 만을 분류하여 조회 가능

#### 4. LIKE
* Post, Comment에 Like, RemoveLike 기능 구현
* Post 조회 시 각각의 Post 의 좋아요 수를 함께 표기
* 좋아요 옆 임의로 구상해놓은 하나의 버튼을 클릭 시, 해당 Post 에 좋아요를 누른 User List 확인 가능
* 현재 인증된 사용자가 Post 전체 조회, 또는 상세 조회 시 좋아요를 누른 Post 는 True 를, 누르지 않은 Post 는 False 를 반환 (HeartStatus: Boolean)

#### 5. LOGIN
* JWT(JSON Web Token) 활용 인증 인가 구현
* RefreshToken 활용 AccessToken 으로만 구현이 불가능한 로그아웃 기능 추가 구현

#### LOGIN, LOGOUT Sequence Diagram
![image](https://github.com/JinkownHong/newsfeedProject/assets/161419351/dd89b2f9-8c0a-4f0b-bd08-297a5fbbba8f)


## ✨ 팀 프로젝트 진행 및 기능 구현 중 경험한 어려움들

#### 1. Github 활용
* 문제점: 팀원 대부분 Github 활용 처음 팀 프로젝트를 진행하다 보니, Conflict 나 Branch, Merge, Git Commit Rule 등 여러가지 문제점들이 존재
* 해결 방안:
  - Git Commit 관련 팀 내 Rule 제작 (Gitmoji Rule / Class, Method 명 등 Commit Message 내 활용 시 영어 그대로 해당 내용을 기입)
  - Slack Github 연동하여 Pr, Issue 등을 실시간으로 모니터링 진행
  - Github Project 및 Issue 기능 활용 팀원 각자 어떤 기능을 구현하고 있는지, 마감기한은 언제까지인지 함께 체크 진행

#### 2. 코드 작성 관련

##### (1) LIKE
* Like 기능 구현에서 단순 데이터를 저장하고 삭제하는 것을 넘어 다방면에서 고려 필요
  - POST, DELETE API MAPPING 을 통해 좋아요 저장(PostLike) 및 취소(RemovetLike) 기능 구현
  - PostService Layer 에서 Post 별 좋아요 수 Count, heartStatus 관련 메소드 구현
  - heartStatus : PostLikeRepository 내 ‘findByPostIdAndUserId’ 메소드 활용 각 Post 별 현재 애플리케이션을 사용중인 유저가 좋아요를 클릭한 Post 를 찾고, True 를 반환하도록 설정
  - 게시글 별 좋아요를 클릭한 유저 리스트 반환 : PostLikeRepository 내 ‘findByPostId’ 메소드 활용 각 PostId를 likes 에 저장, ‘likes.map { like -> PostLikeResponse(nickname = like.user.nickname) }’ 좋아요를 누른 사용자의 nickname 만을 가져와 리스트로 반환


* Post Service Layer 에서 Post 전체 조회, 상세 조회 시 각 Post 별 좋아요 수를 함께 표시하도록 코드를 작성. 처음 Repository 내 Query 를 활용하여 PostId 별 Like 수를 Count 하는 방식으로 구현하려 했으나 지속적으로 오류가 발생
  - Query 를 통해 해당 기능을 구현하는 대신 ‘post.likes.size’ 활용 해결 (그러나 좋아요 테이블을 하나하나 조회하는 방식으로 매우 비효율적이기 때문에 추후 개선 필요)

##### (2) HASHTAG
* Post 작성 시, 하나의 Post 내 여러 개의 HashTag 를 처리하는 방법에 대한 고민
  - String 타입 파라미터 hashTag 를 받아 List<String> 타입으로 반환
  - 입력된 문자열을 쉼표로 구분하여 각 hashTag 추출 후 유효성을 검사, 이후 리스트로 반환

* 이전부터 저장되어 있던 TagName 과 새롭게 들어오는 TagName 을 분리하여 저장하고, 반환하는 방법에 대한 고민
  - “existHashTag” / “createHashTag” 데이터를 저장하고, 반환하는 메소드를 분리하여 해당 문제점 해결


##### (3) LOGIN, LOGOUT
* Token 처리 방식에 대한 고민 (Intercepter)
  - Intercepter 에서 넣은 attribute 를 HttpServletRequest 객체에서 꺼내는 코드가 인가가 필요한 곳에서 반복 
  - 별도의 메소드를 생성하여 반복을 줄일 수는 있으나, HttpServletRequest 보다 좀더 명시적으로 인증, 인가를 다룬다는 것을 표현할 수 있는 방안 필요

* ArgumentResolver
  - ArgumentResolver 는 Controller 메소드의 파라미터 중 조건에 맞는 파라미터가 있으면 원하는 객체에 바인딩
  - 특정 어노테이션이 앞에 붙은 파라미터만 ArgumentResolver 를 통하도록 설정 가능하여 @RequestUser 로 인가요청을 위한 파라미터 임을 명시, AuthUser Class 에 인가 요청을 하는 사용자와 검증된 토큰 정보를 담도록 구현 
  - JWT 는 Stateless 하다는 장점때문에 로그아웃을 구현하는데 어려움

* RefreshToken
  - Token 을 클라이언트에 최초 발급할 때 AccessToken과 RefreshToken 두 가지를 발급 

* RefreshToken 활용 Logout 요청 흐름

1. 로그인 시 발급된 RefreshToken 을 userId 와 함께 테이블에 저장 
2. AccessToken 재발급 요청 시 넘어온 RefreshToken 이 테이블에 존재하는지 체크
3. 해당 토큰이 없다면, “권한 없음” 응답
4. 해당 토큰이 있다면, 새 AccessToken을 발급 
5. 로그아웃 요청 시 해당 userId 를 가진 RefreshToken 을 테이블에서 삭제 
6. 위의 RefreshToken 으로 AccessToken 재발급 요청 시 테이블에 해당 토큰이 존재하지 않기 때문에 재발급 불가 

## 📰 API 문서 확인
Swagger를 사용하여 API 문서를 확인할 수 있습니다. 브라우저에서 `http://localhost:8080/swagger-ui.html`로 접속하거나, 아래의 Notion 링크를 확인해주세요.
- https://www.notion.so/6-9564aab0c22f454ba0ffe8652c4f28e1?pvs=4

## 📜 라이센스

이 프로젝트는 MIT 라이센스를 따릅니다. 자세한 내용은 `LICENSE` 파일을 참조하세요.

## 🔨 빌드 환경

* **Language:** Kotlin
* **IDE:** Intellij
* **SDK:** Eclipse Temurin 22.0.1
