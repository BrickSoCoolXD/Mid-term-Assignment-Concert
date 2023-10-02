package th.mfu;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import th.mfu.domain.Seat;

public interface SeatRepository extends CrudRepository<Seat, Long>{
    public List<Seat> findByConcertId(Long concertId);
    public List<Seat> deleteByConcertId(long id);
    public List<Seat> findByBookedFalseAndConcertId(Long concertId);
    public List<Seat> findByBookedTrueAndConcertId(Long concertId);
    public List<Seat> findByBookedTrue();

    
}
