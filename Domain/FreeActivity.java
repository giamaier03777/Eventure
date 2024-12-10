package Domain;

import Repository.EntityParser;
import Repository.Identifiable;
import Exception.*;


/**
 * Represents a free activity, such as an event or workshop that does not require payment.
 * This class extends {@link ReviewableEntity}, inheriting common properties like ID, name, event type, and location.
 */
public class FreeActivity extends ReviewableEntity implements Identifiable {

    /**
     * The program details or description for the free activity.
     */
    private String program;

    /**
     * Constructs a new {@code FreeActivity} object.
     *
     * @param id        the unique identifier for the activity.
     * @param name      the name of the activity.
     * @param location  the location where the activity will take place.
     * @param eventType the type of the activity, as defined by {@link EventType}.
     * @param program   the details or description of the program.
     * @throws ValidationException if any of the parameters are invalid.
     */
    public FreeActivity(int id, String name, String location, EventType eventType, String program) {
        super(id, name, eventType, location);

        if (program == null || program.trim().isEmpty()) {
            throw new ValidationException("Program description cannot be null or empty.");
        }

        this.program = program;
    }

    /**
     * Default constructor for creating an empty {@code FreeActivity} object.
     */
    public FreeActivity() {
        super();
    }

    /**
     * Retrieves the program details for the activity.
     *
     * @return the program description.
     */
    public String getProgram() {
        return program;
    }

    /**
     * Updates the program details for the activity.
     *
     * @param program the new program description.
     * @throws ValidationException if the program is null or empty.
     */
    public void setProgram(String program) {
        if (program == null || program.trim().isEmpty()) {
            throw new ValidationException("Program description cannot be null or empty.");
        }
        this.program = program;
    }

    @Override
    public String toCSV() {
        return "FreeActivity," + getId() + "," +
                getName() + "," +
                getLocation() + "," +
                getEventType() + "," +
                getProgram();
    }

    @Override
    public String toString() {
        return String.format(
                "Free Activity Details:\n" +
                        "- ID: %d\n" +
                        "- Name: %s\n" +
                        "- Location: %s\n" +
                        "- Event Type: %s\n" +
                        "- Program: %s\n",
                this.getId(), this.getName(), this.getLocation(), this.getEventType(), this.program
        );
    }
}
