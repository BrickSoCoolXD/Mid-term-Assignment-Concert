package th.mfu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import th.mfu.domain.Concert;
import th.mfu.domain.Performer;
import th.mfu.domain.Reservation;
import th.mfu.domain.Seat;

@DataJpaTest
public class ConcertRepositoryTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ReservationRepository reservationRepository;


    @Test
    public void addConcertandPerformer() {

        // Retrieve Ed Sheran's concert "Divide Tour", and the extract the
        // Performer object for Ed.
        Performer perm = new Performer();
        perm.setName("testperformer");

        // Create a new Concert featuring Ed.
        Concert garbage = new Concert();
        garbage.setTitle("Garbage");
        garbage.setPerformer(perm);
        garbage.setDate(new Date());

        // Save the new Concert.
        garbage = em.persist(garbage);

        // Query all Concerts and pick out the new Concert.
        Concert concert = concertRepository.save(garbage);

       

        // Check that the new Concert's ID has been assigned.
        assertNotNull(concert.getId());

        Performer performer = em.find(Performer.class, concert.getPerformer().getId());
        assertNotNull(performer.getId());

        // Check that the result of the query for the new Concert equals
        // the newly created Concert.
        assertEquals(garbage, concert);

    }

    @Test
    public void addReservation(){
        Seat seat = seatRepository.findById(101L).get();
        

        Reservation reservation = new Reservation();
        reservation.setFirstName("John");
        reservation.setLastName("Miller");
        reservation.setEmail("john@email.com");
        reservation.setSeat(seat);

        reservation = reservationRepository.save(reservation);

        Reservation savedReservation = reservationRepository.findById(reservation.getId()).get();

        assertEquals(reservation, savedReservation);

        savedReservation.getSeat().setBooked(true);
        seatRepository.save(savedReservation.getSeat());

        Seat bookedSeat = seatRepository.findById(savedReservation.getSeat().getId()).get();
        assertEquals(true, bookedSeat.isBooked());

    }

    

}
