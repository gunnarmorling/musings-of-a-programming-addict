package de.gmorling.moapa.videorental.service;

import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;

import de.gmorling.moapa.videorental.domain.Movie;

public class MovieRepositoryMockImpl implements MovieRepository {

	private Map<Long, Movie> sampleMovies = new TreeMap<Long, Movie>();
	
	public MovieRepositoryMockImpl() {
		
		Movie movie =
			new Movie(
				1, 
				"The Usual Suspects",
				106,
				"Bryan Singer",
				new GregorianCalendar(1995, 7, 16).getTime());
		
		sampleMovies.put(movie.getId(), movie);
	}
	
	public Movie getMovieById(long id) {
		return sampleMovies.get(id);
	}

}