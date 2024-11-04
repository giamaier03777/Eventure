public class Ticket{
    private Event event;
    private User owner;
    private String participantName;

    public Ticket(Event event, Usert owner, String participantName){
        this.event = event;
        this.owner = owner;
        this.participantName = participantName;

        if(event.getCurrentSize() < event.getCapacity()){
            event.addParticipants(1);
        } else {
            throw new IllegalArgumentException("Event capacity exceeded for" + event.getName());
        }
    }

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