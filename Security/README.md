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
      ``` java
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

## Security2([참고](https://www.youtube.com/watch?v=fG21HKnYt6g))

- 위의 테스트는 인메모리에 사용자를 만들었는데 사실상 이것은 잘 못된 방식임
- 일방적으로 사용자를 만들어주지 않기 때문에 `<form>`을 통해 읽어 오거나 `Rest API`를 통해 읽어 들임
- 로그인이 성공되면 `authentication` 객체를 `Security Context` 안에 넣어줌으로써 매번 요청이 와도 `Context`에 있는 객체를 보고 인증이 되어 있다는 것을 인지하고 있어서 매번 로그인을 할 필요가 없음
- 사용자를 추상화해준 `UserDetailsService`를 지워줌
- 아래에 짜여진 코드는 구조만 맞추기 위해 사용한 것이어서 실제로 프로젝트나 다른 곳에서는 다르게 해줘야 함
- `Account` 생성
  - `Getter & Setter` 해줌
- `AccountController` 생성
  - `@GetMapping("/create")`도 사실은 다 Post로 해줘야 함
  - `RequestBody`로 받아 와야하지만 그냥 `Account account = new Account();`로 받아왔다고 치고 만들기로 함
  - 새로운 Account를 만들게 되는 과정
  ~~~ java
  @RestController
  public class AccountController {

    @Autowired
    AccountRepository accounts;

    @GetMapping("/create")
    public Account create() {
        Account account = new Account();
        account.setEmail("jaehong@mail.com");
        account.setPassword("password");

        return accounts.save(account);
    }
  }
  ~~~
- `AccountService` 생성
  - 위에서 지운 `UserDetailsService`를 서비스에 해줌 `public class AccountService implements UserDetailsService`
  - `account`는 우리 코드 안에 domain이기 때문에 `Spring Security`는 `account`가 뭔지 모르기 때문에 추상화되어 있는 인터페이스 `UserDetails`를 씀
  - `public Collection<? extends GrantedAuthority> getAuthorities()` 권한에 관한 정보를 들고 있음
  - 권한 정보는 `Account` 클래스에서 관리해주는 것이 맞지만 테스트 인코더에 집중하기 위해 서비스에서 해줌
  - 임의대로 "ROLE_USER"를 리턴해줌
    ~~~ java
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
          List<GrantedAuthority> authorities = new ArrayList<>();
          authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
          return authorities;
    }
    ~~~
  - `UserName`은 유일해야 함
    ~~~ java
    @Override
    public String getUsername() {
        return account.getEmail() ;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    ~~~
  - 계정 비활성화를 해주고 싶으면 이 코드를 이용해 쓰라고 함
    ~~~ java
    @Override
    public boolean isEnabled() {
        return false;
    }
    ~~~
- `AccountRepository` 생성
  - JPA로 구현하면 간단하겠지만 직접 구현한다고 함
  - `private Map<String, Account> accounts = new HashMap<>();`이 실제 Account들을 들고 있을 메모리
  - 아이디를 자동으로 생성하기 위해 `private Random random = new Random();` 사용
  - `account.setId(random.nextInt());` 이렇게 사용하면 중복 검사가 안되지만 일단 쓰기로 함
  ~~~ java
  @Repository
  public class AccountRepository {

    private Map<String, Account> accounts = new HashMap<>();
    private Random random = new Random();

    public Account save(Account account) {
        account.setId(random.nextInt());
        accounts.put(account.getEmail(), account);
        return account;
    }

    public Account findByEmail(String username) {
        return accounts.get(username);
    }
  }
  ~~~
- 사용자 생성할 때에도 인증 없이 할 수 있게 수정해줌 `.antMatchers("/", "/home", "/create").permitAll()`
- 실행을 하게 되면 `/create`로 만들어진 계정으로 로그인 할 수 없음. 그 이유는 스프링 부트가 업데이트되면서 패스워드 인코더가 바꼈기 때문
  - `java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"` 에러 발생 : password에 대한 Id가 없기 때문 ([참조](https://docs.spring.io/spring-security/site/docs/5.0.2.BUILD-SNAPSHOT/reference/htmlsingle/#core-services-password-encoding))
  - `WebSecurityConfig`에서 `PasswordEncoder` 등록
    ~~~ java
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    ~~~
  - `PasswordEncoder`에 대한 설정은 `AccountService`에서 해줌
    ~~~ java
    public Account save(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accounts.save(account);
    }
    ~~~
  - `AccountController` 변경
    ~~~ java
    @RestController
    public class AccountController {

      @Autowired
      AccountService accountService;

      @GetMapping("/create")
      public Account create() {
        Account account = new Account();
        account.setEmail("jaehong@mail.com");
        account.setPassword("password");

        return accountService.save(account);
      }
    }
    ~~~
- 성공적으로 실행 완료
- 추가적인 부분
  - `AccountService`에서 `UserDetails` 코드를 줄일 수 있음
    ~~~ java
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accounts.findByEmail(username);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(account.getEmail(), account.getPassword(), authorities);
    }
    ~~~
    - `User`안에 `UserDetails`에 대한 내용이 있음
  - `WebSecurityConfig`에서 `.hasRole("")`으로 ""안에 권한을 임명하면 권한에 대한 접근을 설정해줄 수 있음
