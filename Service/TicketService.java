package Service;

import Domain.Event;
import Domain.Ticket;
import Domain.User;
import Repository.TicketRepo;

import java.util.List;

public class TicketService {

    private TicketRepo ticketRepo;

    public TicketService(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public void addTicket(int id, Event event, User owner, String participantName) {
        if (ticketRepo.read(id) != null) {
            throw new IllegalArgumentException("A ticket with this ID already exists.");
        }

        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null.");
        }

        if (owner == null) {
            throw new IllegalArgumentException("Owner cannot be null.");
        }

        if (participantName == null || participantName.trim().isEmpty()) {
            throw new IllegalArgumentException("Participant name cannot be empty.");
        }

        Ticket ticket = new Ticket(id, event, owner, participantName);
        ticketRepo.create(ticket);
    }

    public Ticket getTicketById(int id) {
        Ticket ticket = ticketRepo.read(id);
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket with the specified ID does not exist.");
        }
        return ticket;
    }

    public void updateTicket(int id, Event event, User owner, String participantName) {
        Ticket existingTicket = ticketRepo.read(id);

        if (existingTicket == null) {
            throw new IllegalArgumentException("Ticket with the specified ID does not exist.");
        }

        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null.");
        }

        if (owner == null) {
            throw new IllegalArgumentException("Owner cannot be null.");
        }

        if (participantName == null || participantName.trim().isEmpty()) {
            throw new IllegalArgumentException("Participant name cannot be empty.");
        }

        existingTicket.setEvent(event);
        existingTicket.setOwner(owner);
        existingTicket.setParticipantName(participantName);
        ticketRepo.update(existingTicket);
    }

    public void deleteTicket(int id) {
        Ticket ticket = ticketRepo.read(id);
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket with the specified ID does not exist.");
        }
        ticketRepo.delete(id);
    }

}
