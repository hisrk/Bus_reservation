package com.reservation.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BusDTO {



    private String busNumber;
    private String busType;
    private int totalSeats;
    private int availableSeats;
    private double price;
    private RouteDTO route; // Assuming you only need the route ID in the DTO
    private List<SubrouteDTO> subroutes;
}
