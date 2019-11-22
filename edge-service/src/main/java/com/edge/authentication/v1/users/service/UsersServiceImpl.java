package com.edge.authentication.v1.users.service;

import com.edge.authentication.v1.users.domain.Users;
import com.edge.authentication.v1.users.repository.UsersRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Collections.singleton;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UsersServiceImpl implements UsersService, UserDetailsService {

    @NonNull
    private final UsersRepository usersRepository;
    @NonNull
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signIn(Users users) {
        Users dbUsers = usersRepository.findOneByEmail(users.getEmail());
        if (dbUsers != null && passwordEncoder.matches(users.getPassword(), dbUsers.getPassword())) {
            SecurityContextHolder.getContext().setAuthentication(authenticate(dbUsers));
        } else {
            throw new UsernameNotFoundException("Username not found or password not match.");
        }
    }

    @Transactional
    @Override
    public Users signUp(Users users) {
        if (usersRepository.findOneByEmail(users.getEmail()) != null) {
            throw new UsernameNotFoundException("There is an user with that email address: " + users.getEmail());
        }

        String rawPassword = users.getPassword();
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        usersRepository.save(users);

        if (users.getId() != null) {
            users.setPassword(rawPassword);
            signIn(users);
        }
        users.setPassword("");

        return users;
    }

    @Override
    public Users findOneByEmail(String email) {
        return usersRepository.findOneByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Users users = usersRepository.findOneByEmail(username);
        if (users == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return createUser(users);
    }

    private Authentication authenticate(Users users) {
        return new UsernamePasswordAuthenticationToken(createUser(users), users.getPassword(),
                singleton(createAuthority(users)));
    }

    private User createUser(Users users) {
        return new User(users.getEmail(), users.getPassword(), singleton(createAuthority(users)));
    }

    private GrantedAuthority createAuthority(Users users) {
        return new SimpleGrantedAuthority(users.getRole());
    }
}
