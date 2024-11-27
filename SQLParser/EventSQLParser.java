package SQLParser;

import Domain.*;
import Repository.SQLParser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * SQLParser implementation for {@link Event} entities.
 */
public class EventSQLParser implements SQLParser<Event> {

    @Override
    public String getColumns() {
        return "id, name, location, capacity, event_type, current_size, start_date, end_date, price";
    }

    @Override
    public String getPlaceholders() {
        return "?, ?, ?, ?, ?, ?, ?, ?, ?";
    }

    @Override
    public String getUpdateColumns() {
        return "name = ?, location = ?, capacity = ?, event_type = ?, current_size = ?, start_date = ?, end_date = ?, price = ?";
    }

    @Override
    public void fillPreparedStatementForInsert(PreparedStatement stmt, Event event) throws SQLException {
        stmt.setInt(1, event.getId());
        stmt.setString(2, event.getName());
        stmt.setString(3, event.getLocation());
        stmt.setInt(4, event.getCapacity());
        stmt.setString(5, event.getEventType().name());
        stmt.setInt(6, event.getCurrentSize());
        stmt.setObject(7, event.getStartDate());
        stmt.setObject(8, event.getEndDate());
        stmt.setDouble(9, event.getPrice());
    }

    @Override
    public void fillPreparedStatementForUpdate(PreparedStatement stmt, Event event) throws SQLException {
        stmt.setString(1, event.getName());
        stmt.setString(2, event.getLocation());
        stmt.setInt(3, event.getCapacity());
        stmt.setString(4, event.getEventType().name());
        stmt.setInt(5, event.getCurrentSize());
        stmt.setObject(6, event.getStartDate());
        stmt.setObject(7, event.getEndDate());
        stmt.setDouble(8, event.getPrice());
        stmt.setInt(9, event.getId()); // WHERE clause
    }

    @Override
    public Event parseFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String location = rs.getString("location");
        int capacity = rs.getInt("capacity");
        EventType eventType = EventType.valueOf(rs.getString("event_type").toUpperCase());
        int currentSize = rs.getInt("current_size");
        LocalDateTime startDate = rs.getObject("start_date", LocalDateTime.class);
        LocalDateTime endDate = rs.getObject("end_date", LocalDateTime.class);
        double price = rs.getDouble("price");

        return new Event(id, name, location, capacity, eventType, currentSize, startDate, endDate, price);
    }

    @Override
    public int getUpdateParametersCount() {
        return 8;
    }
}

