package MyProject.webapp.service;

import MyProject.webapp.exception.GeneralException;
import MyProject.webapp.modle.request.ChangePasswordForm;
import MyProject.webapp.modle.request.UserForm;
import MyProject.webapp.modle.response.UserDetailResponse;
import javassist.NotFoundException;

import java.security.GeneralSecurityException;
import java.util.List;

public interface UserService {
    UserDetailResponse addNewUser(UserForm userRequest) throws GeneralSecurityException;

    UserDetailResponse detail(Long userId) throws NotFoundException, GeneralException;

    void deleteUser(Long userId) throws GeneralSecurityException;

    UserDetailResponse editUser(Long userId, UserForm userRequest) throws GeneralSecurityException;

    List<UserDetailResponse> getAllUser(String email);

    UserDetailResponse changePasswordUser(Long userId, ChangePasswordForm changePasswordForm) throws NotFoundException, GeneralException;
}
