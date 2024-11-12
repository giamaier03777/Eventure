package Controller;

import Domain.Event;
import Domain.Ticket;
import Domain.User;
import Service.TicketService;

import java.util.List;

public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public void addTicket(String idString, Event event, User owner, String participantName, double price) {
        try {
            int id = Integer.parseInt(idString);

            ticketService.addTicket(id, event, owner, participantName, price);
            System.out.println("Ticket added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Ticket getTicketById(String idString) {
        try {
            int id = Integer.parseInt(idString);
            return ticketService.getTicketById(id);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
            return null;
        }
    }

    public void updateTicket(String idString, Event event, User owner, String participantName) {
        try {
            int id = Integer.parseInt(idString);

            ticketService.updateTicket(id, event, owner, participantName);
            System.out.println("Ticket updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteTicket(String idString) {
        try {
            int id = Integer.parseInt(idString);
            ticketService.deleteTicket(id);
            System.out.println("Ticket deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<Ticket> getAvailableTickets() {
        return null;
    }
}
