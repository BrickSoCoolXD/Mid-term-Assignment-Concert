package th.mfu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import th.mfu.domain.Concert;
import th.mfu.domain.Reservation;
import th.mfu.domain.Seat;

@Controller
public class ConcertController {
    // Create auto wired
    @Autowired
    ConcertRepository concertRepo;

    @Autowired
    SeatRepository seatRepo;

    @Autowired
    ReservationRepository reservationRepo;

    // Constructure for controller
    public ConcertController(ConcertRepository concertRepositoryrepository, SeatRepository seatRepository,
            ReservationRepository reservationRepository) {
        this.concertRepo = concertRepositoryrepository;
        this.seatRepo = seatRepository;
        this.reservationRepo = reservationRepository;
    }

    // TODO: add proper annotation for GET method
    @GetMapping("/book")
    public String book(Model model) {
        // List all concerts
        List<Concert> allConcerts = (List<Concert>) concertRepo.findAll();
        model.addAttribute("concerts", allConcerts); // Use "concerts" as the attribute name
        return "book";
    }

    // TODO: add proper annotation for GET method
    @GetMapping("/book/concerts/{concertId}")
    public String reserveSeatForm(@PathVariable Long concertId, Model model) {
        Concert concert = concertRepo.findById(concertId).orElse(null);
        if (concert == null) {
            return "redirect:/concerts/";
        }
        // Add the concert to the model
        model.addAttribute("concert", concert);
        model.addAttribute("reservation", new Reservation());

        // Find availble Seat
        List<Seat> availableSeats = seatRepo.findByBookedFalseAndConcertId(concertId);

        model.addAttribute("seats", availableSeats);

        // return
        return "reserve-seat";
    }

    @Transactional
    // TODO: add proper annotation for POST method
    @PostMapping("/book/concerts/{concertId}")
    public String reserveSeat(@PathVariable Long concertId, @ModelAttribute Reservation reservation) {
        // Find the selected seat by its ID
        Seat selectedSeat = seatRepo.findById(reservation.getSeat().getId()).orElse(null);

        if (selectedSeat != null && !selectedSeat.isBooked()) {
            // Set the selected seat's booked status to true
            selectedSeat.setBooked(true);

            // Save the updated seat
            seatRepo.save(selectedSeat);

            // Save the reservation
            reservationRepo.save(reservation);
        }

        // Redirect to the first page /book
        return "redirect:/book";
    }

    /*************************************/
    /* No Modification beyond this line */
    /*************************************/

    @InitBinder
    public final void initBinderUsuariosFormValidator(final WebDataBinder binder, final Locale locale) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/concerts")
    public String listConcerts(Model model) {
        model.addAttribute("concerts", concertRepo.findAll());
        return "list-concert";
    }

    @GetMapping("/add-concert")
    public String addAConcertForm(Model model) {
        model.addAttribute("concert", new Concert());
        return "add-concert-form";
    }

    @PostMapping("/concerts")
    public String saveConcert(@ModelAttribute Concert concert) {
        concertRepo.save(concert);
        return "redirect:/concerts";
    }

    @Transactional
    @GetMapping("/delete-concert/{id}")
    public String deleteConcert(@PathVariable long id) {
        seatRepo.deleteByConcertId(id);
        concertRepo.deleteById(id);
        return "redirect:/concerts";
    }

    @GetMapping("/concerts/{id}/seats")
    public String showAddSeatForm(Model model, @PathVariable Long id) {
        model.addAttribute("seats", seatRepo.findByConcertId(id));

        Concert concert = concertRepo.findById(id).get();
        Seat seat = new Seat();
        seat.setConcert(concert);
        model.addAttribute("newseat", seat);
        return "seat-mgmt";
    }

    @PostMapping("/concerts/{id}/seats")
    public String saveSeat(@ModelAttribute Seat newseat, @PathVariable Long id) {
        Concert concert = concertRepo.findById(id).get();
        newseat.setConcert(concert);
        seatRepo.save(newseat);
        return "redirect:/concerts/" + id + "/seats";
    }
}
