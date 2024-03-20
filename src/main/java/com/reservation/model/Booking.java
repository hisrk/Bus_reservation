//package com.reservation.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name="bookings")
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//public class Booking {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    private String firstName;
//    private String lastName;
//
//    @Column(unique=true)
//    private String email;
//
//    @Column(unique = true)
//    private long mobile;
//
//    @Column(name="bus_id", unique = true)
//    private long busId;
//
//    @Column(name="route_id", unique = true)
//    private long routeId;
//
//    private String seatNumber;
//
//    @ManyToOne
//    private Bus bus; // Assuming you have a Bus entity
//
//    private boolean seatStatus;
//
//    private long passengerId;
//
//    // Constructors, Getters, and Setters
//
//
//
//    // Other methods, such as toString(), can be included as needed
//}
//
