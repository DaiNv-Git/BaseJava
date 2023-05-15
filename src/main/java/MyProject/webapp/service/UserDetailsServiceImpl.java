package MyProject.webapp.service;

import MyProject.webapp.jwt.UserDetailsImpl;
import MyProject.webapp.modle.entity.UserEntity;
import MyProject.webapp.repository.repositoryjpa.UserDetailReposirory;
import MyProject.webapp.utils.Messageutils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDetailReposirory userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userLogin = userRepository.findByEmail(email);
        if (userLogin.isEmpty()) {
            throw new UsernameNotFoundException(Messageutils.INVALID_USERNAME_OR_PASSWORD);
        }
        return UserDetailsImpl.build(userLogin.get());
    }

}
