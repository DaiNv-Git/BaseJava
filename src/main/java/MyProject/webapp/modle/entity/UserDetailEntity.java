package MyProject.webapp.modle.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "User_Detail")
@Data
public class UserDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "Full_Name", nullable = false)
    private String workTitle;
    @Column(name = "Tel")
    private String tel;
    @Column(name = "Email", unique = true, nullable = false)
    private String email;
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
    private Integer role;
    @Column(name = "image")
    private byte[] image;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkingScheduleEntity> workingSchedules = new ArrayList<>();
}
