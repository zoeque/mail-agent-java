package zoeque.mailer.application.service.mailer;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import zoeque.mailer.configuration.mail.MailServiceCollector;
import zoeque.mailer.domain.model.MailService;
import zoeque.mailer.domain.model.MailServiceProviderModel;

@Slf4j
@Service
@MailService(MailServiceProviderModel.GMAIL)
public class GmailSenderService extends AbstractMailSenderService {
  private MailSender sender;

  public GmailSenderService(@Value("${zoeque.limitchecker.mail.address.to:null}")
                            String toMailAddress,
                            @Value("${zoeque.limitchecker.mail.address.from:null}")
                            String fromMailAddress,
                            @Value("${zoeque.limitchecker.mail.provider:OTHERS}")
                            MailServiceProviderModel model,
                            MailServiceCollector collector,
                            MailSender sender) {
    super(toMailAddress, fromMailAddress, model, collector);
    this.sender = sender;
  }

  @Override
  public Try<String> sendMailToUser(String subject, String messageContent) {
    try {
      SimpleMailMessage msg = new SimpleMailMessage();
      msg.setFrom(fromMailAddress);
      msg.setTo(toMailAddress);
      msg.setSubject(subject);
      msg.setText(messageContent);

      sender.send(msg);
      return Try.success(messageContent);
    } catch (Exception e) {
      return Try.failure(e);
    }
  }
}
