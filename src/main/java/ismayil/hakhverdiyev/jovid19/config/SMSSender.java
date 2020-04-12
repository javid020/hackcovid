package ismayil.hakhverdiyev.jovid19.config;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMSSender {
    public static final String ACCOUNT_SID =
            "AC0d3852c289bc26baaab7cbfdb4bf6de5";
    public static final String AUTH_TOKEN =
            "7ec8ef432550d924eecfcb47860d8cc0";


    public static void smsSender(String phone, int smsCode) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message
                .creator(new PhoneNumber(phone), // to
                        new PhoneNumber("+12052559480"), // from
                        String.valueOf("Your voucher code for future use:" + smsCode
                                + "#stayHome\n"))
                .create();
    }
}
