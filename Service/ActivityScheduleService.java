package Service;

import Domain.Activity;
import Domain.ActivitySchedule;
import Repository.InMemoryRepo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing activity schedules.
 */
public class ActivityScheduleService {

    private final InMemoryRepo<ActivitySchedule> activityScheduleRepo;

    /**
     * Constructs a new {@code ActivityScheduleService}.
     *
     * @param activityScheduleRepo the repository for storing and retrieving activity schedules.
     */
    public ActivityScheduleService(InMemoryRepo<ActivitySchedule> activityScheduleRepo) {
        this.activityScheduleRepo = activityScheduleRepo;
    }

    /**
     * Adds a new activity schedule.
     *
     * @param idString       the unique identifier for the schedule as a string.
     * @param activity       the activity associated with the schedule.
     * @param dateString     the date of the schedule in ISO format.
     * @param startTimeString the start time of the schedule in ISO format.
     * @param endTimeString   the end time of the schedule in ISO format.
     * @param capacityString  the available capacity for the schedule as a string.
     * @throws IllegalArgumentException if any validation fails.
     */
    public void addActivitySchedule(String idString, Activity activity, String dateString, String startTimeString, String endTimeString, String capacityString) {
        int id = Integer.parseInt(idString);
        int availableCapacity = Integer.parseInt(capacityString);
        LocalDate date = LocalDate.parse(dateString);
        LocalTime startTime = LocalTime.parse(startTimeString);
        LocalTime endTime = LocalTime.parse(endTimeString);

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

    /**
     * Retrieves an activity schedule by its ID.
     *
     * @param id the unique identifier for the schedule.
     * @return the corresponding {@code ActivitySchedule} object, or {@code null} if not found.
     */
    public ActivitySchedule getActivityScheduleById(String id) {
        return activityScheduleRepo.read(Integer.parseInt(id));
    }

    /**
     * Updates an existing activity schedule.
     *
     * @param idString       the unique identifier for the schedule as a string.
     * @param activity       the activity associated with the schedule.
     * @param dateString     the date of the schedule in ISO format.
     * @param startTimeString the start time of the schedule in ISO format.
     * @param endTimeString   the end time of the schedule in ISO format.
     * @param capacityString  the available capacity for the schedule as a string.
     * @throws IllegalArgumentException if the schedule does not exist or validation fails.
     */
    public void updateActivitySchedule(String idString, Activity activity, String dateString, String startTimeString, String endTimeString, String capacityString) {
        int id = Integer.parseInt(idString);
        int availableCapacity = Integer.parseInt(capacityString);
        LocalDate date = LocalDate.parse(dateString);
        LocalTime startTime = LocalTime.parse(startTimeString);
        LocalTime endTime = LocalTime.parse(endTimeString);

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

    /**
     * Deletes an activity schedule by its ID.
     *
     * @param id the unique identifier of the schedule.
     * @throws IllegalArgumentException if the schedule does not exist.
     */
    public void deleteActivitySchedule(String id) {
        if (activityScheduleRepo.read(Integer.parseInt(id)) == null) {
            throw new IllegalArgumentException("ActivitySchedule with the specified ID does not exist.");
        }
        activityScheduleRepo.delete(Integer.parseInt(id));
    }

    /**
     * Retrieves all schedules for a given activity.
     *
     * @param activity the activity for which schedules are to be retrieved.
     * @return a list of {@code ActivitySchedule} objects associated with the activity.
     * @throws IllegalArgumentException if the activity is {@code null}.
     */
    public List<ActivitySchedule> getSchedulesForActivity(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity cannot be null.");
        }

        return activityScheduleRepo.findAll()
                .stream()
                .filter(schedule -> schedule.getActivity().getId() == activity.getId())
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an activity schedule by its ID.
     *
     * @param number the unique identifier of the schedule as a string.
     * @return the corresponding {@code ActivitySchedule} object.
     * @throws IllegalArgumentException if the ID is invalid or the schedule does not exist.
     */
    public ActivitySchedule getScheduleById(String number) {
        try {
            int id = Integer.parseInt(number);
            ActivitySchedule schedule = activityScheduleRepo.read(id);
            if (schedule == null) {
                throw new IllegalArgumentException("No ActivitySchedule found with the specified ID: " + number);
            }
            return schedule;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid ID format. ID must be a number: " + number, e);
        }
    }
}
