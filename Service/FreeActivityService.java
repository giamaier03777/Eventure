package Service;

import Domain.FreeActivity;
import Domain.EventType;
import Repository.IRepository;
import Exception.*;

import java.util.Comparator;
import java.util.List;

/**
 * Service class for managing free activities in the system.
 */
public class FreeActivityService {

    private final IRepository<FreeActivity> freeActivityRepo;

    /**
     * Constructs a new {@code FreeActivityService}.
     *
     * @param freeActivityRepo the repository for storing and managing free activities.
     */
    public FreeActivityService(IRepository<FreeActivity> freeActivityRepo) {
        this.freeActivityRepo = freeActivityRepo;
    }

    /**
     * Adds a new free activity to the repository.
     *
     * @param id        the unique identifier of the activity.
     * @param name      the name of the activity.
     * @param location  the location where the activity will take place.
     * @param eventType the type of the activity (e.g., WORKSHOP, RELAXATION).
     * @param program   the program details or description of the activity.
     * @throws ValidationException if validation fails.
     */
    public void addFreeActivity(int id, String name, String location, EventType eventType, String program) {

        validateFreeActivityInputs(name, location, program);

        FreeActivity freeActivity = new FreeActivity(id, name, location, eventType, program);
        freeActivityRepo.create(freeActivity);
    }

    /**
     * Retrieves a free activity by its ID.
     *
     * @param id the unique identifier of the activity.
     * @return the {@code FreeActivity} object if found.
     * @throws EntityNotFoundException if the activity does not exist.
     */
    public FreeActivity getFreeActivityById(int id) {
        FreeActivity activity = freeActivityRepo.read(id);
        if (activity == null) {
            throw new EntityNotFoundException("Free activity with ID " + id + " not found.");
        }
        return activity;
    }

    /**
     * Updates an existing free activity in the repository.
     *
     * @param idString        the unique identifier of the activity as a string.
     * @param name            the updated name of the activity.
     * @param location        the updated location of the activity.
     * @param eventTypeString the updated type of the activity.
     * @param program         the updated program details or description.
     * @throws EntityNotFoundException if the activity does not exist.
     * @throws ValidationException if validation fails.
     */
    public void updateFreeActivity(String idString, String name, String location, String eventTypeString, String program) {
        try {
            int id = Integer.parseInt(idString);
            EventType eventType = EventType.valueOf(eventTypeString.toUpperCase());

            FreeActivity existingActivity = freeActivityRepo.read(id);
            if (existingActivity == null) {
                throw new EntityNotFoundException("Free activity with ID " + id + " not found.");
            }

            validateFreeActivityInputs(name, location, program);

            FreeActivity updatedFreeActivity = new FreeActivity(id, name, location, eventType, program);
            freeActivityRepo.update(updatedFreeActivity);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + idString, e);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid event type: " + eventTypeString, e);
        }
    }

    /**
     * Deletes a free activity by its ID.
     *
     * @param id the unique identifier of the activity as a string.
     * @throws EntityNotFoundException if the activity does not exist.
     */
    public void deleteFreeActivity(String id) {
        try {
            int activityId = Integer.parseInt(id);
            if (freeActivityRepo.read(activityId) == null) {
                throw new EntityNotFoundException("Free activity with ID " + id + " not found.");
            }
            freeActivityRepo.delete(activityId);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
    }

    /**
     * Retrieves all free activities from the repository.
     *
     * @return a list of all free activities.
     */
    public List<FreeActivity> getAllFreeActivities() {
        return freeActivityRepo.findAll();
    }

    /**
     * Retrieves all free activities sorted alphabetically by name.
     *
     * @return a list of free activities sorted by name.
     */
    public List<FreeActivity> getFreeActivitiesSortedByName() {
        List<FreeActivity> freeActivities = freeActivityRepo.findAll();
        freeActivities.sort(Comparator.comparing(FreeActivity::getName));
        return freeActivities;
    }

    private void validateFreeActivityInputs(String name, String location, String program) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Activity name cannot be empty.");
        }
        if (location == null || location.trim().isEmpty()) {
            throw new ValidationException("Activity location cannot be empty.");
        }
        if (program == null || program.trim().isEmpty()) {
            throw new ValidationException("Program details cannot be empty.");
        }
    }
}