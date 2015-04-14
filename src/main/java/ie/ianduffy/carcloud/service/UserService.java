package ie.ianduffy.carcloud.service;

import ie.ianduffy.carcloud.domain.Authority;
import ie.ianduffy.carcloud.domain.User;
import ie.ianduffy.carcloud.repository.AuthorityRepository;
import ie.ianduffy.carcloud.repository.UserRepository;
import ie.ianduffy.carcloud.security.AuthoritiesConstants;
import ie.ianduffy.carcloud.security.SecurityUtils;
import ie.ianduffy.carcloud.web.dto.UserDTO;
import org.hibernate.Hibernate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

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
        if (authorities == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return authorities;
    }

    @Transactional(readOnly = true)
    public Authority getAuthority(String login, String authorityName) {
        List<Authority> authorities = getAuthorities(login);

        for (Authority authority : authorities) {
            if (authority.getName().equals(authorityName)) {
                return authority;
            }
        }

        throw new EmptyResultDataAccessException(1);
    }

    @Override
    protected JpaRepository<User, String> getRepository() {
        return userRepository;
    }

    public void removeAuthority(String login, String authorityName) {
        User user = findOne(login);
        user.getAuthorities().remove(getAuthority(login, authorityName));
        userRepository.save(user);
    }

    public User update(UserDTO userDTO) {
        if (userDTO.getPassword() != null) {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        return super.update(userDTO, findOne(SecurityUtils.getCurrentLogin()));
    }
}
