package ie.ianduffy.carcloud.service;

import com.google.common.collect.Sets;
import ie.ianduffy.carcloud.domain.User;
import ie.ianduffy.carcloud.repository.AuthorityRepository;
import ie.ianduffy.carcloud.repository.UserRepository;
import ie.ianduffy.carcloud.security.AuthoritiesConstants;
import ie.ianduffy.carcloud.security.SecurityUtils;
import ie.ianduffy.carcloud.web.rest.dto.UserDTO;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    @Inject
    private AuthorityRepository authorityRepository;
    @Inject
    private Mapper mapper;
    @Inject
    private PasswordEncoder passwordEncoder;
    @Inject
    private UserRepository userRepository;

    public void changePassword(String password) {
        User user = userRepository.findOne(SecurityUtils.getCurrentLogin());
        String encryptedPassword = passwordEncoder.encode(password);
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }

    public UserDTO create(UserDTO userDTO) {
        if (userRepository.findOne(userDTO.getEmail()) != null) {
            return null;
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if (userDTO.getAuthorities() == null || userDTO.getAuthorities().size() <= 0) {
            userDTO.setAuthorities(Sets.newHashSet(authorityRepository.findOne(AuthoritiesConstants.USER)));
        }

        User user = new User();
        mapper.map(userDTO, user);
        userRepository.save(user);
        mapper.map(user, userDTO);
        return userDTO;
    }

    @Transactional(readOnly = true)
    public UserDTO getUserWithAuthorities() {
        User user = userRepository.findOne(SecurityUtils.getCurrentLogin());
        user.getAuthorities().size();
        UserDTO userDTO = new UserDTO();
        mapper.map(user, userDTO);
        return userDTO;
    }

    public void update(UserDTO userDTO) {
        User currentUser = userRepository.findOne(SecurityUtils.getCurrentLogin());
        currentUser.setFirstName(userDTO.getFirstName());
        currentUser.setLastName(userDTO.getLastName());
        currentUser.setEmail(userDTO.getEmail());
        currentUser.setVersion(userDTO.getVersion());
        userRepository.save(currentUser);
    }
}
