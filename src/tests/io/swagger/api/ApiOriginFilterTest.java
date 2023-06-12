package io.swagger.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiOriginFilterTest {

    private ApiOriginFilter apiOriginFilter;

    @BeforeEach
    public void setup() {
        apiOriginFilter = new ApiOriginFilter();
    }

    @Test
    public void testApiOriginFilter() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        apiOriginFilter.doFilter(request, response, filterChain);

        assertEquals("*", response.getHeader("Access-Control-Allow-Origin"));
        assertEquals("GET, POST, DELETE, PUT", response.getHeader("Access-Control-Allow-Methods"));
        assertEquals("Content-Type", response.getHeader("Access-Control-Allow-Headers"));
    }
}