package Repository;


import Domain.Ticket;
import java.util.ArrayList;
import java.util.List;

public class TicketRepo implements IRepository<Ticket> {

    private List<Ticket> ticketList = new ArrayList<>();

    @Override
    public void create(Ticket entity) {
        if (entity != null) {
            ticketList.add(entity);
        }
    }

    @Override
    public Ticket read(int id) {
        for (Ticket ticket : ticketList) {
            if (ticket.getId() == id) {
                return ticket;
            }
        }
        return null;
    }

    @Override
    public void update(Ticket entity) {
        if (entity == null) {
            return;
        }
        for (int i = 0; i < ticketList.size(); i++) {
            if (ticketList.get(i).getId() == entity.getId()) {
                ticketList.set(i, entity);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        ticketList.removeIf(ticket -> ticket.getId() == id);
    }

    @Override
    public List<Ticket> findAll() {
        return new ArrayList<>(ticketList);
    }
}