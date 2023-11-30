# Mail-agent-java
## Overview
The library that performs easy implementation, easy embedded to send an e-mail with simple settings on the application.  

## Usage
### Publish the mail event to the library
This module provides the class `MailRequestEvent` as the Data Transfer Object formatted.  
Set all fields, and call `ApplicationEventPublisher#publishEvent()` provided by the Spring Framework.
Publishing the instance of the `MailRequestEvent` is the trigger of the send message to the specified user.  

Example;
```java
  @Autowired
  ApplicationEventPublisher publisher;

  public void doSomething() {
    MailRequestEvent request = new MailRequestEvent("Subject of the mail",
            "content of the mail",
            "hogehoge(at)example.com",
            "piyopiyo(at)example.com");
    publisher.publishEvent(request);
    // do something, omitted
  }
```

## Development
This application is built with the environment bellow;

- OpenJDK 17
- Spring boot 3.1.1
- IntelliJ IDEA 2023.1.2

Note that this module is needed the gradle.properties on the root directory.
The property file is used when the module is published to GitHub Package manager.  
Here is an example of the gradle.properties bellow (name must be set as your name);

```properties
GITHUB_USERNAME = zoeque
GITHUB_TOKEN = [[TOKEN]]
```

