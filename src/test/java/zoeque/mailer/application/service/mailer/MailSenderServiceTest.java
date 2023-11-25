package zoeque.mailer.application.service.mailer;

import io.vavr.control.Try;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import zoeque.mailer.application.dto.MailDto;
import zoeque.mailer.configuration.mail.MailServiceCollector;
import zoeque.mailer.domain.model.MailServiceProviderModel;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MailSenderServiceTest {
  @Mock
  JavaMailSender mockMailSender;
  @Value("${zoeque.integration.test:false}")
  boolean integrationTestMode;
  @Autowired
  MailServiceCollector collector;
  @Autowired
  GmailSenderService gmailSenderService;

  @Test
  public void givenMockMailServiceAndMail_thenSendSuccess() {
    AbstractMailSenderService service
            = new MailSenderService("foo", "bar", MailServiceProviderModel.OTHERS,
            collector, mockMailSender);
    MimeMessage dummyMessage = new MimeMessage((Session) null);
    when(mockMailSender.createMimeMessage()).thenReturn(dummyMessage);
    Try<MailDto> sendTry = service.sendMailToUser(new MailDto("hoge", "piyo", "test", "test"));
    Assertions.assertTrue(sendTry.isSuccess());
  }

  @Test
  public void noArgumentOfMailAddress_thenThrowIllegalArgumentException() {
    AbstractMailSenderService service
            = new MailSenderService("foo", "bar", MailServiceProviderModel.OTHERS,
            collector, mockMailSender);
    MimeMessage dummyMessage = new MimeMessage((Session) null);
    when(mockMailSender.createMimeMessage()).thenReturn(dummyMessage);
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      Try<MailDto> sendTry = service.sendMailToUser(new MailDto(null, "piyo", "test", "test"));
      Assertions.assertTrue(sendTry.isFailure());
      sendTry.get();
    });
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      Try<MailDto> sendTry = service.sendMailToUser(new MailDto("hoge", null, "test", "test"));
      Assertions.assertTrue(sendTry.isFailure());
      sendTry.get();
    });
  }

  /**
   * The test method to send real e-mail message
   * that defined in application.properties.
   */
  @Test
  @Disabled
  public void givenMailServiceAndMail_thenSendSuccess() {
    if (integrationTestMode) {
      String subject = "【テスト】このメールは消費期限管理アプリケーションからのテスト配信メールです。";
      String message = """
              本メールには個人情報が含まれる場合がございます。
              本メールに心当たりがございませんでしたら、お手数をおかけしますが転送や複製は行わず、
              本メールへの返信の上、削除していただきますようお願いいたします。
                               
              /** 消費期限管理アプリケーション **/
              """;
      Try<MailDto> sendTry = gmailSenderService.sendMailToUser(
              new MailDto(null, null, subject, message)
      );
      Assertions.assertTrue(sendTry.isSuccess());
    }
  }
}
