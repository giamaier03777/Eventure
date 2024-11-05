package Repository;

import Domain.FreeActivity;
import java.util.ArrayList;
import java.util.List;

public class FreeActivityRepo implements IRepository<FreeActivity> {

    private List<FreeActivity> freeActivityList = new ArrayList<>();

    @Override
    public void create(FreeActivity entity) {
        if (entity != null) {
            freeActivityList.add(entity);
        }
    }

    @Override
    public FreeActivity read(int id) {
        for (FreeActivity freeActivity : freeActivityList) {
            if (freeActivity.getId() == id) {
                return freeActivity;
            }
        }
        return null;
    }

    @Override
    public void update(FreeActivity entity) {
        if (entity == null) {
            return;
        }
        for (int i = 0; i < freeActivityList.size(); i++) {
            if (freeActivityList.get(i).getId() == entity.getId()) {
                freeActivityList.set(i, entity);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        freeActivityList.removeIf(f -> f.getId() == id);
    }

    @Override
    public List<FreeActivity> findAll() {
        return new ArrayList<>(freeActivityList);
    }
}
