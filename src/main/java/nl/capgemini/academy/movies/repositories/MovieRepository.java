package nl.capgemini.academy.movies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.capgemini.academy.movies.models.Movie;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
