package Controller;

import Domain.Activity;
import Domain.ActivitySchedule;
import Service.ActivityScheduleService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ActivityScheduleController {

    private ActivityScheduleService activityScheduleService;

    public ActivityScheduleController(ActivityScheduleService activityScheduleService) {
        this.activityScheduleService = activityScheduleService;
    }

    public void addActivitySchedule(String idString, Activity activity, String dateString, String startTimeString, String endTimeString, String capacityString) {
        try {
            int id = Integer.parseInt(idString);
            int availableCapacity = Integer.parseInt(capacityString);
            LocalDate date = LocalDate.parse(dateString);
            LocalTime startTime = LocalTime.parse(startTimeString);
            LocalTime endTime = LocalTime.parse(endTimeString);

            activityScheduleService.addActivitySchedule(id, activity, date, startTime, endTime, availableCapacity);
            System.out.println("Activity schedule added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID and available capacity must be valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public ActivitySchedule getActivityScheduleById(String idString) {
        try {
            int id = Integer.parseInt(idString);
            return activityScheduleService.getActivityScheduleById(id);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
            return null;
        }
    }

    public void updateActivitySchedule(String idString, Activity activity, String dateString, String startTimeString, String endTimeString, String capacityString) {
        try {
            int id = Integer.parseInt(idString);
            int availableCapacity = Integer.parseInt(capacityString);
            LocalDate date = LocalDate.parse(dateString);
            LocalTime startTime = LocalTime.parse(startTimeString);
            LocalTime endTime = LocalTime.parse(endTimeString);

            activityScheduleService.updateActivitySchedule(id, activity, date, startTime, endTime, availableCapacity);
            System.out.println("Activity schedule updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID and available capacity must be valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteActivitySchedule(String idString) {
        try {
            int id = Integer.parseInt(idString);
            activityScheduleService.deleteActivitySchedule(id);
            System.out.println("Activity schedule deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
