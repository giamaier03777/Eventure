package Domain;

public class Activity extends ReviewableEntity {
    private int currentSize;
    private String description;
    private int capacity;
    private double price;


    public Activity(int id, String activityName, int capacity, String location, EventType eventType, String description, double price) {
        super(id, activityName,eventType, location);
        this.capacity = capacity;
        this.description = description;
        this.price = price;
        this.currentSize = 0;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "description='" + description + '\'' +
                ", capacity=" + capacity +
                ", price=" + price +
                '}';
    }

    public EventType getCategory() {
        return super.getEventType();
    }
}
