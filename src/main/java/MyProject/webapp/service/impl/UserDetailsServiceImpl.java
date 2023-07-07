package MyProject.webapp.service.impl;

import MyProject.webapp.exception.GeneralException;
import MyProject.webapp.jwt.UserDetailsImpl;
import MyProject.webapp.modle.entity.UserEntity;
import MyProject.webapp.modle.request.ChangePasswordForm;
import MyProject.webapp.modle.request.UserForm;
import MyProject.webapp.modle.response.UserDetailResponse;
import MyProject.webapp.repository.UserDetailReposirory;
import MyProject.webapp.service.UserService;
import MyProject.webapp.utils.Messageutils;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService, UserService {
    private final UserDetailReposirory userRepository;
    private final PasswordEncoder encoder;

    public UserDetailsServiceImpl(UserDetailReposirory userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userLogin = userRepository.findByEmail(email);
        if (userLogin.isEmpty()) {
            throw new UsernameNotFoundException(Messageutils.INVALID_USERNAME_OR_PASSWORD);
        }
        var user = userLogin.get();
        return UserDetailsImpl.builder()
                .id(user.getId())
                .name(user.getFullName())
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(getAuthoritiesForUser(user.getRole()))
                .enabled(true)
                .build();
    }

    private static List<GrantedAuthority> getAuthoritiesForUser(int isAdmin) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (isAdmin == 2) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return authorities;
    }

    @Override
    public UserDetailResponse addNewUser(UserForm userRequest) throws GeneralSecurityException {
        try {
            UserEntity userIn = new UserEntity(userRequest);
            userIn.setPassword(encoder.encode(userRequest.getPassword()));
            var newUser = userRepository.save(userIn);
            return new UserDetailResponse(newUser);
        } catch (Exception ex) {
            throw new GeneralSecurityException(ex.getMessage());
        }
    }

    @Override
    public UserDetailResponse detail(Long userId) throws NotFoundException, GeneralException {
        var userOtp = userRepository.findById(userId);
        if (userOtp.isPresent()) {
            return new UserDetailResponse(userOtp.get());
        }
        throw new NotFoundException(Messageutils.ITEM_NOT_EXITS);
    }

    @Override
    public void deleteUser(Long userId) throws GeneralSecurityException {
        try {
            userRepository.deleteById(userId);
        } catch (Exception ex) {
            throw new GeneralSecurityException(ex.getMessage());
        }
    }

    @Override
    public UserDetailResponse editUser(Long userId, UserForm userRequest) throws GeneralSecurityException {
        try {
            var userUpdateOtp = userRepository.findById(userId);
            if (!userUpdateOtp.isPresent()) {
                throw new NotFoundException(Messageutils.ITEM_NOT_EXITS);
            }
            var userUpdate = userUpdateOtp.get();
            BeanUtils.copyProperties(userRequest, userUpdate);
            userUpdate.setImage(userRequest.getImage());
            userUpdate.setCreateAt();
            userUpdate.setBirthDay(userRequest.getBirthDay());
            userUpdate.setPassword(encoder.encode(userRequest.getPassword()));

            return new UserDetailResponse(userRepository.save(userUpdate));
        } catch (Exception ex) {
            throw new GeneralSecurityException(ex.getMessage());
        }
    }

    @Override
    public List<UserDetailResponse> getAllUser(String email) {
        List<UserEntity> userEntities = userRepository.findAll(email);
        if (CollectionUtils.isEmpty(userEntities)) return Collections.emptyList();
        return userEntities.stream().map(i -> {
            try {
                return new UserDetailResponse(i);
            } catch (GeneralException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public UserDetailResponse changePasswordUser(Long userId, ChangePasswordForm changePasswordForm) throws NotFoundException, GeneralException {
        var userOtp = userRepository.findById(userId);
        if (!userOtp.isPresent()) {
            throw new NotFoundException(Messageutils.ITEM_NOT_EXITS);
        }
        var user = userOtp.get();
        String newPassEncoder = encoder.encode(changePasswordForm.getNewPassword());
        if (!encoder.matches(user.getPassword(), newPassEncoder)) {
            throw new GeneralException("The old password you entered is incorrect");
        }
        user.setPassword(newPassEncoder);
        userRepository.save(user);
        return detail(userId);
    }
}
