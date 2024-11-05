package Domain;

import java.util.ArrayList;
import java.util.List;

public class Wishlist {
    private User user;
    private List<ReviewableEntity> items;

    public Wishlist(User user){
        this.user = user;
        this.items = new ArrayList<>();
    }

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
}