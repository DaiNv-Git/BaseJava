package MyProject.webapp.modle.entity;
import MyProject.webapp.exception.GeneralException;
import MyProject.webapp.modle.request.UserForm;
import MyProject.webapp.utils.ConvertUtils;
import MyProject.webapp.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "Full_Name", nullable = false)
    private String fullName;
    @Column(name = "Tel")
    private String tel;
    @Column(name = "Email", unique = true, nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "Sex")
    private Integer sex;
    @Column(name = "Birth_Day")
    private LocalDate birthDay;
    @Column(name = "Address")
    private String address;
    @Column(name = "CreateAt")
    private LocalDateTime createAt;
    @Column(name = "Creator")
    private String creator;
    @Column(name = "role")
    // 1 : ROLE_USER
    // 2 : ROLE_ADMIN
    private Integer role;

    @Column(name = "image")
    private byte[] image;
    @Column(name = "description")
    private String description;

    public UserEntity(UserForm userRequest) throws IOException, GeneralException {
        BeanUtils.copyProperties(userRequest, this);
        setImage(userRequest.getImage());
        setCreateAt();
        setBirthDay(userRequest.getBirthDay());
    }

    public void setImage(MultipartFile file) throws IOException, GeneralException {
        if (file != null) {
            this.image = ConvertUtils.convertMultipartFileToByteArray(file);
        }
    }

    public void setCreateAt() {
        this.createAt = LocalDateTime.now();
    }

    public void setBirthDay(String birthDay) throws GeneralException {
        this.birthDay = DateUtils.parseStringToLocalDate(birthDay);
    }
}
