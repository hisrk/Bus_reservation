package com.reservation.respository;

import com.reservation.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat,Long> {

//    Optional<Seat> findBySeatNumber(String seatNumber);

    Optional<Seat> findBySeatNumberAndBusId(String seatNumber, long busId);

      List<Seat> findReservationByBusId(Long busId);
}
