package ie.ianduffy.carcloud.service;

import ie.ianduffy.carcloud.domain.Authority;
import ie.ianduffy.carcloud.domain.User;
import ie.ianduffy.carcloud.repository.AuthorityRepository;
import ie.ianduffy.carcloud.repository.UserRepository;
import ie.ianduffy.carcloud.security.AuthoritiesConstants;
import ie.ianduffy.carcloud.security.SecurityUtils;
import ie.ianduffy.carcloud.web.dto.UserDTO;

import org.hibernate.Hibernate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService extends AbstractService<User, String, UserDTO> {

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UserRepository userRepository;

    public void addAuthority(String login, String authorityName) {
        User user = findOne(login);
        Authority authority = authorityRepository.findOne(authorityName);
        user.getAuthorities().add(authority);
        userRepository.save(user);
    }

    public void changePassword(String password) {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword(passwordEncoder.encode(password));
        update(userDTO);
    }

    public User create(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        User user = new User();
        user.setAuthorities(Arrays.asList(authorityRepository.findOne(AuthoritiesConstants.USER)));

        return super.create(userDTO, user);
    }

    @Transactional(readOnly = true)
    public List<User> findLike(String username) {
        return userRepository.findTop10ByUsernameLike("%" + username + "%");
    }

    @Transactional(readOnly = true)
    public List<Authority> getAuthorities(String login) {
        User user = findOne(login);
        List<Authority> authorities = user.getAuthorities();
        Hibernate.initialize(authorities);
        return authorities;
    }

    @Override
    protected JpaRepository<User, String> getRepository() {
        return userRepository;
    }

    public void removeAuthority(String login, int index) {
        User user = findOne(login);
        user.getAuthorities().remove(index);
        userRepository.save(user);
    }

    public void update(UserDTO userDTO) {
        super.update(userDTO, findOne(SecurityUtils.getCurrentLogin()));
    }
}
