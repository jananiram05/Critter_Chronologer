package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;


    @Transactional
    public Optional<Pet> find(Long id) {

        return petRepository.findById(id);
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByCustomerId(long customerId) {
        return petRepository.findAllByCustomerId(customerId);
    }


    public Pet savePetWithOwnerId(Pet pet, long ownerId) {
        Optional<Customer> customerOptional = customerRepository.findById(ownerId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            pet.setCustomer(customer);
            pet = petRepository.save(pet);
            customer.getPets().add(pet);
            customerRepository.save(customer);
        }


        return pet;
    }
}
