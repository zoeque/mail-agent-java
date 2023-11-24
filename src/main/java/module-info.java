module mail.agent.java.main {
  exports zoeque.mailer.application.event;
  requires io.vavr;
  requires lombok;
  requires org.slf4j;
  requires jakarta.mail;
  requires spring.context;
  requires spring.beans;
  requires spring.context.support;
}