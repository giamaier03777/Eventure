package Controller;

import Domain.Event;
import Domain.EventType;
import Service.EventService;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    public void addEvent(String idString, String eventName, String location, String capacityString, String eventTypeString, String currentSizeString, String startDateString, String endDateString, double price) {
        try {
            int id = Integer.parseInt(idString);
            int capacity = Integer.parseInt(capacityString);
            int currentSize = Integer.parseInt(currentSizeString);
            EventType eventType = EventType.valueOf(eventTypeString.toUpperCase());
            LocalDateTime startDate = LocalDateTime.parse(startDateString);
            LocalDateTime endDate = LocalDateTime.parse(endDateString);

            eventService.addEvent(id, eventName, location, capacity, eventType, currentSize, startDate, endDate, price);
            System.out.println("Event added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID, capacity, and current size must be valid numbers.");
        } catch (DateTimeParseException e) {
            System.out.println("Start and end dates must be in a valid format (e.g., '2023-12-31T10:15:30').");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Event getEventById(String idString) {
        try {
            int id = Integer.parseInt(idString);
            return eventService.getEventById(id);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
            return null;
        }
    }

    public void updateEvent(String idString, String eventName, String location, String capacityString, String eventTypeString, String currentSizeString, String startDateString, String endDateString, double price) {
        try {
            int id = Integer.parseInt(idString);
            int capacity = Integer.parseInt(capacityString);
            int currentSize = Integer.parseInt(currentSizeString);
            EventType eventType = EventType.valueOf(eventTypeString.toUpperCase());
            LocalDateTime startDate = LocalDateTime.parse(startDateString);
            LocalDateTime endDate = LocalDateTime.parse(endDateString);

            eventService.updateEvent(id, eventName, location, capacity, eventType, currentSize, startDate, endDate, price);
            System.out.println("Event updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID, capacity, and current size must be valid numbers.");
        } catch (DateTimeParseException e) {
            System.out.println("Start and end dates must be in a valid format (e.g., '2023-12-31T10:15:30').");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteEvent(String idString) {
        try {
            int id = Integer.parseInt(idString);
            eventService.deleteEvent(id);
            System.out.println("Event deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<Event> searchEvents(String keyword) {
        return null;
    }

    public List<Event> filterByEventType(String eventType) {
        return null;
    }

    public List<Event> getUpcomingEvents() {
        return eventService.getUpcomingEvents();
    }

    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }
}
