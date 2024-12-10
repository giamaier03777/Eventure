package Service;

import Domain.Event;
import Domain.Activity;
import Domain.ReviewableEntity;
import Domain.Ticket;
import Domain.User;
import Repository.IRepository;
import Exception.*;

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
     * @throws EntityAlreadyExistsException if the ticket ID already exists.
     * @throws ValidationException          if the owner is null, the participant name is empty,
     *                                       or the entity has reached its capacity.
     */
    public void addTicket(String id, ReviewableEntity entity, User owner, String participantName) {
        try {
            int ticketId = Integer.parseInt(id);

            if (ticketRepo.read(ticketId) != null) {
                throw new EntityAlreadyExistsException("A ticket with this ID already exists.");
            }

            validateTicketInputs(entity, owner, participantName);

            Ticket ticket = new Ticket(ticketId, entity, owner, participantName);
            ticketRepo.create(ticket);

            adjustEntityCapacity(entity, 1);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
    }

    /**
     * Retrieves a ticket by its unique ID.
     *
     * @param id the unique ID of the ticket.
     * @return the ticket with the specified ID.
     * @throws EntityNotFoundException if the ticket with the specified ID does not exist.
     */
    public Ticket getTicketById(String id) {
        try {
            int ticketId = Integer.parseInt(id);
            Ticket ticket = ticketRepo.read(ticketId);
            if (ticket == null) {
                throw new EntityNotFoundException("Ticket with ID " + id + " not found.");
            }
            return ticket;
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
    }

    /**
     * Updates the details of an existing ticket.
     *
     * @param id              the unique ID of the ticket to update.
     * @param entity          the updated reviewable entity associated with the ticket.
     * @param owner           the updated owner of the ticket.
     * @param participantName the updated participant name.
     * @throws EntityNotFoundException if the ticket does not exist.
     * @throws ValidationException     if the owner is null or the participant name is empty.
     */
    public void updateTicket(String id, ReviewableEntity entity, User owner, String participantName) {
        try {
            int ticketId = Integer.parseInt(id);

            Ticket existingTicket = ticketRepo.read(ticketId);
            if (existingTicket == null) {
                throw new EntityNotFoundException("Ticket with ID " + id + " not found.");
            }

            validateTicketInputs(entity, owner, participantName);

            existingTicket.setEvent(entity);
            existingTicket.setOwner(owner);
            existingTicket.setParticipantName(participantName);

            ticketRepo.update(existingTicket);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
    }

    /**
     * Deletes a ticket by its unique ID and adjusts the associated entity's current size.
     *
     * @param id the unique ID of the ticket to delete.
     * @throws EntityNotFoundException if the ticket does not exist.
     */
    public void deleteTicket(String id) {
        try {
            int ticketId = Integer.parseInt(id);

            Ticket ticket = ticketRepo.read(ticketId);
            if (ticket == null) {
                throw new EntityNotFoundException("Ticket with ID " + id + " not found.");
            }

            adjustEntityCapacity(ticket.getEvent(), -1);
            ticketRepo.delete(ticketId);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
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

    private void adjustEntityCapacity(ReviewableEntity entity, int adjustment) {
        if (entity instanceof Event) {
            Event event = (Event) entity;
            if (event.getCurrentSize() + adjustment > event.getCapacity()) {
                throw new ValidationException("No available tickets for this event.");
            }
            event.setCurrentSize(event.getCurrentSize() + adjustment);
        } else if (entity instanceof Activity) {
            Activity activity = (Activity) entity;
            if (activity.getCurrentSize() + adjustment > activity.getCapacity()) {
                throw new ValidationException("No available tickets for this activity.");
            }
            activity.setCurrentSize(activity.getCurrentSize() + adjustment);
        } else {
            throw new ValidationException("Provided entity is not a valid Event or Activity.");
        }
    }

    private void validateTicketInputs(ReviewableEntity entity, User owner, String participantName) {
        if (owner == null) {
            throw new ValidationException("Owner cannot be null.");
        }
        if (participantName == null || participantName.trim().isEmpty()) {
            throw new ValidationException("Participant name cannot be empty.");
        }
        if (entity == null) {
            throw new ValidationException("Reviewable entity cannot be null.");
        }
    }
}
