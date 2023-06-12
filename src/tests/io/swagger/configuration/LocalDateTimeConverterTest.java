package io.swagger.configuration;

import org.junit.jupiter.api.Test;
import org.threeten.bp.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LocalDateTimeConverterTest {
    @Test
    public void testLocalDateTimeConverter() {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        LocalDateTimeConverter converter = new LocalDateTimeConverter(dateFormat);

        String dateTimeString = "2023-06-12 13:30:00";
        LocalDateTime expectedDateTime = LocalDateTime.of(2023, 6, 12, 13, 30, 0);

        LocalDateTime convertedDateTime = converter.convert(dateTimeString);

        assertEquals(expectedDateTime, convertedDateTime);
    }

    @Test
    public void testLocalDateTimeConverter_NullInput() {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        LocalDateTimeConverter converter = new LocalDateTimeConverter(dateFormat);

        LocalDateTime convertedDateTime = converter.convert(null);

        assertNull(convertedDateTime);
    }
}
