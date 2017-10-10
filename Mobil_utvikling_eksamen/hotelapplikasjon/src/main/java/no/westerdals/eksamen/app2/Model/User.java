package no.westerdals.eksamen.app2.Model;

/**
 * Created by mariu on 25-May-17.
 */

public class User {

    private String email;
    private String roomNumber;

    public User(String email, String roomNumber) {

        this.email = email;
        this.roomNumber = roomNumber;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
