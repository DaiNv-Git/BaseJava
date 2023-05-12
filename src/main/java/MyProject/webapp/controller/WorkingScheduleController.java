package MyProject.webapp.controller;

import MyProject.webapp.exception.DataNotFoundException;
import MyProject.webapp.exception.GeneralException;
import MyProject.webapp.modle.request.ScheduleForUserForm;
import MyProject.webapp.modle.response.BaseResponse;
import MyProject.webapp.service.WorkingScheduleSerivice;
import MyProject.webapp.utils.Messageutils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class WorkingScheduleController {
    private final WorkingScheduleSerivice workingScheduleSerivice;

    public WorkingScheduleController(WorkingScheduleSerivice workingScheduleSerivice) {
        this.workingScheduleSerivice = workingScheduleSerivice;
    }

    @GetMapping("/user/working-schedule")
    public ResponseEntity<Object> getUserSchedule(@RequestParam String startDate,
                                                  @RequestParam String endDate) throws GeneralException {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), Messageutils.SUCCESSFULLY, workingScheduleSerivice.getUserSchedule(startDate, endDate)));
    }

    @GetMapping("/user/working-schedule-detail")
    public ResponseEntity<Object> findUserScheduleDetail(@RequestParam Long scheduleId) throws GeneralException {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), Messageutils.SUCCESSFULLY, workingScheduleSerivice.getUserScheduleDetai(scheduleId)));
    }

    @DeleteMapping("/user/working-schedule-delete")
    public ResponseEntity<Object> deleteUserScheduleDetail(@RequestParam Long scheduleId) throws GeneralException {
        workingScheduleSerivice.deleteUserSchedule(scheduleId);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), Messageutils.SUCCESSFULLY));
    }

    @PostMapping("/user/working-schedule-add")
    public ResponseEntity<Object> addScheduleForUser(@Validated @RequestBody ScheduleForUserForm scheduleForUserForm) throws DataNotFoundException {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), Messageutils.SUCCESSFULLY, workingScheduleSerivice.addScheduleForUser(scheduleForUserForm)));
    }

    @PostMapping("/user/working-schedule-add")
    public ResponseEntity<Object> updateScheduleForUser(@Validated @RequestBody ScheduleForUserForm scheduleForUserUpdateForm) throws DataNotFoundException, GeneralException {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), Messageutils.SUCCESSFULLY, workingScheduleSerivice.updateScheduleForUser(scheduleForUserUpdateForm)));

    }
}
