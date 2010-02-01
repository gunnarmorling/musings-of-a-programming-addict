package de.gmorling.moapa.videorental.ws.xmlbeans;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import xmlbeans.Movie;
import xmlbeans.FindMoviesByDirectorRequest;
import xmlbeans.FindMoviesByDirectorResponse;
import xmlbeans.GetMovieByIdRequest;
import xmlbeans.GetMovieByIdResponse;
import de.gmorling.moapa.videorental.service.MovieRepository;
import de.gmorling.moapa.videorental.service.MovieRepositoryMockImpl;

public class VideoRentalPort implements VideoRentalPortType {

	private MovieRepository movieRepository = new MovieRepositoryMockImpl();

	public GetMovieByIdResponse getMovieById(GetMovieByIdRequest request) {

		GetMovieByIdResponse response = GetMovieByIdResponse.Factory.newInstance();

		de.gmorling.moapa.videorental.domain.Movie movie = movieRepository.getMovieById(request.getId());

		if(movie != null) {
			response.setMovie(convert(movie));
		}		

		return response;
	}
	
	public FindMoviesByDirectorResponse findMoviesByDirector(FindMoviesByDirectorRequest request) {

		FindMoviesByDirectorResponse response = FindMoviesByDirectorResponse.Factory.newInstance();
		
		List<de.gmorling.moapa.videorental.domain.Movie> movies = movieRepository.findMoviesByDirector(request.getDirector());
		List<Movie> convertedMovies = new ArrayList<Movie>(movies.size());
		for (de.gmorling.moapa.videorental.domain.Movie oneMovie : movies) {
			convertedMovies.add(convert(oneMovie));
		}
		
		response.setMovieArray(convertedMovies.toArray(new Movie[movies.size()]));
		
		return response;
	}

	private Movie convert(de.gmorling.moapa.videorental.domain.Movie movie) {

		Movie theValue = Movie.Factory.newInstance();
		
		theValue.setId(movie.getId());
		theValue.setTitle(movie.getTitle());
		theValue.setRunTime(movie.getRunTime());
		
		if(movie.getDirector() != null) {
			theValue.setDirector(movie.getDirector());
		}
		
		if(movie.getReleaseDate() != null) {

			GregorianCalendar releaseDate = new GregorianCalendar();
			releaseDate.setTime(movie.getReleaseDate());
			theValue.setReleaseDate(releaseDate);
		}
		
		return theValue;
	}
}
