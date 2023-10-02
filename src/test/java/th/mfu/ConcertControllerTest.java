package th.mfu;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import th.mfu.domain.Concert;
import th.mfu.domain.Reservation;
import th.mfu.domain.Seat;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;

public class ConcertControllerTest {

    @InjectMocks
    private ConcertController concertController;

    @Mock
    private ConcertRepository concertRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    

    @Test
    public void testReserveSeatForm() {
        // Mock data
        Long concertId = 1L;
        Concert mockConcert = new Concert();
        mockConcert.setId(concertId);
        when(concertRepository.findById(concertId)).thenReturn(Optional.of(mockConcert));

        // Mock available seats
        List<Seat> availableSeats = new ArrayList<>();
        availableSeats.add(new Seat()); // Booked = false
        availableSeats.add(new Seat()); // Booked = false
        when(seatRepository.findByBookedFalseAndConcertId(concertId)).thenReturn(availableSeats);

        // Test model attributes
        Model model = mock(Model.class);

        String viewName = concertController.reserveSeatForm(concertId, model);

        // Verify that the concert is added to the model
        verify(model).addAttribute("concert", mockConcert);

        // Verify that an empty Reservation is added to the model
        verify(model, times(1)).addAttribute(eq("reservation"), any(Reservation.class));

        // Verify that the available seats with booked=false are added to the model
        verify(model).addAttribute("seats", availableSeats);

        // Check the view name returned
        assertEquals("reserve-seat", viewName);

        // Verify that the number of seats added to the model is 2
        assertEquals(2, availableSeats.size());
    }

    @Test
    public void testReserveSeat() {
        // Mock data
        Long concertId = 1L;
        Seat mockSeat = new Seat();
        mockSeat.setId(1L);
        Reservation mockReservation = new Reservation();
        mockReservation.setSeat(mockSeat);

        // Mock the behavior of seatRepo.findById
        when(seatRepository.findById(mockSeat.getId())).thenReturn(Optional.of(mockSeat));

        // Call the reserveSeat method
        Model model = mock(Model.class);
        String viewName = concertController.reserveSeat(mockReservation, concertId, model);

        // Verify that the seat is marked as booked
        assertEquals(true, mockSeat.isBooked());

        // Verify that the seat is saved
        verify(seatRepository).save(mockSeat);

        // Verify that the reservation is saved
        verify(reservationRepository).save(mockReservation);

        // Check the view name returned
        assertEquals("redirect:/book", viewName);
    }
}
