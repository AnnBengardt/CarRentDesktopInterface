package sample.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The type Date util.
 */
public class DateUtil {
    private static final String dateFormat = "yyyy-MM-dd";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(dateFormat);

    /**
     * Format string.
     *
     * @param date the date
     * @return the string
     */
    public static String format(LocalDate date){
        if(date==null){
            return null;
        }
        return DATE_TIME_FORMATTER.format(date);
    }

    /**
     * Parse local date.
     *
     * @param stringDate the string date
     * @return the local date
     */
    public static LocalDate parse(String stringDate){
        try{
            return DATE_TIME_FORMATTER.parse(stringDate, LocalDate::from);
        } catch (DateTimeParseException e){
            return null;
        }
    }

    /**
     * Is valid date boolean.
     *
     * @param stringDate the string date
     * @return the boolean
     */
    public static boolean isValidDate(String stringDate){
        if(DateUtil.parse(stringDate) == null){
            return false;
        }else{
            return true;
        }
    }
}

