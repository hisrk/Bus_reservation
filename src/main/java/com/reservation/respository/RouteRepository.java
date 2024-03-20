package com.reservation.respository;

import com.reservation.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Long> {


      List<Route> findByFromLocationAndToLocationAndFromDate(@Param("fromLocation") String fromLocation, @Param("toLocation") String toLocation, @Param("fromDate") String fromDate);


}
