package com.reservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="passengers")
@AllArgsConstructor
@NoArgsConstructor
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;
   @Column(unique=true)
    private String email;
   @Column(unique = true)
    private long mobile;


   @Column(name="bus_id",unique = true)
    private long busId;

    @Column(name="routee_id",unique = true)
    private long routeId;





}
