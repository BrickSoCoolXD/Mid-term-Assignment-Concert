package th.mfu.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//TODO: add proper annotation
@Entity
@Table(name = "Reservation")
public class Reservation {
    
    //TODO: add proper annotation
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;


    //TODO: add proper annotation for relationship to seat
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    // Constructure
    public Reservation(Long id, String firstName, String lastName, String email, Seat seat) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.seat = seat;
    }

    // Default Constructure
    public Reservation() {
        // Default Constructor
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Seat getSeat() {
        return seat;
    }
    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    
}
