package Repository;

import Domain.Payment;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepo implements IRepository<Payment> {

    private List<Payment> paymentList = new ArrayList<>();


    @Override
    public void create(Payment entity) {
        if (entity != null) {
            paymentList.add(entity);
        }
    }

    @Override
    public Payment read(int id) {
        for (Payment payment : paymentList) {
            if (payment.getId() == id) {
                return payment;
            }
        }
        return null;
    }

    @Override
    public void update(Payment entity) {
        if (entity == null) {
            return;
        }
        for (int i = 0; i < paymentList.size(); i++) {
            if (paymentList.get(i).getId() == entity.getId()) {
                paymentList.set(i, entity);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        paymentList.removeIf(payment -> payment.getId() == id);
    }

    @Override
    public List<Payment> findAll() {
        return new ArrayList<>(paymentList);
    }
}