package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setDate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());
        schedule = scheduleService.save(schedule, scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds());
        return copyScheduleToScheduleDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.findAllSchedules();
        List<ScheduleDTO> scheduleDTOs = null;
        if (schedules != null) {
            scheduleDTOs = schedules.stream().map(x->this.copyScheduleToScheduleDTO(x)).collect(Collectors.toList());
        }
        return scheduleDTOs;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.findPetSchedules(petId);
        List<ScheduleDTO> scheduleDTOs = null;
        if (schedules != null) {
            scheduleDTOs = schedules.stream().map(x->this.copyScheduleToScheduleDTO(x)).collect(Collectors.toList());
        }
        return scheduleDTOs;

    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.findEmployeeSchedules(employeeId);
        List<ScheduleDTO> scheduleDTOs = null;
        if (schedules != null) {
            scheduleDTOs = schedules.stream().map(x->this.copyScheduleToScheduleDTO(x)).collect(Collectors.toList());
        }
        return scheduleDTOs;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.findCustomerSchedules(customerId);
        List<ScheduleDTO> scheduleDTOs = null;
        if (schedules != null) {
            scheduleDTOs = schedules.stream().map(x->this.copyScheduleToScheduleDTO(x)).collect(Collectors.toList());
        }
        return scheduleDTOs;
    }

    public ScheduleDTO copyScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(x -> x.getId()).collect(Collectors.toList()));
        scheduleDTO.setPetIds(schedule.getPets().stream().map(x -> x.getId()).collect(Collectors.toList()));
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getActivities());
        return scheduleDTO;
    }
}
