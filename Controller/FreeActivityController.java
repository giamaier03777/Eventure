package Controller;

import Domain.FreeActivity;
import Domain.EventType;
import Service.FreeActivityService;

public class FreeActivityController {

    private final FreeActivityService freeActivityService;

    public FreeActivityController(FreeActivityService freeActivityService) {
        this.freeActivityService = freeActivityService;
    }

    public void addFreeActivity(String idString, String name, String location, String eventTypeString, String program) {
        try {
            int id = Integer.parseInt(idString);
            EventType eventType = EventType.valueOf(eventTypeString.toUpperCase());

            freeActivityService.addFreeActivity(id, name, location, eventType, program);
            System.out.println("Free activity added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public FreeActivity getFreeActivityById(String idString) {
        try {
            int id = Integer.parseInt(idString);
            return freeActivityService.getFreeActivityById(id);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
            return null;
        }
    }

    public void updateFreeActivity(String idString, String name, String location, String eventTypeString, String program) {
        try {
            int id = Integer.parseInt(idString);
            EventType eventType = EventType.valueOf(eventTypeString.toUpperCase());

            freeActivityService.updateFreeActivity(id, name, location, eventType, program);
            System.out.println("Free activity updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteFreeActivity(String idString) {
        try {
            int id = Integer.parseInt(idString);
            freeActivityService.deleteFreeActivity(id);
            System.out.println("Free activity deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
