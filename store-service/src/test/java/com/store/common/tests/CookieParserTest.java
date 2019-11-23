package com.store.common.tests;

import com.store.common.CookieParser;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.store.common.StoreConstants.JSESSIONID;
import static org.junit.Assert.assertEquals;

@Ignore
public class CookieParserTest {

    private HttpServletRequest mockHttpServletRequest;

    private CookieParser cookieParser;
    //@MockBean
    //private HttpServletRequest request;
    private String cookieValue;
    private Cookie[] cookies;

    @Before
    public void setUp() {
        cookieParser = new CookieParser();
        mockHttpServletRequest = new MockHttpServletRequest();
        cookieValue = "JSESSIONID-VALUE";
        //cookies = new Cookie[]{};

        mockHttpServletRequest.setAttribute(JSESSIONID, cookieValue);
    }

    @Test
    public void testGetCookie() {
        //when(request.getCookies()).thenReturn(cookies);
        assertEquals(Optional.of(cookieValue), cookieParser.getCookie(JSESSIONID));

    }
}
