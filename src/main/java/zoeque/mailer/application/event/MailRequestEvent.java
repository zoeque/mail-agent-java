package zoeque.mailer.application.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import zoeque.mailer.application.service.mailer.IMailService;

/**
 * The email requesting with the message.
 */
@Getter
@NoArgsConstructor
public class MailRequestEvent {
  String subject;
  String message;
  String fromMailAddress;
  String toMailAddress;

  /**
   * The mail requesting event.
   * This event is required the subject and the message body part.
   * Mail address (from and to) also can be set, but it is not required.
   * If the address is null in the instance of this class,
   * use address defined in application.properties.
   * This event is notified via ApplicationEventPublisher provided by Spring framework
   * and receive this event in {@link IMailService}.
   *
   * @param subject         The subject of the mail. Must not be null.
   * @param message         The message of the mail. Must not be null.
   * @param fromMailAddress The mail address to send From.
   * @param toMailAddress   The mail address to send To.
   */
  public MailRequestEvent(String subject,
                          String message,
                          String fromMailAddress,
                          String toMailAddress) {
    this.subject = subject;
    this.message = message;
    this.fromMailAddress = fromMailAddress;
    this.toMailAddress = toMailAddress;
  }

  /**
   * The setter for the from address.
   * TODO hind from any other process, use module.
   *
   * @param fromMailAddress The endpoint of the mail address send From.
   */
  public void setFromMailAddress(String fromMailAddress) {
    if (fromMailAddress == null) {
      throw new IllegalArgumentException("The from mail address is required!!");
    }
    this.fromMailAddress = fromMailAddress;
  }


  /**
   * The setter for the To address.
   *
   * @param toMailAddress The endpoint of the mail address send To.
   */
  public void setToMailAddress(String toMailAddress) {
    if (toMailAddress == null) {
      throw new IllegalArgumentException("The to mail address is required!!");
    }
    this.toMailAddress = toMailAddress;
  }
}
