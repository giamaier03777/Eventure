package SQLParser;

import Repository.SQLParser;
import Domain.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * SQLParser implementation for {@link ActivitySchedule} entities.
 */
public class ActivityScheduleSQLParser implements SQLParser<ActivitySchedule> {

    private final ActivitySQLParser activitySQLParser;

    /**
     * Constructs a new {@code ActivityScheduleSQLParser} with a dependency on {@code ActivitySQLParser}.
     *
     * @param activitySQLParser the parser for {@link Activity} entities.
     */
    public ActivityScheduleSQLParser(ActivitySQLParser activitySQLParser) {
        this.activitySQLParser = activitySQLParser;
    }

    @Override
    public String getColumns() {
        return "id, activity_id, date, start_time, end_time, available_capacity";
    }

    @Override
    public String getPlaceholders() {
        return "?, ?, ?, ?, ?, ?";
    }

    @Override
    public String getUpdateColumns() {
        return "activity_id = ?, date = ?, start_time = ?, end_time = ?, available_capacity = ?";
    }

    @Override
    public void fillPreparedStatementForInsert(PreparedStatement stmt, ActivitySchedule schedule) throws SQLException {
        stmt.setInt(1, schedule.getId());
        stmt.setInt(2, schedule.getActivity().getId());
        stmt.setDate(3, java.sql.Date.valueOf(schedule.getDate()));
        stmt.setTime(4, java.sql.Time.valueOf(schedule.getStartTime()));
        stmt.setTime(5, java.sql.Time.valueOf(schedule.getEndTime()));
        stmt.setInt(6, schedule.getAvailableCapacity());
    }

    @Override
    public void fillPreparedStatementForUpdate(PreparedStatement stmt, ActivitySchedule schedule) throws SQLException {
        stmt.setInt(1, schedule.getActivity().getId());
        stmt.setDate(2, java.sql.Date.valueOf(schedule.getDate()));
        stmt.setTime(3, java.sql.Time.valueOf(schedule.getStartTime()));
        stmt.setTime(4, java.sql.Time.valueOf(schedule.getEndTime()));
        stmt.setInt(5, schedule.getAvailableCapacity());
        stmt.setInt(6, schedule.getId()); //WHERE clause
    }

    @Override
    public ActivitySchedule parseFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        Activity activity = activitySQLParser.parseFromResultSet(rs);
        LocalDate date = rs.getDate("date").toLocalDate();
        LocalTime startTime = rs.getTime("start_time").toLocalTime();
        LocalTime endTime = rs.getTime("end_time").toLocalTime();
        int availableCapacity = rs.getInt("available_capacity");

        ActivitySchedule schedule = new ActivitySchedule(activity, date, startTime, endTime, availableCapacity);
        schedule.setId(id);
        return schedule;
    }

    @Override
    public int getUpdateParametersCount() {
        return 5;
    }
}
