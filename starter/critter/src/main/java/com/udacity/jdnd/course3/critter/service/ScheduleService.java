package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<Schedule> findAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findPetSchedules(long petId) {
        List<Schedule> schedules =null;
        Optional<Pet> petOptional = petRepository.findById(petId);
        if(petOptional.isPresent()){
            Pet pet = petOptional.get();
            schedules= scheduleRepository.findAllByPets(pet);
        }
        return schedules;
    }

    public List<Schedule> findEmployeeSchedules(long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        return scheduleRepository.findAllByEmployees(employee);
    }

    public List<Schedule> findCustomerSchedules(long customerId) {
        List<Schedule> schedules =null;
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(customerOptional.isPresent()){
            Customer customer = customerOptional.get();
            schedules=  scheduleRepository.findAllByPetsIn(customer.getPets());
        }
        return  schedules;
    }

    public Schedule save(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        List<Pet> pets = petRepository.findAllById(petIds);
        schedule.setEmployees(employees);
        schedule.setPets(pets);
        return scheduleRepository.save(schedule);
    }
}
