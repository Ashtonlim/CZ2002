package sg.edu.ntu.cz2002.grp3.Controller;

import java.util.ArrayList;

import sg.edu.ntu.cz2002.grp3.Entity.notification.INotification;

/**
 * The Class NotificationManager is an abstraction for the notification
 * packages.
 * 
 * @author Guat Kwan, Wei Xing, Ashton, Yi Bai, Zhe Ming
 */
public class NotificationManager {

    /** The Constant queue. */
    private static final ArrayList<INotification> queue = new ArrayList<>();

    /**
     * Send notification by taking in a notification object of some type e.g. Email,
     * SMS, etc.
     *
     * @param notification the notification
     */
    public static void sendNotification(INotification notification) {
        notification.send();
    }

    /**
     * Adds the to queue.
     *
     * @param notification the notification
     */
    public static void addToQueue(INotification notification) {
        queue.add(notification);
    }

    /**
     * Send queue.
     *
     * @param number the number
     */
    public static void sendQueue(int number) {
        if (number == 0) {
            number = queue.size();
        } else if (number > queue.size()) {
            number = queue.size();
        }

        for (int i = 0; i < number; i++) {
            sendNotification(queue.remove(0));
        }
    }

}
