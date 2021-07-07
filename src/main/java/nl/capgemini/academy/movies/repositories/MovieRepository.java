package nl.capgemini.academy.movies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.capgemini.academy.movies.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
