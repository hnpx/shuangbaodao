package cn.px.base.util;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author PXHLT
 * @since 2019年9月20日 下午8:14:03
 */
public class TimeUtil {
    // 默认使用系统当前时区
    private static final ZoneId ZONE = ZoneId.systemDefault();

    public static Instant getInstant(Object d) {
        if (d instanceof Date) {
            return ((Date)d).toInstant();
        } else if (d instanceof Long) {
            return new Date((Long)d).toInstant();
        }
        return null;
    }

    /**
     * 将Date转换成LocalDateTime
     * @param d date
     * @return
     */
    public static LocalDateTime date2LocalDateTime(Object d) {
        Instant instant = getInstant(d);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        return localDateTime;
    }

    /**
     * 将Date转换成LocalDate
     * @param d date
     * @return
     */
    public static LocalDate date2LocalDate(Object d) {
        Instant instant = getInstant(d);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        return localDateTime.toLocalDate();
    }

    /**
     * 将Date转换成LocalTime
     * @param d date
     * @return
     */
    public static LocalTime date2LocalTime(Object d) {
        Instant instant = getInstant(d);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        return localDateTime.toLocalTime();
    }

    /**
     * 将LocalDate转换成Date
     * @param localDate
     * @return date
     */
    public static Date localDate2Date(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZONE).toInstant();
        return Date.from(instant);
    }

    /**
     * 将LocalDateTime转换成Date
     * @param localDateTime
     * @return date
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZONE).toInstant();
        return Date.from(instant);
    }

    /**
     * 获取Period（时间段）
     * @param format
     *            较小时间
     * @param format
     *            较大时间
     * @return
     */
    public static Period localDateDiff(LocalDate lt, LocalDate gt) {
        Period p = Period.between(lt, gt);
        return p;
    }

    /**
     * 获取Duration（持续时间）
     * @param format
     *            较小时间
     * @param format
     *            较大时间
     * @return
     */
    public static Duration localTimeDiff(LocalTime lt, LocalTime gt) {
        Duration d = Duration.between(lt, gt);
        return d;
    }

    /**
     * 获取时间间隔（毫秒）
     * @param format
     *            较小时间
     * @param format
     *            较大时间
     * @return
     */
    public static long millisDiff(LocalTime lt, LocalTime gt) {
        Duration d = Duration.between(lt, gt);
        return d.toMillis();
    }

    /**
     * 获取时间间隔（秒）
     * @param format
     *            较小时间
     * @param format
     *            较大时间
     * @return
     */
    public static long secondDiff(LocalTime lt, LocalTime gt) {
        Duration d = Duration.between(lt, gt);
        return d.getSeconds();
    }

    /**
     * 获取时间间隔（天）
     * @param format
     *            较小时间
     * @param format
     *            较大时间
     * @return
     */
    public static long daysDiff(LocalDate lt, LocalDate gt) {
        long daysDiff = ChronoUnit.DAYS.between(lt, gt);
        return daysDiff;
    }

    /**
     * 创建一个新的日期，它的值为上月的第一天
     * @param date
     * @return
     */
    public static LocalDate getFirstDayOfLastMonth(LocalDate date) {
        return date.with((temporal) -> temporal.with(ChronoField.DAY_OF_MONTH, 1).plus(-1, ChronoUnit.MONTHS));
    }

    /**
     * 创建一个新的日期，它的值为上月的最后一天
     * @param date
     * @return
     */
    public static LocalDate getLastDayOfLastMonth(LocalDate date) {
        return date.with((temporal) -> temporal.plus(-1, ChronoUnit.MONTHS).with(ChronoField.DAY_OF_MONTH,
            temporal.range(ChronoField.DAY_OF_MONTH).getMaximum()));
    }

    /**
     * 创建一个新的日期，它的值为当月的第一天
     * @param date
     * @return
     */
    public static LocalDate getFirstDayOfMonth(LocalDate date) {
        return date.with((temporal) -> temporal.with(ChronoField.DAY_OF_MONTH, 1));
    }

    /**
     * 创建一个新的日期，它的值为当月的最后一天
     * @param date
     * @return
     */
    public static LocalDate getLastDayOfMonth(LocalDate date) {
        return date.with((temporal) -> temporal.with(ChronoField.DAY_OF_MONTH,
            temporal.range(ChronoField.DAY_OF_MONTH).getMaximum()));
    }

    /**
     * 创建一个新的日期，它的值为下月的第一天
     * @param date
     * @return
     */
    public static LocalDate getFirstDayOfNextMonth(LocalDate date) {
        return date.with((temporal) -> temporal.plus(1, ChronoUnit.MONTHS).with(ChronoField.DAY_OF_MONTH, 1));
    }

    /**
     * 创建一个新的日期，它的值为下月的最后一天
     * @param date
     * @return
     */
    public static LocalDate getLastDayOfNextMonth(LocalDate date) {
        return date.with((temporal) -> temporal.plus(1, ChronoUnit.MONTHS).with(ChronoField.DAY_OF_MONTH,
            temporal.range(ChronoField.DAY_OF_MONTH).getMaximum()));
    }

    /**
     * 创建一个新的日期，它的值为上年的第一天
     * @param date
     * @return
     */
    public static LocalDate getFirstDayOfLastYear(LocalDate date) {
        return date.with((temporal) -> temporal.with(ChronoField.DAY_OF_YEAR, 1).plus(-1, ChronoUnit.YEARS));
    }

    /**
     * 创建一个新的日期，它的值为上年的最后一天
     * @param date
     * @return
     */
    public static LocalDate getLastDayOfLastYear(LocalDate date) {
        return date.with(
            (temporal) -> temporal.with(ChronoField.DAY_OF_YEAR, temporal.range(ChronoField.DAY_OF_YEAR).getMaximum())
            .plus(-1, ChronoUnit.YEARS));
    }

    /**
     * 创建一个新的日期，它的值为当年的第一天
     * @param date
     * @return
     */
    public static LocalDate getFirstDayOfYear(LocalDate date) {
        return date.with((temporal) -> temporal.with(ChronoField.DAY_OF_YEAR, 1));
    }

    /**
     * 创建一个新的日期，它的值为今年的最后一天
     * @param date
     * @return
     */
    public static LocalDate getLastDayOfYear(LocalDate date) {
        return date.with(
            (temporal) -> temporal.with(ChronoField.DAY_OF_YEAR, temporal.range(ChronoField.DAY_OF_YEAR).getMaximum()));
    }

    /**
     * 创建一个新的日期，它的值为明年的第一天
     * @param date
     * @return
     */
    public static LocalDate getFirstDayOfNextYear(LocalDate date) {
        return date.with((temporal) -> temporal.with(ChronoField.DAY_OF_YEAR, 1).plus(1, ChronoUnit.YEARS));
    }

    /**
     * 创建一个新的日期，它的值为明年的最后一天
     * @param date
     * @return
     */
    public static LocalDate getLastDayOfNextYear(LocalDate date) {
        return date.with(
            (temporal) -> temporal.with(ChronoField.DAY_OF_YEAR, temporal.range(ChronoField.DAY_OF_YEAR).getMaximum())
            .plus(1, ChronoUnit.YEARS));
    }

    /**
     * 获取当前时间
     * @return LocalDateTime
     */
    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now(ZONE);
    }

    /**
     * 判断当前时间是否在时间范围内
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isInRange(Date startTime, Date endTime) throws Exception {
        LocalDateTime now = getCurrentLocalDateTime();
        LocalDateTime start = date2LocalDateTime(startTime);
        LocalDateTime end = date2LocalDateTime(endTime);
        return start.isBefore(now) && end.isAfter(now) || start.isEqual(now) || end.isEqual(now);
    }
}
