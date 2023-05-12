package MyProject.webapp.utils;

import java.lang.RuntimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static LocalDate parseStringToLocalDate(String inputDate) throws RuntimeException {
        try {
            return LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
    public static LocalTime parseStringToLocalTime(String timeString) throws RuntimeException {
        try {
            return LocalTime.parse(timeString);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static String parseLocalDateToString(LocalDate localDate) throws RuntimeException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return localDate.format(formatter);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static String parseLocalTimeToString(LocalTime localTime) throws RuntimeException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return localTime.format(formatter);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static String concatLocalDateToLocalTime(LocalDate localDateIn, LocalTime localTimeIn) throws RuntimeException {
        String localDate = parseLocalDateToString(localDateIn);
        String localTime = parseLocalTimeToString(localTimeIn);
        return localDate.concat(" ").concat(localTime);
    }

}
