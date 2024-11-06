package Repository;

import Domain.Payment;
import Domain.Reservation;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepo implements IRepository<Reservation> {

    private List<Reservation> reservationList = new ArrayList<>();

    @Override
    public void create(Reservation entity) {
        if (entity != null) {
            reservationList.add(entity);
        }
    }

    @Override
    public Reservation read(int id) {
        for (Reservation reservation : reservationList) {
            if (reservation.getId() == id) {
                return reservation;
            }
        }
        return null;
    }

    @Override
    public void update(Reservation entity) {
        if (entity == null) {
            return;
        }
        for (int i = 0; i < reservationList.size(); i++) {
            if (reservationList.get(i).getId() == entity.getId()) {
                reservationList.set(i, entity);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        reservationList.removeIf(reservation -> reservation.getId() == id);
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservationList);
    }
}