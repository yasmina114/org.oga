package com.example.org.oga.queries.repository;

import com.example.org.oga.queries.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository  extends JpaRepository<Reservation,String> {
}
