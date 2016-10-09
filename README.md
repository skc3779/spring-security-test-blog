## spring-security-test-blog

스프링 시큐리티에 대한 테스트 소스 코드 입니다.

### 블로그

http://www.programcreek.com/java-api-examples/index.php?api=org.springframework.security.test.context.support.WithUserDetails

### pom.xml 수정

1. spring-security 4.0.0.M2 -> 4.1.3.RELEASE 업그레이드
2. spring 4.0.2.RELEASE -> 4.3.2.RELEASE 업그레이드

### 임베디드 톰켓 실행

EMBEDDED TOMCAT 참고
https://www.mkyong.com/maven/how-to-create-a-web-application-project-with-maven/
https://www.lesstif.com/pages/viewpage.action?pageId=14090451

```bash
$> mvn eclipse:eclipse
$> mvn tomcat7:run
```