package Repository;

import Domain.Booking;
import java.util.ArrayList;
import java.util.List;

public class BookingRepo implements IRepository<Booking> {

    private List<Booking> bookingList = new ArrayList<>();

    @Override
    public void create(Booking entity) {
        if (entity != null) {
            bookingList.add(entity);
        }
    }

    @Override
    public Booking read(int id) {
        for (Booking booking : bookingList) {
            if (booking.getId() == id) {
                return booking;
            }
        }
        return null;
    }

    @Override
    public void update(Booking entity) {
        if (entity == null) {
            return;
        }
        for (int i = 0; i < bookingList.size(); i++) {
            if (bookingList.get(i).getId() == entity.getId()) {
                bookingList.set(i, entity);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        bookingList.removeIf(b -> b.getId() == id);
    }

    @Override
    public List<Booking> findAll() {
        return new ArrayList<>(bookingList);
    }
}
