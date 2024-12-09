package SQLParser;

import Domain.*;
import Repository.DBRepository;
import Repository.SQLParser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQLParser implementation for the {@link Booking} class.
 */
public class BookingSQLParser implements SQLParser<Booking> {

    private final DBRepository<ActivitySchedule> activityScheduleRepo;

    /**
     * Constructs a {@link BookingSQLParser} with its dependencies.
     *
     * @param activityScheduleRepo the repository for {@link ActivitySchedule} objects.
     */
    public BookingSQLParser(DBRepository<ActivitySchedule> activityScheduleRepo) {
        this.activityScheduleRepo = activityScheduleRepo;
    }

    @Override
    public String getColumns() {
        return "id, schedule_id, customer_name, number_of_people";
    }

    @Override
    public String getPlaceholders() {
        return "?, ?, ?, ?";
    }

    @Override
    public String getUpdateColumns() {
        return "schedule_id = ?, customer_name = ?, number_of_people = ?";
    }

    @Override
    public void fillPreparedStatementForInsert(PreparedStatement stmt, Booking booking) throws SQLException {
        stmt.setInt(1, booking.getId());
        stmt.setInt(2, booking.getSchedule().getId());
        stmt.setString(3, booking.getCustomerName());
        stmt.setInt(4, booking.getNumberOfPeople());
    }

    @Override
    public void fillPreparedStatementForUpdate(PreparedStatement stmt, Booking booking) throws SQLException {
        stmt.setInt(1, booking.getSchedule().getId());
        stmt.setString(2, booking.getCustomerName());
        stmt.setInt(3, booking.getNumberOfPeople());
        stmt.setInt(4, booking.getId());
    }

    @Override
    public Booking parseFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int scheduleId = rs.getInt("schedule_id");
        String customerName = rs.getString("customer_name");
        int numberOfPeople = rs.getInt("number_of_people");

        ActivitySchedule schedule = activityScheduleRepo.read(scheduleId);
        if (schedule == null) {
            throw new SQLException("ActivitySchedule with ID " + scheduleId + " not found in the database.");
        }

        Booking booking = new Booking(schedule, customerName, numberOfPeople);
        booking.setId(id);
        return booking;
    }

    @Override
    public int getUpdateParametersCount() {
        return 3;
    }
}
