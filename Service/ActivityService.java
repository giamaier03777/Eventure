package Service;

import Domain.Activity;
import Domain.EventType;
import Repository.ActivityRepo;

import java.util.List;
import java.util.stream.Collectors;

public class ActivityService {

    private final ActivityRepo activityRepo;

    public ActivityService(ActivityRepo activityRepo) {
        this.activityRepo = activityRepo;
    }

    public void addActivity(int id, String activityName, int capacity, String location, EventType eventType, String description) {
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

        Activity activity = new Activity(id, activityName, capacity, location, eventType, description);
        activityRepo.create(activity);
    }

    public Activity getActivityById(int id) {
        return activityRepo.read(id);
    }

    public void updateActivity(int id, String activityName, int capacity, String location, EventType eventType, String description) {
        Activity existingActivity = activityRepo.read(id);
        if (existingActivity == null) {
            throw new IllegalArgumentException("Activity with the specified ID does not exist.");
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

        Activity updatedActivity = new Activity(id, activityName, capacity, location, eventType, description);
        activityRepo.update(updatedActivity);
    }

    public void deleteActivity(int id) {
        if (activityRepo.read(id) == null) {
            throw new IllegalArgumentException("Activity with the specified ID does not exist.");
        }

        activityRepo.delete(id);
    }

    public List<Activity> getAllActivities() {
        return activityRepo.findAll();
    }

    public List<Activity> filterActivitiesByCapacity(int minCapacity) {
        return activityRepo.findAll().stream()
                .filter(activity -> activity.getCapacity() >= minCapacity)
                .collect(Collectors.toList());
    }
}
