package de.gmorling.moapa.videorental.service;

import de.gmorling.moapa.videorental.domain.Movie;

public interface MovieRepository {

	Movie getMovieById(long id);
}
