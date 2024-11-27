package SQLParser;

import Domain.*;
import Repository.SQLParser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQLParser implementation for {@link FreeActivity} entities.
 */
public class FreeActivitySQLParser implements SQLParser<FreeActivity> {

    @Override
    public String getColumns() {
        return "id, name, location, event_type, program";
    }

    @Override
    public String getPlaceholders() {
        return "?, ?, ?, ?, ?";
    }

    @Override
    public String getUpdateColumns() {
        return "name = ?, location = ?, event_type = ?, program = ?";
    }

    @Override
    public void fillPreparedStatementForInsert(PreparedStatement stmt, FreeActivity activity) throws SQLException {
        stmt.setInt(1, activity.getId());
        stmt.setString(2, activity.getName());
        stmt.setString(3, activity.getLocation());
        stmt.setString(4, activity.getEventType().name());
        stmt.setString(5, activity.getProgram());
    }

    @Override
    public void fillPreparedStatementForUpdate(PreparedStatement stmt, FreeActivity activity) throws SQLException {
        stmt.setString(1, activity.getName());
        stmt.setString(2, activity.getLocation());
        stmt.setString(3, activity.getEventType().name());
        stmt.setString(4, activity.getProgram());
        stmt.setInt(5, activity.getId()); //  WHERE clause
    }

    @Override
    public FreeActivity parseFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String location = rs.getString("location");
        EventType eventType = EventType.valueOf(rs.getString("event_type").toUpperCase());
        String program = rs.getString("program");

        return new FreeActivity(id, name, location, eventType, program);
    }

    @Override
    public int getUpdateParametersCount() {
        return 4;
    }
}

