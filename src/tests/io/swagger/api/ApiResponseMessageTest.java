package io.swagger.api;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiResponseMessageTest {

    @Test
    public void testApiResponseMessage() {
        ApiResponseMessage apiResponseMessage = new ApiResponseMessage(ApiResponseMessage.ERROR, "Error message");

        assertEquals(ApiResponseMessage.ERROR, apiResponseMessage.getCode());
        assertEquals("error", apiResponseMessage.getType());
        assertEquals("Error message", apiResponseMessage.getMessage());
    }

    @Test
    public void testApiResponseMessage_UnknownCode() {
        ApiResponseMessage apiResponseMessage = new ApiResponseMessage(10, "Unknown message");

        assertEquals(10, apiResponseMessage.getCode());
        assertEquals("unknown", apiResponseMessage.getType());
        assertEquals("Unknown message", apiResponseMessage.getMessage());
    }
}