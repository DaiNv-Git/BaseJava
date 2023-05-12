package MyProject.webapp.modle.entity;

import MyProject.webapp.modle.request.ScheduleForUserForm;
import MyProject.webapp.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Working_Schedule")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkingScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "Work_Title", nullable = false)
    private String workTitle;
    @Column(name = "Work_Date")
    private LocalDate workDate;
    @Column(name = "Start_Time")
    private LocalTime startTime;
    @Column(name = "End_Time")
    private LocalTime endTime;
    @Column(name = "Description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Shift_ID")
    private ShiftEntity shift;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Work_Type_ID")
    private WorkTypeEntity workType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Work_Type_ID")
    private UserDetailEntity user;

    public WorkingScheduleEntity(ScheduleForUserForm scheduleForUserForm) {
        this.id = scheduleForUserForm.getId() == null ? null : scheduleForUserForm.getId();
        this.workTitle = scheduleForUserForm.getTitle();
        this.workDate = DateUtils.parseStringToLocalDate(scheduleForUserForm.getWorkDate());
        this.startTime = DateUtils.parseStringToLocalTime(scheduleForUserForm.getStartTime());
        this.endTime = DateUtils.parseStringToLocalTime(scheduleForUserForm.getEndTime());
        this.description = scheduleForUserForm.getDescription();
    }
}
