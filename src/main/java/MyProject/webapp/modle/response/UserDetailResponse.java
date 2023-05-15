package MyProject.webapp.modle.response;

import MyProject.webapp.modle.entity.UserEntity;
import MyProject.webapp.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class UserDetailResponse {
    @JsonProperty(value = "userId")
    private Long id;
    @JsonProperty(value = "fullName")
    private String fullName;
    @JsonProperty(value = "phoneNumber")
    private String tel;
    @JsonProperty(value = "email")
    private String email;
    @JsonProperty(value = "sex")
    @ApiModelProperty(value = "Giới tính (0: Nam, 1: Nữ)", example = "0")
    private Integer sex;
    @JsonProperty(value = "birthDay")
    private String birthDay;
    @JsonProperty(value = "address")
    private String address;
    @JsonProperty(value = "role")
    @ApiModelProperty(value = "Role (1 : ROLE_USER, 2 : ROLE_ADMIN)", example = "1")
    private Integer role;
    @JsonProperty(value = "image")
    private byte[] image;
    @JsonProperty(value = "description")
    private String description;

    public UserDetailResponse(UserEntity user) {
        BeanUtils.copyProperties(user, this);
        this.image = user.getImage() == null ? null :  user.getImage();
        this.birthDay = DateUtils.parseLocalDateToString(user.getBirthDay());
    }
}
