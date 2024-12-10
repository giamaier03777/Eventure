package Service;

import Domain.Activity;
import Domain.ActivitySchedule;
import Repository.IRepository;
import Exception.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing activity schedules.
 */
public class ActivityScheduleService {

    private final IRepository<ActivitySchedule> activityScheduleRepo;

    /**
     * Constructs a new {@code ActivityScheduleService}.
     *
     * @param activityScheduleRepo the repository for storing and retrieving activity schedules.
     */
    public ActivityScheduleService(IRepository<ActivitySchedule> activityScheduleRepo) {
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
     * @throws EntityAlreadyExistsException if a schedule with the same ID already exists.
     * @throws ValidationException if validation of input data fails.
     */
    public void addActivitySchedule(String idString, Activity activity, String dateString, String startTimeString, String endTimeString, String capacityString) {
        try {
            int id = Integer.parseInt(idString);
            int availableCapacity = Integer.parseInt(capacityString);
            LocalDate date = LocalDate.parse(dateString);
            LocalTime startTime = LocalTime.parse(startTimeString);
            LocalTime endTime = LocalTime.parse(endTimeString);

            if (activityScheduleRepo.read(id) != null) {
                throw new EntityAlreadyExistsException("An ActivitySchedule with this ID already exists.");
            }

            validateActivityScheduleInputs(activity, date, startTime, endTime, availableCapacity);

            ActivitySchedule activitySchedule = new ActivitySchedule(activity, date, startTime, endTime, availableCapacity);
            activitySchedule.setId(id);
            activityScheduleRepo.create(activitySchedule);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid number format for ID or capacity.", e);
        }
    }

    /**
     * Retrieves an activity schedule by its ID.
     *
     * @param id the unique identifier for the schedule.
     * @return the corresponding {@code ActivitySchedule} object.
     * @throws EntityNotFoundException if the schedule does not exist.
     */
    public ActivitySchedule getActivityScheduleById(String id) {
        try {
            int scheduleId = Integer.parseInt(id);
            ActivitySchedule schedule = activityScheduleRepo.read(scheduleId);
            if (schedule == null) {
                throw new EntityNotFoundException("No ActivitySchedule found with ID: " + id);
            }
            return schedule;
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
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
     * @throws EntityNotFoundException if the schedule does not exist.
     * @throws ValidationException if validation of input data fails.
     */
    public void updateActivitySchedule(String idString, Activity activity, String dateString, String startTimeString, String endTimeString, String capacityString) {
        try {
            int id = Integer.parseInt(idString);
            int availableCapacity = Integer.parseInt(capacityString);
            LocalDate date = LocalDate.parse(dateString);
            LocalTime startTime = LocalTime.parse(startTimeString);
            LocalTime endTime = LocalTime.parse(endTimeString);

            ActivitySchedule existingSchedule = activityScheduleRepo.read(id);
            if (existingSchedule == null) {
                throw new EntityNotFoundException("ActivitySchedule with ID " + id + " not found.");
            }

            validateActivityScheduleInputs(activity, date, startTime, endTime, availableCapacity);

            ActivitySchedule updatedSchedule = new ActivitySchedule(activity, date, startTime, endTime, availableCapacity);
            updatedSchedule.setId(id);
            activityScheduleRepo.update(updatedSchedule);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid number format for ID or capacity.", e);
        }
    }

    /**
     * Deletes an activity schedule by its ID.
     *
     * @param id the unique identifier of the schedule.
     * @throws EntityNotFoundException if the schedule does not exist.
     */
    public void deleteActivitySchedule(String id) {
        try {
            int scheduleId = Integer.parseInt(id);
            if (activityScheduleRepo.read(scheduleId) == null) {
                throw new EntityNotFoundException("ActivitySchedule with ID " + id + " not found.");
            }
            activityScheduleRepo.delete(scheduleId);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
    }

    /**
     * Retrieves all schedules for a given activity.
     *
     * @param activity the activity for which schedules are to be retrieved.
     * @return a list of {@code ActivitySchedule} objects associated with the activity.
     * @throws ValidationException if the activity is {@code null}.
     */
    public List<ActivitySchedule> getSchedulesForActivity(Activity activity) {
        if (activity == null) {
            throw new ValidationException("Activity cannot be null.");
        }

        return activityScheduleRepo.findAll()
                .stream()
                .filter(schedule -> schedule.getActivity().getId() == activity.getId())
                .collect(Collectors.toList());
    }

    private void validateActivityScheduleInputs(Activity activity, LocalDate date, LocalTime startTime, LocalTime endTime, int capacity) {
        if (activity == null) {
            throw new ValidationException("Activity cannot be null.");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new ValidationException("Date cannot be in the past.");
        }
        if (startTime == null || endTime == null) {
            throw new ValidationException("Start and end times cannot be null.");
        }
        if (endTime.isBefore(startTime)) {
            throw new ValidationException("End time must be after start time.");
        }
        if (capacity <= 0) {
            throw new ValidationException("Available capacity must be a positive number.");
        }
    }
}
