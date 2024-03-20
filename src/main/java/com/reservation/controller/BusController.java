package com.reservation.controller;


import com.reservation.model.Bus;
import com.reservation.model.Route;
import com.reservation.model.SubRoute;
import com.reservation.payload.BusDTO;
import com.reservation.payload.RouteDTO;
import com.reservation.respository.RouteRepository;
import com.reservation.respository.SubrouteRepository;
import com.reservation.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/bus")
@RestController
public class BusController {

    @Autowired
    private BusService busService;
//    @Autowired
//    private RouteService routeService;

  @Autowired
    private  RouteRepository routeRepository;
@Autowired
    private SubrouteRepository subrouteRepository;


    @PostMapping("/save")
    public String saveBusWithRoutesAndSubroutes(@RequestBody BusDTO busDto){

              busService.saveBus(busDto);

               return "data is saved";

    }


    @GetMapping("/searchBus")
    public ResponseEntity<?> searchBus(@RequestParam("fromLocation") String fromLocation,
                                                    @RequestParam("toLocation") String toLocation,
                                                    @RequestParam("fromDate") String fromDate) {

        List<Route> routes = routeRepository.findByFromLocationAndToLocationAndFromDate(fromLocation, toLocation, fromDate);


        if(!routes.isEmpty()) {
            // Map Route entities to RouteDTOs
            List<RouteDTO> routeDTOs = routes.stream()
                    // .map(this::convertToRouteDTO).
                    //.map(System.out::prinln()
                    .map(route -> this.convertToRouteDTO(route))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(routeDTOs, HttpStatus.OK);
        }else{

        List<SubRoute> subRoutes = subrouteRepository.findByFromLocationAndToLocationAndFromDate(fromLocation,toLocation,fromDate);

           if(!subRoutes.isEmpty()){


//        route.setBus(routes.get(bus));
                   List<RouteDTO> routeDTOS=subRoutes.stream().map((subroute)->this.convertToSubRouteDTO(subroute)).collect(Collectors.toList());


               return new ResponseEntity<>(routeDTOS, HttpStatus.OK);
           }


        }

           return ResponseEntity.ok("no buses found on this route");
    }


    private RouteDTO convertToRouteDTO(Route route) {
        RouteDTO routeDTO = new RouteDTO();
        routeDTO.setId(route.getId());
        routeDTO.setFromLocation(route.getFromLocation());
        routeDTO.setToLocation(route.getToLocation());
        routeDTO.setFromDate(route.getFromDate());
        routeDTO.setToDate(route.getToDate());
        routeDTO.setTotalTime(route.getTotalTime());
        routeDTO.setFromTime(route.getFromTime());
        routeDTO.setToTime(route.getToTime());

        // Map Bus entity to BusDTO if bus is not null
        if (route.getBus() != null) {
            BusDTO busDTO = new BusDTO();
            Bus bus = route.getBus();

            busDTO.setBusNumber(bus.getBusNumber());
            busDTO.setBusType(bus.getBusType());
            busDTO.setTotalSeats(bus.getTotalSeats());
            busDTO.setAvailableSeats(bus.getAvailableSeats());
            busDTO.setPrice(bus.getPrice());

            routeDTO.setBus(busDTO);

        }

        return routeDTO;
    }
    private RouteDTO convertToSubRouteDTO(SubRoute subRoute) {
        RouteDTO routeDTO = new RouteDTO();
        routeDTO.setId(subRoute.getId());
        routeDTO.setFromLocation(subRoute.getFromLocation());
        routeDTO.setToLocation(subRoute.getToLocation());
        routeDTO.setFromDate(subRoute.getFromDate());
        routeDTO.setToDate(subRoute.getToDate());
        routeDTO.setTotalTime(subRoute.getTotalTime());
        routeDTO.setFromTime(subRoute.getFromTime());
        routeDTO.setToTime(subRoute.getToTime());


          Route route1=subRoute.getRoute();

if(route1!=null) {

    Bus bus=route1.getBus();

    if (route1.getBus() != null) {
        BusDTO busDTO = new BusDTO();


        busDTO.setBusNumber(bus.getBusNumber());
        busDTO.setBusType(bus.getBusType());
        busDTO.setTotalSeats(bus.getTotalSeats());
        busDTO.setAvailableSeats(bus.getAvailableSeats());
        busDTO.setPrice(bus.getPrice());

        routeDTO.setBus(busDTO);

    }
}

        return routeDTO;
    }





}
