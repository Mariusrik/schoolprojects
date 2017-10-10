package no.westerdals.eksamen.app2.Model;

/**
 * Created by mariu on 25-May-17.
 */

public class RoomServiceRow {

    String item;
    String description;
    String price;
    String whereToBuy;

    public RoomServiceRow(String item, String description, String price, String whereToBuy) {
        this.item = item;
        this.description = description;
        this.price = price;
        this.whereToBuy = whereToBuy;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWhereToBuy() {
        return whereToBuy;
    }

    public void setWhereToBuy(String whereToBuy) {
        this.whereToBuy = whereToBuy;
    }
}
