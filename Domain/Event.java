public class Event extends ReviewableEntity {
    private int currentSize;
    private int capacity;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Event(int id, String eventName, String location, int capacity, int currentSize, LocalDateTime startDate, LocalDateTime endDate) {
        super(id, eventName, location);
        this.location = location;
        this.capacity = capacity;
        this.currentSize = currentSize;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
