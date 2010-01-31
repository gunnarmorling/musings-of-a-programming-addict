package de.gmorling.moapa.videorental.service;

import java.util.List;

import de.gmorling.moapa.videorental.domain.Movie;

public interface MovieRepository {

	Movie getMovieById(long id);
	
	List<Movie> findMoviesByDirector(String director);
}
