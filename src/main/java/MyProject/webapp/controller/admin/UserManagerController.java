package MyProject.webapp.controller.admin;

import MyProject.webapp.exception.GeneralException;
import MyProject.webapp.modle.request.ChangePasswordForm;
import MyProject.webapp.modle.request.UserForm;
import MyProject.webapp.modle.response.BaseResponse;
import MyProject.webapp.service.UserService;
import MyProject.webapp.utils.Messageutils;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/admin/user-manager")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserManagerController {
    private final UserService userService;

    public UserManagerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<Object> getAllUser(@RequestParam(required = false) String email) {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), Messageutils.SUCCESSFULLY, userService.getAllUser(email)));
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addUser(@Validated @RequestBody UserForm userRequest) throws GeneralSecurityException {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), Messageutils.SUCCESSFULLY, userService.addNewUser(userRequest)));
    }

    @GetMapping("/detail/{userId}")
    public ResponseEntity<Object> detail(@PathVariable("userId") Long userId) throws NotFoundException, GeneralException {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), Messageutils.SUCCESSFULLY, userService.detail(userId)));
    }

    @DeleteMapping("/detele/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable("userId") Long userId) throws GeneralSecurityException {
        userService.deleteUser(userId);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), Messageutils.SUCCESSFULLY));
    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<Object> editUser(@PathVariable("userId") Long userId, @Validated @RequestBody UserForm userRequest) throws GeneralSecurityException {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), Messageutils.SUCCESSFULLY, userService.editUser(userId,userRequest)));
    }

    @PutMapping("/changePassword/{userId}")
    public ResponseEntity<Object> editUser(@PathVariable("userId") Long userId, @Validated @RequestBody ChangePasswordForm changePasswordForm) throws  GeneralException, NotFoundException {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), Messageutils.SUCCESSFULLY, userService.changePasswordUser(userId,changePasswordForm)));
    }
}
