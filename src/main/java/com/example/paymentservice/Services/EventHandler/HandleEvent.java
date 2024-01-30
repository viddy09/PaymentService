package com.example.paymentservice.Services.EventHandler;

import com.example.paymentservice.DTOs.EventDTO;
import jdk.jfr.Event;

public interface HandleEvent {
    void handleEvent(EventDTO eventDTO);
}
