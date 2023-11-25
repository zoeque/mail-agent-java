package zoeque.mailer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"zoeque.mailer"})
@ConfigurationPropertiesScan(basePackages = {"zoeque.mailer"})
public class MailSenderModuleTest {
  @Test
  public void contextLoad() {

  }
}
