package it.finanze.entrate.coopint.dboard.dpi.com.command.utility;

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
    public static final String PATTERN_HTML_INPUT_DATE_WITH_TIME = "yyyy-MM-dd'T'HH:mm:ss";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public static Timestamp toTimestamp(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (date == null)
            return null;
        return Timestamp.valueOf(LocalDateTime.parse(date, formatter));
    }

    public static Date toDate(String date) throws ParseException {
        log.info("toDate parsing [" + date + "]");
        return new SimpleDateFormat(FORMAT_ISO).parse(date);
    }

    public static Date toDateWithTime(String date) throws ParseException {
        log.info("toDateTime parsing [" + date + "]");

        return new SimpleDateFormat(PATTERN_HTML_INPUT_DATE_WITH_TIME).parse(date);
    }

    public static Date htmlDateStringToDate(String date) throws ParseException {
        log.info("toDate parsing [" + date + "]");
        return new SimpleDateFormat(PATTERN_HTML_INPUT_DATE).parse(date);
    }

    public static LocalDate toLocalDate( XMLGregorianCalendar xgc) {
        return xgc != null ? LocalDate.of(xgc.getYear(), xgc.getMonth(), xgc.getDay()) : null;
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

    public static Long getYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (long) calendar.get(Calendar.YEAR);
    }

    public static String convertDateToString(Date date) {
        return sdf.format(date);
    }

    public static String convertDateToStringWithoutHour(Date date) {
        return new SimpleDateFormat(PATTERN_ITA_WITHOUT_HOUR).format(date);
    }
}
