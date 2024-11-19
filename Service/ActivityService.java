package Service;

import Domain.Activity;
import Domain.EventType;
import Repository.IRepository;
import Repository.InMemoryRepo;

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
     * @throws IllegalArgumentException if validation fails or the activity already exists.
     */
    public void addActivity(String id_s, String activityName, String capacity_s, String location, String eventType_s, String description, double price) {
        int id = Integer.parseInt(id_s);
        int capacity = Integer.parseInt(capacity_s);
        EventType eventType = EventType.valueOf(eventType_s.toUpperCase());

        if (activityRepo.read(id) != null) {
            throw new IllegalArgumentException("An activity with this ID already exists.");
        }

        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be a positive number.");
        }

        if (activityName == null || activityName.trim().isEmpty()) {
            throw new IllegalArgumentException("Activity name cannot be empty.");
        }

        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Activity location cannot be empty.");
        }

        Activity activity = new Activity(id, activityName, capacity, location, eventType, description, price);
        activityRepo.create(activity);
    }

    /**
     * Retrieves an activity by its ID.
     *
     * @param id the unique identifier of the activity as a string.
     * @return the {@code Activity} object if found, or {@code null} if not found.
     */
    public Activity getActivityById(String id) {
        return activityRepo.read(Integer.parseInt(id));
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
     * @throws IllegalArgumentException if the activity does not exist or validation fails.
     */
    public void updateActivity(String id, String activityName, String capacity, String location, String eventType, String description, double price) {

        Activity existingActivity = activityRepo.read(Integer.parseInt(id));
        if (existingActivity == null) {
            throw new IllegalArgumentException("Activity with the specified ID does not exist.");
        }

        if (Integer.parseInt(capacity) <= 0) {
            throw new IllegalArgumentException("Capacity must be a positive number.");
        }

        if (activityName == null || activityName.trim().isEmpty()) {
            throw new IllegalArgumentException("Activity name cannot be empty.");
        }

        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Activity location cannot be empty.");
        }

        Activity updatedActivity = new Activity(Integer.parseInt(id), activityName, Integer.parseInt(capacity), location, EventType.valueOf(eventType.toUpperCase()), description, price);
        activityRepo.update(updatedActivity);
    }

    /**
     * Deletes an activity by its ID.
     *
     * @param id the unique identifier of the activity as a string.
     * @throws IllegalArgumentException if the activity does not exist.
     */
    public void deleteActivity(String id) {
        if (activityRepo.read(Integer.parseInt(id)) == null) {
            throw new IllegalArgumentException("Activity with the specified ID does not exist.");
        }

        activityRepo.delete(Integer.parseInt(id));
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
}
