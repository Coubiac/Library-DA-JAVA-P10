package com.library.batch.services;

import com.library.batch.beans.EmpruntBean;
import com.library.batch.beans.ExemplaireBean;
import com.library.batch.beans.UserBean;
import com.library.batch.configuration.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService {

    @Autowired
    ApplicationConfiguration applicationConfiguration;



    public void sendmail(UserBean theUser, EmpruntBean theEmprunt, ExemplaireBean theExemplaire) {

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(applicationConfiguration.getSenderEmail(), applicationConfiguration.getSenderPassword());
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(applicationConfiguration.getSenderEmail()));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(theUser.getEmail())
            );
            message.setSubject("Emprunt de livre");
            message.setText("Cher client,"
                    + "\n\n Vous avez emprunté un livre et maintenant il faut nous le rendre !!!\n\n "
                    + "Titre du livre: " + theExemplaire.getBook().getTitle() + "\n"
                    + "Date d'emprunt: " + theEmprunt.getDateEmpruntAsString("dd/MM/yyyy") + "\n"
                    + "Date de retour prévu avant: " + theEmprunt.getDateRetourPrevuAsString("dd/MM/yyyy"));

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
