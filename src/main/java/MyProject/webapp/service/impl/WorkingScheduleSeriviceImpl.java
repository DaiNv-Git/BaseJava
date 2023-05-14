package MyProject.webapp.service.impl;

import MyProject.webapp.exception.DataNotFoundException;
import MyProject.webapp.exception.GeneralException;
import MyProject.webapp.modle.entity.ShiftEntity;
import MyProject.webapp.modle.entity.UserDetailEntity;
import MyProject.webapp.modle.entity.WorkTypeEntity;
import MyProject.webapp.modle.entity.WorkingScheduleEntity;
import MyProject.webapp.modle.request.ScheduleForUserForm;
import MyProject.webapp.modle.response.schedule.ScheduleUserDetailResponse;
import MyProject.webapp.modle.response.schedule.ScheduleUserResponse;
import MyProject.webapp.repository.repositoryjpa.ShiftRepository;
import MyProject.webapp.repository.repositoryjpa.WorkTypeRepository;
import MyProject.webapp.repository.repositoryjpa.WorkingScheduleRepository;
import MyProject.webapp.service.WorkingScheduleSerivice;
import MyProject.webapp.utils.ColorEnum;
import MyProject.webapp.utils.DateUtils;
import MyProject.webapp.utils.Messageutils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkingScheduleSeriviceImpl implements WorkingScheduleSerivice {
    private final WorkingScheduleRepository workingScheduleRepository;
    private final ShiftRepository shiftRepository;
    private final WorkTypeRepository workTypeRepository;

    public WorkingScheduleSeriviceImpl(WorkingScheduleRepository workingScheduleRepository, ShiftRepository shiftRepository, WorkTypeRepository workTypeRepository) {
        this.workingScheduleRepository = workingScheduleRepository;
        this.shiftRepository = shiftRepository;
        this.workTypeRepository = workTypeRepository;
    }

    @Override
    public List<ScheduleUserResponse> getUserSchedule(String startDate, String endDate) throws GeneralException {
        try {
            LocalDate parsedStartDate = DateUtils.parseStringToLocalDate(startDate);
            LocalDate parsedEndDate = DateUtils.parseStringToLocalDate(endDate);

            UserDetailEntity user = new UserDetailEntity();
            List<WorkingScheduleEntity> workingScheduleEntities = workingScheduleRepository.findByWorkDateBetweenAndUser(parsedStartDate, parsedEndDate, user);
            if (CollectionUtils.isEmpty(workingScheduleEntities)) return Collections.emptyList();
            return workingScheduleEntities.stream()
                    .filter(Objects::nonNull)
                    .map(item -> mapWorkingScheduleToScheduleUserResponse(item))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new GeneralException(ex.getMessage());
        }
    }

    @Override
    public ScheduleUserDetailResponse getUserScheduleDetai(Long scheduleId) throws GeneralException {
        try {
            Optional<WorkingScheduleEntity> workingScheduleEntityOtp = workingScheduleRepository.findById(scheduleId);
            if (!workingScheduleEntityOtp.isPresent())
                throw new DataNotFoundException("Work Schedule ".concat(Messageutils.ITEM_NOT_EXITS));
            return new ScheduleUserDetailResponse(workingScheduleEntityOtp.get());
        } catch (Exception ex) {
            throw new GeneralException(ex.getMessage());
        }
    }

    @Override
    public void deleteUserSchedule(Long scheduleId) throws GeneralException {
        try {
            workingScheduleRepository.deleteById(scheduleId);
        } catch (Exception ex) {
            throw new GeneralException(ex.getMessage());
        }
    }

    @Override
    public ScheduleUserDetailResponse addScheduleForUser(ScheduleForUserForm scheduleForUserForm) throws DataNotFoundException, GeneralException {
        try {
            Optional<ShiftEntity> shiftEntityOtp = shiftRepository.findById(scheduleForUserForm.getShiftId());
            if (!shiftEntityOtp.isPresent())
                throw new DataNotFoundException("Shift ".concat(Messageutils.ITEM_NOT_EXITS));
            Optional<WorkTypeEntity> workTypeEntityOtp = workTypeRepository.findById(scheduleForUserForm.getWorkTypeId());
            if (!workTypeEntityOtp.isPresent())
                throw new DataNotFoundException("Work Type ".concat(Messageutils.ITEM_NOT_EXITS));
            WorkingScheduleEntity entity = new WorkingScheduleEntity(scheduleForUserForm);
            entity.setShift(shiftEntityOtp.get());
            entity.setWorkType(workTypeEntityOtp.get());
            entity.setUser(new UserDetailEntity());
            var newEntity = workingScheduleRepository.save(entity);
            return new ScheduleUserDetailResponse(newEntity);
        } catch (Exception ex) {
            throw new GeneralException(ex.getMessage());
        }
    }

    @Override
    public ScheduleUserDetailResponse updateScheduleForUser(ScheduleForUserForm scheduleForUserUpdateForm) throws DataNotFoundException, GeneralException {
        try {
            if (!checkCanEditSchedule(DateUtils.parseStringToLocalDate(scheduleForUserUpdateForm.getWorkDate()))) {
                throw new GeneralException(Messageutils.SCHEDULE_CAN_NOT_EDIT);
            }
            Optional<WorkingScheduleEntity> workingScheduleEntityOtp = workingScheduleRepository.findById(scheduleForUserUpdateForm.getId());
            if (!workingScheduleEntityOtp.isPresent())
                throw new DataNotFoundException("Work Type ".concat(Messageutils.ITEM_NOT_EXITS));
            return addScheduleForUser(scheduleForUserUpdateForm);
        } catch (Exception ex) {
            throw new GeneralException(ex.getMessage());
        }
    }

    @Override
    public List<WorkTypeEntity> getAllWorkType() throws GeneralException {
        try {
            return workTypeRepository.findAll();
        } catch (Exception ex) {
            throw new GeneralException(ex.getMessage());
        }
    }

    @Override
    public List<ShiftEntity> getAllShift() throws GeneralException {
        try {
            return shiftRepository.findAll();
        } catch (Exception ex) {
            throw new GeneralException(ex.getMessage());
        }
    }

    private ScheduleUserResponse mapWorkingScheduleToScheduleUserResponse(WorkingScheduleEntity item) {
        ScheduleUserResponse response = new ScheduleUserResponse();
        response.setId(item.getId());
        response.setTitle(item.getWorkTitle());
        response.setStartDate(DateUtils.concatLocalDateToLocalTime(item.getWorkDate(), item.getStartTime()));
        response.setEndDate(DateUtils.concatLocalDateToLocalTime(item.getWorkDate(), item.getEndTime()));
        response.setColor(ColorEnum.getColorById(item.getShift().getId()));
        response.setTimed(true);
        response.setCanEdit(checkCanEditSchedule(item.getWorkDate()));
        return response;
    }

    private boolean checkCanEditSchedule(LocalDate workDate) {
        LocalDate currentDate = LocalDate.now();
        return workDate.isAfter(currentDate);
    }
}
