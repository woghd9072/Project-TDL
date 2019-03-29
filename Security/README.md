# Security (참고 : [백기선 YouTube](https://www.youtube.com/watch?v=zANzxwy4y3k))

### Security 적용하기 (참고 : [Spring Guide](https://spring.io/guides/gs/securing-web/))
- 새 프로젝트 생성
- 의존성 설정
  ~~~
  dependencies {
    compile("org.springframework.boot:spring-boot-starter-thymeleaf")
    compile("org.springframework.boot:spring-boot-starter-web")
    testCompile("junit:junit")
    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("org.springframework.security:spring-security-test")
  }
  ~~~
- `home.html` & `hello.html` 생성
  ~~~
  <!DOCTYPE html>
  <html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
  <head>
      <title>Spring Security Example</title>
  </head>
  <body>
    <h1>Welcome!</h1>

    <p>Click <a th:href="@{/hello}">here</a> to see a greeting.</p>
  </body>
  </html>
  ~~~

  ~~~
  <!DOCTYPE html>
  <html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
  <head>
    <title>Hello World!</title>
  </head>
  <body>
    <h1>Hello world!</h1>
  </body>
  </html>
  ~~~
- ViewController 추가해주는 `MvcConfig` 생성
  1. `registry.addViewController("/home").setViewName("home");` `"/home"` 입력하면, `home.html`을 반환
  2. `registry.addViewController("/").setViewName("home");` `"/"` 입력하면, `home.html`을 반환
  3. `registry.addViewController("/hello").setViewName("hello");` `"/hello"` 입력하면, `hello.html`을 반환
  4. `registry.addViewController("/login").setViewName("login");` `"/login"` 입력하면, `login.html`을 반환 (현재 `login.html`이 없으므로 에러 발생)
- 로그인 하지 못한 사용자들이 hello 창을 못 가게 막아보자
  - here을 눌렀을 때 `hello.html`이 뜨지 않도록 함
  - `"/login"`을 입력했을 때, Security가 제공하는 기본 `login.html`이 반환
- `WebSecurityConfig` 생성
  - `@EnableWebSecurity`를 사용하면 Spring Security가 제공하는 것이 날라감
  - `configure` 함수 생성
    - `http.authorizeRequests()`은 요청을 어떻게 보안을 걸 것인가 하는 내용이고 가장 중요한 설정임
    - `.antMatchers("/", "/home").permitAll()`를 통해 어떠한 특정한 패턴에 해당되는 요청에다가 설정해주는 것, 여기서는 root와 home을 요청하면 로그인을 안한 사용자들도 볼 수 있게 허용시켜줌
    - `.anyRequest().authenticated()` : 다른 요청들은 인증이 필요하다는 명령임 즉, 로그인을 한 사람들만 볼 수 있음을 의미
    - 위에 코드처럼 하면 css파일이나 js파일들이 적용되지 않기 때문에 나중에 고치도록 함
    - `.loginPage("/login").permitAll()` : 로그인 페이지로 가는 요청이 무엇임을 명시해주고 모든 사람들이 로그인 페이지로 갈 수 있도록 허용해줌
  - 인메모리 방식으로 User 정보를 관리해주기 위해 `UserDetailsService userDetailsService()` 생성
    - builder패턴 형식을 이용하여 user 생성 (여러가지 설정이 필요하지만 user에 가장 핵심적인 설정만 build 해줌)
      ```
      UserDetails user =
                User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
      ```
   - `roles("")`을 통해 상세한 권한 부여 가능 ex) "USER", "ADMIN" ...
- `login.html` 생성
  ~~~
  <!DOCTYPE html>
  <html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
  <head>
    <title>Spring Security Example </title>
  </head>
  <body>
    <div th:if="${param.error}">
      Invalid username and password.
    </div>
    <div th:if="${param.logout}">
      You have been logged out.
    </div>
    <form th:action="@{/login}" method="post">
      <div><label> User Name : <input type="text" name="username"/> </label></div>
      <div><label> Password: <input type="password" name="password"/> </label></div>
      <div><input type="submit" value="Sign In"/></div>
    </form>
  </body>
  </html>
  ~~~
- `hello.html` 변경
  ~~~
  <!DOCTYPE html>
  <html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
  <head>
    <title>Hello World!</title>
  </head>
  <body>
    <h1 th:inline="text">Hello [[${#httpServletRequest.remoteUser}]]!</h1>
    <form th:action="@{/logout}" method="post">
      <input type="submit" value="Sign Out"/>
    </form>
  </body>
  </html>
  ~~~
- 최종 실행
  - `"/"`나 `"/home"`을 입력하면 접근 가능
  - `"here"`을 누르면 중간에 `Filter`가 가로채서 `formLogin()`을 처리하는 곳으로 보냄
  - `MvcConfig`에서 로그인을 받으면 `login.html`을 반환하게 함
  - 생성된 user 정보로 로그인을 해줌
  - 로그인을 해주면 원래 가고자 했던 hello창으로 이동함
  - 로그아웃 버튼을 누르면 로그인 화면으로 감
  - `.logoutSuccessUrl("/home")`으로 설정해주면 home 화면으로 감
