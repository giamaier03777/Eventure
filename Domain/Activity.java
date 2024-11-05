package Domain;

public class Activity extends ReviewableEntity {
    private String description;
    private int capacity;


    public Activity(int id, String activityName, int capacity, String location, EventType eventType, String description) {
        super(id, activityName,eventType, location);
        this.capacity = capacity;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Activity{" +
                "capacity=" + capacity +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
