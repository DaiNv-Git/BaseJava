package MyProject.webapp.controller.admin;

import MyProject.webapp.exception.GeneralException;
import MyProject.webapp.modle.response.BaseResponse;
import MyProject.webapp.service.WorkingScheduleSerivice;
import MyProject.webapp.utils.Messageutils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ScheduleManagerController {

    private final WorkingScheduleSerivice workingScheduleSerivice;

    public ScheduleManagerController(WorkingScheduleSerivice workingScheduleSerivice) {
        this.workingScheduleSerivice = workingScheduleSerivice;
    }

    @GetMapping("/working-schedule")
    public ResponseEntity<Object> getUserSchedule(@RequestParam(defaultValue = "1") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam String startDate,
                                                  @RequestParam String endDate,
                                                  @RequestParam (required = false) String employeeName) throws GeneralException {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), Messageutils.SUCCESSFULLY, workingScheduleSerivice.getAllUsersWorkSchedule(page, size, startDate, endDate, employeeName)));
    }
}
