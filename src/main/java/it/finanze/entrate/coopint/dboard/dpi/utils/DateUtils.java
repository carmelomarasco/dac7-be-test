package it.finanze.entrate.coopint.dboard.dpi.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static final String PATTERN_HTML_INPUT_DATE = "yyyy-MM-dd";
    public static final String PATTERN_ITA_WITHOUT_HOUR = "dd/MM/yyyy";
    public static final String PATTERN_GREGORIAN = "yyyy-MM-dd hh:mm";
    public static final String PATTERN_ITA_WITH_TIME = "dd/MM/yyyy HH:mm:ss";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public static Timestamp toTimestamp(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (date == null)
            return null;

        return Timestamp.valueOf(LocalDateTime.parse(date, formatter));
    }

    public static Instant toInstant(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        if (date == null)
            return null;
        return Timestamp.valueOf(LocalDateTime.parse(date, formatter)).toInstant();
    }
    
    public static Timestamp toTimestampWithoutHour(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_ITA_WITHOUT_HOUR);
        if (date == null)
            return null;

        return Timestamp.valueOf(LocalDate.parse(date, formatter).atStartOfDay());
    }

    public static LocalDate toLocalDate(String date) {
        return LocalDate.parse(date);
    }

    public static Date toDate(String date) throws ParseException {
        return new SimpleDateFormat(PATTERN_ITA_WITHOUT_HOUR).parse(date);
    }


    public static String fromLocalDateToString(LocalDate collectFrom) {
        return collectFrom.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String fromTimestampToString(Timestamp messageTimestamp) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date(messageTimestamp.getTime()));
    }

    public static String fromTimestampToString(Timestamp messageTimestamp, String pattern) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(new Date(messageTimestamp.getTime()));
    }

    public static String convertDateToString(Date date) {
        return sdf.format(date);
    }

    public static String convertDateWithSecondsToString(Date date) {
        return new SimpleDateFormat(PATTERN_ITA_WITH_TIME).format(date);
    }

    public static String convertDateToStringWithoutHour(Date date) {
        return new SimpleDateFormat(PATTERN_ITA_WITHOUT_HOUR).format(date);
    }

    public static boolean isValidDate(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_ITA_WITHOUT_HOUR);
        try {
            sdf.parse(data);
        } catch (ParseException e) {
            return false;
        }

        return true;

    }

    public static LocalDate minusMonth(LocalDate dateToCheck, long monthsToSubtract) {
        return dateToCheck.minusMonths(monthsToSubtract);
    }

    public static String getStringDateForHTML(Date input) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(input);
    }

    public static String increaseDateByDays(Date dateInput, int days, String formatDateOutput) {
        Calendar c = Calendar.getInstance();
        c.setTime(dateInput);
        c.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(formatDateOutput);
        return sdf.format(c.getTime());
    }

    public static String increaseDateByDays(Timestamp dateInput, int days, String formatDateOutput) {
        Calendar c = Calendar.getInstance();
        c.setTime(dateInput);
        c.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(formatDateOutput);
        return sdf.format(c.getTime());
    }

    public static Timestamp increaseDateTimeStampByDays(Timestamp dateInput, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(dateInput);
        c.add(Calendar.DATE, days);
        return new Timestamp(c.getTimeInMillis());
    }

    public static Timestamp toTimestampFromHTMLEndDay(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_HTML_INPUT_DATE);
        if (date == null)
            return null;
        return increaseDateTimeStampByDays(Timestamp.valueOf(LocalDate.parse(date, formatter).atStartOfDay()), 1);
    }

    public static Timestamp toTimestampFromHTMLStartDay(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_HTML_INPUT_DATE);
        if (date == null)
            return null;

        return Timestamp.valueOf(LocalDate.parse(date, formatter).atStartOfDay());
    }

    public static String changeStringDateFormat(String input, String formatFrom, String formatTo) throws ParseException {
        if (input == null || input.equals("null") || input.equals("")) {
            return "";
        }

        Date date = new SimpleDateFormat(formatFrom).parse(input);
        SimpleDateFormat sdf = new SimpleDateFormat(formatTo);
        return sdf.format(date);
    }

    public static Date htmlDateStringToDate(String date) throws ParseException {
        return new SimpleDateFormat(PATTERN_HTML_INPUT_DATE).parse(date);
    }

    public static Date getDateFromString(String date, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(date);
    }

    public static long getDaysPassed(Date startDate) {
        Date toDay = new Date();
        long startTime = startDate.getTime();
        long endTime = toDay.getTime();
        long diffTime = endTime - startTime;
        return diffTime / (1000 * 60 * 60 * 24);
    }
}
