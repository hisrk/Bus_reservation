package com.reservation.payload;

import com.reservation.model.Route;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubrouteDTO {

    private String fromLocation;
    private String toLocation;
    private String fromDate;
    private String toDate;
    private String totalTime;
    private String fromTime;
    private String toTime;
    private RouteDTO dto;

}
