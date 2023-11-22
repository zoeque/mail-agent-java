package zoeque.mailer.application.service.mailer;

import io.vavr.control.Try;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import zoeque.mailer.application.dto.MailDto;
import zoeque.mailer.configuration.mail.MailServiceCollector;
import zoeque.mailer.domain.model.MailService;
import zoeque.mailer.domain.model.MailServiceProviderModel;

/**
 * The class to send the mail via {@link JavaMailSender}.
 */
@Service
@MailService(MailServiceProviderModel.OTHERS)
public class MailSenderService extends AbstractMailSenderService {
  JavaMailSender javaMailSender;

  public MailSenderService(@Value("${zoeque.limitchecker.mail.address.to:null}")
                           String toMailAddress,
                           @Value("${zoeque.limitchecker.mail.address.from:null}")
                           String fromMailAddress,
                           @Value("${zoeque.limitchecker.mail.provider:OTHERS}")
                           MailServiceProviderModel model,
                           MailServiceCollector collector,
                           JavaMailSender javaMailSender) {
    super(toMailAddress, fromMailAddress, model, collector);
    this.javaMailSender = javaMailSender;
  }

  /**
   * Send e-mail with full parameter in application.properties
   */
  public Try<MailDto> sendMailToUser(MailDto dto) {
    MimeMessage message = javaMailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom(dto.getHeaderFrom());
      helper.setTo(dto.getHeaderTo());
      helper.setSubject(dto.getSubject());
      helper.setText(dto.getMessage());
      javaMailSender.send(message);
      return Try.success(dto);
    } catch (Exception e) {
      return Try.failure(e);
    }
  }
}
