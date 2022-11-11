package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer getCustomerByPetId(long petId) {
        Pet pet = petRepository.findById(petId).orElse(null);
        Customer customer = null;
        if (pet != null) {
            customer = pet.getCustomer();
        }
        return customer;
    }

    public Customer saveCustomerWithPets(Customer customer, List<Long> petIds) {
        List<Pet> pets = new ArrayList<>();
        if (petIds != null && !petIds.isEmpty()) {
            pets = petRepository.findAllById(petIds);
        }
        customer.setPets(pets);
        return customerRepository.save(customer);
    }
}
