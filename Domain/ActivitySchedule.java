import java.time.LocalDate;
import java.time.LocalTime;

public class ActivitySchedule {
    private Activity activity;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int availableCapacity;

    public ActivitySchedule(Activity activity, LocalDate date, LocalTime
            startTime, LocalTime endTime, int availableCapacity){
        this.activity = activity;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.availableCapacity = availableCapacity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getAvailableCapacity() {
        return availableCapacity;
    }

    public void setAvailableCapacity(int availableCapacity) {
        this.availableCapacity = availableCapacity;
    }

    public void reduceCapacity(int numberOfPeople){
        if(availableCapacity >= numberOfPeople){
            availableCapacity -= numberOfPeople;
        } else {
            throw new IllegalArgumentException("Insufficient capacity for booking!");
        }
    }
}