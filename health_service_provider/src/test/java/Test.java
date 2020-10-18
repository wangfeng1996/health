import com.itheima.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date);
        int year = date.getYear();
        System.out.println(year);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);



    }
}
