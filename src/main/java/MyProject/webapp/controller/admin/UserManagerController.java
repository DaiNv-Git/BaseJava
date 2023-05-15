package MyProject.webapp.controller.admin;

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
public class UserManagerController {
    private final UserService userService;

    public UserManagerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<Object> getAllUser(@RequestParam(required = false) String username) throws GeneralSecurityException {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), Messageutils.SUCCESSFULLY, userService.getAllUser(username)));
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addUser(@Validated @RequestBody UserForm userRequest) throws GeneralSecurityException {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), Messageutils.SUCCESSFULLY, userService.addNewUser(userRequest)));
    }

    @GetMapping("/detail/{userId}")
    public ResponseEntity<Object> detail(@PathVariable("userId") Long userId) throws GeneralSecurityException, NotFoundException {
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
}
