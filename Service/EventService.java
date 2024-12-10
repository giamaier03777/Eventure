package Service;

import Domain.Event;
import Domain.EventType;
import Repository.IRepository;
import Exception.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing events in the system.
 */
public class EventService {

    private final IRepository<Event> eventRepo;

    /**
     * Constructs a new {@code EventService}.
     *
     * @param eventRepo the repository for storing and managing events.
     */
    public EventService(IRepository<Event> eventRepo) {
        this.eventRepo = eventRepo;
    }

    /**
     * Adds a new event to the repository.
     *
     * @param idString          the unique identifier of the event as a string.
     * @param eventName         the name of the event.
     * @param location          the location of the event.
     * @param capacityString    the total capacity of the event as a string.
     * @param eventTypeString   the type of the event.
     * @param currentSizeString the current number of attendees as a string.
     * @param startDateString   the start date and time of the event as a string.
     * @param endDateString     the end date and time of the event as a string.
     * @param price             the price per ticket for the event.
     * @throws ValidationException if validation fails.
     */
    public void addEvent(String idString, String eventName, String location, String capacityString, String eventTypeString, String currentSizeString, String startDateString, String endDateString, double price) {
        try {
            int id = Integer.parseInt(idString);
            int capacity = Integer.parseInt(capacityString);
            int currentSize = Integer.parseInt(currentSizeString);
            EventType eventType = EventType.valueOf(eventTypeString.toUpperCase());
            LocalDateTime startDate = LocalDateTime.parse(startDateString);
            LocalDateTime endDate = LocalDateTime.parse(endDateString);


            validateEventInputs(eventName, location, capacity, currentSize, startDate, endDate);

            Event event = new Event(id, eventName, location, capacity, eventType, currentSize, startDate, endDate, price);
            eventRepo.create(event);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid number format for ID, capacity, or current size.", e);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid event type: " + eventTypeString, e);
        }
    }

    /**
     * Retrieves an event by its ID.
     *
     * @param id the unique identifier of the event as a string.
     * @return the {@code Event} object if found.
     * @throws EntityNotFoundException if the event does not exist.
     */
    public Event getEventById(String id) {
        try {
            int eventId = Integer.parseInt(id);
            Event event = eventRepo.read(eventId);
            if (event == null) {
                throw new EntityNotFoundException("Event with ID " + id + " not found.");
            }
            return event;
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
    }

    /**
     * Updates an existing event in the repository.
     *
     * @param idString          the unique identifier of the event as a string.
     * @param eventName         the updated name of the event.
     * @param location          the updated location of the event.
     * @param capacityString    the updated total capacity of the event as a string.
     * @param eventTypeString   the updated type of the event.
     * @param currentSizeString the updated current number of attendees as a string.
     * @param startDateString   the updated start date and time of the event as a string.
     * @param endDateString     the updated end date and time of the event as a string.
     * @param price             the updated price per ticket for the event.
     * @throws EntityNotFoundException if the event does not exist.
     * @throws ValidationException if validation fails.
     */
    public void updateEvent(String idString, String eventName, String location, String capacityString, String eventTypeString, String currentSizeString, String startDateString, String endDateString, double price) {
        try {
            int id = Integer.parseInt(idString);
            int capacity = Integer.parseInt(capacityString);
            int currentSize = Integer.parseInt(currentSizeString);
            EventType eventType = EventType.valueOf(eventTypeString.toUpperCase());
            LocalDateTime startDate = LocalDateTime.parse(startDateString);
            LocalDateTime endDate = LocalDateTime.parse(endDateString);

            Event existingEvent = eventRepo.read(id);
            if (existingEvent == null) {
                throw new EntityNotFoundException("Event with ID " + id + " not found.");
            }

            validateEventInputs(eventName, location, capacity, currentSize, startDate, endDate);

            Event updatedEvent = new Event(id, eventName, location, capacity, eventType, currentSize, startDate, endDate, price);
            eventRepo.update(updatedEvent);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid number format for ID, capacity, or current size.", e);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid event type: " + eventTypeString, e);
        }
    }

    /**
     * Deletes an event by its ID.
     *
     * @param id the unique identifier of the event as a string.
     * @throws EntityNotFoundException if the event does not exist.
     */
    public void deleteEvent(String id) {
        try {
            int eventId = Integer.parseInt(id);
            if (eventRepo.read(eventId) == null) {
                throw new EntityNotFoundException("Event with ID " + id + " not found.");
            }
            eventRepo.delete(eventId);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
    }

    /**
     * Retrieves a list of upcoming events.
     *
     * @return a list of events that are scheduled to start in the future.
     */
    public List<Event> getUpcomingEvents() {
        LocalDateTime now = LocalDateTime.now();
        return eventRepo.findAll().stream()
                .filter(event -> event.getStartDate().isAfter(now))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all events from the repository.
     *
     * @return a list of all events.
     */
    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    /**
     * Retrieves events sorted by price in ascending order.
     *
     * @return a list of events sorted by price (low to high).
     */
    public List<Event> getEventsSortedByPriceAsc() {
        List<Event> events = eventRepo.findAll();
        events.sort((e1, e2) -> Double.compare(e1.getPrice(), e2.getPrice()));
        return events;
    }

    /**
     * Retrieves events sorted by price in descending order.
     *
     * @return a list of events sorted by price (high to low).
     */
    public List<Event> getEventsSortedByPriceDesc() {
        List<Event> events = eventRepo.findAll();
        events.sort((e1, e2) -> Double.compare(e2.getPrice(), e1.getPrice()));
        return events;
    }

    /**
     * Retrieves events sorted alphabetically by name.
     *
     * @return a list of events sorted by name.
     */
    public List<Event> getEventsSortedByName() {
        List<Event> events = eventRepo.findAll();
        events.sort(Comparator.comparing(Event::getName));
        return events;
    }

    private void validateEventInputs(String eventName, String location, int capacity, int currentSize, LocalDateTime startDate, LocalDateTime endDate) {
        if (eventName == null || eventName.trim().isEmpty()) {
            throw new ValidationException("Event name cannot be empty.");
        }
        if (location == null || location.trim().isEmpty()) {
            throw new ValidationException("Event location cannot be empty.");
        }
        if (capacity <= 0) {
            throw new ValidationException("Capacity must be a positive number.");
        }
        if (currentSize < 0) {
            throw new ValidationException("Current size cannot be negative.");
        }
        if (currentSize > capacity) {
            throw new ValidationException("Current size cannot exceed capacity.");
        }
        if (startDate == null || endDate == null) {
            throw new ValidationException("Start and end dates cannot be null.");
        }
        if (endDate.isBefore(startDate)) {
            throw new ValidationException("End date must be after start date.");
        }
        if (startDate.isBefore(LocalDateTime.now())) {
            throw new ValidationException("Event cannot start in the past.");
        }
    }
}