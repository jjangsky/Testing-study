package sample.cafekiosk.spring.client.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MailsendClient {
    public boolean sendEmail(String fromEmail, String toEmail, String subject, String content) {
        // 메일 전송
        log.info("메일 전송");
        // 실제 메일이 날라가면 테스트할 때 비용이 소모 되어서  오류로 확인
        throw new IllegalArgumentException("메일 전송");
    }
}
