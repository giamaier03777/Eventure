package Repository;



import Domain.User;
import java.util.ArrayList;
import java.util.List;

public class UserRepo implements IRepository<User> {

    private List<User> userList = new ArrayList<>();


    @Override
    public void create(User entity) {
        if (entity != null) {
            userList.add(entity);
        }
    }

    @Override
    public User read(int id) {
        for (User user : userList) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void update(User entity) {
        if (entity == null) {
            return;
        }
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == entity.getId()) {
                userList.set(i, entity);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        userList.removeIf(user -> user.getId() == id);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userList);
    }
}