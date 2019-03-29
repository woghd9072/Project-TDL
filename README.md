# Project-TDL
스프링부트를 이용하여 ToDoList 만들기

## 목적
- Spring Boot & JPA 학습 및 Web Application(To Do List) 개발
- Spring Security 학습 및 로그인, 회원가입 구현

## 개발환경(필요사항)
|Tool|Version|
|:---:|:---:|
|Spring|Spring boot 2.1.3|
|OS|Window 10|
|IDE|IntelliJ|
|JDK|JDK 8|
|DB|MySQL|
|Build Tool|Gradle 5.2.1|

## With Member
- [민경환](https://github.com/ber01)
- [박동현](https://github.com/pdh6547)
- [신무곤](https://github.com/mkshin96)
- [신재홍](https://github.com/woghd9072)
- [양기석](https://github.com/yks095)
- [엄태균](https://github.com/etg6550)
- [임동훈](https://github.com/dongh9508)
- [최광민](https://github.com/rhkd4560)
- [하상엽](https://github.com/hagome0)

## Day별 진행
### Day1
- Gradle Project로 생성
- ToDo domain 생성

### Day2
- domain(ToDoList), repository(ToDoListRepository) 생성
- MySQL 연동 (문제점 발생)
  1. TimeZone 오류 : `url: jdbc:mysql://127.0.0.1:3306/db?serverTimezone=Asia/Seoul`로 수정시 오류 해결!!
- CommandLineRunner로 DB 생성
- controller 생성
  - 서비스 호출 및 뷰 생성
- service 생성
  - 저장소 호출 및 데이터 반환
- list.html 생성
- 부트스트랩 적용시키기(css 활용)
  - `<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>`
    `<link rel="stylesheet" href="/css/bootstrap.min.css"/>`

### Day3
- 부트스트랩 대신 css를 만들어 사용
- form 태그를 사용해서 등록 창 만듬
- 등록하기 성공

### Day4
- 삭제 기능 구현
  - 각 객체의 현재의 idx값을 받아 오기 위해 `var data = $(this).val();`을 사용

### Day5
- Status 변환 구현
  - 클릭시 아이콘을 바꾸고 CompleteDate도 찍히게 만듬

### Day6
- 수정 기능 구현
  - 수정할 값을 받아 오기 위해 `$(this).parent().parent().parent().find('.description')`을 사용
  - 수정 버튼 클릭시 `contenteditable` 활성화 & 텍스트에 `focus`를 맞춤
  - 다시 버튼 클릭시 `contenteditable` 비활성화 후에 변경내용 전송

### Day7
- 로그인 구현(View는 아직 미완성)
  - `LoginController` 클래스 생성
  - ajax를 통해 input태그에 입력한 Id와 Password를 받아오는것을 구현
  - `Map<String, String>` 형식(Key, Value)으로 받아온 값을 디비에 있는 해당 user에 해당하는 Id와 Password를 비교하여 일치하면 로그인 되게 구현
- 로그인한 해당 유저 찾기
  - `CommandLineRunner`를 통해 우선 한 명의 user객체를 생성
  - 한 명이기 때문에 `ToDoListService`에서 `UserRepository`를 통해 찾아옴
  - `userRepository.getone(1)`을 사용
- 로그인 성공
  - ajax를 통해 값을 받아와서 받아온 값으로 찾은 user객체를 `currentUser`에 넣어줌

### Day8
- 회원가입 구현(View는 아직 미완성)
  - `RegisterController` 클래스 생성
  - 등록창에서 Id와 Password와 Email값을 input태그에 입력하고, 그 입력한 값을 ajax를 통해 넘겨 받아옴
  - 받아온 값을 UserRepository를 통해 DB에 저장해줌

### Day9
- 로그인을 안하고 list 화면이 조회되는 것을 해결
  - `currentUser` 값이 null 값일 경우 `redirect:/login`을 해줌으로써 `list` 화면으로 못가게 하였음
- 로그아웃 구현(버튼은 헤더의 이미지)
  - 버튼을 눌렀을 시에 `currentUser` 값을 null로 주면서 `redirect:/login`으로 하였음

### Day10
- ToDoList 클래스에 `@OneToOne` 으로 단방향 관계를 맺어줌
- ToDoList에 해당 유저가 쓴 list에 User객체에 `PK`인 `user_idx` 값을 넣어줌
  - `currentUser`의 값을 DB에서 찾기 위해 찾을 수 있는 Key값이 Id이기 때문에 `UserRepository`에 쿼리를 짰음
  - `User findById(String id);` Id로 통해 찾은 user를 ToDoList에 `toDoList.setUser(currentUser)` 해줌

### Day11
- 현재 유저가 자신이 썼던 게시물만 볼 수 있게 해줌
  - `ToDoList`에 있는 `user_idx` 값을 통해 현재 유저의 idx값과 비교하여 DB에서 값을 가져와야함
  - `toDoListRepository`에 `user_idx`를 통한 값을 찾기 위해 새로운 쿼리를 짜줌
  - `List<ToDoList> findByUserIdx(Integer idx);`
  - 서비스에 리스트를 가져오는 함수에 새로운 쿼리를 이용해 `toDoListRepository.findByUserIdx(idx)`로 바꿔줌
  - `toDoListController`에서 `@GetMapping('/list')`를 해줄 때 `model`에 addAttribute 해줄 시에 `toDoListService.findList(currentUser.getidx())`로 해줌

### Day12
- `ToDoList`와 `User`를 `@ManyToOne`,`@OneToMany`로 연관 관계 매핑
  - `ToDoList` 클래스에 `@ManyToOne`를 해주고, `User` 클래스에 `@OneToMany`를 해줌
  - 양방향 관계를 맺어주기 위해 `mappedBy`를 사용해줌(백기선 유튜브 영상을 참고하였음)
  - 통상적으로 `FK`를 가진 객체가 오너쉽을 가지고 있는 객체이다. 따라서 오너쉽이 없는 객체쪽에서 오너쉽이 있는 객체에 참조하고 있는 변수명을 `mappedBy`를 해줘야 함
- `User` 객체에 `ToDoList` 객체를 넣어줘야함 (그냥 해줬을 때는 `User`에서 매핑이 안됬었음)
  - 백기선 유튜브를 참고하여 `User` 클래스에 `add()` 함수를 추가해줘서 List객체가 Post될 때 `add()` 함수를 써줘서 `ToDoList`에 user를 set해주고 `User`에 todolist를 set해줌

### [Day13](./Security)
- Spring Security 관련 내용
