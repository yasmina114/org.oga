package com.example.org.oga.commands.controllers;


import com.example.org.oga.commonapi.commands.CreateReservationCommand;
import com.example.org.oga.commonapi.commands.DeleteReservationCommand;
import com.example.org.oga.commonapi.dtos.CreateReservationRequestDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@CrossOrigin (origins = "")
@RequestMapping(path="/commands/reservation")
@AllArgsConstructor
public class ReservationCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;
    @PostMapping(path="/create")
    public CompletableFuture<String> createReservation(@RequestBody CreateReservationRequestDTO request){
      CompletableFuture<String> commandeResponse = commandGateway.send(new CreateReservationCommand(
              UUID.randomUUID().toString(),
              request.getReference(),
              request.getLieuDep(),
              request.getLieuArr(),
              request.getDateReservation()
      ));
      return commandeResponse;
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception){
        ResponseEntity<String> entity= new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        return entity;
    }
    @GetMapping("/eventStore/{reservationId}")
    public Stream eventStore(@PathVariable String reservationId){
        return eventStore.readEvents(reservationId).asStream();

    }


    @DeleteMapping(path = "/{reservationId}")
    public CompletableFuture<String> Delete(@PathVariable  String resevationId) {
        return  commandGateway.send(new DeleteReservationCommand(resevationId));
    }
}
