package healthapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ankit.kaushal on 8/14/2018.
 */

public class DateUtil {
    public static String getDateAsString(Date date){
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("dd-MMM yyyy");
        return simpleDateFormat.format(date);
    }


    public static Date tommorowFirstInstant() {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        dt.setMinutes(0);
        dt.setHours(0);
        dt.setSeconds(0);




        return dt;
    }
}
