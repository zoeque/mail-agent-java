package zoeque.mailer.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The mail with full set of the required parameters.
 */
@AllArgsConstructor
@Getter
public class MailDto {
  String headerTo;
  String headerFrom;
  String subject;
  String message;
}
