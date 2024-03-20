package com.reservation.payload;

import lombok.Data;

@Data
public class BookingDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private long mobile;
    private long busId;
    private long routeId;
    private String seatNumber;
    private boolean seatStatus;
    private long passengerId;


}