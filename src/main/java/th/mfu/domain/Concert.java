package th.mfu.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

//TODO: add proper annotation
@Entity
public class Concert {

    // TODO: add proper annotation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Date date;

    //TODO: add proper annotation
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "performer_id")
    private Performer performer;


    public Concert() {
    }
    public Concert(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Performer getPerformer() {
        return performer;
    }
    public void setPerformer(Performer performer) {
        this.performer = performer;
    }
 

    
}
