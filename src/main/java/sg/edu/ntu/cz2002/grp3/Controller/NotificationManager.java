package sg.edu.ntu.cz2002.grp3.Controller;

import java.util.ArrayList;

import sg.edu.ntu.cz2002.grp3.Entity.notification.INotification;

public class NotificationManager {
    private static final ArrayList<INotification> queue = new ArrayList<>();

    public static void sendNotification(INotification notification){
        notification.send();
    }

    public static void addToQueue(INotification notification){
        queue.add(notification);
    }

    public static void sendQueue(int number){
        if (number == 0){
            number = queue.size();
        } else if (number > queue.size()) {
            number = queue.size();
        }

        for (int i = 0; i < number; i++){
            sendNotification( queue.remove(0) );
        }
    }

}
