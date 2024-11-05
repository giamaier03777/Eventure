package Domain;

public class FreeActivity extends ReviewableEntity {
    private String program;

    public FreeActivity(int id, String name, String location, EventType eventType, String program) {
        super(id, name, eventType, location);
        this.program = program;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}
