package sg.edu.ntu.cz2002.grp3.Entity.notification;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailNotification implements INotification {
    private static String usr = "oopmystarapp@gmail.com";
    private static String pwd = "myStarApp1";
    private static final Properties props;
    static {
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }
    private final String to;
    private final String subject;
    private final String body;

    public EmailNotification(String to, String subject, String body){
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    @Override
    public boolean send() {
        if (sendMail(to, subject, body)) {
            System.out.println("System log: Email sent to " + to);
            return true;
        }

        System.out.println("System log: Email failed to send");
        return false;
    }

    private static boolean notifyAllEmail(ArrayList<String> tos, ArrayList<String> subjects, ArrayList<String> bodys) {
        if (tos.size() == subjects.size() && tos.size() == bodys.size()) {
            for (int i = 0; i < tos.size(); i++) {
                if (sendMail(tos.get(i), subjects.get(i), bodys.get(i))) {
                    System.out.println("System log: Email sent to " + tos.get(i));

                }
            }
            return true;
        }

        System.out.println("System log: Emails failed to send");
        return false;
    }

    public static void setUsr(String usr) {
        EmailNotification.usr = usr;
    }

    public static void setPwd(String pwd) {
        EmailNotification.pwd = pwd;
    }

    public static void setFrom(String usr, String pwd) {
        setUsr(usr);
        setPwd(pwd);
    }

    private static boolean sendMail(String to, String subject, String body) {

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
