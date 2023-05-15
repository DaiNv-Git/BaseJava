package MyProject.webapp.modle.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkingScheduleEntity> workingSchedules = new ArrayList<>();
}
