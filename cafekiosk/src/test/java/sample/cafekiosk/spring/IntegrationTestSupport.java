package sample.cafekiosk.spring;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.client.mail.MailsendClient;

@ActiveProfiles("test")
@SpringBootTest
public abstract class IntegrationTestSupport {
    /**
     * 통합 테스트 환경 구축
     * 서비스 테스트 로직에서 간혹 `@ActiveProfiles("test")` 같은 설정을
     * 한 경우도 있고 하지 않은 경우도 있다.
     * 하지만 스프링 부트가 켜지는 시점에서는 이러한 설정차이가 존재하면
     * 서버를 그냥 새로 켜버려서 많은 리소스 낭비가 된다.
     * 이를 해결하기 위해 통합 테스트 환경 클래스를 생성하고
     * 테스트 코드 클래스에 상속해주는 방식으로 작성한다.
     */


    /**
     * (issue)
     * 통합 테스트 환경으로 서비스들을 상속하였는데 OrderSatisticsServiceTest 실행 시,
     * 스프링 서버를 재시작하는 이슈
     *
     * MockBean을 사용하여 메소드를 Stubbing 처리를 하고 있는데
     * 이게 문제가 될 수 있는게 MockBean을 사용하므로 서버를
     * 새로 켜야하는 상황이여서 상위 클래스에서 주입하여 서버를 새로
     * 키는 상황을 만들지 않음
     */
    @MockBean
    protected MailsendClient mailSendClient;


}