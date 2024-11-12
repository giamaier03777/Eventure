package Domain;

import java.util.ArrayList;
import java.util.List;

public class Wishlist {
    int id;
    private User user;
    private List<ReviewableEntity> items;

    public Wishlist(int id, User user, List<ReviewableEntity> items) {
        this.id = id;
        this.user = user;
        this.items = new ArrayList<>();
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ReviewableEntity> getItems() {
        return items;
    }

    public void setItems(List<ReviewableEntity> items) {
        this.items = items;
    }

    public void addItem(ReviewableEntity item){
        if(!items.contains(item)){
            items.add(item);
        } else {
            System.out.println("Item already exists in wishlist");
        }
    }

    public void removeItem(ReviewableEntity item){
        items.remove(item);
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "id=" + id +
                ", user=" + user +
                ", items=" + items +
                '}';
    }
}