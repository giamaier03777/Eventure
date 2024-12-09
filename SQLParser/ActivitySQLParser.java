package SQLParser;

import Domain.*;
import Repository.SQLParser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQLParser implementation for Activity entities.
 */
public class ActivitySQLParser implements SQLParser<Activity> {

    @Override
    public String getColumns() {
        return "id, name, capacity, location, category, description, price";
    }

    @Override
    public String getPlaceholders() {
        return "?, ?, ?, ?, ?, ?, ?";
    }

    @Override
    public String getUpdateColumns() {
        return "name = ?, capacity = ?, location = ?, category = ?, description = ?, price = ?";
    }

    @Override
    public void fillPreparedStatementForInsert(PreparedStatement stmt, Activity activity) throws SQLException {
        stmt.setInt(1, activity.getId());
        stmt.setString(2, activity.getName());
        stmt.setInt(3, activity.getCapacity());
        stmt.setString(4, activity.getLocation());
        stmt.setString(5, activity.getCategory().name());
        stmt.setString(6, activity.getDescription());
        stmt.setDouble(7, activity.getPrice());
    }

    @Override
    public void fillPreparedStatementForUpdate(PreparedStatement stmt, Activity activity) throws SQLException {
        stmt.setString(1, activity.getName());
        stmt.setInt(2, activity.getCapacity());
        stmt.setString(3, activity.getLocation());
        stmt.setString(4, activity.getCategory().name());
        stmt.setString(5, activity.getDescription());
        stmt.setDouble(6, activity.getPrice());
        stmt.setInt(7, activity.getId());
    }

    @Override
    public Activity parseFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int capacity = rs.getInt("capacity");
        String location = rs.getString("location");
        EventType category = EventType.valueOf(rs.getString("category"));
        String description = rs.getString("description");
        double price = rs.getDouble("price");

        return new Activity(id, name, capacity, location, category, description, price);
    }

    @Override
    public int getUpdateParametersCount() {
        return 6;
    }
}

