package com.reservation.respository;

import com.reservation.model.SubRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubrouteRepository extends JpaRepository<SubRoute,Long> {

    List<SubRoute> findByFromLocationAndToLocationAndFromDate(@Param("fromLocation") String fromLocation, @Param("toLocation") String toLocation, @Param("fromDate") String fromDate);
}
