package SQLParser;

import Domain.*;
import Repository.SQLParser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * SQLParser implementation for {@link Reservation} entities.
 */
public class ReservationSQLParser implements SQLParser<Reservation> {

    private final UserSQLParser userSQLParser;
    private final ActivityScheduleSQLParser activityScheduleSQLParser;

    /**
     * Constructs a {@link ReservationSQLParser} with its dependencies.
     *
     * @param userSQLParser             the parser for {@link User} objects.
     * @param activityScheduleSQLParser the parser for {@link ActivitySchedule} objects.
     */
    public ReservationSQLParser(UserSQLParser userSQLParser, ActivityScheduleSQLParser activityScheduleSQLParser) {
        this.userSQLParser = userSQLParser;
        this.activityScheduleSQLParser = activityScheduleSQLParser;
    }

    @Override
    public String getColumns() {
        return "id, user_id, activity_schedule_id, number_of_people, reservation_date";
    }

    @Override
    public String getPlaceholders() {
        return "?, ?, ?, ?, ?";
    }

    @Override
    public String getUpdateColumns() {
        return "user_id = ?, activity_schedule_id = ?, number_of_people = ?, reservation_date = ?";
    }

    @Override
    public void fillPreparedStatementForInsert(PreparedStatement stmt, Reservation reservation) throws SQLException {
        stmt.setInt(1, reservation.getId());
        stmt.setInt(2, reservation.getUser().getId());
        stmt.setInt(3, reservation.getActivitySchedule().getId());
        stmt.setInt(4, reservation.getNumberOfPeople());
        stmt.setObject(5, reservation.getReservationDate());
    }

    @Override
    public void fillPreparedStatementForUpdate(PreparedStatement stmt, Reservation reservation) throws SQLException {
        stmt.setInt(1, reservation.getUser().getId());
        stmt.setInt(2, reservation.getActivitySchedule().getId());
        stmt.setInt(3, reservation.getNumberOfPeople());
        stmt.setObject(4, reservation.getReservationDate());
        stmt.setInt(5, reservation.getId()); // WHERE clause
    }

    @Override
    public Reservation parseFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int userId = rs.getInt("user_id");
        int activityScheduleId = rs.getInt("activity_schedule_id");
        int numberOfPeople = rs.getInt("number_of_people");
        LocalDateTime reservationDate = rs.getObject("reservation_date", LocalDateTime.class);

        User user = userSQLParser.parseFromResultSet(rs);
        ActivitySchedule activitySchedule = activityScheduleSQLParser.parseFromResultSet(rs);

        return new Reservation(id, user, activitySchedule, numberOfPeople, reservationDate);
    }

    @Override
    public int getUpdateParametersCount() {
        return 4;
    }
}

