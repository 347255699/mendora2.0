package org.mendora.util.generate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * created by:xmf
 * date:2018/4/8
 * description:
 */
public class DateUtils {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DEFAULT_PATTERN));
    }

    public static String now(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }
}
