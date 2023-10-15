package th.mfu.domain;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


//TODO: add proper annotation
@Entity
@Table(name = "Seat")
public class Seat {

    //TODO: add proper annotation
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String number;
    private String zone;
    private boolean booked;


    //TODO: add proper annotation for relationship to concert
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "concert_id")
    private Concert concert;

    // Constructure
    public Seat(Long id, String number, String zone, boolean booked, Concert concert) {
        this.id = id;
        this.number = number;
        this.zone = zone;
        this.booked = booked;
        this.concert = concert;
    }

    // default default
    public Seat() {
        // Default Constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public Concert getConcert() {
        return concert;
    }

    public void setConcert(Concert concert) {
        this.concert = concert;
    }



}
