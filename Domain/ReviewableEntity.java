public abstract class ReviewableEntity {
    private int id;
    private String name;
    private EventType eventType;
    private String location;

    public ReviewableEntity(int id, String name, EventType eventType, String location) {
        this.id = id;
        this.name = name;
        this.eventType = eventType;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
