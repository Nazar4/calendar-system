package com.scs.calendar_system.services;

import com.scs.calendar_system.models.Event;
import com.scs.calendar_system.services.dtos.EventDTO;

import java.util.List;
import java.util.Optional;

public interface EventService {

    List<Event> getAll();

    Optional<Event> getEventById(final long eventId);

    Event createEvent(final EventDTO event);

    Optional<Event> updateEvent(final long eventID, final EventDTO event);

    Optional<Event> removeEvent(final long eventId);
}