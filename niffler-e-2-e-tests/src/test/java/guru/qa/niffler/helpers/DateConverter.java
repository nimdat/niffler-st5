package guru.qa.niffler.helpers;

import java.util.Date;

public class DateConverter {
    public static java.sql.Date getSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }
}