package zoeque.mailer.application.service.mailer;

import io.vavr.control.Try;
import zoeque.mailer.application.dto.MailDto;

/**
 * The interface for mail services
 */
public interface IMailService {
  /**
   * Send e-mail with full parameter in application.properties
   */
   Try<MailDto> sendMailToUser(MailDto dto);
}
