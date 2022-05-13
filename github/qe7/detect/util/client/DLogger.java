package github.qe7.detect.util.client;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DLogger {

    public static String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

    public static void DEBUG(String string) {
        System.out.println("[" + timeStamp + "] [Detect thread/DEBUG] " + string);
    }

    public static void INFO(String string) {
        System.out.println("[" + timeStamp + "] [Detect thread/INFO] " + string);
    }

    public static void WARN(String string) {
        System.out.println("[" + timeStamp + "] [Detect thread/WARN] " + string);
    }

    public static void ERROR(String string) {
        System.out.println("[" + timeStamp + "] [Detect thread/ERROR] " + string);
    }

}
