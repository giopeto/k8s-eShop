package com.files.common;

import com.files.v1.remote.call.security.domain.Users;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.files.common.FilesConstants.JSESSIONID;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringRunner.class)
public class SecurityInterceptorTest {

    private SecurityInterceptor securityInterceptor;
    @MockBean
    private CookieParser cookieParser;
    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private Environment environment;

    private MockHttpServletRequest mockHttpServletRequest;
    private MockHttpServletResponse mockHttpServletResponse;

    @Before
    public void setUp() {
        securityInterceptor = new SecurityInterceptor(cookieParser, restTemplate, environment);
        mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletResponse = new MockHttpServletResponse();
    }

    @Test
    public void preHandleJSessionCookieAndUserExistsTest() {
        when(cookieParser.getCookie(JSESSIONID)).thenReturn(Optional.of("123-456"));

        ResponseEntity<Users> responseEntity = new ResponseEntity<>(
                new Users("123", "email", "pass", "ROLE_ADMIN"), HttpStatus.OK);
        when(restTemplate.exchange(
                anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity>any(),
                ArgumentMatchers.<Class<Users>>any())
        ).thenReturn(responseEntity);

        assertTrue("preHandleJSessionCookieAndUserExistsTest",
                securityInterceptor.preHandle(mockHttpServletRequest, mockHttpServletResponse, new Object()));
    }

    @Test
    public void preHandleJSessionCookieAndUserNotExistsTest() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(
                "{\"message\":\"There is no current logged user\"}", HttpStatus.NOT_FOUND);
        when(restTemplate.exchange(
                anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity>any(),
                ArgumentMatchers.<Class<String>>any())
        ).thenReturn(responseEntity);

        assertFalse("preHandleJSessionCookieAndUserNotExistsTest",
                securityInterceptor.preHandle(mockHttpServletRequest, mockHttpServletResponse, new Object()));
    }

}
