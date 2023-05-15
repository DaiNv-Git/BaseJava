package MyProject.webapp.controller.admin;

import MyProject.webapp.exception.GeneralException;
import MyProject.webapp.modle.response.BaseResponse;
import MyProject.webapp.service.WorkingScheduleSerivice;
import MyProject.webapp.utils.Messageutils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/admin/working-schedule")
public class ScheduleManagerController {

    private final WorkingScheduleSerivice workingScheduleSerivice;

    public ScheduleManagerController(WorkingScheduleSerivice workingScheduleSerivice) {
        this.workingScheduleSerivice = workingScheduleSerivice;
    }

    @GetMapping("/")
    public ResponseEntity<Object> getUserSchedule(@RequestParam String startDate,
                                                  @RequestParam String endDate) throws GeneralException {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), Messageutils.SUCCESSFULLY, workingScheduleSerivice.getUserSchedule(startDate, endDate)));
    }
}
