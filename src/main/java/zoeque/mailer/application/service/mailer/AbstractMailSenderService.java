package zoeque.mailer.application.service.mailer;

import io.micrometer.common.util.StringUtils;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import zoeque.mailer.application.dto.MailDto;
import zoeque.mailer.application.event.MailRequestEvent;
import zoeque.mailer.configuration.mail.MailServiceCollector;
import zoeque.mailer.domain.model.MailServiceProviderModel;


/**
 * Class to send e-mail
 */
@Slf4j
public abstract class AbstractMailSenderService implements IMailService {
  protected String toMailAddress;
  protected String fromMailAddress;
  protected MailServiceProviderModel model;
  MailServiceCollector collector;

  public AbstractMailSenderService(@Value("${zoeque.limitchecker.mail.address.to:null}")
                                   String toMailAddress,
                                   @Value("${zoeque.limitchecker.mail.address.from:null}")
                                   String fromMailAddress,
                                   @Value("${zoeque.limitchecker.mail.provider:GMAIL}")
                                   MailServiceProviderModel model,
                                   MailServiceCollector collector) {
    this.toMailAddress = toMailAddress;
    this.fromMailAddress = fromMailAddress;
    this.model = model;
    this.collector = collector;
  }

  /**
   * The event listener that receives event with the message
   * and send it to the end point defined in application.properties
   * or specified field in the given event.
   *
   * @param event The instance of {@link MailRequestEvent} with full fields..
   */
  @EventListener
  public void sendMail(MailRequestEvent event) {
    MailRequestEvent request = determineMailAddresses(event).get();
    collector.findMailService(model)
            .get()
            .sendMailToUser(new MailDto(
                    event.getToMailAddress(),
                    event.getFromMailAddress(),
                    event.getSubject(),
                    event.getMessage()
            ));
  }

  private Try<MailRequestEvent> determineMailAddresses(MailRequestEvent event) {
    try {
      if (StringUtils.isEmpty(event.getToMailAddress())) {
        event.setToMailAddress(toMailAddress);
      }
      if (StringUtils.isEmpty(event.getFromMailAddress())) {
        event.setFromMailAddress(fromMailAddress);
      }
      return Try.success(event);
    } catch (Exception e) {
      return Try.failure(e);
    }
  }
}
