package Service;

import Domain.Event;
import Domain.Activity;
import Domain.ReviewableEntity;
import Domain.Ticket;
import Domain.User;
import Repository.IRepository;
import Repository.InMemoryRepo;

/**
 * Service class for managing tickets in the system.
 */
public class TicketService {

    private final IRepository<Ticket> ticketRepo;

    /**
     * Constructs a TicketService with the specified ticket repository.
     *
     * @param ticketRepo the repository used to store tickets.
     */
    public TicketService(IRepository<Ticket> ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    /**
     * Adds a new ticket to the system.
     *
     * @param id              the unique ID of the ticket.
     * @param entity          the reviewable entity (event or activity) associated with the ticket.
     * @param owner           the user who owns the ticket.
     * @param participantName the name of the participant the ticket is assigned to.
     * @throws IllegalArgumentException if the ticket ID already exists, the owner is null,
     *                                  the participant name is empty, or the entity has reached its capacity.
     */
    public void addTicket(String id, ReviewableEntity entity, User owner, String participantName) {
        if (ticketRepo.read(Integer.parseInt(id)) != null) {
            throw new IllegalArgumentException("A ticket with this ID already exists.");
        }

        if (owner == null) {
            throw new IllegalArgumentException("Owner cannot be null.");
        }

        if (participantName == null || participantName.trim().isEmpty()) {
            throw new IllegalArgumentException("Participant name cannot be empty.");
        }

        Ticket ticket = new Ticket(Integer.parseInt(id), entity, owner, participantName);
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

    /**
     * Retrieves a ticket by its unique ID.
     *
     * @param id the unique ID of the ticket.
     * @return the ticket with the specified ID.
     * @throws IllegalArgumentException if the ticket with the specified ID does not exist.
     */
    public Ticket getTicketById(String id) {
        Ticket ticket = ticketRepo.read(Integer.parseInt(id));
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket with the specified ID does not exist.");
        }
        return ticket;
    }

    /**
     * Updates the details of an existing ticket.
     *
     * @param id              the unique ID of the ticket to update.
     * @param entity          the updated reviewable entity associated with the ticket.
     * @param owner           the updated owner of the ticket.
     * @param participantName the updated participant name.
     * @param orice           an extra parameter not currently used.
     * @throws IllegalArgumentException if the ticket does not exist, the owner is null,
     *                                  or the participant name is empty.
     */
    public void updateTicket(String id, ReviewableEntity entity, User owner, String participantName, double orice) {
        Ticket existingTicket = ticketRepo.read(Integer.parseInt(id));

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

    /**
     * Deletes a ticket by its unique ID and adjusts the associated entity's current size.
     *
     * @param id the unique ID of the ticket to delete.
     * @throws IllegalArgumentException if the ticket does not exist.
     */
    public void deleteTicket(String id) {
        Ticket ticket = ticketRepo.read(Integer.parseInt(id));
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

        ticketRepo.delete(Integer.parseInt(id));
    }

    /**
     * Generates a unique ID for a new ticket.
     *
     * @return a unique ID for the ticket.
     */
    public int generateUniqueId() {
        return ticketRepo.findAll().stream()
                .mapToInt(Ticket::getId)
                .max()
                .orElse(0) + 1;
    }
}
