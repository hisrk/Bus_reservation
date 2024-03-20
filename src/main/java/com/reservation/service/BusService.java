package com.reservation.service;

import com.reservation.model.Bus;
import com.reservation.model.Route;
import com.reservation.model.SubRoute;
import com.reservation.payload.BusDTO;
import com.reservation.payload.SubrouteDTO;
import com.reservation.respository.BusRepository;
import com.reservation.respository.RouteRepository;
import com.reservation.respository.SubrouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusService {
    @Autowired
    private  BusRepository busRepository;
    @Autowired
    private  RouteRepository routeRepository;
    @Autowired
    private  SubrouteRepository subrouteRepository;





    public void saveBus(BusDTO busDTO) {

        Bus bus = new Bus();
        bus.setBusNumber(busDTO.getBusNumber());
        bus.setBusType(busDTO.getBusType());
        bus.setTotalSeats(busDTO.getTotalSeats());
        bus.setAvailableSeats(busDTO.getAvailableSeats());
        bus.setPrice(busDTO.getPrice());




        Route route = new Route();

        route.setFromLocation(busDTO.getRoute().getFromLocation());
        route.setToLocation(busDTO.getRoute().getToLocation());
        route.setFromDate(busDTO.getRoute().getFromDate());
        route.setToDate(busDTO.getRoute().getToDate());
        route.setTotalTime(busDTO.getRoute().getTotalTime());
        route.setFromTime(busDTO.getRoute().getFromTime());
        route.setToTime(busDTO.getRoute().getToTime());



        // Check if the route exists, if not, create a new one
        Route savedRoute = routeRepository.save(route);





        bus.setRoute(route);

        Bus savedBus=busRepository.save(bus);

              Route updateRoute =  routeRepository.findById(savedRoute.getId()).get();

              routeRepository.save((updateRoute));

    updateRoute.setBus(savedBus);
        // Save subroutes if they exist
        if (busDTO.getSubroutes() != null && !busDTO.getSubroutes().isEmpty()) {
            for (SubrouteDTO subrouteDTO : busDTO.getSubroutes()) {
                SubRoute subroute = new SubRoute();
                subroute.setFromLocation(subrouteDTO.getFromLocation());
                subroute.setToLocation(subrouteDTO.getToLocation());
                subroute.setFromDate(subrouteDTO.getFromDate());
                subroute.setToDate(subrouteDTO.getToDate());
                subroute.setTotalTime(subrouteDTO.getTotalTime());
                subroute.setFromTime(subrouteDTO.getFromTime());
                subroute.setToTime(subrouteDTO.getToTime());
                // Associate the subroute with the route
                subroute.setRoute(route);

                

                 subrouteRepository.save(subroute);


            }
        }
        // Save the updated route

    }







}