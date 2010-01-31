package de.gmorling.moapa.videorental.ws.xmlbeans;

import java.util.GregorianCalendar;

import xmlbeans.Movie;
import xmlbeans.GetMovieByIdRequestDocument.GetMovieByIdRequest;
import xmlbeans.GetMovieByIdResponseDocument.GetMovieByIdResponse;
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
