package com.udacity.jdnd.course3.critter.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Nationalized
    @Column(length=500)
    private String name;

    private String phoneNumber;

    @Column(length=5000)
    private String notes;


    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customer", orphanRemoval = true)

    private List<Pet> pets;


}
