package MyProject.webapp.modle.entity;

import MyProject.webapp.exception.GeneralException;
import MyProject.webapp.modle.request.ScheduleForUserAddForm;
import MyProject.webapp.modle.request.ScheduleForUserEditForm;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Shift_ID")
    private ShiftEntity shift;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Work_Type_ID")
    private WorkTypeEntity workType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "User_Detail_ID")
    private UserEntity user;

    public WorkingScheduleEntity(ScheduleForUserAddForm scheduleForUserAddForm) throws GeneralException {

        this.workTitle = scheduleForUserAddForm.getTitle();
        this.workDate = DateUtils.parseStringToLocalDate(scheduleForUserAddForm.getWorkDate());
        this.startTime = DateUtils.parseStringToLocalTime(scheduleForUserAddForm.getStartTime());
        this.endTime = DateUtils.parseStringToLocalTime(scheduleForUserAddForm.getEndTime());
        this.description = scheduleForUserAddForm.getDescription();
    }

    public WorkingScheduleEntity(ScheduleForUserEditForm scheduleForUserEditForm) throws GeneralException {
        this.id = scheduleForUserEditForm.getId();
        this.workTitle = scheduleForUserEditForm.getTitle();
        this.workDate = DateUtils.parseStringToLocalDate(scheduleForUserEditForm.getWorkDate());
        this.startTime = DateUtils.parseStringToLocalTime(scheduleForUserEditForm.getStartTime());
        this.endTime = DateUtils.parseStringToLocalTime(scheduleForUserEditForm.getEndTime());
        this.description = scheduleForUserEditForm.getDescription();
    }
}
