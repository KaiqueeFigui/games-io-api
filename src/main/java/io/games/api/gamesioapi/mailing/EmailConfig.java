package io.games.api.gamesioapi.mailing;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

@Getter
@Setter
public class EmailConfig {

    private final String emailAddress;
    private SimpleEmail email;

    public EmailConfig(String emailAddress, String emailPassword) {
        this.email = new SimpleEmail();
        this.emailAddress = emailAddress;

        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(emailAddress, emailPassword));
        email.setSSLOnConnect(true);
    }

    public boolean sendEmail(String message, String subject, String to){

        try {

            email.setFrom(emailAddress);
            email.setSubject(subject);
            email.setMsg(message);
            email.addTo(to);
            email.send();

            return true;
        }catch (EmailException e) {
            e.printStackTrace();
            return false;
        }

    }
}
