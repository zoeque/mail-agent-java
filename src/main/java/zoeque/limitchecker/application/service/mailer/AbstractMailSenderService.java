package zoeque.limitchecker.application.service.mailer;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import zoeque.limitchecker.configuration.mail.MailServiceCollector;
import zoeque.limitchecker.domain.model.MailServiceProviderModel;


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
   * TODO
   *
   * @param event TODO
   */
  @EventListener
  public void sendMail() {
    if (StringUtils.isEmpty(toMailAddress) || StringUtils.isEmpty(fromMailAddress)) {
      log.error("The mail address must not be null!! Failed to send email!!");
      return;
    }
    collector.findMailService(model)
            .get()
            .sendMailToUser(null,null);
  }
}
