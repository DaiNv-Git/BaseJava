package MyProject.webapp.modle.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Work_Type")
@Data
public class WorkTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "Name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "workType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkingScheduleEntity> workingSchedules = new ArrayList<>();
}
