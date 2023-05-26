package MyProject.webapp.service.impl;

import MyProject.webapp.exception.DataNotFoundException;
import MyProject.webapp.exception.GeneralException;
import MyProject.webapp.modle.entity.ShiftEntity;
import MyProject.webapp.modle.entity.UserEntity;
import MyProject.webapp.modle.entity.WorkTypeEntity;
import MyProject.webapp.modle.entity.WorkingScheduleEntity;
import MyProject.webapp.modle.request.ScheduleForUserAddForm;
import MyProject.webapp.modle.request.ScheduleForUserEditForm;
import MyProject.webapp.modle.response.ShiftResponse;
import MyProject.webapp.modle.response.WorkTypeResponse;
import MyProject.webapp.modle.response.adminResponse.ScheduleAdminResponse;
import MyProject.webapp.modle.response.schedule.ScheduleUserDetailResponse;
import MyProject.webapp.modle.response.schedule.ScheduleUserResponse;
import MyProject.webapp.repository.repositoryjpa.ShiftRepository;
import MyProject.webapp.repository.repositoryjpa.UserDetailReposirory;
import MyProject.webapp.repository.repositoryjpa.WorkTypeRepository;
import MyProject.webapp.repository.repositoryjpa.WorkingScheduleRepository;
import MyProject.webapp.service.WorkingScheduleSerivice;
import MyProject.webapp.utils.ColorEnum;
import MyProject.webapp.utils.DateUtils;
import MyProject.webapp.utils.Messageutils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorkingScheduleSeriviceImpl implements WorkingScheduleSerivice {
    private final WorkingScheduleRepository workingScheduleRepository;
    private final ShiftRepository shiftRepository;
    private final WorkTypeRepository workTypeRepository;
    private final UserDetailReposirory userDetailReposirory;

    public WorkingScheduleSeriviceImpl(WorkingScheduleRepository workingScheduleRepository, ShiftRepository shiftRepository, WorkTypeRepository workTypeRepository, UserDetailReposirory userDetailReposirory) {
        this.workingScheduleRepository = workingScheduleRepository;
        this.shiftRepository = shiftRepository;
        this.workTypeRepository = workTypeRepository;
        this.userDetailReposirory = userDetailReposirory;
    }

    @Override
    public List<ScheduleUserResponse> getUserSchedule(String startDate, String endDate) throws GeneralException {
        try {
            LocalDate parsedStartDate = DateUtils.parseStringToLocalDate(startDate);
            LocalDate parsedEndDate = DateUtils.parseStringToLocalDate(endDate);

            UserEntity user = getCurrentUser();
            List<WorkingScheduleEntity> workingScheduleEntities = workingScheduleRepository.findByWorkDateBetweenAndUser(parsedStartDate, parsedEndDate, user);
            if (CollectionUtils.isEmpty(workingScheduleEntities)) return Collections.emptyList();
            return workingScheduleEntities.stream()
                    .filter(Objects::nonNull)
                    .map(i -> {
                        try {
                            return mapWorkingScheduleToScheduleUserResponse(i);
                        } catch (GeneralException e) {
                            throw new RuntimeException(e);
                        }
                    })
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
    public ScheduleUserDetailResponse addScheduleForUser(ScheduleForUserAddForm scheduleForUserAddForm) throws DataNotFoundException, GeneralException {
        try {
            Optional<ShiftEntity> shiftEntityOtp = shiftRepository.findById(scheduleForUserAddForm.getShiftId());
            if (!shiftEntityOtp.isPresent())
                throw new DataNotFoundException("Shift ".concat(Messageutils.ITEM_NOT_EXITS));
            Optional<WorkTypeEntity> workTypeEntityOtp = workTypeRepository.findById(scheduleForUserAddForm.getWorkTypeId());
            if (!workTypeEntityOtp.isPresent())
                throw new DataNotFoundException("Work Type ".concat(Messageutils.ITEM_NOT_EXITS));
            WorkingScheduleEntity entity = new WorkingScheduleEntity(scheduleForUserAddForm);
            entity.setShift(shiftEntityOtp.get());
            entity.setWorkType(workTypeEntityOtp.get());
            entity.setUser(getCurrentUser());
            var newEntity = workingScheduleRepository.save(entity);
            return new ScheduleUserDetailResponse(newEntity);
        } catch (Exception ex) {
            throw new GeneralException(ex.getMessage());
        }
    }

    @Override
    public ScheduleUserDetailResponse updateScheduleForUser(ScheduleForUserEditForm scheduleForUserUpdateForm) throws DataNotFoundException, GeneralException {
        try {
            if (!checkCanEditSchedule(DateUtils.parseStringToLocalDate(scheduleForUserUpdateForm.getWorkDate()))) {
                throw new GeneralException(Messageutils.SCHEDULE_CAN_NOT_EDIT);
            }
            Optional<WorkingScheduleEntity> workingScheduleEntityOtp = workingScheduleRepository.findById(scheduleForUserUpdateForm.getId());
            if (!workingScheduleEntityOtp.isPresent())
                throw new DataNotFoundException("Work Type ".concat(Messageutils.ITEM_NOT_EXITS));
            Optional<ShiftEntity> shiftEntityOtp = shiftRepository.findById(scheduleForUserUpdateForm.getShiftId());
            if (!shiftEntityOtp.isPresent())
                throw new DataNotFoundException("Shift ".concat(Messageutils.ITEM_NOT_EXITS));
            Optional<WorkTypeEntity> workTypeEntityOtp = workTypeRepository.findById(scheduleForUserUpdateForm.getWorkTypeId());
            if (!workTypeEntityOtp.isPresent())
                throw new DataNotFoundException("Work Type ".concat(Messageutils.ITEM_NOT_EXITS));
            WorkingScheduleEntity entity = new WorkingScheduleEntity(scheduleForUserUpdateForm);
            entity.setShift(shiftEntityOtp.get());
            entity.setWorkType(workTypeEntityOtp.get());
            entity.setUser(getCurrentUser());
            var newEntity = workingScheduleRepository.save(entity);
            return new ScheduleUserDetailResponse(newEntity);
        } catch (Exception ex) {
            throw new GeneralException(ex.getMessage());
        }
    }

    @Override
    public List<WorkTypeResponse> getAllWorkType() throws GeneralException {
        try {
            return workTypeRepository.findAll().stream()
                    .filter(Objects::nonNull)
                    .map(WorkTypeResponse::new).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new GeneralException(ex.getMessage());
        }
    }

    @Override
    public List<ShiftResponse> getAllShift() throws GeneralException {
        try {
            return shiftRepository.findAll().stream()
                    .filter(Objects::nonNull)
                    .map(ShiftResponse::new).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new GeneralException(ex.getMessage());
        }
    }


    private ScheduleUserResponse mapWorkingScheduleToScheduleUserResponse(WorkingScheduleEntity item) throws GeneralException {
        try {
            ScheduleUserResponse response = new ScheduleUserResponse();
            response.setId(item.getId());
            response.setTitle(item.getWorkTitle());
            response.setStartDate(DateUtils.concatLocalDateToLocalTime(item.getWorkDate(), item.getStartTime()));
            response.setEndDate(DateUtils.concatLocalDateToLocalTime(item.getWorkDate(), item.getEndTime()));
            response.setColor(ColorEnum.getColorById(item.getShift().getId()));
            response.setTimed(true);
            response.setCanEdit(checkCanEditSchedule(item.getWorkDate()));
            return response;
        }catch (Exception ex){
            return null;
        }
    }

    private boolean checkCanEditSchedule(LocalDate workDate) {
        LocalDate currentDate = LocalDate.now();
        return workDate.isAfter(currentDate);
    }


//    logic retrurn response admin scre

    @Override
    public Page<ScheduleAdminResponse> getAllUsersWorkSchedule(int pageNumber, int pageSize, String startDate, String endDate, String employeeName) throws GeneralException {
        List<WorkingScheduleEntity> scheduleEntities = workingScheduleRepository.findWorkingSchedulesByDateAndFullName(
                DateUtils.parseStringToLocalDate(startDate),
                DateUtils.parseStringToLocalDate(endDate),
                employeeName
        );
        if (CollectionUtils.isEmpty(scheduleEntities)) {
            return new PageImpl<>(Collections.emptyList(), PageRequest.of(pageNumber, pageSize), 0);
        }

        Map<UserEntity, Map<LocalDate, List<WorkingScheduleEntity>>> userScheduleMap = scheduleEntities.stream()
                .collect(Collectors.groupingBy(
                        WorkingScheduleEntity::getUser,
                        Collectors.groupingBy(WorkingScheduleEntity::getWorkDate)));
        List<ScheduleAdminResponse> responses = new ArrayList<>();

        for (Map.Entry<UserEntity, Map<LocalDate, List<WorkingScheduleEntity>>> userEntry : userScheduleMap.entrySet()) {
            UserEntity user = userEntry.getKey();
            ScheduleAdminResponse item = new ScheduleAdminResponse(user);
            Map<LocalDate, List<WorkingScheduleEntity>> userDateMap = userEntry.getValue();
            for (Map.Entry<LocalDate, List<WorkingScheduleEntity>> dateEntry : userDateMap.entrySet()) {
                LocalDate workDate = dateEntry.getKey();
                int whatDay = DateUtils.getDayOfWeek(workDate);
                List<WorkingScheduleEntity> userDateEvents = dateEntry.getValue();
                for (WorkingScheduleEntity event : userDateEvents) {
                    item.setScheduleData(whatDay, event);
                }
            }
            responses.add(item);
        }

        int startIndex = pageNumber > 0 ? (pageNumber - 1) * pageSize : pageNumber * pageSize;
        int endIndex = Math.min(startIndex + pageSize, responses.size());
        List<ScheduleAdminResponse> sublist = responses.subList(startIndex, endIndex);

        return new PageImpl<>(sublist, PageRequest.of(pageNumber, pageSize), responses.size());
    }

    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userDetailReposirory.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(Messageutils.ITEM_NOT_EXITS));
    }

}
