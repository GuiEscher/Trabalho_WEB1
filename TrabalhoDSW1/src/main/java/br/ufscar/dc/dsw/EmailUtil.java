package br.ufscar.dc.dsw;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {
    private static final String HOST = "smtp.sendgrid.net";
    private static final int PORT = 587;
    private static final String USER = "apikey"; // Usuário fixo do SendGrid
    private static final String PASS = "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";  //API Key do SendGrid

    public static void sendEmail(String to, String subject, String body) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER, PASS);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("Guilhermeeschersalvador@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }
}
