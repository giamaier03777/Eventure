package Service;

import Domain.Activity;
import Domain.ActivitySchedule;
import Repository.ActivityScheduleRepo;

import java.time.LocalDate;
import java.time.LocalTime;

public class ActivityScheduleService {

    private final ActivityScheduleRepo activityScheduleRepo;

    public ActivityScheduleService(ActivityScheduleRepo activityScheduleRepo) {
        this.activityScheduleRepo = activityScheduleRepo;
    }

    public void addActivitySchedule(int id, Activity activity, LocalDate date, LocalTime startTime, LocalTime endTime, int availableCapacity) {
        if (activityScheduleRepo.read(id) != null) {
            throw new IllegalArgumentException("An ActivitySchedule with this ID already exists.");
        }

        if (activity == null) {
            throw new IllegalArgumentException("Activity cannot be null.");
        }

        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be in the past.");
        }

        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start and end times cannot be null.");
        }

        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }

        if (availableCapacity <= 0) {
            throw new IllegalArgumentException("Available capacity must be a positive number.");
        }

        ActivitySchedule activitySchedule = new ActivitySchedule(activity, date, startTime, endTime, availableCapacity);
        activitySchedule.setId(id);
        activityScheduleRepo.create(activitySchedule);
    }

    public ActivitySchedule getActivityScheduleById(int id) {
        return activityScheduleRepo.read(id);
    }

    public void updateActivitySchedule(int id, Activity activity, LocalDate date, LocalTime startTime, LocalTime endTime, int availableCapacity) {
        ActivitySchedule existingSchedule = activityScheduleRepo.read(id);
        if (existingSchedule == null) {
            throw new IllegalArgumentException("ActivitySchedule with the specified ID does not exist.");
        }

        if (activity == null) {
            throw new IllegalArgumentException("Activity cannot be null.");
        }

        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be in the past.");
        }

        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start and end times cannot be null.");
        }

        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }

        if (availableCapacity <= 0) {
            throw new IllegalArgumentException("Available capacity must be a positive number.");
        }

        ActivitySchedule updatedSchedule = new ActivitySchedule(activity, date, startTime, endTime, availableCapacity);
        updatedSchedule.setId(id);
        activityScheduleRepo.update(updatedSchedule);
    }

    public void deleteActivitySchedule(int id) {
        if (activityScheduleRepo.read(id) == null) {
            throw new IllegalArgumentException("ActivitySchedule with the specified ID does not exist.");
        }

        activityScheduleRepo.delete(id);
    }
}
