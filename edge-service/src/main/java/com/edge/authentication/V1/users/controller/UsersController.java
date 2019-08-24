package com.edge.authentication.V1.users.controller;

import com.edge.authentication.V1.users.domain.Users;
import com.edge.authentication.V1.users.service.UsersService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

import static com.edge.authentication.common.AuthenticationConstants.AUTHENTICATION_BASE_URL;
import static org.springframework.util.Assert.notNull;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(AUTHENTICATION_BASE_URL + "/users")
public class UsersController {

    @NonNull
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(value = "/signIn", method = POST)
    public void signIn(@RequestBody Users users) {
        usersService.signIn(users);
    }

    @RequestMapping(value = "/signUp", method = POST)
    public Users signUp(@RequestBody Users users) {
        System.out.println(AUTHENTICATION_BASE_URL + " signUp " + users.toString());
        return usersService.signUp(users);
    }

    @RequestMapping(value = "/signOut", method = GET)
    public void signOut() {
        SecurityContextHolder.clearContext();
    }

    @RequestMapping(method = GET)
    public Users getCurrentAccount(Principal principal, HttpServletRequest req) {
        notNull(principal);
        System.out.println("principal: " + principal.toString() + " URIIIII " + req.getRequestURI());
        System.out.println("SecurityContextHolder.getContext().getAuthentication()1: " + SecurityContextHolder.getContext().getAuthentication());


        for (Cookie c : req.getCookies()) {
            System.out.println(c.getName() + " : " + c.getValue());
        }
        return usersService.findOneByEmail(principal.getName());

    }
}
