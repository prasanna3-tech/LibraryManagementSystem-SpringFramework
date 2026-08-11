package org.pras.utils;
import java.sql.Date;
import java.time.temporal.ChronoUnit;

public class DateUtilNew {
    public static int getLateDays(Date dueDate, Date currentDate) {

        if (currentDate.after(dueDate)) {

            return (int) ChronoUnit.DAYS.between(
                    dueDate.toLocalDate(),
                    currentDate.toLocalDate()
            );
        }

        return 0;
    }
}
