package Controller;

import Domain.EventType;
import Service.ActivityService;
import Domain.Activity;

import java.util.List;

public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    public void addActivity(String idString, String activityName, String capacityString, String location, String eventTypeString, String description) {
        try {
            int id = Integer.parseInt(idString);
            int capacity = Integer.parseInt(capacityString);
            EventType eventType = EventType.valueOf(eventTypeString.toUpperCase());

            activityService.addActivity(id, activityName, capacity, location, eventType, description);
            System.out.println("Activity added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID and capacity must be valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Activity getActivityById(String idString) {
        try {
            int id = Integer.parseInt(idString);
            return activityService.getActivityById(id);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
            return null;
        }
    }

    public List<Activity> getAllActivities() {
        return activityService.getAllActivities();
    }

    public void updateActivity(String idString, String activityName, String capacityString, String location, String eventTypeString, String description) {
        try {
            int id = Integer.parseInt(idString);
            int capacity = Integer.parseInt(capacityString);
            EventType eventType = EventType.valueOf(eventTypeString.toUpperCase());

            activityService.updateActivity(id, activityName, capacity, location, eventType, description);
            System.out.println("Activity updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID and capacity must be valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteActivity(String idString) {
        try {
            int id = Integer.parseInt(idString);
            activityService.deleteActivity(id);
            System.out.println("Activity deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<Activity> filterActivitiesByCapacity(int minCapacity) {
        return activityService.filterActivitiesByCapacity(minCapacity);

    }
}
