package sg.edu.ntu.cz2002.grp3.Controller;

import sg.edu.ntu.cz2002.grp3.Entity.Email;

public class NotificationManager {
    public static boolean notifyEmail(String to, String subject, String body) {
        if (Email.sendMail(to, subject, body)) {
            System.out.println("System: Email sent");
            return true;
        }

        System.out.println("System: Email failed to send");
        return false;
    }

    public static boolean notifySMS(String to, String message) {
        System.out.println("System: Sending out SMS");
        return true;
    }

    public static boolean notifyWhatsApp(String to, String message) {
        System.out.println("System: Sending out WhatsApp message");
        return true;
    }

}
