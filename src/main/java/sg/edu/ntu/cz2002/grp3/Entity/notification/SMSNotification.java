package sg.edu.ntu.cz2002.grp3.Entity.notification;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMSNotification implements INotification{
    // Find your Account Sid and Token at twilio.com/user/account
    public static final String ACCOUNT_SID = "AC517f614e8eede2731b6917a3874c30ef";
    public static final String AUTH_TOKEN = "bf646d67dd2720affe7a80af4c26d775";
    public static final String from = "+12052367076";
    public static boolean hasInit = false;
    public final String to;
    public final String body;

    public SMSNotification(String to, String body){
        this.to = to;
        this.body = body;
        if (!hasInit){
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            hasInit = true;
        }
    }

    @Override
    public boolean send() {
        Message message = Message.creator(new PhoneNumber(to), new PhoneNumber(from), body).create();
        if (message.getErrorCode() == null){
            System.out.println("System: SMS has been sent to " + to);
            return true;
        }
        return false;
    }

}
