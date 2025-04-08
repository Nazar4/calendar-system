package com.scs.calendar_system.services.impl;

import com.scs.calendar_system.models.Event;
import com.scs.calendar_system.repositories.EventRepository;
import com.scs.calendar_system.services.EventService;
import com.scs.calendar_system.services.dtos.EventDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    public EventServiceImpl(final EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> getEventById(long eventId) {
        return eventRepository.findById(eventId);
    }

    @Override
    public Event createEvent(final EventDTO event) {
        if (!event.getStartTime().isBefore(event.getEndTime())) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }

        final var newEvent = new Event(
                event.getTitle(),
                event.getDescription(),
                event.getStartTime(),
                event.getEndTime(),
                event.getLocation()
        );

        return eventRepository.save(newEvent);
    }

    @Override
    public Optional<Event> updateEvent(final long eventID, final EventDTO eventDTO) {
        return eventRepository.findById(eventID)
                .map(existingEvent -> {
                    existingEvent.setTitle(Optional.ofNullable(eventDTO.getTitle()).orElse(existingEvent.getTitle()));
                    existingEvent.setStartTime(Optional.ofNullable(eventDTO.getStartTime()).orElse(existingEvent.getStartTime()));
                    existingEvent.setEndTime(Optional.ofNullable(eventDTO.getEndTime()).orElse(existingEvent.getEndTime()));
                    existingEvent.setDescription(Optional.ofNullable(eventDTO.getDescription()).orElse(existingEvent.getDescription()));
                    existingEvent.setLocation(Optional.ofNullable(eventDTO.getLocation()).orElse(existingEvent.getLocation()));

                    final LocalDateTime startTime = existingEvent.getStartTime();
                    final LocalDateTime endTime = existingEvent.getEndTime();
                    if (!startTime.isBefore(endTime)) {
                        throw new IllegalArgumentException("Start time must be before end time.");
                    }

                    return eventRepository.save(existingEvent);
                });
    }


    @Override
    public Optional<Event> removeEvent(long eventId) {
        return eventRepository.findById(eventId)
                .map(event -> {
                    eventRepository.delete(event);
                    return event;
                });
    }
}
