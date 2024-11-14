package Service;

import Domain.Event;
import Domain.Activity;
import Domain.ReviewableEntity;
import Domain.Ticket;
import Domain.User;
import Repository.InMemoryRepo;

public class TicketService {

    private final InMemoryRepo<Ticket> ticketRepo;

    public TicketService(InMemoryRepo<Ticket> ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public void addTicket(int id, ReviewableEntity entity, User owner, String participantName) {
        if (ticketRepo.read(id) != null) {
            throw new IllegalArgumentException("A ticket with this ID already exists.");
        }

        if (owner == null) {
            throw new IllegalArgumentException("Owner cannot be null.");
        }

        if (participantName == null || participantName.trim().isEmpty()) {
            throw new IllegalArgumentException("Participant name cannot be empty.");
        }

        Ticket ticket = new Ticket(id, entity, owner, participantName);
        ticketRepo.create(ticket);

        if (entity instanceof Event) {
            Event event = (Event) entity;
            if (event.getCurrentSize() >= event.getCapacity()) {
                throw new IllegalArgumentException("No available tickets for this event.");
            }
            event.setCurrentSize(event.getCurrentSize() + 1);
        } else if (entity instanceof Activity) {
            Activity activity = (Activity) entity;
            if (activity.getCurrentSize() >= activity.getCapacity()) {
                throw new IllegalArgumentException("No available tickets for this activity.");
            }
            activity.setCurrentSize(activity.getCurrentSize() + 1);
        } else {
            throw new IllegalArgumentException("Provided entity is not a valid Event or Activity.");
        }
    }

    public Ticket getTicketById(int id) {
        Ticket ticket = ticketRepo.read(id);
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket with the specified ID does not exist.");
        }
        return ticket;
    }

    public void updateTicket(int id, ReviewableEntity entity, User owner, String participantName, double orice) {
        Ticket existingTicket = ticketRepo.read(id);

        if (existingTicket == null) {
            throw new IllegalArgumentException("Ticket with the specified ID does not exist.");
        }

        if (owner == null) {
            throw new IllegalArgumentException("Owner cannot be null.");
        }

        if (participantName == null || participantName.trim().isEmpty()) {
            throw new IllegalArgumentException("Participant name cannot be empty.");
        }

        existingTicket.setEvent(entity);
        existingTicket.setOwner(owner);
        existingTicket.setParticipantName(participantName);

        ticketRepo.update(existingTicket);
    }

    public void deleteTicket(int id) {
        Ticket ticket = ticketRepo.read(id);
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket with the specified ID does not exist.");
        }

        ReviewableEntity entity = ticket.getEvent();
        if (entity instanceof Event) {
            Event event = (Event) entity;
            if (event.getCurrentSize() > 0) {
                event.setCurrentSize(event.getCurrentSize() - 1);
            }
        } else if (entity instanceof Activity) {
            Activity activity = (Activity) entity;
            if (activity.getCurrentSize() > 0) {
                activity.setCurrentSize(activity.getCurrentSize() - 1);
            }
        }

        ticketRepo.delete(id);
    }

    public int generateUniqueId() {
        return ticketRepo.findAll().stream()
                .mapToInt(Ticket::getId)
                .max()
                .orElse(0) + 1;
    }
}
