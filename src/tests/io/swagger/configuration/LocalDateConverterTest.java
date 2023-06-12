package io.swagger.configuration;

import org.junit.jupiter.api.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LocalDateConverterTest {
    @Test
    public void testLocalDateConverter() {
        String dateFormat = "yyyy-MM-dd";
        LocalDateConverter converter = new LocalDateConverter(dateFormat);

        String dateString = "2023-06-12";
        LocalDate expectedDate = LocalDate.of(2023, 6, 12);

        LocalDate convertedDate = converter.convert(dateString);

        assertEquals(expectedDate, convertedDate);
    }

    @Test
    public void testLocalDateConverter_NullInput() {
        String dateFormat = "yyyy-MM-dd";
        LocalDateConverter converter = new LocalDateConverter(dateFormat);

        LocalDate convertedDate = converter.convert(null);

        assertNull(convertedDate);
    }
}
