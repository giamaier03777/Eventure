package Domain;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Event extends ReviewableEntity {
    private int currentSize;
    private int capacity;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    double price;

    public Event(int id, String eventName, String location, int capacity, EventType eventType, int currentSize, LocalDateTime startDate, LocalDateTime endDate, double price) {
        super(id, eventName, eventType, location);
        this.capacity = capacity;
        this.currentSize = currentSize;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


    public Instant getDate() {
        return startDate.toInstant(ZoneOffset.UTC);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Id: " + this.getId() +
                ", Event name: " + this.getName() +
                ", Location: " + this.getLocation() +
                ", Event type: " + this.getEventType() +
                ", price: " + price + "\n";
    }
}
