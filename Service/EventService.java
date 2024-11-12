package Service;

import Domain.Event;
import Domain.EventType;
import Repository.EventRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class EventService {

    private final EventRepo eventRepo;

    public EventService(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    public void addEvent(int id, String eventName, String location, int capacity, EventType eventType, int currentSize, LocalDateTime startDate, LocalDateTime endDate, double price) {
        if (eventRepo.read(id) != null) {
            throw new IllegalArgumentException("An event with this ID already exists.");
        }

        if (eventName == null || eventName.trim().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be empty.");
        }

        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Event location cannot be empty.");
        }

        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be a positive number.");
        }

        if (currentSize < 0) {
            throw new IllegalArgumentException("Current size cannot be negative.");
        }

        if (currentSize > capacity) {
            throw new IllegalArgumentException("Current size cannot exceed capacity.");
        }

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start and end dates cannot be null.");
        }

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must be after start date.");
        }

        if (startDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Event cannot start in the past.");
        }

        Event event = new Event(id, eventName, location, capacity, eventType, currentSize, startDate, endDate, price);
        eventRepo.create(event);
    }

    public Event getEventById(int id) {
        return eventRepo.read(id);
    }

    public void updateEvent(int id, String eventName, String location, int capacity, EventType eventType, int currentSize, LocalDateTime startDate, LocalDateTime endDate, double price) {
        Event existingEvent = eventRepo.read(id);
        if (existingEvent == null) {
            throw new IllegalArgumentException("Event with the specified ID does not exist.");
        }

        if (eventName == null || eventName.trim().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be empty.");
        }

        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Event location cannot be empty.");
        }

        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be a positive number.");
        }

        if (currentSize < 0) {
            throw new IllegalArgumentException("Current size cannot be negative.");
        }

        if (currentSize > capacity) {
            throw new IllegalArgumentException("Current size cannot exceed capacity.");
        }

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start and end dates cannot be null.");
        }

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must be after start date.");
        }

        if (startDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Event cannot start in the past.");
        }

        Event updatedEvent = new Event(id, eventName, location, capacity, eventType, currentSize, startDate, endDate, price);
        eventRepo.update(updatedEvent);
    }

    public void deleteEvent(int id) {
        if (eventRepo.read(id) == null) {
            throw new IllegalArgumentException("Event with the specified ID does not exist.");
        }

        eventRepo.delete(id);
    }

    public List<Event> getUpcomingEvents() {
        LocalDateTime now = LocalDateTime.now();
        return eventRepo.findAll().stream()
                .filter(event -> event.getStartDate().isAfter(now))
                .collect(Collectors.toList());
    }

    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }
}
