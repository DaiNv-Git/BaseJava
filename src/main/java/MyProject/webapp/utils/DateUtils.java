package MyProject.webapp.utils;

import MyProject.webapp.exception.GeneralException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static LocalDate parseStringToLocalDate(String inputDate) throws GeneralException {
        try {
            return LocalDate.parse(inputDate, checkFormatInputDate(inputDate));
        }catch (Exception ex){
            throw new GeneralException(ex.getMessage());
        }
    }
    public static DateTimeFormatter checkFormatInputDate(String date){
        if (date.contains("-")) return DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }
    public static LocalTime parseStringToLocalTime(String timeString) throws GeneralException {
        try {
            return LocalTime.parse(timeString);
        }catch (Exception ex){
            throw new GeneralException(ex.getMessage());
        }
    }

    public static String parseLocalDateToString(LocalDate localDate) throws GeneralException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return localDate.format(formatter);
        }catch (Exception ex){
            throw new GeneralException(ex.getMessage());
        }
    }

    public static String parseLocalTimeToString(LocalTime localTime) throws GeneralException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return localTime.format(formatter);
        }catch (Exception ex){
            throw new GeneralException(ex.getMessage());
        }
    }

    public static String concatLocalDateToLocalTime(LocalDate localDateIn, LocalTime localTimeIn) throws GeneralException {
        String localDate = parseLocalDateToString(localDateIn);
        String localTime = parseLocalTimeToString(localTimeIn);
        return localDate.concat(" ").concat(localTime);
    }

    public static int getDayOfWeek(LocalDate workDate) {
        DayOfWeek dayOfWeek = workDate.getDayOfWeek();
        // Trả về giá trị từ 1 (Thứ Hai) đến 7 (Chủ Nhật)
        return dayOfWeek.getValue();
    }
}
