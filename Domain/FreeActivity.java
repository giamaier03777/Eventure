public class FreeActivity extends ReviewableEntity {
    private String location;
    private String program;

    public FreeActivity(int id, String name, String location, String program) {
        super(id, name, location);
        this.location = location;
        this.program = program;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}
