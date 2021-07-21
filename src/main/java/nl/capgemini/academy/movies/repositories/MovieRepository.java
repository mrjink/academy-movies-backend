package nl.capgemini.academy.movies.repositories;

import nl.capgemini.academy.movies.models.Movie;
import nl.capgemini.academy.movies.models.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findAllByUser(User user, Sort sort);

    Optional<Movie> findByUserAndId(User user, long id);

    void deleteByUserAndId(User user, long id);
}
