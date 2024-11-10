package Service;

import Domain.FreeActivity;
import Domain.EventType;
import Repository.FreeActivityRepo;

public class FreeActivityService {

    private FreeActivityRepo freeActivityRepo;

    public FreeActivityService(FreeActivityRepo freeActivityRepo) {
        this.freeActivityRepo = freeActivityRepo;
    }

    public void addFreeActivity(int id, String name, String location, EventType eventType, String program) {
        if (freeActivityRepo.read(id) != null) {
            throw new IllegalArgumentException("A free activity with this ID already exists.");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Activity name cannot be empty.");
        }

        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Activity location cannot be empty.");
        }

        if (program == null || program.trim().isEmpty()) {
            throw new IllegalArgumentException("Program details cannot be empty.");
        }

        FreeActivity freeActivity = new FreeActivity(id, name, location, eventType, program);
        freeActivityRepo.create(freeActivity);
    }

    public FreeActivity getFreeActivityById(int id) {
        return freeActivityRepo.read(id);
    }

    public void updateFreeActivity(int id, String name, String location, EventType eventType, String program) {
        FreeActivity existingActivity = freeActivityRepo.read(id);
        if (existingActivity == null) {
            throw new IllegalArgumentException("Free activity with the specified ID does not exist.");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Activity name cannot be empty.");
        }

        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Activity location cannot be empty.");
        }

        if (program == null || program.trim().isEmpty()) {
            throw new IllegalArgumentException("Program details cannot be empty.");
        }

        FreeActivity updatedFreeActivity = new FreeActivity(id, name, location, eventType, program);
        freeActivityRepo.update(updatedFreeActivity);
    }

    public void deleteFreeActivity(int id) {
        if (freeActivityRepo.read(id) == null) {
            throw new IllegalArgumentException("Free activity with the specified ID does not exist.");
        }

        freeActivityRepo.delete(id);
    }
}
