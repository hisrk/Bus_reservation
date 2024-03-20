package com.reservation.controller;

import com.reservation.model.*;
import com.reservation.payload.BookingDto;
import com.reservation.payload.ReservationDto;
import com.reservation.payload.SeatNumber;
import com.reservation.respository.*;
import com.reservation.util.EmailAndTicketService;
import com.reservation.util.ExcelGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {

        @Autowired
        private PassengerRepository passengerRepository;
        @Autowired
        private BusRepository busRepository;
        @Autowired
        private RouteRepository routeRepository;
        @Autowired
        private EmailAndTicketService emailAndTicketService;

        @Autowired
        private SubrouteRepository subrouteRepository;

        @Autowired
        private SeatRepository seatRepository;



    @Autowired
    private ExcelGenerator excelGenerator;

    boolean busIsPresent;
    boolean routeIsPresent;
    boolean subRouteIsPresent;
    boolean seatIsPresent;



    @PostMapping("/save")
    public ResponseEntity<?> bookTicket(@RequestParam long busId,
                                        @RequestParam long routeId,
                                        @RequestBody Passenger passenger,
                                        @RequestParam String seatnumber) {

        Optional<Bus> busOptional = busRepository.findById(busId);
        Optional<Route> routeOptional = routeRepository.findById(routeId);
        Optional<SubRoute> subrouteOptional = subrouteRepository.findById(routeId);
        Optional<Seat> seatOptional = seatRepository.findBySeatNumberAndBusId(seatnumber, busId);

        boolean busIsPresent = busOptional.isPresent();
        boolean routeIsPresent = routeOptional.isPresent();
        boolean subRouteIsPresent = subrouteOptional.isPresent();
        boolean seatIsPresent = seatOptional.isPresent();

        if ((busIsPresent && routeIsPresent) || (busIsPresent && subRouteIsPresent)) {

            if (seatIsPresent) {
                return new ResponseEntity<>("Seat not found", HttpStatus.BAD_REQUEST);
            }


            Bus bus = busOptional.orElse(null);


            if (bus != null && bus.getAvailableSeats() > 0) {

                SeatNumber requestedSeat = null;
                try {
                    requestedSeat = SeatNumber.valueOf(seatnumber);
                } catch (IllegalArgumentException e) {

                    return new ResponseEntity<>("Seat number not found", HttpStatus.BAD_REQUEST);
                }


                Seat seat = seatOptional.orElse(null);
                if (seat != null && seat.getPassengerId() != 0 && seat.getBusId() == busId) {

                    return new ResponseEntity<>("Seat already booked with this bus", HttpStatus.BAD_REQUEST);
                }


                if (seat == null && (passenger.getId() == 0 || seat.getBusId() != busId)) {

                    Passenger newPassenger = new Passenger();
                    newPassenger.setFirstName(passenger.getFirstName());
                    newPassenger.setLastName(passenger.getLastName());
                    newPassenger.setEmail(passenger.getEmail());
                    newPassenger.setMobile(passenger.getMobile());
                    newPassenger.setBusId(busId);
                    newPassenger.setRouteId(routeId);


                    Passenger savedPassenger = passengerRepository.save(newPassenger);

                     Seat seat1= new Seat();

                    seat1.setPassengerId(savedPassenger.getId());
                    seat1.setSeatStatus(true);
                    seat1.setBusId(busId);
                    seat1.setSeatNumber(seatnumber);
                    seatRepository.save(seat1);


                    bus.setAvailableSeats(bus.getAvailableSeats() - 1);
                    busRepository.save(bus);


                    BookingDto bookingDto = new BookingDto();
                    bookingDto.setFirstName(savedPassenger.getFirstName());
                    bookingDto.setLastName(savedPassenger.getLastName());
                    bookingDto.setEmail(savedPassenger.getEmail());
                    bookingDto.setMobile(savedPassenger.getMobile());
                    bookingDto.setBusId(busId);
                    bookingDto.setRouteId(routeId);
                    bookingDto.setSeatNumber(seatnumber);
                    bookingDto.setPassengerId(seat1.getPassengerId());

                    emailAndTicketService.generateAndSendTicket(savedPassenger, routeOptional.get().getFromLocation(), routeOptional.get().getToLocation(), routeOptional.get().getFromDate());

                    return new ResponseEntity<>(bookingDto, HttpStatus.OK);
                } else {

                    return new ResponseEntity<>("Seat already booked or not available", HttpStatus.BAD_REQUEST);
                }
            } else {

                return new ResponseEntity<>("No available seats for this bus", HttpStatus.BAD_REQUEST);
            }
        } else {

            return new ResponseEntity<>("Invalid bus or route", HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/All")
    public ResponseEntity<List<BookingDto>> getAllReservations() {
        List<Seat> seats = seatRepository.findAll();
        List<BookingDto> bookingDtoList = new ArrayList<>();

        for (Seat seat : seats) {
            Optional<Passenger> passengerOptional = passengerRepository.findById(seat.getPassengerId());
            if (passengerOptional.isPresent()) {
                Passenger passenger = passengerOptional.get();
                BookingDto bookingDto = new BookingDto();
                bookingDto.setId(seat.getId());
                bookingDto.setFirstName(passenger.getFirstName());
                bookingDto.setLastName(passenger.getLastName());
                bookingDto.setEmail(passenger.getEmail());
                bookingDto.setMobile(passenger.getMobile());
                bookingDto.setBusId(seat.getBusId());
                bookingDto.setSeatStatus(seat.isSeatStatus());
                bookingDto.setRouteId(passenger.getRouteId());
                bookingDto.setSeatNumber(seat.getSeatNumber());
                bookingDto.setPassengerId(seat.getPassengerId());

                bookingDtoList.add(bookingDto);
            }
        }

        return ResponseEntity.ok(bookingDtoList);
    }

    @GetMapping("/passengers/excel")
    public ResponseEntity<byte[]> generatePassengersExcel() {
        try {

            List<Passenger> passengers = getPassengers();


            byte[] excelBytes = excelGenerator.generateExcel(passengers);


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("filename", "passengers.xlsx");

            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        }


        @GetMapping("/byBus")
        public ResponseEntity<?> getReservationByBusId(@RequestParam("busId") Long busId){

                    List<Seat> listOfSeatsOnBusId= seatRepository.findReservationByBusId(busId) ;


                    List<BookingDto> listDto=new ArrayList<>();

        for(Seat seat:listOfSeatsOnBusId){

            Passenger passenger=passengerRepository.findById(seat.getPassengerId()).get();

            BookingDto bookingDto = new BookingDto();
            bookingDto.setId(seat.getId());
            bookingDto.setFirstName(passenger.getFirstName());
            bookingDto.setLastName(passenger.getLastName());
            bookingDto.setEmail(passenger.getEmail());
            bookingDto.setMobile(passenger.getMobile());
            bookingDto.setBusId(seat.getBusId());
            bookingDto.setSeatStatus(seat.isSeatStatus());
            bookingDto.setRouteId(passenger.getRouteId());
            bookingDto.setSeatNumber(seat.getSeatNumber());
            bookingDto.setPassengerId(seat.getPassengerId());



                listDto.add(bookingDto);

        }

        return  ResponseEntity.ok(listDto);
        }




    public   List<Passenger> getPassengers(){

        return passengerRepository.findAll();
    }
    }













