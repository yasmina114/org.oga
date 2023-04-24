package com.example.org.oga.queries.controllers;

import com.example.org.oga.commonapi.query.GetAllResrvationsQuery;
import com.example.org.oga.commonapi.query.GetReservationByIdQuery;
import com.example.org.oga.queries.entities.Reservation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/query/resrvations")
public class ReservationQueryController {
    private QueryGateway queryGateway;

    @GetMapping("/GetAllReservations")
    public List<Reservation> reservationList() {
        List<Reservation> reponse = queryGateway.query(new GetAllResrvationsQuery(), ResponseTypes.multipleInstancesOf(Reservation.class)).join();
        return reponse;
    }

    @GetMapping("GetResrvationById/{id}")
        public Reservation getReservation (@PathVariable String id){
            return queryGateway.query(new GetReservationByIdQuery(id), ResponseTypes.instanceOf(Reservation.class)).join();
        }


    }



