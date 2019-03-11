# Project-TDL
스프링부트를 이용하여 ToDoList 만들기

|도구|버전|
|:---:|:---:|
|Spring|Spring boot 2.1.3|
|OS|Window 10|
|IDE|IntelliJ|
|JDK|JDK 8|
|DB|MySQL|
|Build Tool|Gradle 5.2.1|


### [Day1](https://github.com/woghd9072/Project-TDL/tree/master/Project-TDL)
- Gradle Project로 생성
- ToDo domain 생성

### [Day2]()
- domain(ToDoList), repository(ToDoListRepository) 생성
- MySQL 연동 (문제점 발생)
  1. TimeZone 오류 : url: jdbc:mysql://127.0.0.1:3306/db?serverTimezone=Asia/Seoul로 수정시 오류 해결!!
- CommandLineRunner로 DB 생성
- controller 생성
  - 서비스 호출 및 뷰 생성
- service 생성
  - 저장소 호출 및 데이터 반환
- list.html 생성
