package com.reservation.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Route {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromLocation;
    private String toLocation;
    private String fromDate;
    private String toDate;
    private String totalTime; // Or you can use Duration if you prefer


    private String fromTime;


    private String toTime;

            @OneToOne(fetch=FetchType.LAZY)
          @JoinColumn(name="bus_id")
                private Bus bus;


    @OneToMany(mappedBy = "route",fetch=FetchType.LAZY)
    private List<SubRoute> subroutes;





        @Override
        public int hashCode() {
            return Objects.hash(id, fromLocation, toLocation, fromDate, toDate, totalTime, fromTime, toTime);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Route route = (Route) obj;
            return Objects.equals(id, route.id) &&
                    Objects.equals(fromLocation, route.fromLocation) &&
                    Objects.equals(toLocation, route.toLocation) &&
                    Objects.equals(fromDate, route.fromDate) &&
                    Objects.equals(toDate, route.toDate) &&
                    Objects.equals(totalTime, route.totalTime) &&
                    Objects.equals(fromTime, route.fromTime) &&
                    Objects.equals(toTime, route.toTime);
        }
    }



