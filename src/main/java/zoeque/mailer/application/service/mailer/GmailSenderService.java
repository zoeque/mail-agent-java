package zoeque.mailer.application.service.mailer;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import zoeque.mailer.application.dto.MailDto;
import zoeque.mailer.application.event.MailRequestEvent;
import zoeque.mailer.configuration.mail.MailServiceCollector;
import zoeque.mailer.domain.model.MailService;
import zoeque.mailer.domain.model.MailServiceProviderModel;

@Slf4j
@Service
@MailService(MailServiceProviderModel.GMAIL)
public class GmailSenderService extends AbstractMailSenderService {
  private MailSender sender;
  private SimpleMailMessage simpleMailMessage;

  public GmailSenderService(@Value("${zoeque.limitchecker.mail.address.to:null}")
                            String toMailAddress,
                            @Value("${zoeque.limitchecker.mail.address.from:null}")
                            String fromMailAddress,
                            @Value("${zoeque.limitchecker.mail.provider:OTHERS}")
                            MailServiceProviderModel model,
                            MailServiceCollector collector,
                            MailSender sender,
                            SimpleMailMessage simpleMailMessage) {
    super(toMailAddress, fromMailAddress, model, collector);
    this.sender = sender;
    this.simpleMailMessage = simpleMailMessage;
  }

  @Override
  public Try<MailDto> sendMailToUser(MailDto dto) {
    try {
      simpleMailMessage.setFrom(dto.getHeaderFrom());
      simpleMailMessage.setTo(dto.getHeaderTo());
      simpleMailMessage.setSubject(dto.getSubject());
      simpleMailMessage.setText(dto.getMessage());

      sender.send(simpleMailMessage);
      return Try.success(dto);
    } catch (Exception e) {
      return Try.failure(e);
    }
  }
}
