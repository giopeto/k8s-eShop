package com.authentication.authentication.v1.users.controller;

import com.authentication.authentication.v1.users.domain.Users;
import com.authentication.authentication.v1.users.service.UsersService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

import static com.authentication.authentication.common.AuthenticationConstants.AUTHENTICATION_BASE_URL;
import static org.springframework.util.Assert.notNull;

@RestController
@RequestMapping(AUTHENTICATION_BASE_URL + "/users")
@AllArgsConstructor
public class UsersController {

    @NonNull
    private final UsersService usersService;

    @PostMapping(value = "/signIn")
    public void signIn(@RequestBody Users users) {
        usersService.signIn(users);
    }

    @PostMapping(value = "/signUp")
    public Users signUp(@RequestBody Users users) {
        return usersService.signUp(users);
    }

    @GetMapping(value = "/signOut")
    public void signOut() {
        SecurityContextHolder.clearContext();
    }

    @GetMapping
    public Users getCurrentAccount(Principal principal, HttpServletRequest req) {
        notNull(principal, "There is no current logged user");
        return usersService.findOneByEmail(principal.getName());

    }
}
