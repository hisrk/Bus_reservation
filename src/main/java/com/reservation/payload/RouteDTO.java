package com.reservation.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RouteDTO {

  private long id;
    private String fromLocation;
    private String toLocation;
    private String fromDate;
    private String toDate;
    private String totalTime;
    private String fromTime;
    private String toTime;
private RouteDTO routeDto;
    private BusDTO bus;












}
