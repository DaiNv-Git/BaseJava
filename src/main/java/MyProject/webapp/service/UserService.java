package MyProject.webapp.service;

import MyProject.webapp.modle.request.UserForm;
import MyProject.webapp.modle.response.UserDetailResponse;
import javassist.NotFoundException;

import java.security.GeneralSecurityException;
import java.util.List;

public interface UserService {
    UserDetailResponse addNewUser(UserForm userRequest) throws GeneralSecurityException;

    UserDetailResponse detail(Long userId) throws NotFoundException;

    void deleteUser(Long userId) throws GeneralSecurityException;

    UserDetailResponse editUser(Long userId, UserForm userRequest) throws GeneralSecurityException;

    List<UserDetailResponse> getAllUser(String username);
}
