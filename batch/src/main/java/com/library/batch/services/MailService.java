package com.library.batch.services;

import com.library.batch.beans.EmpruntBean;
import com.library.batch.beans.ExemplaireBean;
import com.library.batch.beans.UserBean;
import com.library.batch.configuration.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService {

    @Autowired
    ApplicationConfiguration applicationConfiguration;

    Properties prop;

    Session session;


    public MailService(){
        prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(applicationConfiguration.getSenderEmail(), applicationConfiguration.getSenderPassword());
                    }
                });
    }



    public void sendmail(UserBean theUser, String theSubject, String theMessageContent) {
        try {
            Message theMessage = prepareMessage(theUser.getEmail(), theSubject, theMessageContent);
            Transport.send(theMessage);
            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public Message prepareMessage(String recipient, String subject, String messageContent) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(applicationConfiguration.getSenderEmail()));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(recipient)
        );
        message.setSubject(subject);
        message.setText(messageContent);
        return message;
    }
}
