package MyProject.webapp.service;

import MyProject.webapp.exception.DataNotFoundException;
import MyProject.webapp.exception.GeneralException;
import MyProject.webapp.modle.request.ScheduleForUserAddForm;
import MyProject.webapp.modle.request.ScheduleForUserEditForm;
import MyProject.webapp.modle.response.ShiftResponse;
import MyProject.webapp.modle.response.WorkTypeResponse;
import MyProject.webapp.modle.response.adminResponse.ScheduleAdminResponse;
import MyProject.webapp.modle.response.schedule.ScheduleUserDetailResponse;
import MyProject.webapp.modle.response.schedule.ScheduleUserResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WorkingScheduleSerivice {
    List<ScheduleUserResponse> getUserSchedule(String startDate, String endDate) throws GeneralException;

    ScheduleUserDetailResponse getUserScheduleDetai(Long scheduleId) throws GeneralException;

    void deleteUserSchedule(Long scheduleId) throws GeneralException;

    ScheduleUserDetailResponse addScheduleForUser(ScheduleForUserAddForm scheduleForUserAddForm) throws DataNotFoundException, GeneralException;

    ScheduleUserDetailResponse updateScheduleForUser(ScheduleForUserEditForm scheduleForUserUpdateForm) throws DataNotFoundException, GeneralException;

    List<WorkTypeResponse> getAllWorkType() throws GeneralException;

    List<ShiftResponse> getAllShift() throws GeneralException;

    Page<ScheduleAdminResponse> getAllUsersWorkSchedule(int page, int size, String startDate, String endDate, String employeeName);
}
