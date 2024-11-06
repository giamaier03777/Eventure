package Domain;

import Domain.Event;

public class Ticket{
    private int id;
    private Event event;
    private User owner;
    private String participantName;

    public Ticket(Event event, User owner, String participantName){
        this.id = id;
        this.event = event;
        this.owner = owner;
        this.participantName = participantName;
    }

    public int getId(){return id;}

    public void setId(int id) {this.id = id;}

    public Event getEvent(){
        return event;
    }

    public void setEvent(Event event){
        this.event = event;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

}