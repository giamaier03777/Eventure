package Domain;

public class Booking {
    private int id;
    private ActivitySchedule schedule;
    private String customerName;
    private int numberOfPeople;

    public Booking(ActivitySchedule schedule, String customerName, int numberOfPeople){
        this.schedule = schedule;
        this.customerName = customerName;
        this.numberOfPeople = numberOfPeople;

        this.schedule.reduceCapacity(numberOfPeople);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ActivitySchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(ActivitySchedule schedule) {
        this.schedule = schedule;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
}