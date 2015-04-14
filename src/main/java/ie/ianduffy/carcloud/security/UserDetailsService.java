package ie.ianduffy.carcloud.security;

import ie.ianduffy.carcloud.domain.Authority;
import ie.ianduffy.carcloud.domain.User;
import ie.ianduffy.carcloud.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;

@Slf4j
@Component("userDetailsService")
public class UserDetailsService
    implements org.springframework.security.core.userdetails.UserDetailsService {

    @Inject
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        User userFromDatabase = userRepository.findOneByUsernameOrEmailOrPhone(login);
        if (userFromDatabase == null) {
            throw new UsernameNotFoundException(
                "User " + login + " was not found in the database");
        }

        Collection<Authority> grantedAuthorities = userFromDatabase.getAuthorities();

        return new org.springframework.security.core.userdetails.User(userFromDatabase.getUsername().toLowerCase(),
            userFromDatabase
                .getPassword(),
            grantedAuthorities);
    }
}
