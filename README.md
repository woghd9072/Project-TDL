# Project-TDL
스프링부트를 이용하여 ToDoList 만들기

|Tool|Version|
|:---:|:---:|
|Spring|Spring boot 2.1.3|
|OS|Window 10|
|IDE|IntelliJ|
|JDK|JDK 8|
|DB|MySQL|
|Build Tool|Gradle 5.2.1|


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
