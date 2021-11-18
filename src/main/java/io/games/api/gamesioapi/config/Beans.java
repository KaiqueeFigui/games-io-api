package io.games.api.gamesioapi.config;

import io.games.api.gamesioapi.mailing.EmailConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

    @Value("${spring.mail.username}")
    private String emailAddress;

    @Value("${spring.mail.password}")
    private String emailPassword;

    @Bean
    public EmailConfig emailConfig(){
        return new EmailConfig(emailAddress, emailPassword);
    }

}
