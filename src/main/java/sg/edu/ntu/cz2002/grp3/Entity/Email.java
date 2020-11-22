package sg.edu.ntu.cz2002.grp3.Entity;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
    private static String usr = "oopmystarapp@gmail.com";
    private static String pwd = "myStarApp1";

    // public Email() {
    // }

    // public Email(String usr, String pwd) {
    // this.usr = usr;
    // this.pwd = pwd;
    // }

    public static boolean sendMail(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usr, pwd);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(usr));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);

            return true;

        } catch (MessagingException e) {
            System.out.println(e);
            throw new RuntimeException(e);

        }

    }
}
