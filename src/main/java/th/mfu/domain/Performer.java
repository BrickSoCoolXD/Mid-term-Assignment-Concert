package th.mfu.domain;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;

//TODO: add proper annotation
@Entity

public class Performer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // TODO: add proper annotation
    private Long id;
    private String name;


    public Performer() {
        this.id = id;
        this.name = name;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


}
