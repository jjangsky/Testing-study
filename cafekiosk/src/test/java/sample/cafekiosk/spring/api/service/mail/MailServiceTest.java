package sample.cafekiosk.spring.api.service.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import sample.cafekiosk.spring.client.mail.MailsendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

// 해당 어노테이션을 붙여야 Test 시작할 때 Mockito 를 사용하여 Mock 객체 생성 가능
// @Mock 어노테이션 사용 안하고 Mockito.mock Method를 사용해서 직접 생성하거면 필요 없음
@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock
    private MailsendClient mailSendClient;

    /**
     * `@Spy`
     * 만약 mailSendClient가 여러 Method를 갖고 있고
     * 테스트 시, 사용하고자 하는 메소드는 오직 mailSendEmail 메소드 뿐이고
     * 다른 메소드들은 기존과 동일하게 동작하길 바랄 때 사용\
     * 단, @Spy 어노테이션 사용 시, Mockito의 `when`절 사용이 불가하다.
     * -> 실제 객체 기반으로 만들어지기 때문
     *
     *
     */

    /*@Spy
    private MailsendClient mailSendClient;*/

    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    private MailService mailService;

    // 순수 Mockito Test(단위 테스트)
    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail(){

        // given
        /**
         * 상단에 @Mock 어노테이션을 사용하여 객체 생성 가능함
         * MailsendClient mailSendClient = Mockito.mock(MailsendClient.class);
         * MailSendHistoryRepository mailSendHistoryRepository = Mockito.mock(MailSendHistoryRepository.class);
        */

        /**
         * `@InjectMock` 어노테이션을 사용하여 DI 와 비슷한 개념으로 사용 가능함
         * MailService mailService = new MailService(mailSendClient, mailSendHistoryRepository);
         */

        // stub 처리(@Spy 시 사용 불가)
        when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(true);

        /**
         * `@Spy` 시 사용, 실제 객체를 사용하기 때문
         *  -> sendEmail Method 만 Stubing 처리 되고 나머지 Method 들은 실제 객체 사용
         * doReturn(true)
         *       .when(mailSendClient)
         *       .sendEmail(anyString(), anyString(), anyString(), anyString());
         *
         */


        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        assertThat(result).isTrue();
        // save 행위에 대한 횟수 검증 -> 1번 불렸는가?
        Mockito.verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));

    }

}