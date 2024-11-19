package Domain;

import Repository.EntityParser;
import Repository.Identifiable;

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
     */
    public FreeActivity(int id, String name, String location, EventType eventType, String program) {
        super(id, name, eventType, location);
        this.program = program;
    }

    /**
     * Default constructor for creating an empty {@code FreeActivity} object.
     */
    public FreeActivity() {

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
     */
    public void setProgram(String program) {
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

}
