package SQLParser;

import Domain.*;
import Repository.DBRepository;
import Repository.SQLParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WishlistSQLParser implements SQLParser<Wishlist> {

    private final DBRepository<User> userRepo;
    private final DBRepository<Activity> activityRepo;
    private final DBRepository<Event> eventRepo;
    private final DBRepository<FreeActivity> freeActivityRepo;

    public WishlistSQLParser(DBRepository<User> userRepo,
                             DBRepository<Activity> activityRepo,
                             DBRepository<Event> eventRepo,
                             DBRepository<FreeActivity> freeActivityRepo) {
        this.userRepo = userRepo;
        this.activityRepo = activityRepo;
        this.eventRepo = eventRepo;
        this.freeActivityRepo = freeActivityRepo;
    }

    @Override
    public String getColumns() {
        return "id, user_id";
    }

    @Override
    public String getPlaceholders() {
        return "?, ?";
    }

    @Override
    public String getUpdateColumns() {
        return "user_id = ?";
    }

    @Override
    public void fillPreparedStatementForInsert(PreparedStatement stmt, Wishlist wishlist) throws SQLException {
        if (idExists("wishlists", wishlist.getId(), stmt.getConnection())) {
            System.out.println("Wishlist with ID " + wishlist.getId() + " already exists. Skipping insertion.");
        } else {
            System.out.println("Inserting Wishlist with ID: " + wishlist.getId());
            stmt.setInt(1, wishlist.getId());
            stmt.setInt(2, wishlist.getUser().getId());
            stmt.executeUpdate();
            System.out.println("Wishlist inserted successfully.");
        }

        System.out.println("Now inserting items.");
        insertWishlistItems(wishlist, stmt.getConnection());
        System.out.println("Items inserted successfully.");
    }

    private boolean idExists(String tableName, int id, Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }


    @Override
    public void fillPreparedStatementForUpdate(PreparedStatement stmt, Wishlist wishlist) throws SQLException {
        stmt.setInt(1, wishlist.getUser().getId());
        stmt.setInt(2, wishlist.getId());

        updateWishlistItems(wishlist, stmt.getConnection());
    }

    @Override
    public Wishlist parseFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int userId = rs.getInt("user_id");

        User user = userRepo.read(userId);
        if (user == null) {
            throw new SQLException("User with ID " + userId + " not found.");
        }

        List<ReviewableEntity> items = getWishlistItems(id, rs.getStatement().getConnection());
        return new Wishlist(id, user, items);
    }

    private void insertWishlistItems(Wishlist wishlist, Connection connection) throws SQLException {
        String insertSQL = "INSERT INTO wishlist_items (id, wishlist_id, entity_type, entity_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            for (ReviewableEntity item : wishlist.getItems()) {
                int newId = generateNewId("wishlist_items", connection);
                stmt.setInt(1, newId);
                stmt.setInt(2, wishlist.getId());
                stmt.setString(3, item.getClass().getSimpleName());
                stmt.setInt(4, getEntityId(item));
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }



    private void updateWishlistItems(Wishlist wishlist, Connection connection) throws SQLException {
        String deleteSQL = "DELETE FROM wishlist_items WHERE wishlist_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(deleteSQL)) {
            stmt.setInt(1, wishlist.getId());
            stmt.executeUpdate();
        }
        insertWishlistItems(wishlist, connection);
    }

    private List<ReviewableEntity> getWishlistItems(int wishlistId, Connection connection) throws SQLException {
        List<ReviewableEntity> items = new ArrayList<>();
        String selectSQL = "SELECT entity_type, entity_id FROM wishlist_items WHERE wishlist_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(selectSQL)) {
            stmt.setInt(1, wishlistId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String entityType = rs.getString("entity_type");
                    int entityId = rs.getInt("entity_id");
                    items.add(fetchEntityFromRepository(entityType, entityId));
                }
            }
        }
        return items;
    }

    private ReviewableEntity fetchEntityFromRepository(String entityType, int entityId) throws SQLException {
        switch (entityType) {
            case "Activity":
                return activityRepo.read(entityId);
            case "Event":
                return eventRepo.read(entityId);
            case "FreeActivity":
                return freeActivityRepo.read(entityId);
            default:
                throw new SQLException("Unknown entity type: " + entityType);
        }
    }

    private int getEntityId(ReviewableEntity item) {
        if (item instanceof Activity) {
            return ((Activity) item).getId();
        } else if (item instanceof Event) {
            return ((Event) item).getId();
        } else if (item instanceof FreeActivity) {
            return ((FreeActivity) item).getId();
        } else {
            throw new IllegalArgumentException("Unknown entity type: " + item.getClass().getSimpleName());
        }
    }

    @Override
    public int getUpdateParametersCount() {
        return 2;
    }

    private int generateNewId(String tableName, Connection connection) throws SQLException {
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 AS new_id FROM " + tableName;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("new_id");
            }
            throw new SQLException("Failed to generate new ID for table: " + tableName);
        }
    }
}
