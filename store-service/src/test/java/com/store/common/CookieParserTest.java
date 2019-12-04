package com.store.common;

import com.store.common.CookieParser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.Cookie;
import java.util.Optional;

import static com.store.common.StoreConstants.JSESSIONID;
import static org.junit.Assert.assertEquals;

public class CookieParserTest {

    private CookieParser cookieParser;
    private MockHttpServletRequest mockHttpServletRequest;
    private String cookieValue;

    @Before
    public void setUp() {
        cookieValue = "JSESSIONID-VALUE";
        mockHttpServletRequest = new MockHttpServletRequest();
        cookieParser = new CookieParser(mockHttpServletRequest);
    }

    @Test
    public void testGetCookieWithExistingCookie() {
        setRequestCookie(JSESSIONID, cookieValue);
        assertEquals(Optional.of(cookieValue), cookieParser.getCookie(JSESSIONID));
    }

    @Test
    public void testGetCookieWithNotExistingCookie() {
        setRequestCookie(JSESSIONID, cookieValue);
        assertEquals(Optional.empty(), cookieParser.getCookie("NotExistingCookie"));
    }

    @Test
    public void testGetCookieWithRequestWithoutCookies() {
        assertEquals(Optional.empty(), cookieParser.getCookie("NotExistingCookie"));
    }

    private void setRequestCookie(String cookieName, String cookieValue) {
        mockHttpServletRequest.setCookies(new Cookie(cookieName, cookieValue));
    }
}
