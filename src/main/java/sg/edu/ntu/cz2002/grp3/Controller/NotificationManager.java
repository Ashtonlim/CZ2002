package sg.edu.ntu.cz2002.grp3.Controller;

import java.util.ArrayList;

import sg.edu.ntu.cz2002.grp3.Entity.Email;

public class NotificationManager {
    public static boolean notifyEmail(String to, String subject, String body) {
        if (Email.sendMail(to, subject, body)) {
            System.out.println("System: Email sent to " + to);
            return true;
        }

        System.out.println("System: Email failed to send");
        return false;
    }

    public static boolean notifyAllEmail(ArrayList<String> tos, ArrayList<String> subjects, ArrayList<String> bodys) {
        if (tos.size() == subjects.size() && tos.size() == bodys.size()) {
            for (int i = 0; i < tos.size(); i++) {
                if (Email.sendMail(tos.get(i), subjects.get(i), bodys.get(i))) {
                    System.out.println("System: Email sent to " + tos.get(i));

                }
            }
            return true;
        }

        System.out.println("System: Emails failed to send");
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
