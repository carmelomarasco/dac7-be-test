package it.finanze.entrate.coopint.dboard.dpi.res.command.utils;

import lombok.extern.apachecommons.CommonsLog;

import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@CommonsLog
public class DateUtil {

    private final static String FORMAT_ISO = "dd-MM-yyyy";
    public static final String PATTERN_HTML_INPUT_DATE = "yyyy-MM-dd";
    public static final String PATTERN_ITA_WITHOUT_HOUR = "dd/MM/yyyy";
    public static final String PATTERN_ITA_WITH_TIME = "dd/MM/yyyy HH:mm:ss";
    public static final String PATTERN_ISO_WITH_TIME = "yyyy-MM-dd'T'HH:mm:ss"; //2020-09-11T21:53:35
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static Timestamp toTimestamp(String date) {
        log.info("toTimestamp parsing [" + date + "]");
        if (date == null)
            return null;
        return Timestamp.valueOf(LocalDateTime.parse(date));
    }

    public static Date toDate(String date) throws ParseException {
        log.info("toDate parsing [" + date + "]");
        return new SimpleDateFormat(PATTERN_ITA_WITH_TIME).parse(date);
    }

    public static Date htmlDateStringToDate(String date) throws ParseException {
        log.info("toDate parsing [" + date + "]");
        return new SimpleDateFormat(PATTERN_HTML_INPUT_DATE).parse(date);
    }

    /**
     * returns the {@link LocalDateTime} object parsed from this string
     *
     * @param date the string representing the date and time in format yyyy-MM-dd'T'HH:mm:ss
     * @return the LocalDateTime parsed object, otherwise a parsing exception is throwed
     */
    public static LocalDateTime toLocalDateTime(String date) {
        log.info("toLocalDateTime parsing [" + date + "]");
        return LocalDateTime.parse(date);
    }

    public static String fromLocalDateToString(LocalDate collectFrom) {
        return collectFrom.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String fromTimestampToString(Timestamp messageTimestamp) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date(messageTimestamp.getTime()));

    }

    /**
     * transforms an instance of tipe {@link XMLGregorianCalendar} to an {@link Timestamp} one
     *
     * @param xmlGregorianCalendar the object to be transformed
     * @return the timestamp instance, otherwose a parse exception is thrown
     */
    public static Timestamp xmlGregorianCalendarToTimestamp(XMLGregorianCalendar xmlGregorianCalendar) {
        return xmlGregorianCalendar != null ? new Timestamp(xmlGregorianCalendar.toGregorianCalendar().getTimeInMillis()) : null;
    }

    /**
     * transforms an instance of tipe {@link XMLGregorianCalendar} to an {@link Date} one
     *
     * @param xmlGregorianCalendar the object to be transformed
     * @return the date instance, otherwose a parse exception is thrown
     */
    public static Date xmlGregorianCalendarToDate(XMLGregorianCalendar xmlGregorianCalendar) {
        return xmlGregorianCalendar != null ? xmlGregorianCalendar.toGregorianCalendar().getTime() : null;
    }

    /**
     * converts a xmlGregorianCalendar object in a LocalDate one
     *
     * @param xmlGregorianCalendar the xmlGregorianCalendar to convert
     * @return the LocalDate object representation of the param passed in
     */
    public static LocalDate xmlGregorianCalendarToLocalDate(XMLGregorianCalendar xmlGregorianCalendar) {
        Date toConvert = xmlGregorianCalendarToDate(xmlGregorianCalendar);
        return toConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Long getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return (long) calendar.get(Calendar.YEAR);
    }

    public static String convertDateToString(Date date) {
        return sdf.format(date);
    }


    public static String convertDateWithSecondsToString(Date date) {
        return new SimpleDateFormat(PATTERN_ITA_WITH_TIME).format(date);
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

    public static boolean isValidDate(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_ITA_WITHOUT_HOUR);
        try {
            sdf.parse(data);
        } catch (ParseException e) {
            return false;
        }

        return true;

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
    
    public static Date convertStringToDate(String dateString, String pattern) throws Exception {
        if (dateString == null || pattern == null) return null;
        return new SimpleDateFormat(pattern).parse(dateString);
    }

}
