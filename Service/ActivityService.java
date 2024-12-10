package Service;

import Domain.Activity;
import Domain.EventType;
import Repository.IRepository;
import Exception.*;


import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing activities.
 */
public class ActivityService {

    private final IRepository<Activity> activityRepo;

    /**
     * Constructs a new {@code ActivityService}.
     *
     * @param activityRepo the repository for storing and managing activities.
     */
    public ActivityService(IRepository<Activity> activityRepo) {
        this.activityRepo = activityRepo;
    }

    /**
     * Adds a new activity to the repository.
     *
     * @param id_s        the unique identifier for the activity as a string.
     * @param activityName the name of the activity.
     * @param capacity_s   the capacity of the activity as a string.
     * @param location     the location of the activity.
     * @param eventType_s  the type of event as a string.
     * @param description  the description of the activity.
     * @param price        the price of the activity.
     * @throws ValidationException if validation of input data fails.
     */
    public void addActivity(String id_s, String activityName, String capacity_s, String location, String eventType_s, String description, double price) {
        try {
            int id = Integer.parseInt(id_s);
            int capacity = Integer.parseInt(capacity_s);
            EventType eventType = EventType.valueOf(eventType_s.toUpperCase());

            validateActivityInputs(activityName, capacity, location);

            Activity activity = new Activity(id, activityName, capacity, location, eventType, description, price);
            activityRepo.create(activity);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid number format for ID or capacity.", e);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid event type: " + eventType_s, e);
        }
    }

    /**
     * Retrieves an activity by its ID.
     *
     * @param id the unique identifier of the activity as a string.
     * @return the {@code Activity} object if found.
     * @throws EntityNotFoundException if the activity does not exist.
     */
    public Activity getActivityById(String id) {
        try {
            int activityId = Integer.parseInt(id);
            Activity activity = activityRepo.read(activityId);
            if (activity == null) {
                throw new EntityNotFoundException("Activity with ID " + id + " not found.");
            }
            return activity;
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
    }

    /**
     * Updates an existing activity in the repository.
     *
     * @param id           the unique identifier of the activity as a string.
     * @param activityName the updated name of the activity.
     * @param capacity     the updated capacity of the activity as a string.
     * @param location     the updated location of the activity.
     * @param eventType    the updated event type as a string.
     * @param description  the updated description of the activity.
     * @param price        the updated price of the activity.
     * @throws EntityNotFoundException if the activity does not exist.
     * @throws ValidationException if validation of input data fails.
     */
    public void updateActivity(String id, String activityName, String capacity, String location, String eventType, String description, double price) {
        try {
            int activityId = Integer.parseInt(id);
            int updatedCapacity = Integer.parseInt(capacity);
            EventType updatedEventType = EventType.valueOf(eventType.toUpperCase());

            Activity existingActivity = activityRepo.read(activityId);
            if (existingActivity == null) {
                throw new EntityNotFoundException("Activity with ID " + id + " not found.");
            }

            validateActivityInputs(activityName, updatedCapacity, location);

            Activity updatedActivity = new Activity(activityId, activityName, updatedCapacity, location, updatedEventType, description, price);
            activityRepo.update(updatedActivity);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid number format for ID or capacity.", e);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid event type: " + eventType, e);
        }
    }

    /**
     * Deletes an activity by its ID.
     *
     * @param id the unique identifier of the activity as a string.
     * @throws EntityNotFoundException if the activity does not exist.
     */
    public void deleteActivity(String id) {
        try {
            int activityId = Integer.parseInt(id);
            if (activityRepo.read(activityId) == null) {
                throw new EntityNotFoundException("Activity with ID " + id + " not found.");
            }
            activityRepo.delete(activityId);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
    }

    /**
     * Retrieves all activities from the repository.
     *
     * @return a list of all {@code Activity} objects.
     */
    public List<Activity> getAllActivities() {
        return activityRepo.findAll();
    }

    /**
     * Filters activities based on their minimum capacity.
     *
     * @param minCapacity the minimum capacity to filter by.
     * @return a list of {@code Activity} objects that match the criteria.
     */
    public List<Activity> filterActivitiesByCapacity(int minCapacity) {
        return activityRepo.findAll().stream()
                .filter(activity -> activity.getCapacity() >= minCapacity)
                .collect(Collectors.toList());
    }

    /**
     * Filters activities based on their category (event type).
     *
     * @param category the category to filter by as a string.
     * @return a list of {@code Activity} objects that match the category.
     */
    public List<Activity> filterActivitiesByCategory(String category) {
        return activityRepo.findAll().stream()
                .filter(activity -> activity.getCategory().name().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all activities sorted alphabetically by name.
     *
     * @return a list of {@code Activity} objects sorted by name.
     */
    public List<Activity> getActivitiesSortedByName() {
        List<Activity> activities = activityRepo.findAll();
        activities.sort(Comparator.comparing(Activity::getName));
        return activities;
    }

    private void validateActivityInputs(String activityName, int capacity, String location) {
        if (activityName == null || activityName.trim().isEmpty()) {
            throw new ValidationException("Activity name cannot be empty.");
        }
        if (capacity <= 0) {
            throw new ValidationException("Capacity must be a positive number.");
        }
        if (location == null || location.trim().isEmpty()) {
            throw new ValidationException("Activity location cannot be empty.");
        }
    }
}