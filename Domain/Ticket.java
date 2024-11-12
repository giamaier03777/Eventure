package Domain;

import Domain.Event;

public class Ticket{
    private int id;
    private Event event;
    private User owner;
    private String participantName;
    private double cost;


    public Ticket(int id, Event event, User owner, String participantName, double cost){
        this.id = id;
        this.event = event;
        this.owner = owner;
        this.participantName = participantName;
        this.cost = cost;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}